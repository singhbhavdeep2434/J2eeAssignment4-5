<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Claim" %>
<%@ page import="java.util.Optional" %>
<%@ page import="crud.service.CrudService1" %>

<%
    String id = request.getParameter("id");
    CrudService1<Claim> claimService = new CrudService1<>(Claim.class);
    Optional<Claim> claimOpt = claimService.getById(id);
    Claim claim = claimOpt.isPresent() ? claimOpt.get() : new Claim();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Update Claim</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #333;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin-bottom: 10px;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #295F98;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #1a3a6d;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #295F98;
        }
    </style>
</head>
<body>
    <h1>Update Claim</h1>
    <form action="updateClaim" method="post">
        <input type="hidden" name="id" value="<%= claim.getId() %>" />
        <label for="policyId">Policy ID:</label>
        <input type="text" name="policyId" id="policyId" value="<%= claim.getPolicyId() %>" required />
        
        <label for="description">Description:</label>
        <input type="text" name="description" id="description" value="<%= claim.getDescription() %>" required />
        
        <label for="amount">Amount:</label>
        <input type="number" name="amount" id="amount" value="<%= claim.getAmount() %>" required />
        
        <button type="submit">Update Claim</button>
    </form>
    <a href="claims">Back to Claim List</a>
</body>
</html>
