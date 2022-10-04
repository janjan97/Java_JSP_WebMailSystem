<%-- 
    Document   : View_myinfo
    Created on : 2021. 6. 5., 오전 1:33:28
    Author     : Jiwon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.Statement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>개인정보수정</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_settings_menu.jsp" />
        </div>

        <div id = "reg_form">
            <form name="Register" action="UpdateDataHandler.do" method="POST">
                <table class="reg_table">
                    <tr>
                        <td colspan="2">
                            회원정보 수정<br>
                            ※ 비밀번호를 수정하시려면 <br>
                            비밀번호, 비밀번호 확인을 입력해주세요.
                        </td>
                    </tr>
                    <tr>
                        <td>아이디 </td>
                        <td class="left"> <input type="text" name="view_id" value=<%= session.getAttribute("userid")%> readonly/></td>
                    </tr>

                    <tr>
                        <td>비밀번호 </td>
                        <td class="left"> <input type="password" name="pw"/></td>
                    </tr>

                    <tr>
                        <td>비밀번호 확인 </td>
                        <td class="left"> <input type="password" name="chkpw"/></td>
                    </tr>

                    <tr>
                        <td>연락처 </td>
                        <td class="left"> <input type="text" name="view_tel" value= 
                                                 <%
                                                     // 1. JDBC 드라이버 로딩
                                                     Class.forName("com.mysql.cj.jdbc.Driver");

                                                     Connection conn = null; // DBMS와 Java연결객체
                                                     Statement stmt = null; // SQL구문을 실행
                                                     ResultSet rs = null; // SQL구문의 실행결과를 저장

                                                     String userid = (String) session.getAttribute("userid");

                                                     try {
                                                         String jdbcDriver = "jdbc:mysql://35.184.209.113:11269/ood?autoReconnect=true&serverTimezone=Asia/Seoul&useSSL=false";
                                                         String dbUser = "ooduser";
                                                         String dbPass = "ooduser";

                                                         String query = "select * from users where username = \"" + userid + "\";";

                                                         // 2. 데이터베이스 커넥션 생성
                                                         conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

                                                         // 3. Statement 생성
                                                         stmt = conn.createStatement();

                                                         // 4. 쿼리 실행
                                                         rs = stmt.executeQuery(query);

                                                         // 5. 쿼리 실행 결과 출력
                                                         while (rs.next()) {
                                                 %>

                                                 <%= rs.getString("tel")%>

                                                 <%
                                                         }
                                                     } catch (SQLException ex) {
                                                         out.println(ex.getMessage());
                                                         ex.printStackTrace();
                                                     } finally {
                                                         // 6. 사용한 Statement 종료
                                                         if (rs != null) try {
                                                             rs.close();
                                                         } catch (SQLException ex) {
                                                         }
                                                         if (stmt != null) try {
                                                             stmt.close();
                                                         } catch (SQLException ex) {
                                                         }

                                                         // 7. 커넥션 종료
                                                         if (conn != null) try {
                                                             conn.close();
                                                         } catch (SQLException ex) {
                                                         }
                                                     }
                                                 %>
                                                 /> </td>



                    </tr>
                    <td colspan="2">
                        <input type="submit" value = "수정"/>
                    </td>
                    </tr>
                </table>

            </form>
        </div>

        <jsp:include page="footer.jsp" />
</html>
