/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author access
 */
public class addaccount extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection Con = null;
        Statement Stmt = null;
        ResultSet RS = null;
        PreparedStatement preparedStatement = null;
        int customerID = Integer.parseInt(request.getSession().getAttribute("sessionCustomerID").toString());
        LocalDate currentdate = LocalDate.now();	      
        int currentDay = currentdate.getDayOfMonth();
	int currentMonth = currentdate.getMonthValue();	
	int currentYear = currentdate.getYear();
        String date = currentDay + "-" + currentMonth + "-" + currentYear;
	
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/customer";
            String user = "root";
            String password = "";
            

            Con = DriverManager.getConnection(url, user, password);
            
            int random_id = (int)(Math.random() * (2000 - 1000 + 1) + 1000);
            Stmt = Con.createStatement();
            String anotherQuery = "INSERT INTO bankaccount(baaccountid, bacreationdate, bacurrentbalance, customerid) VALUES("+ random_id + ","+ "'" + date + "',"+ "'" + 1000 + "'," + customerID + ")";
            int Rows = Stmt.executeUpdate(anotherQuery);
            response.sendRedirect("customerhome.jsp");
            Con.close();
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

