<%-- 
    Document   : find_id
    Created on : 2021. 6. 5., 오전 2:46:42
    Author     : Jiwon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>아이디 찾기</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_find_menu.jsp" />
        </div>
        
                <div id = "reg_form">
            <form name="Register" action="FindidHandler.do" method="POST">
                <table class="reg_table">
                    <tr>
                        <td colspan="2">
                            아이디 찾기 
                        </td>
                    </tr>
                    <tr>
                        <td>연락처 </td>
                        <td class="left"> <input type="tel" name="tel" value="" /> </td>
                    </tr>
                    <td colspan="2">
                        <input type="submit" value = "조회하기"/>
                    </td>
                    </tr>
                </table>

            </form>
        </div>
        
        <jsp:include page="footer.jsp" />
    </body>
</html>
