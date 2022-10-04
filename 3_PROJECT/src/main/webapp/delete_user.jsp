<%-- 
    Document   : delete_user.jsp
    Author     : gsh & lsj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.model.UserAdminAgent" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib tagdir = "/WEB-INF/tags/" prefix="mytags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>사용자 제거 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <%-- 사용자 추가때와 동일하므로 같은 메뉴 사용함. --%>
            <jsp:include page="sidebar_admin_previous_menu.jsp" />
        </div>
        <div id="main">
            <h2> 삭제할 사용자를 선택해 주세요. </h2>
            <form name = "AdminDeleteServlet" action ="AdminDeleteServlet.do" method="POST">
                <c:catch var = "errorReason">
                    <mytags:deluser dataSource="jdbc/JamesWebmail" schema="ood" table="users"/>
                </c:catch>
                ${empty errorReason ? "<noerror/>" : erroReason}
                <br>
                <input type="submit" name="button" value ="삭제">
                <input type="reset" value="선택 전부 취소" /> 
            </form>  
        </div>

        <jsp:include page="footer.jsp" />
    </body>
</html>
