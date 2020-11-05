<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 05/11/2020
  Time: 09:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th scope="col">#</th>
        <th scope="col">Bruger</th>
        <th scope="col">Ordre Oprettet</th>
        <th scope="col">Ordre ID</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${requestScope.orders}" varStatus="loop">
        <tr class="d-lg-table-row">
            <th scope="row"><c:out value="${loop.index + 1}" /></th>
            <td><c:out value="${order.user.email}"/></td>
            <td><c:out value="${order.createdAt}"/></td>
            <td><a href="${pageContext.request.contextPath}/order/${order.uuid}"><c:out value="${order.uuid}"/></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>