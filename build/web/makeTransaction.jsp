<%-- 
    Document   : makeATransaction
    Created on : Dec 23, 2020, 9:59:45 AM
    Author     : fsam5
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Making A Transaction - Banking System</title>
        
    </head>
    <body>
         <%
            int customerID = Integer.parseInt(request.getSession().getAttribute("sessionCustomerID").toString());
        %>
            <div class="m-auto text-center mt-5" style="margin: 10px 0 0 10px; width: 400px">
        
            <form action="maketrans" method="post"class="mt-5 border px-4 py-5" style="padding: 10px;">
                 From Account: <p class="text-primary"> <%= customerID %></p>
                <table class="w-100">
                    <tbody>
                        <tr>

                        </tr>
                         <tr>
                            <td><input type="number" placeholder="To Account Number:"
                                name="toaccount" class="form-control mt-2" /> <span id="usernameErrorMsg" class="text-danger" required=""></span></td>
                        </tr>
                        <tr></tr>
                        <tr>
                            <td><input type="number" placeholder="Money Amount" name="amount"
                                class="form-control mt-2" /> <span id="nameErrorMsg"
                                class="text-danger"></span></td>
                        </tr>
                        <tr></tr>

                    </tbody>
                    
                <tr></tr>
            </table><br><br>
            <br> <input type="submit" class="btn btn-primary" value="Confirm Transaction">

        </form>
                <br><a href="transaction.jsp"><button class="btn btn-success">Back</button></a>
    </div>
    </body>
</html>
