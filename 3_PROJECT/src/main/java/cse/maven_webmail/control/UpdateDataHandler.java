/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import cse.maven_webmail.model.UserAdminAgent;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jiwon
 */
public class UpdateDataHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String userid = request.getParameter("view_id");
            String Tel = request.getParameter("view_tel");

            // System.out.println("TEST >>" + userid);
            // System.out.println("TEST >>" + Tel);
            if (!request.getParameter("pw").equals("")) {
                if (request.getParameter("pw").equals(request.getParameter("chkpw"))) {
                    if (UpdatePW(request, response, out, request.getParameter("view_id"), request.getParameter("pw")) == 1) {
                        out.println(UpdateSuccessandlogoutPopUp());
                    }
                } else {
                    out.println(chkpwFailedPopUp());
                }
            } else {
                if (request.getParameter("view_tel").equals("")) {
                    out.println(chktelFailedPopUp());
                } else {
                    if (updateTel(userid, Tel) == 1) {
                        out.println(UpdateSuccessPopUp());
                    }
                }
            }
        }
    }

    private int updateTel(String userid, String tel) {
        final String JdbcDriver = "com.mysql.cj.jdbc.Driver";

        final String JdbcUrl = "jdbc:mysql://35.184.209.113:11269/ood?autoReconnect=true&serverTimezone=Asia/Seoul&useSSL=false";
        final String User = "ooduser";
        final String Password = "ooduser";

        try {
            Class.forName(JdbcDriver);
            Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);

            String sql = "UPDATE users SET tel='" + tel + "' WHERE username='" + userid + "'";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return 1;
    }

    private String UpdateSuccessPopUp() {
        String alertMessage = "정보가 수정되었습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>회원정보 수정 - 수정 완료</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMain()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMain() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"setting_user_info.jsp\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }

    private String UpdateSuccessandlogoutPopUp() {
        String alertMessage = "정보가 수정되었습니다. 비밀번호 수정으로 로그아웃 됩니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>회원정보 수정 - 수정 완료</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMain()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMain() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"index.jsp\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }

    private String chkpwFailedPopUp() {
        String alertMessage = "입력한 비밀번호가 일치하지 않습니다.";
        StringBuilder failedPopUp = new StringBuilder();
        failedPopUp.append("<html>");
        failedPopUp.append("<head>");

        failedPopUp.append("<title>회원정보 수정 - 수정 실패</title>");
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

    private String chktelFailedPopUp() {
        String alertMessage = "전화번호를 입력해주세요.";
        StringBuilder failedPopUp = new StringBuilder();
        failedPopUp.append("<html>");
        failedPopUp.append("<head>");

        failedPopUp.append("<title>회원정보 수정 - 수정 실패</title>");
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

    private int UpdatePW(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String id, String pw) {
        String server = "127.0.0.1";
        int port = 4555;

        try {
            UserAdminAgent agent = new UserAdminAgent(server, port, this.getServletContext().getRealPath("."));
            if (agent.UpdateUserPW(id, pw)) {
                return 1;
            }
        } catch (Exception ex) {
            Logger.getLogger(FindpwHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
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
