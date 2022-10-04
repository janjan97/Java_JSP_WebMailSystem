/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gsh & lsj
 */
public class AdminDeleteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger logger = LoggerFactory.getLogger(AdminDeleteServlet.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
        final String JdbcUrl = "jdbc:mysql://35.184.209.113:11269/ood?autoReconnect=true&serverTimezone=Asia/Seoul&useSSL=false";
        final String User = "ooduser";
        final String Password = "ooduser";
        request.setCharacterEncoding("UTF-8");

        String string_perpage = request.getParameter("perpage");//페이지 단위 값
        String page = request.getParameter("page");//페이지 값
        String totalusers = request.getParameter("totalusers");//전체 인원 수 값
        String button = request.getParameter("button");//버튼 이름 가져오기
        String[] userid = request.getParameterValues("userid");//체크박스 표시된 이름 배열
        String username = request.getParameter("username");//검색 이름 값
        logger.info("페이지단위 : {}", string_perpage);
        logger.info("페이지 값 : {}", page);
        logger.info("전체 인원 수 값 : {}", totalusers);
        logger.info("버튼 이름 : {}",button);
        logger.info("고른 유저들 : {}",userid);
        logger.info("검색 이름 값 : {}", username);

        //페이지 단위 : 라디오 버튼에 따른 값 결정
        int perpage = 1;
        int limitpage = 0;
        switch (string_perpage) {
            case "10":
                perpage = 10;
                break;
            case "20":
                perpage = 20;
                break;
            case "30":
                perpage = 30;
                break;
            case "":
                perpage = 1;
                break;
            default:
                break;
        }
        limitpage = (int) Math.floor(Integer.parseInt(totalusers) / perpage) + 1;
        //삭제 버튼 눌러 DB안에 있는 데이터 삭제
        if (button.equals("삭제")) {
            try {
                Class.forName(JdbcDriver);
                Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);
                for (int i = 0; i < userid.length; i++) {
                    String sql = "DELETE FROM users WHERE username = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, userid[i]);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.close();
                response.sendRedirect("admin_menu.jsp");
                logger.info("삭제된 유저들 : {}",userid);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                logger.info("삭제오류");
            }
        } else if (button.equals("이동")) {//페이지 이동 버튼
            if ("".equals(page) || limitpage < Integer.parseInt(page)) {
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/delete_user.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect("delete_user.jsp");
                logger.info("페이지 잘못된 값 입력으로 초기화면으로감 : {}", page);
            } else {
                try {
                    //한 페이지에서 시작하는 번호 계산후 String으로 변환
                    int intstartcol = (Integer.parseInt(page) - 1) * perpage;
                    String startcol = Integer.toString(intstartcol);
                    request.setAttribute("startcol", startcol);

                    int intlastcol = intstartcol + perpage;
                    String lastcol = Integer.toString(intlastcol);
                    request.setAttribute("lastcol", lastcol);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/delete_user_page.jsp");
                    dispatcher.forward(request, response);
                    response.sendRedirect("delete_user_page.jsp");
                    logger.info("페이지 이동 : {}", page);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    logger.info("페이지 이동 오류");
                }
            }
        } else if (button.equals("검색")) {//검색 버튼
            if ("".equals(username)) {
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/delete_user.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect("delete_user.jsp");
                logger.info("검색 잘못된 값 입력으로 초기화면으로감 : {}", page);
            } else {
                request.setAttribute("username", username);
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/delete_user_search_result.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect("delete_user_search_result.jsp");
                logger.info(" 검색 이름 값 : {}", username);
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
