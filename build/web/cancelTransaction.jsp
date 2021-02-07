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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="https://www.google.com/recaptcha/api.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js"></script>
        
        <title>Cancel A Transaction - Banking System</title>
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
        <h1 style="text-align:center;">Transactions Customer: <p class="text-primary"> <%= customerID %></p></h1>
        <h4 style="text-align:center; color: red">Click On Bank Transaction ID To Cancel transaction</h4>
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
        
        <table border="1" class='center'>
            <tr>
                <th>Bank Transaction ID</th> 
                <th>Transaction Date</th> 
                <th>Transaction Amount</th> 
                <th>From</th>
                <th>To</th>
                <th>Status</th>
            </tr>
            <% while(RS.next()) {%>
            <tr>
                <td><a href="cancelTransaction?id=<%=RS.getString("banktransactionid")%>"><%=RS.getString("banktransactionid")%></a></td>
                <td><%=RS.getString("btcreationdate")%></td>
                <td><%=RS.getString("btcurrentamount")%></td>
                <td><%=RS.getString("btfromaccount")%></td>
                <td><%=RS.getString("bttoaccount")%></td>
                <td><%=RS.getString("status")%></td>
                
            </tr>
            
            <%}%>
        </table>
        <br>
        <div style="margin: auto; width: 50%; text-align: center">
      <a href="transaction.jsp"><button class = "btn btn-success btn-lg">Back</button></a>
        </div>
    </body>
</html>
