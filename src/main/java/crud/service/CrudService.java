package crud.service;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrudService<T> {

    private String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Class<T> type; 

    public CrudService(Class<T> type) {
        this.type = type; 
    }

    public String getFilePath() {
        this.filePath = type.getSimpleName() + ".json";
        return this.filePath;
    }

    public List<T> getAll() throws IOException {
        filePath = getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            List<T> objectList = new ArrayList<>();

            for (JsonElement jsonElement : jsonArray) {
                objectList.add(gson.fromJson(jsonElement, type));
            }

            return objectList;
        }
    }

    public T getById(String id) throws IOException {
        return getAll().stream()
                .filter(item -> {
                    if (item instanceof Map) {
                        Map<String, Object> map = (Map<String, Object>) item;
                        return map.containsKey("id") && id.equals(map.get("id").toString());
                    }
                    return false;
                })
                .findFirst()
                .orElse(null);
    }

    public void saveAll(List<T> objectList) throws IOException {
        JsonArray jsonArray = new JsonArray();
        objectList.forEach(item -> jsonArray.add(gson.toJsonTree(item)));

        this.filePath = getFilePath();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(jsonArray, writer);
        }
    }

    public void add(T obj) throws IOException {
        List<T> objectList = getAll();
        objectList.add(obj);
        saveAll(objectList);
    }

    public void update(String id, T updatedObject) throws IOException {
        List<T> objectList = getAll();

        objectList = objectList.stream().map(item -> {
            if (item instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) item;
                if (map.containsKey("id") && map.get("id").toString().equals(id)) {
                    return updatedObject; // Replace the object
                }
            }
            return item; 
        }).collect(Collectors.toList());

        saveAll(objectList);
    }

    public void delete(String id) throws IOException {
        List<T> objectList = getAll();

        objectList.removeIf(item -> {
            if (item instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) item;
                return map.containsKey("id") && map.get("id").toString().equals(id);
            }
            return false;
        });

        saveAll(objectList);
    }
}
