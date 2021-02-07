import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;


public class cancelTransaction extends HttpServlet {

    public static int findDifference(LocalDate start_date, LocalDate end_date) {
        Period diff = Period.between(start_date, end_date);
        return (Math.abs(diff.getDays()));
    }

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection Con = null;
        Statement stmt = null;
        ResultSet RS = null;
        PreparedStatement preparedStatement = null;

        LocalDate currentdate = LocalDate.now();
        int currentDay = currentdate.getDayOfMonth();
        int currentMonth = currentdate.getMonthValue();
        int currentYear = currentdate.getYear();

        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/customer";
            String user = "root";
            String password = "";
            String value;
            Con = DriverManager.getConnection(url, user, password);
            int ID = Integer.parseInt(request.getParameter("id"));

            String sql = String.format("SELECT * FROM banktransaction WHERE banktransactionid = %d ; ", ID);
            stmt = Con.createStatement();
            RS = stmt.executeQuery(sql);
            

                while (RS.next()) {
                    if (RS.getString("status").equals("Cancelled")) {
                        response.sendRedirect("CancelledTransaction.jsp");
                    } else {

                    String transDate = RS.getString("btcreationdate");
                    int transactionDay = Integer.parseInt(new StringBuilder().append(transDate.charAt(0)).append(transDate.charAt(1)).toString());
                    int transactionMonth = Integer.parseInt(new StringBuilder().append(transDate.charAt(3)).append(transDate.charAt(4)).toString());
                    int transactionYear = Integer.parseInt(new StringBuilder().append(transDate.charAt(6)).append(transDate.charAt(7)).append(transDate.charAt(8)).append(transDate.charAt(9)).toString());

                    LocalDate start_date = LocalDate.of(currentYear, currentMonth, currentDay);

                    // End date
                    LocalDate end_date = LocalDate.of(transactionYear, transactionMonth, transactionDay);

                    int difference = findDifference(start_date, end_date);

                    if (difference == 1 || difference == 0) {
                        int transactionAmount = Integer.parseInt(RS.getString("btcurrentamount"));
                        int fromAccount = Integer.parseInt(RS.getString("btfromaccount"));
                        int toAccount = Integer.parseInt(RS.getString("bttoaccount"));
                        //String Status = RS.getString("status");

                        String query2 = String.format("SELECT bacurrentbalance FROM bankaccount WHERE customerid = %d ; ", fromAccount);
                        stmt = Con.createStatement();
                        RS = stmt.executeQuery(query2);
                        while (RS.next()) {
                            int balance = Integer.parseInt(RS.getString("bacurrentbalance"));
                            int total = balance + transactionAmount;
                            String aQuery = "UPDATE bankaccount SET bacurrentbalance = '" + total + "' WHERE customerid = '" + fromAccount + "';";
                            preparedStatement = Con.prepareStatement(aQuery);
                            preparedStatement.executeUpdate(aQuery);
                        }

                        String query3 = String.format("SELECT bacurrentbalance FROM bankaccount WHERE customerid = %d ; ", toAccount);
                        stmt = Con.createStatement();
                        RS = stmt.executeQuery(query3);
                        
                        while (RS.next()) {
                            value = RS.getString("bacurrentbalance");
                            int currentBalance = Integer.parseInt(value);                
                            int result = currentBalance - transactionAmount;
                            String anotherQuery = "UPDATE bankaccount SET bacurrentbalance = '" + result + "' WHERE customerid = '" + toAccount + "';";
                            preparedStatement = Con.prepareStatement(anotherQuery);
                            preparedStatement.executeUpdate(anotherQuery);
                        }

                        String query4 = "UPDATE banktransaction SET status = 'Cancelled' WHERE banktransactionid = " + ID + ";";
                        preparedStatement = Con.prepareStatement(query4);
                        preparedStatement.executeUpdate(query4);

                        response.sendRedirect("cancelTransaction.jsp");
                    } else {
                        response.sendRedirect("FailedCancellation.jsp");
                    }
                }
            }

            Con.close();
            preparedStatement.close();
            RS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
