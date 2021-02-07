<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bank Account</title>
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity=
              "sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        
    </head>

    <body>
         <div class="m-auto text-center mt-5" style="margin: 10px 0 0 10px; width: 400px">
        <h1>Bank Account</h1>
        <%
            int customerID = Integer.parseInt(request.getSession().getAttribute("sessionCustomerID").toString());
        %>
        <%
            String dbUrl = "jdbc:mysql://localhost:3306/customer";
            String dbUser = "root";
            String dbPassword = "";
            String query;
            Connection Con = null;
            Statement Stmt = null;
            ResultSet RS = null;

            try {
                Con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                Stmt = Con.createStatement();
                query = String.format("SELECT * FROM bankaccount WHERE CustomerID = %d ;", customerID);
                RS = Stmt.executeQuery(query);
                //RS1 = Stmt.executeQuery("SELECT *  FROM login;");String id1= RS1.getString("id");
                
            } catch (Exception cnfe) {
                System.err.println("Exception: " + cnfe);
            }
            
           
        %>
        
        <%  if(RS.next()) { %>
                
                <% out.print("Your Current Balance Is: "); %>
               
                <font color="blue"><%= RS.getString("bacurrentbalance")%></font>        
                
                <button disabled>Add Account</button>
                <br><br>
                <a href='transaction.jsp'<button class = "btn btn-success btn-lg">Transaction Page</button></a>
               
            
         <%} else { %>
         
         <font color="blue">Click on down Button to create new bank account </font><br><br>
         <br><a href="addaccount"><button class = "btn btn-success btn-lg">Add Account</button></a>
         <%}%>
         </div>    
    </body>
</html>
