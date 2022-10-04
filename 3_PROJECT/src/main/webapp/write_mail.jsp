<%-- 
    Document   : write_mail.jsp
    Author     : jongmin
--%>


<%@page import="cse.maven_webmail.control.CommandType" %>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding(StandardCharsets.UTF_8.name());%>
<!DOCTYPE html>

<%-- @taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>메일 쓰기 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <script type="text/javascript">
           function doSubmit() { frm.action = "List.jsp"; frm.encoding = "application/x-www-form-urlencoded"; frm.submit(); } 
        </script>
        
            
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_previous_menu.jsp" />
        </div>

        <div id="main">
            <form enctype="multipart/form-data" method="POST"
                  action="WriteMail.do?menu=<%= CommandType.SEND_MAIL_COMMAND%>" >
                <table>
                    <tr>
                        <td> 수신 </td>
                        <!-- 체크박스의 체크여부가 확인되면 userid의 값을 넘겨받는다.(JSTL 구현 진행중) -->
                        <td> <input type="text" name="to" size="80"
                                    value= "<%=request.getParameter("fromAddress")==null ?"":request.getParameter("fromAddress")%>" />
                        </td>
                    </tr>
                    <tr>
                        <td>참조</td>
                        <td> <input type="text" name="cc" size="80">  </td>
                    </tr>
                    <tr>
                        <td> 메일 제목 </td>
                        <td> <input type="text" name="subj" size="80" value="${param.subject}" >  </td>
                    </tr>
                    <tr>
                        <td colspan="2">본  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 문</td>
                    </tr>
                    <tr>  <%-- TextArea    --%>
                        <td colspan="2">  <textarea rows="15" name="body" cols="80">${param.body}</textarea> </td>
                    </tr>
                    <tr>
                        <td>첨부 파일</td>
                        <td> <input type="file" name="file1"  size="80">  </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="메일 보내기">
                            <input type="reset" value="다시 입력">
                        </td>
                    </tr>
                </table>
            </form>
            <script type="text/javascript">
                function doSubmit()
                    { frm.action = "write_mail.jsp";
                    frm.encoding = "application/x-www-form-urlencoded";
                    frm.submit(); }
                doSubmit();
            </script>
        </div>

        <jsp:include page="footer.jsp" />
    </body>
</html>
