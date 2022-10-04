<%-- 
    Document   : admin_menu_search_result.jsp
    Author     : gsh & lsj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.model.UserAdminAgent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib tagdir = "/WEB-INF/tags/" prefix="mytags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>사용자 관리 메뉴</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_admin_previous_menu.jsp" />
        </div>

        <div id="main">
            <h2> 메일 사용자 목록 </h2>
            "${username}" 검색 결과
            <form name = "AdminMainPageServlet" action ="AdminMainPageServlet.do" method="POST">
                <c:catch var = "errorReason">
                    <mytags:addrbook_search_result dataSource="jdbc/JamesWebmail" schema="ood" table="users" />
                </c:catch>
                ${empty errorReason ? "<noerror/>" : erroReason}
            </form>
        </div>

        <jsp:include page="footer.jsp" />
    </body>
</html>
