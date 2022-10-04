<%-- 
    Document   : addrbook
    Created on : 2021. 5. 27., 오전 6:47:34
    Author     : lsj
--%>

<%@tag description="JSTL" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@attribute name="dataSource" required="true" %>
<%@attribute name="schema" required="true" %>
<%@attribute name="table" required="true" %>

<sql:query var = "rs" dataSource="${dataSource}">
    SELECT username, tel, regdate FROM ${schema}.${table} WHERE username LIKE '%${username}%'
</sql:query>

<table border="1">
    <thead>
        <tr>
            <th>선택</th>
            <th>이름</th>
            <th>전화번호</th>
            <th>가입일</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var = "row" items="${rs.rows}">
            <tr>
                <td>
                    <input type="checkbox" name = "userid" value="${row.username}" />
                </td>
                <td>${row.username}</td>
                <td>${row.tel}</td>
                <td>${row.regdate}</td>
            </tr> 
        </c:forEach>
    </tbody>    
</table>
<%-- 파라미터값 전달시 nullpointer방지 --%>
<input type="hidden" name="perpage" value="10">
<input type="hidden" name="totalusers" value="1">