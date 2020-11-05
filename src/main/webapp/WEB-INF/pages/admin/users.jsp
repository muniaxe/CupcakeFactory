<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 03-11-2020
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th scope="col">#</th>
        <th scope="col">Email</th>
        <th scope="col">Bruger oprettet</th>
        <th scope="col">Balance</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr class="d-lg-table-row">
            <th scope="row"><c:out value="${user.id}" /></th>
            <td><c:out value="${user.email}" /></td>
            <td><c:out value="${user.createdAt}" /></td>
            <td><c:out value="${user.balance}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>