package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import crud.service.CrudService1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Claim;

@WebServlet("/claims")
public class ClaimServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CrudService1<Claim> claimService;

    public ClaimServlet() {
        super();
        claimService = new CrudService1<>(Claim.class); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if ("update".equals(action)) {
            // Show the update claim form
            Optional<Claim> claimOpt = claimService.getById(id);
            if (claimOpt.isPresent()) {
                request.setAttribute("claim", claimOpt.get());
                request.getRequestDispatcher("updateClaim.jsp").forward(request, response);
            } else {
                response.sendRedirect("claims"); // Claim not found, redirect to claim list
            }
        } else {
            // Show the claim list
            List<Claim> claims = claimService.getAll(); 
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
            out.println("<head><title>Claim List</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }");
            out.println("h1 { color: #333; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }");
            out.println("th { background-color: #295F98; color: #EAE4DD; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #ddd; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Claim List</h1>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Policy ID</th><th>Description</th><th>Amount</th><th>Status</th><th>Actions</th></tr>");
            
            for (Claim claim : claims) {
                out.println("<tr>");
                out.println("<td>" + claim.getId() + "</td>");
                out.println("<td>" + claim.getPolicyId() + "</td>");
                out.println("<td>" + claim.getDescription() + "</td>");
                out.println("<td>" + claim.getAmount() + "</td>");
                out.println("<td>" + claim.getStatus() + "</td>");
                out.println("<td><a href='claims?action=update&id=" + claim.getId() + "'>Update</a> | "
                        + "<a href='claims?action=approve&id=" + claim.getId() + "'>Approve</a> | "
                        + "<a href='claims?action=reject&id=" + claim.getId() + "'>Reject</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<a href='addClaim.html'>Add New Claim</a>");
            out.println("</body>");
            out.println("</html>");

            out.close(); 
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            // Creating a new claim
            String policyId = request.getParameter("policyId");
            String description = request.getParameter("description");
            String status = request.getParameter("status");
            double amount = Double.parseDouble(request.getParameter("amount"));
            
            Claim claim = new Claim(); 
            claim.setPolicyId(policyId);
            claim.setDescription(description);
            claim.setStatus(status);
            claim.setAmount(amount);
            claimService.add(claim);
        } else {
            // Updating an existing claim
            String policyId = request.getParameter("policyId");
            String description = request.getParameter("description");
            String status = request.getParameter("status");
            double amount = Double.parseDouble(request.getParameter("amount"));

            Claim claim = new Claim(); 
            claim.setId(id);
            claim.setPolicyId(policyId);
            claim.setDescription(description);
            claim.setStatus(status);
            claim.setAmount(amount);
            claimService.update(id, claim);
        }

        response.sendRedirect("claims");
    }

    protected void doApprove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Optional<Claim> claimOpt = claimService.getById(id);
        if (claimOpt.isPresent()) {
            Claim claim = claimOpt.get();
            claim.setStatus("Approved");
            claimService.update(id, claim);  
        }
        response.sendRedirect("claims");
    }

    protected void doReject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Optional<Claim> claimOpt = claimService.getById(id);
        if (claimOpt.isPresent()) {
            Claim claim = claimOpt.get();
            claim.setStatus("Rejected");
            claimService.update(id, claim); 
        }
        response.sendRedirect("claims");
    }
}
