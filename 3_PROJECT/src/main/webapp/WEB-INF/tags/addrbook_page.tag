<%-- 
    Document   : addrbook
    Created on : 2021. 5. 27., 오전 6:47:34
    Author     : lsj
--%>
</script>
<%@tag description="JSTL" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@attribute name="dataSource" required="true" %>
<%@attribute name="schema" required="true" %>
<%@attribute name="table" required="true" %>

<sql:query var = "rs" dataSource="${dataSource}">
    SELECT username, tel, regdate FROM ${schema}.${table} LIMIT ${startcol}, ${lastcol-startcol}
</sql:query>

<sql:query var = "cs" dataSource="${dataSource}">
    SELECT username, tel, regdate FROM ${schema}.${table}
</sql:query>

전체 인원 : 
<c:forEach var = "row" items="${cs.rows}" varStatus="status">
    <c:if test = "${status.last == true}">
        <input type="hidden" name="totalusers" value="${status.count}">
        ${status.count}
    </c:if>
</c:forEach>

<table border="1">
    <thead>
        <tr>
            <th>번호</th>
            <th>이름</th>
            <th>전화번호</th>
            <th>가입일</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var = "row" items="${rs.rows}" varStatus="status">
            <tr>
                <td><c:out value="${status.count+startcol}" /></td>
                <td>${row.username}</td>
                <td>${row.tel}</td>
                <td>${row.regdate}</td>
            </tr> 
        </c:forEach>
    </tbody>
</table>

<input type="text" name="page" value="" size="2" />
/<c:forEach var = "row" items="${cs.rows}" varStatus="status">
    <c:if test = "${status.last == true}">
        <fmt:parseNumber var="value" value="${status.count/(lastcol-startcol)+1}" integerOnly="true" />
        ${value}
    </c:if>
</c:forEach>
<input type="submit"  name = "button" value= "이동">

다음 단위로 페이지 보기 : 
<input type="radio" id="perpage" name="perpage" value="10" checked="checked">10명
(<c:forEach var = "row" items="${cs.rows}" varStatus="status">
    <c:if test = "${status.last == true}">
        <fmt:parseNumber var="value" value="${(status.count-1)/10+1}" integerOnly="true" />
        ${value}
    </c:if>
</c:forEach>)
<input type="radio" id="perpage" name="perpage" value="20">20명
(<c:forEach var = "row" items="${cs.rows}" varStatus="status">
    <c:if test = "${status.last == true}">
        <fmt:parseNumber var="value" value="${(status.count-1)/20+1}" integerOnly="true" />
        ${value}
    </c:if>
</c:forEach>)
<input type="radio" id="perpage" name="perpage" value="30">30명
(<c:forEach var = "row" items="${cs.rows}" varStatus="status">
    <c:if test = "${status.last == true}">
        <fmt:parseNumber var="value" value="${(status.count-1)/30+1}" integerOnly="true" />
        ${value}
    </c:if>
</c:forEach>)
<br>
<input type="text" name="username" value=""/>
<input type="submit"  name = "button" value= "검색">
