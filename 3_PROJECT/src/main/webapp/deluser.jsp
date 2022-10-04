<%-- 
    Document   : deluser
    Created on : 2021. 6. 4., 오후 10:41:33
    Author     : Jiwon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원탈퇴 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_settings_menu.jsp" />
        </div>

        <div id = "reg_form">
            <form name="Register" action="DeluserServlet.do" method="POST">
                <table class="reg_table">
                    <tr>
                        <td colspan="2">
                            회원탈퇴 
                        </td>
                    </tr>
                    <tr>
                        <td>암호 </td>
                        <td class="left"> <input type="password" name="password" value="" /> </td>
                    </tr>
                    <td colspan="2">
                        <input type="submit" />
                    </td>
                    </tr>
                </table>

            </form>
        </div>

        <jsp:include page="footer.jsp" />
    </body>
</html>
