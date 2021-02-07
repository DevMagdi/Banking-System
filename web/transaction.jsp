<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity=
              "sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <title>Transaction</title>
    </head>

    <body>
       
        <div  class="m-auto text-center mt-5" style="margin: 100px 50px 30px 700px; width: 100px" class="btn btn-primary">
            
            <a href="makeTransaction.jsp"><button class="btn btn-primary">Make transaction </button></a>
        <br>
        <br>
        <a href="viewTransaction.jsp"><button class="btn btn-primary">View transactions </button></a>
        <br>
        <br>
        <a href="cancelTransaction.jsp"><button class="btn btn-danger">Cancel transaction</button></a>
        <br>
        <br>
        <a href="customerhome.jsp"><button class = "btn btn-success btn-lg">Back</button></a>
        </div>
    </body>
</html>