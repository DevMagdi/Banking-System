<%-- 
    Document   : viewTransactions
    Created on : Dec 22, 2020, 10:45:58 PM
    Author     : fsam5
--%>


<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>List of Transactions - Banking System</title>
        <style>
            .center {
                margin-left: auto;
                margin-right: auto;
            }
        </style>
    </head>
    <body>
        
        <%
            int customerID = Integer.parseInt(request.getSession().getAttribute("sessionCustomerID").toString());
        %>
        <h1 style="text-align:center;">Transactions For Customer: <span style="color: blue;"> <%= customerID %></span></h1>
       <%
            String url = "jdbc:mysql://localhost:3306/customer";
            String user = "root";
            String password = "";
            Connection Con = null;
            String query;
            Statement Stmt = null;
            ResultSet RS = null;
            
            try {
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();

                query = "SELECT * FROM banktransaction WHERE btfromaccount = '" + customerID + "';";
                
                RS = Stmt.executeQuery(query);
            } catch (Exception cnfe) {
                System.err.println("Exception: " + cnfe);
            }
        %>
        
        <table border="1" class='center' class="table">
            <tbody class="thead-dark">
                <tr>
                <th scope="col">Bank Transaction ID</th> 
                <th scope="col">Transaction Date</th> 
                <th scope="col">Transaction Amount</th> 
                <th scope="col">From</th>
                <th scope="col">To</th>
                <th scope="col">Status</th>
            </tr>
            <% while(RS.next()) {%>
            <tr scope="row">
                <td><%=RS.getString("banktransactionid")%></td>
                <td><%=RS.getString("btcreationdate")%></td>
                <td><%=RS.getString("btcurrentamount")%></td>
                <td><%=RS.getString("btfromaccount")%></td>
                <td><%=RS.getString("bttoaccount")%></td>
                <td><%=RS.getString("status")%></td>
            </tr>
            
            <%}%>
            </tbody>
        </table>
        
        <br>
        <div style="margin: auto; width: 70%; text-align: center">
        <a href="transaction.jsp"><button style='cursor: pointer' class = "btn btn-success btn-lg">Back</button></a>
        </div>
    </body>
</html>
