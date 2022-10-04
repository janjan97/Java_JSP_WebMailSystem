/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import cse.maven_webmail.model.TrashTransperDAO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bhi84
 */
public class TrashHandler extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TrashHandler.class);
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
                case CommandType.DELETE_MAIL_COMMAND:
                deleteT(request, response);
                break;
                
                case CommandType.RESTORE_MAIL_COMMAND:
                restoreT(request, response);
                break;
                
                case CommandType.DOWNLOAD_COMMAND:
                downloadT(request, response);
                break;
                
                default:
                try (PrintWriter out = response.getWriter()) {
                    logger.info("없는 메뉴입니다.");
                }
                break;
            }
        }catch (Exception e) {
            logger.error("휴지통 메뉴 에러"+e.getMessage());
        }
    }
    
      private void downloadT(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String messageName = request.getParameter("messageName");
        try (ServletOutputStream sos = response.getOutputStream();
        ) {
            TrashTransperDAO tDAO = new TrashTransperDAO();
            tDAO.setMessageName(messageName);
            tDAO.download();
            String fileName = tDAO.getFileName();
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ";");
            try (FileInputStream fileInputStream = new FileInputStream( "c:/jsp/" + fileName)) {
                sos.write(fileInputStream.readAllBytes());
            }
        }
    }
    
    private void deleteT(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String messageName = request.getParameter("messageName");
        TrashTransperDAO tDAO = new TrashTransperDAO();
        tDAO.setMessageName(messageName);
        if (tDAO.trashDel()) {
            response.sendRedirect("trash.jsp");
        } else {
            try (PrintWriter out = response.getWriter()) {
                logger.info("삭제 오류 발생");
            }
        }
    }
    
     private void restoreT(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String messageName = request.getParameter("messageName");
        TrashTransperDAO tDAO = new TrashTransperDAO();
        tDAO.setMessageName(messageName);
        if (tDAO.trashRes()) {
            response.sendRedirect("trash.jsp");
        } else {
            try (PrintWriter out = response.getWriter()) {
                logger.info("복원 오류 발생");
            }
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
