package cse.maven_webmail.control;

import cse.maven_webmail.model.UserAdminAgent;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeluserServlet extends HttpServlet {

    boolean isConnected = false;
    private final String EOL = "\r\n";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            String inputpassword = request.getParameter("password");
            String mypassword = (String) session.getAttribute("password");

            if (inputpassword.equals(mypassword)) {
                delUser(request, response, out);
            } else {
                out.println(chkpwFailedPopUp());
            }
        }
    }

    private void delUser(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String server = "127.0.0.1";
        int port = 4555;
        try {
            UserAdminAgent agent = new UserAdminAgent(server, port, this.getServletContext().getRealPath("."));

            HttpSession session = request.getSession();

            String[] userid = {(String) session.getAttribute("userid")};

            // out.println("userid = " + userid + "<br>");
            out.flush();
            if (agent.deleteUsers(userid)) {
                out.println(sayGoodByePopUp());
            }

            out.flush();
        } catch (Exception ex) {
            out.println("시스템 접속에 실패했습니다.");
        }
    }

    private String sayGoodByePopUp() {
        String alertMessage = "탈퇴가 완료되었습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>회원 탈퇴</title>");
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
        String alertMessage = "비밀번호가 일치하지 않습니다.";
        StringBuilder failedPopUp = new StringBuilder();
        failedPopUp.append("<html>");
        failedPopUp.append("<head>");

        failedPopUp.append("<title>회원탈퇴 - 비밀번호 확인</title>");
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
