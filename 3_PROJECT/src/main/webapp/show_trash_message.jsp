<%-- 
    Document   : show_trash_message
    Created on : 2021 May 31, 00:58:39
    Author     : bhi84
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="cse.maven_webmail.control.CommandType" %>
<jsp:useBean id="tao" class="cse.maven_webmail.model.TrashTransperDAO" scope="page"/>
<jsp:setProperty name="tao" property="messageName" value="${param.messageName}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/main_style.css"/>
        <title>휴지통 내용 보기</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div id="sidebar">
            <br/>
            <jsp:include page="sidebar_menu.jsp" />
        </div>
      
        <div id="msgBody">
            ${tao.subject}
        </div>
         <jsp:include page="footer.jsp" />
    </body>
</html>
