<%-- 
    Document   : login_fail
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<script type="text/javascript">
    <!--
    function gohome(){
        window.location = "/maven_webmail/"
    }
    -->
</script>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>로그인 실패</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body onload="setTimeout('gohome()', 5000)">

        <jsp:include page="header.jsp" />

        <p id="login_fail">
            사용자의 오류 또는 네트워크적 오류로 문제가 발생하였습니다. 5초후 초기화면으로 이동하게 됩니다.
            <!-- <a href="/WebMailSystem/" title="초기 화면">초기 화면</a>을 선택해 주세요.-->
            <a href="<%= getServletContext().getInitParameter("HomeDirectory") %>" title="초기 화면">초기 화면</a>을 선택해 주세요.
        </p>
        <%session.invalidate();%>
        <jsp:include page="footer.jsp" />

    </body>
</html>
