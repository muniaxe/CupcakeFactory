<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 03-11-2020
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Alle brugere</h1>
<table class="table table-bordered">
    <thead class="thead-light">
    <tr>
        <th scope="col">#</th>
        <th scope="col">E-mail</th>
        <th class="text-right" scope="col">Konto beløb</th>
        <th scope="col">Bruger oprettet</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr class="d-lg-table-row">
            <th scope="row" class="text-nowrap"><c:out value="${user.id}" /></th>
            <td class="w-100 text-nowrap"><c:out value="${user.email}" /></td>
            <td class="text-right text-nowrap"><c:out value="${requestScope.utils.formattedPrice(user.balance)} DKK" /></td>
            <td class="text-nowrap"><c:out value="${requestScope.utils.formattedDate(user.createdAt)}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>