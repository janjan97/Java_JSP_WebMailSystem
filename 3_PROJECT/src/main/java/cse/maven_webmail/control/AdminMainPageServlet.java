/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author gsh & lsj
 */
@WebServlet(name = "AdminMainPageServlet", urlPatterns = {"/AdminMainPageServlet.do"})
public class AdminMainPageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger logger = LoggerFactory.getLogger(AdminMainPageServlet.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String string_perpage = request.getParameter("perpage");//페이지 단위 값
        String page = request.getParameter("page");//페이지 값
        String totalusers = request.getParameter("totalusers");//전체 인원 수 값
        String button = request.getParameter("button");//버튼 이름 가져오기
        String username = request.getParameter("username");//검색 이름 값
        logger.info("페이지단위 : {}", string_perpage);
        logger.info("페이지 값 : {}", page);
        logger.info("전체 인원 수 값 : {}", totalusers);
        logger.info("버튼 이름 : {}",button);
        logger.info("검색 이름 값 : {}", username);

        //페이지 단위 : 라디오 버튼에 따른 값 결정
        int perpage = 0;
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
            default:
                break;
        }
        limitpage = (int) Math.floor(Integer.parseInt(totalusers)/perpage)+1;
        request.setAttribute("limitpage", limitpage);
        if (button.equals("이동")) {//페이지 이동 버튼
            if ("".equals(page) || limitpage < Integer.parseInt(page)) {
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/admin_menu.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect("admin_menu.jsp");
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
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/admin_menu_page.jsp");
                    dispatcher.forward(request, response);
                    response.sendRedirect("admin_menu_page.jsp");
                    logger.info("페이지 이동 : {}", page);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } else if (button.equals("검색")) {//검색 버튼
            if ("".equals(username)) {
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/admin_menu.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect("admin_menu.jsp");
                logger.info("검색 잘못된 값 입력으로 초기화면으로감 : {}", page);
            } else {
                request.setAttribute("username", username);
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/admin_menu_search_result.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect("admin_menu_search_result.jsp");
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
