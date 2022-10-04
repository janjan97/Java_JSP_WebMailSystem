<%-- 
    Document   : sidebar_settings_menu
    Created on : 2021. 6. 5., 오전 12:53:05
    Author     : Jiwon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>마이 페이지 메뉴</title>
    </head>
    <body>
        <br> <br>
        
        <span style="color: indigo"> <strong>사용자: <%= session.getAttribute("userid") %> </strong> </span> <br>

        <p> <a href="setting_user_info.jsp"> 개인정보수정 </a> </p>
        <p> <a href="deluser.jsp"> 회원탈퇴 </a> </p>
        <p> <a href="main_menu.jsp"> 이전 메뉴로 </a> </p>
    </body>
</html>
