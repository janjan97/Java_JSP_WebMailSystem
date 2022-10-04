<%-- 
    Document   : sidebar_menu.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>웹메일 시스템 메뉴</title>
    </head>
    <body>
        <br> <br>
        
        <span style="color: indigo"> <strong>사용자: <%= session.getAttribute("userid") %> </strong> </span> <br>

        <p> <a href="main_menu.jsp"> 메일 읽기 </a> </p>
        <p> <a href="write_mail.jsp"> 메일 쓰기 </a> </p>
        <!-- 내게 쓴 메일함으로 이동을 위한 메뉴 생성(By J.) -->
        <p> <a href="mymail_menu.jsp"> 내게 쓴 메일함 </a> </p>
        <p> <a href="sendmail.jsp"> 보낸 메일함 </a> </p>
        <p> <a href="trash.jsp"> 휴지통 </a> </p>
        <p> <a href="contact.jsp"> 연락처 </a> </p>
        <p> <a href="setting_user_info.jsp"> 마이 페이지 </a> </p>     <!-- 기능 작동 확인을 위한 코드 -->   
        <p><a href="Login.do?menu=<%= CommandType.LOGOUT %>">로그아웃</a></p>
    </body>
</html>
