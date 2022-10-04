/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import cse.maven_webmail.model.TrashTransperDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bhi84
 */
public class BanHandler extends HttpServlet {
    String banUser ;
    private DataSource dataSource;
    String userId;
    private static final Logger logger = LoggerFactory.getLogger(BanHandler.class);
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BanHandler() {
        try{
            //Class.forName(driver);
            Context context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/JamesWebmail");
        } catch(Exception e){
                e.printStackTrace();
        }
    }
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
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        int selected_menu = Integer.parseInt(request.getParameter("menu"));
        try {
            switch (selected_menu) {
                case CommandType.BAN_USER:
                    banU(request, response);
                    break;
                
                default:
                try (PrintWriter out = response.getWriter()) {
                    logger.info("없는 메뉴");

                }
                break;
            }
        }catch (Exception ex) {
            logger.error("휴지통 메뉴 에러"+ex.getMessage());
        }
    }
    
    private void banU(HttpServletRequest request, HttpServletResponse response) throws IOException {
         banUser = request.getParameter("banUser");
         userId = request.getParameter("userId");
        if (bansql()) {
            response.sendRedirect("main_menu.jsp");
        } else {
            try (PrintWriter out = response.getWriter()) {
                logger.info("차단 오류 발생");
            }
        }
    }
    
    private boolean bansql() {
        boolean status;
        String sql1 = "insert into bans select ?,? from dual where  not exists (select * from bans where banID = ? and registId=?)";
        Connection con = null;
        try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement restoreStatement = con.prepareStatement(sql1); 
              
                restoreStatement.setString(1, banUser);
                restoreStatement.setString(2, userId);
                restoreStatement.setString(3, banUser);
                restoreStatement.setString(4, userId);
                restoreStatement.executeUpdate();
                status= true;
            }catch (Exception e) {
                e.printStackTrace();
                status= false;
            } 
        return status;
    }
    
        private Connection getConnection() throws NamingException, SQLException {
        String name = "java:/comp/env/jdbc/JamesWebmail";
        javax.naming.Context context = new javax.naming.InitialContext();
        javax.sql.DataSource dataSource = (javax.sql.DataSource) context.lookup(name);
        return dataSource.getConnection();
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
