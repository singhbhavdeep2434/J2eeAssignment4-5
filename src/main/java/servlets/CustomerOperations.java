package servlets;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import crud.service.CrudService1;

/**
 * Servlet implementation class CustomerOperations
 */
public class CustomerOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	


	public CustomerOperations() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		Customer c1 = new Customer("1", "Sourav", "email", "987654321");
		CrudService1 crudService = new CrudService1();

		try {
			crudService.add(c1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CrudService1 crudService = new CrudService1();
		response.setContentType("text/html");
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		List<Object> list = (List<Object>) crudService.getAll1(new Customer());

		// Print the list of rides
		out.println("<html><body>");
		out.println("<h2>List of Customer:</h2>");
		for (Object ride : list) {
			out.println("<p>" + ride.toString() + "</p>");
		}
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
