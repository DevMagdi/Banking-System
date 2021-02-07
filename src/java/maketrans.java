import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.http.HttpSession;


public class maketrans extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection Con = null;
        ResultSet RS = null;
        Statement stmt = null;
        PreparedStatement preparedStatement = null;
        
        LocalDate currentdate = LocalDate.now();	      
        int currentDay = currentdate.getDayOfMonth();
	int currentMonth = currentdate.getMonthValue();	
	int currentYear = currentdate.getYear();
        String date = "";
        String day = "";
        String month = "";
        
        if(String.valueOf(currentDay).length() == 1){
            day = "0" + currentDay;
        }
        if(String.valueOf(currentMonth).length() == 1){
            month = "0" + currentMonth;
        }
        if(String.valueOf(currentMonth).length() > 1){
            month = String.valueOf(currentMonth);
        }
        if(String.valueOf(currentDay).length() > 1){
            day = String.valueOf(currentDay);
        }
        date = day + "-" + month + "-" + currentYear;
       
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/customer";
            String user = "root";
            String password = "";         
            String amountfrom;
            String status  = "Done";

            Con = DriverManager.getConnection(url, user, password);
            int fromaccount =  Integer.parseInt(request.getSession().getAttribute("sessionCustomerID").toString());
            int toaccount = Integer.parseInt(request.getParameter("toaccount"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            
            String line1 = String.format("SELECT * FROM bankaccount WHERE customerid = %d ", toaccount);
            stmt = Con.createStatement();
            RS = stmt.executeQuery(line1);

            if(RS.next()) {
                String line2 = String.format("SELECT bacurrentbalance FROM bankaccount WHERE customerid = %d ; ", fromaccount);
                stmt = Con.createStatement();
                RS = stmt.executeQuery(line2);
                while(RS.next()){
                     amountfrom = RS.getString("bacurrentbalance");
                     int currentBalance = Integer.parseInt(amountfrom);                
                     int newcurrentbalance = currentBalance - amount;
                     String line3 = "UPDATE bankaccount SET bacurrentbalance = " + newcurrentbalance + " WHERE customerid = " +  fromaccount + " ;";
                     preparedStatement = Con.prepareStatement(line3);
                     preparedStatement.executeUpdate(line3);
                }
                String BalanceAfterTransaction = String.format("SELECT bacurrentbalance FROM bankaccount WHERE customerid = %d ; ", toaccount);
                stmt = Con.createStatement();
                RS = stmt.executeQuery(BalanceAfterTransaction);
                if(RS.next()){
                    int newcurrentbalance_to_account = Integer.parseInt(RS.getString("bacurrentbalance"));
                    newcurrentbalance_to_account += amount;
                    String line4 = "UPDATE bankaccount SET bacurrentbalance = " + newcurrentbalance_to_account + " WHERE customerid = " +  toaccount + " ;";
                    preparedStatement = Con.prepareStatement(line4);
                    preparedStatement.executeUpdate(line4);  
                    
                }
                else{
                    String line4 = "UPDATE bankaccount SET bacurrentbalance = " + amount + " WHERE customerid = " +  toaccount + " ;";
                    preparedStatement = Con.prepareStatement(line4);
                    preparedStatement.executeUpdate(line4);
                    
                    
                }
                String line5 = "SELECT * FROM banktransaction;";
                stmt = Con.createStatement();
                RS = stmt.executeQuery(line5);
                if(RS.next()){
                    
                    String line6 = "SELECT MAX(banktransactionid) AS 'MAX ID TRansaction' FROM banktransaction; ";
                    stmt = Con.createStatement();
                    RS = stmt.executeQuery(line6);     
                    while(RS.next()){
                        int MAXIDTRansaction = RS.getInt("MAX ID TRansaction");
                        int transID = MAXIDTRansaction + 1;
                        String SaveTransaction = "INSERT INTO banktransaction(banktransactionid, btcreationdate, btcurrentamount, btfromaccount, bttoaccount, status) VALUES("+ transID + ","+ "'" + date + "',"+ "'" + amount + "'," + "'" + fromaccount + "'," + "'" + toaccount + "'," + "'" + status + "');" ;
                        preparedStatement = Con.prepareStatement(SaveTransaction);
                       preparedStatement.executeUpdate(SaveTransaction);  
                      }   
                        response.sendRedirect("customerhome.jsp");
                }
                else{
                    String SaveTransaction = "INSERT INTO banktransaction(banktransactionid, btcreationdate, btcurrentamount,btfromaccount, bttoaccount,status) VALUES("+ 1 + ","+ "'" + date + "',"+ "'" + amount + "'," + "'" + fromaccount + "'," + "'" + toaccount + "'," + "'" + status + "');" ;
                    preparedStatement = Con.prepareStatement(SaveTransaction);
                    preparedStatement.executeUpdate(SaveTransaction);
                    response.sendRedirect("customerhome.jsp");
                }
            
                
            }
            else{
                HttpSession session = request.getSession(true);
                session.setAttribute("toAccountSession", toaccount);
                response.sendRedirect("accountDoesntExist.jsp");
            }
            

            
            Con.close();
            stmt.close();
            preparedStatement.close();
            RS.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
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
