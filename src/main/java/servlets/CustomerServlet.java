package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import crud.service.CrudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Customer;

/**
 * Servlet implementation class CustomerServlet
 */
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CrudService<Customer> customerService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        customerService = new CrudService<>(Customer.class); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customers = customerService.getAll();
        
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head><title>Customer List</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }");
        out.println("h1 { color: #333; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 10px; text-align: center; border: 1px solid #ddd; }");
        out.println("th { background-color: #295F98; color: #EAE4DD; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("tr:hover { background-color: #ddd; }");
        out.println("a { display: inline-block; margin-top: 20px; padding: 10px 15px; background-color: #295F98; color: #EAE4DD; text-decoration: none; border-radius: 5px; }");
        out.println("a:hover { background-color: #EAE4DD;color:#295F98; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Customer List</h1>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th></tr>");

        for (Customer customer : customers) {
            out.println("<tr>");
            out.println("<td>" + customer.getCustomerId() + "</td>");
            out.println("<td>" + customer.getName() + "</td>");
            out.println("<td>" + customer.getEmail() + "</td>");
            out.println("<td>" + customer.getPhone() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='addCustomer.html'>Add New Customer</a>"); 
        out.println("</body>");
        out.println("</html>");

        out.close(); 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Customer customer = new Customer(id, name, email, phone);
        customerService.add(customer); 

        response.sendRedirect("customers"); 
    }
}
