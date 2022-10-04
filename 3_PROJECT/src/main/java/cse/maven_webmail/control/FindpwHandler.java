package cse.maven_webmail.control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cse.maven_webmail.model.UserAdminAgent;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class FindpwHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String id = request.getParameter("id");
            String Tel = request.getParameter("tel");

            // System.out.println("TEST >>" + Tel);
            int result = findpw(id, Tel);

            if (result == 1) {
                String tmp = PWGenerator(); // 비밀번호 임의 생성

                // 임의 생성된 비밀번호 DB에 갱신
                if (UpdateTempPW(request, response, out, id, tmp) == 1) {
                    // 비밀번호 팝업창 생성
                    PrintWriter p = response.getWriter();
                    p.println(findSuccessPopUp(tmp));
                }
            } else {
                PrintWriter p = response.getWriter();
                p.println(findFailPopUp());
            }
        }
    }

    private int findpw(String id, String tel) {
        final String JdbcDriver = "com.mysql.cj.jdbc.Driver";

        final String JdbcUrl = "jdbc:mysql://35.184.209.113:11269/ood?autoReconnect=true&serverTimezone=Asia/Seoul&useSSL=false";
        final String User = "ooduser";
        final String Password = "ooduser";

        int result = 0;

        try {
            Class.forName(JdbcDriver);
            Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);

            String sql = "select EXISTS (select * from users where username=\"" + id + "\" and tel=\"" + tel + "\") as res";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result = Integer.parseInt(rs.getString("res"));
            }

            pstmt.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    private String findSuccessPopUp(String tmp) {
        String alertMessage = "비밀번호가 " + tmp + "로 초기화 되었습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>비밀번호 찾기 - 조회 결과</title>");
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

    private String findFailPopUp() {
        String alertMessage = "일치하는 정보가 없습니다.";
        StringBuilder failedPopUp = new StringBuilder();
        failedPopUp.append("<html>");
        failedPopUp.append("<head>");

        failedPopUp.append("<title>비밀번호 찾기 - 조회 결과</title>");
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

    private String PWGenerator() {
        int Fixnum = 8;
        if (Fixnum > 0) {
            char[] tmp = new char[Fixnum];
            for (int i = 0; i < tmp.length; i++) {
                int div = (int) Math.floor(Math.random() * 2);

                if (div == 0) { // 0이면 숫자로
                    tmp[i] = (char) (Math.random() * 10 + '0');
                } else { //1이면 알파벳
                    tmp[i] = (char) (Math.random() * 26 + 'a');
                }
            }
            return new String(tmp);
        }
        return "1";
    }

    private int UpdateTempPW(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String id, String tmp) {
        String server = "127.0.0.1";
        int port = 4555;

        try {
            UserAdminAgent agent = new UserAdminAgent(server, port, this.getServletContext().getRealPath("."));
            if (agent.UpdateUserPW(id, tmp)) {
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
