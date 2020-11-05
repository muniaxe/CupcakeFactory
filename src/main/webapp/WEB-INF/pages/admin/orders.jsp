<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 05/11/2020
  Time: 09:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Alle ordre</h1>
<table class="table table-bordered">
    <thead class="thead-light">
    <tr>
        <th scope="col">#</th>
        <th scope="col">Bestilt af</th>
        <th scope="col">Udstedt</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${requestScope.orders}">
        <tr class="d-lg-table-row">
            <td><a href="${pageContext.request.contextPath}/order/${order.uuid}"><c:out value="#${order.uuid}"/></a></td>
            <td><c:out value="${order.user.email}"/></td>
            <td><c:out value="${requestScope.utils.formattedDate(order.createdAt)}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>