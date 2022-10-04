/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindidHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String Tel = request.getParameter("tel");

            // System.out.println("TEST >>" + Tel);
            String result = findid(Tel);

            if (!result.equals("")) {
                PrintWriter p = response.getWriter();
                p.println(findSuccessPopUp(result));
            } else {
                PrintWriter p = response.getWriter();
                p.println(findFailPopUp());
            }
        }
    }

    private String findid(String tel) {
        final String JdbcDriver = "com.mysql.cj.jdbc.Driver";

        final String JdbcUrl = "jdbc:mysql://35.184.209.113:11269/ood?autoReconnect=true&serverTimezone=Asia/Seoul&useSSL=false";
        final String User = "ooduser";
        final String Password = "ooduser";
        
        String result = "";

        try {
            Class.forName(JdbcDriver);
            Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);

            String sql = "SELECT username FROM users WHERE tel='" + tel + "'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result = rs.getString("username");
            }

            pstmt.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    private String findSuccessPopUp(String userid) {
        String alertMessage = "아이디는 " + userid + "입니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>아이디 찾기 - 조회 결과</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMain()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMain() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.history.back();");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }
    
        private String findFailPopUp() {
        String alertMessage = "조회된 정보가 없습니다.";
        StringBuilder failedPopUp = new StringBuilder();
        failedPopUp.append("<html>");
        failedPopUp.append("<head>");

        failedPopUp.append("<title>아이디 찾기 - 조회 결과</title>");
        failedPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        failedPopUp.append("</head>");
        failedPopUp.append("<body onload=\"goMain()\">");
        failedPopUp.append("<script type=\"text/javascript\">");
        failedPopUp.append("function goMain() {");
        failedPopUp.append("alert(\"");
        failedPopUp.append(alertMessage);
        failedPopUp.append("\"); ");
        failedPopUp.append("window.history.back();");
        failedPopUp.append("}  </script>");
        failedPopUp.append("</body></html>");
        return failedPopUp.toString();
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
