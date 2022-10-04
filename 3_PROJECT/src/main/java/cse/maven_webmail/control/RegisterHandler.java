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
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jiwon
 */
public class RegisterHandler extends HttpServlet {

            int ischk = -2;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // 요청이 "중복확인"의 버튼으로 들어오면, 아래 코드를 실행
        String req = request.getParameter("reg");

        String result = ""; // result에서 값 여부를 확인

        if (req.equals("중복확인")) {
            // System.out.println("중복확인을 진행합니다." + req); // TC

            final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
            final String JdbcUrl = "jdbc:mysql://35.184.209.113:11269/ood?autoReconnect=true&serverTimezone=Asia/Seoul&useSSL=false";
            final String User = "ooduser";
            final String Password = "ooduser";

            try {
                Class.forName(JdbcDriver);
                Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);

                String sql = "select EXISTS (select * from users where username=\"" + request.getParameter("id") + "\") as res";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    result = rs.getString("res");
                }

                pstmt.close();
                conn.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            if (result.equals("0")) {
                //System.out.println("사용가능한 아이디입니다. >> " + result + request.getParameter("id"));
                PrintWriter p = response.getWriter();
                p.println(getUserchkidSuccessPopUp());
            } else {
                // System.out.println("중복된 아이디입니다. >> " + result + request.getParameter("id"));
                PrintWriter p = response.getWriter();
                p.println(getUserchkidFailurePopUp());
            }
        } else {
            if (ischk == -2) {
                PrintWriter p = response.getWriter();
                p.println(idchkidFailurePopUp());
            } else if (ischk == 1) {

                System.out.println("회원가입을 진행합니다." + req); // TC

                try (PrintWriter out = response.getWriter()) {
                    request.setCharacterEncoding("UTF-8");

                    // 사용자가 비밀번호 입력란과 재확인 입력란의 값 일치 여부를 확인
                    //String userid = request.getParameter("id");
                    String password = request.getParameter("password");
                    String passwordchk = request.getParameter("passwordcheck");
                    //String tel = request.getParameter("tel");

                    if (password.equals(passwordchk)) {
                        // 최종 실행(사용자 등록 : 4555)
                        addUser(request, response, out);

                    } else //System.out.println("비밀번호가 일치하지 않습니다!");
                    {
                        out.println(chkpwFailedPopUp());
                    }

                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            }
        }
    }

    private void addUserTel(String userid, String tel) {
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
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String server = "127.0.0.1";
        int port = 4555;
        try {
            UserAdminAgent agent = new UserAdminAgent(server, port, this.getServletContext().getRealPath("."));
            String userid = request.getParameter("id");  // for test
            String password = request.getParameter("password");// for test
            String tel = request.getParameter("tel");
            out.flush();
            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (agent.addUser(userid, password)) {
                out.println(getUserRegistrationSuccessPopUp());
                // 사용자 전화번호 등록
                addUserTel(userid, tel);
            } else {
                out.println(getUserRegistrationFailurePopUp());
            }
            out.flush();
        } catch (Exception ex) {
            out.println("시스템 접속에 실패했습니다.");
        }
    }

    private String getUserRegistrationSuccessPopUp() {
        String alertMessage = "사용자 등록이 성공했습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>사용자 등록</title>");
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

    private String getUserRegistrationFailurePopUp() {
        String alertMessage = "사용자 등록이 실패했습니다.";
        StringBuilder failurePopUp = new StringBuilder();
        failurePopUp.append("<html>");
        failurePopUp.append("<head>");

        failurePopUp.append("<title>사용자 등록</title>");
        failurePopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        failurePopUp.append("</head>");
        failurePopUp.append("<body onload=\"goMain()\">");
        failurePopUp.append("<script type=\"text/javascript\">");
        failurePopUp.append("function goMain() {");
        failurePopUp.append("alert(\"");
        failurePopUp.append(alertMessage);
        failurePopUp.append("\"); ");
        failurePopUp.append("window.location = \"index.jsp\"; ");
        failurePopUp.append("}  </script>");
        failurePopUp.append("</body></html>");
        return failurePopUp.toString();
    }

    private String getUserchkidSuccessPopUp() {
        String alertMessage = "사용가능한 아이디 입니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>회원가입 - 아이디 확인</title>");
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
        ischk = 1;
        return successPopUp.toString();
    }

    private String getUserchkidFailurePopUp() {
        String alertMessage = "사용할 수 없는 아이디입니다.";
        StringBuilder failurePopUp = new StringBuilder();
        failurePopUp.append("<html>");
        failurePopUp.append("<head>");

        failurePopUp.append("<title>회원가입 - 아이디 확인</title>");
        failurePopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        failurePopUp.append("</head>");
        failurePopUp.append("<body onload=\"goMain()\">");
        failurePopUp.append("<script type=\"text/javascript\">");
        failurePopUp.append("function goMain() {");
        failurePopUp.append("alert(\"");
        failurePopUp.append(alertMessage);
        failurePopUp.append("\"); ");
        failurePopUp.append("window.history.back();");
        failurePopUp.append("}  </script>");
        failurePopUp.append("</body></html>");
        ischk = -1;

        return failurePopUp.toString();
    }

    private String idchkidFailurePopUp() {
        String alertMessage = "아이디 중복확인을 해주세요.";
        StringBuilder failurePopUp = new StringBuilder();
        failurePopUp.append("<html>");
        failurePopUp.append("<head>");

        failurePopUp.append("<title>회원가입 - 아이디 중복확인 검사</title>");
        failurePopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        failurePopUp.append("</head>");
        failurePopUp.append("<body onload=\"goMain()\">");
        failurePopUp.append("<script type=\"text/javascript\">");
        failurePopUp.append("function goMain() {");
        failurePopUp.append("alert(\"");
        failurePopUp.append(alertMessage);
        failurePopUp.append("\"); ");
        failurePopUp.append("window.history.back();");
        failurePopUp.append("}  </script>");
        failurePopUp.append("</body></html>");

        return failurePopUp.toString();
    }
    
        private String chkpwFailedPopUp() {
        String alertMessage = "비밀번호가 일치하지 않습니다.";
        StringBuilder failedPopUp = new StringBuilder();
        failedPopUp.append("<html>");
        failedPopUp.append("<head>");

        failedPopUp.append("<title>회원가입 - 비밀번호 확인</title>");
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
