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
        <th scope="col">Status</th>
        <th scope="col">Udstedt</th>
        <th scole="col"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${requestScope.orders}">
        <tr class="d-lg-table-row">
            <td><a href="${pageContext.request.contextPath}/order/${order.uuid}"><c:out value="#${order.uuid}"/></a></td>
            <td><c:out value="${order.user.email}"/></td>
            <td><c:out value="${order.status.name}"/></td>
            <td><c:out value="${requestScope.utils.formattedDate(order.createdAt)}"/></td>
            <td>
                <form class="d-inline" method="post" action="${pageContext.request.contextPath}/admin/orders">
                <input name="action" value="delete-order" hidden>
                <input name="uuid" value="${order.uuid}" hidden>
                <button type="submit" class="btn text-danger d-flex border-0 bg-transparent p-0">
                    SLET
                </button>
            </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>