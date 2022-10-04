<%-- 
    Document   : ㅊontact
    Created on : 2021 Jun 3, 08:55:21
    Author     : bhi84
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ctc" class="cse.maven_webmail.model.ContactSelect"/>
<jsp:setProperty name="ctc" property="userId" value="${userid}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <style>
        input.submitLink {
            background-color: transparent;
            text-decoration: underline;
            border: none;
            color: blue;
            cursor: pointer;
            }
        </style>
        <title>연락처</title>
    </head>
    <body>
            <jsp:include page="header.jsp" />
             
        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>
        <div id="main">
            <table>
                    <tr>
                        <th> 아이디 </td>
                        <th> 전화번호 </td>
                        <th> 차단등록 </td>
                        <th> 메일 보내기 </td> 
                    </tr>

                    <c:forEach items="${ctc.select()}" var="item">
                    ${item}
                </c:forEach>
            </table> 
        </div>
         <jsp:include page="footer.jsp" />
    </body>
</html>
