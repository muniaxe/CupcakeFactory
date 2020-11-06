<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 05/11/2020
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Ordre historik</h1>
<c:choose>
    <c:when test="${requestScope.orders.size() < 1}">
        <p>Ingen tidligere ordre fundet.</p>
    </c:when>
    <c:otherwise>
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th scope="col">Dato</th>
                <th scope="col">Beskrivelse</th>
                <th scope="col">Pris</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${requestScope.orders}" varStatus="loop">
                <tr class="d-lg-table-row">
                    <td><c:out value="${requestScope.utils.formattedDate(order.createdAt)}"/></td>
                    <td><a href="${pageContext.request.contextPath}/order/${order.uuid}">Faktura for bestillingen af <c:out value="${order.totalQuantity}"/> cupcakes</a></td>
                    <td>${requestScope.utils.formattedPrice(order.totalPrice)} DKK</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </c:otherwise>
</c:choose>
