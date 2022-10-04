<%-- 
    Document   : setting_user_info
    Created on : 2021. 6. 5., 오전 12:54:23
    Author     : Jiwon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>마이페이지</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_settings_menu.jsp" />
        </div>
        
                <div id = "reg_form">
            <form name="Register" action="ChkPasswordHandler.do" method="POST">
                <table class="reg_table">
                    <tr>
                        <td colspan="2">
                            비밀번호 확인 
                        </td>
                    </tr>
                    <tr>
                        <td>암호 </td>
                        <td class="left"> <input type="password" name="password" value="" /> </td>
                    </tr>
                    <td colspan="2">
                        <input type="submit" value="확인" />
                    </td>
                    </tr>
                </table>

            </form>
        </div>
        
        <jsp:include page="footer.jsp" />
    </body>
</html>
