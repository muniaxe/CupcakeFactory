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
        <th scope="col" class="text-nowrap">#</th>
        <th scope="col" class="text-nowrap">E-mail</th>
        <th class="text-right text-nowrap" scope="col">Konto beløb</th>
        <th scope="col" class="text-nowrap">Bruger oprettet</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr class="d-lg-table-row">
            <th scope="row" class="text-nowrap user-id"><c:out value="${user.id}" /></th>
            <td class="w-100 text-nowrap"><c:out value="${user.email}" /></td>
            <td class="text-right text-nowrap">
                <span class="d-inline-block admin--balance-editor" contenteditable="true" data-rawvalue="<c:out value="${user.balance}" />"><c:out value="${requestScope.utils.formattedPrice(user.balance)}" /></span> DKK
            </td>
            <td class="text-nowrap"><c:out value="${requestScope.utils.formattedDate(user.createdAt)}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>