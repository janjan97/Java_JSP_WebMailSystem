<%-- 
    Document   : register
    Created on : 2021. 5. 8., 오전 1:04:59
    Author     : Jiwon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원가입 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />
        
        <div id = "reg_form">
            <form name="Register" action="RegisterHandler.do" method="POST">
                <table class="reg_table">
                    <tr>
                        <td colspan="2">
                           회원가입 
                        </td>
                    </tr>
                    <tr>
                        <td>사용자 ID</td>
                        <td> <input type="text" name="id" value="" size="20" />
                        <input type="submit" value="중복확인" name="reg" /> </td>
                    </tr>
                    
                    <!-- 아래 사용되는 입력 필드는 레이아웃을 고려하여 각 style 적용 -->
                    
                    <tr>
                        <td>암호 </td>
                        <td class="left"> <input type="password" name="password" value="" /> </td>
                    </tr>
                    <tr>
                        <td>암호 재확인 </td>
                        <td class="left"> <input type="password" name="passwordcheck" value="" /> </td>
                    </tr>
                    <tr>
                        <td>전화번호 </td>
                        <td class="left"> <input type="text" name="tel" value="" /> </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="가입하기" name="reg" />
                        </td>
                    </tr>
                </table>

            </form>
        </div>
        
        <jsp:include page="footer.jsp" />
    </body>
</html>
