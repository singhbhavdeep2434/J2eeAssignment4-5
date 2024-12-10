package crud.service;

import com.google.gson.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrudService1<T> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String filePath;
    private final Class<T> type;

    public CrudService1(Class<T> type) {
        this.type = type;
    }

    private String getFilePath() {
        return type.getSimpleName() + ".json";
    }

    // Retrieve all items from the JSON file
    public List<T> getAll() throws IOException {
        filePath = getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = com.google.gson.reflect.TypeToken.getParameterized(List.class, type).getType();
            return gson.fromJson(reader, listType);
        }
    }

    // Retrieve a single item by ID
    public Optional<T> getById(String id) throws IOException {
        return getAll().stream()
                .filter(item -> {
                    JsonObject jsonObject = gson.toJsonTree(item).getAsJsonObject();
                    return jsonObject.has("id") && id.equals(jsonObject.get("id").getAsString());
                })
                .findFirst();
    }

    // Save the entire list to the file
    private void saveAll(List<T> objectList) throws IOException {
        try (FileWriter writer = new FileWriter(getFilePath())) {
            gson.toJson(objectList, writer);
        }
    }

    // Add a new item
    public void add(T obj) throws IOException {
        List<T> objectList = getAll();
        objectList.add(obj);
        saveAll(objectList);
    }

    // Update an item by ID
    public void update(String id, T updatedObject) throws IOException {
        List<T> objectList = getAll();
        objectList = objectList.stream().map(item -> {
            JsonObject jsonObject = gson.toJsonTree(item).getAsJsonObject();
            if (jsonObject.has("id") && jsonObject.get("id").getAsString().equals(id)) {
                return updatedObject;
            }
            return item;
        }).collect(Collectors.toList());
        saveAll(objectList);
    }

    // Delete an item by ID
    public void delete(String id) throws IOException {
        List<T> objectList = getAll();
        objectList.removeIf(item -> {
            JsonObject jsonObject = gson.toJsonTree(item).getAsJsonObject();
            return jsonObject.has("id") && jsonObject.get("id").getAsString().equals(id);
        });
        saveAll(objectList);
    }
}
