<%-- 
    Document   : trash
    Created on : 2021 May 24, 10:32:55
    Author     : bhi84
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.model.TrashTransperDAO"%>
<%@page import="cse.maven_webmail.model.TrashTransperDTO"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="sao" class="cse.maven_webmail.model.SendMailDAO"/>
<jsp:setProperty name="sao" property="userId" value="${userid}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <title>보낸 메일함</title>
        <style>
        input.submitLink {
            color: blue;
            background-color: transparent;
            text-decoration: underline;
            border: none;

            cursor: pointer;
            }
        </style>
    </head>
    <body>
            <jsp:include page="header.jsp" />
             
        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>
        <div id="main">
            <table>
                    <tr>
                        <th> NO. </td>
                        <th> 받은 사람 </td>
                        <th> 제목 </td>
                        <th> 받은 날짜 </td>   
                    </tr>

                    <c:forEach items="${sao.select()}" var="item">
                    ${item}
                </c:forEach>
            </table> 
        </div>
         <jsp:include page="footer.jsp" />
    </body>
</html>
