package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import crud.service.CrudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Policy;

/**
 * Servlet implementation class PolicyServlet
 */
@WebServlet("/policies")
public class PolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CrudService<Policy> policyService; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolicyServlet() {
        super();
        policyService = new CrudService<>(Policy.class); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Policy> policies = policyService.getAll();
        
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head><title>Policy List</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }");
        out.println("h1 { color: #333; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }");
        out.println("th { background-color: #295F98; color: #EAE4DD; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("tr:hover { background-color: #ddd; }");
        out.println("a { display: inline-block; margin-top: 20px; padding: 10px 15px; background-color: #295F98; color: #EAE4DD; text-decoration: none; border-radius: 5px; }");
        out.println("a:hover { background-color: #EAE4DD; color:#295F98; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Policy List</h1>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Customer ID</th><th>Type</th><th>Premium</th></tr>");
        
        
        for (Policy policy : policies) {
            out.println("<tr>");
            out.println("<td>" + policy.getId() + "</td>");
            out.println("<td>" + policy.getCustomerId() + "</td>");
            out.println("<td>" + policy.getType() + "</td>");
            out.println("<td>" + policy.getPremium() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='addPolicy.html'>Add New Policy</a>"); 
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to add a new policy
        String id = request.getParameter("id");
        String customerId = request.getParameter("customerId");
        String type = request.getParameter("type");
        double premium = Double.parseDouble(request.getParameter("premium"));

        Policy policy = new Policy(id, customerId, type, premium);
        policyService.add(policy); 

        response.sendRedirect("policies");
    }
}
