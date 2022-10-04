/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jiwon
 */
public class ChkPasswordHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            String inputpassword = request.getParameter("password");
            String mypassword = (String) session.getAttribute("password");

            if (inputpassword.equals(mypassword)) {
                out.println(redirect());
            } else {
                out.println(chkpwFailedPopUp());
            }
        }
    }

    private String chkpwFailedPopUp() {
        String alertMessage = "비밀번호가 일치하지 않습니다.";
        StringBuilder failedPopUp = new StringBuilder();
        failedPopUp.append("<html>");
        failedPopUp.append("<head>");

        failedPopUp.append("<title>개인정보수정 - 비밀번호 확인</title>");
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
    
        private String redirect() {
        StringBuilder redirect = new StringBuilder();
        redirect.append("<html>");
        redirect.append("<head>");

        redirect.append("<title></title>");
        redirect.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        redirect.append("</head>");
        redirect.append("<body onload=\"redirect()\">");
        redirect.append("<script type=\"text/javascript\">");
        redirect.append("function redirect() {");
        redirect.append("window.location = \"view_myinfo.jsp\"; ;");
        redirect.append("}  </script>");
        redirect.append("</body></html>");
        return redirect.toString();
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
