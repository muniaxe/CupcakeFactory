<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 04-11-2020
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Faktura for <small class="text-muted">#<c:out value="${requestScope.order.uuid}" /></small></h1>
<div class="mb-4">
    <p>
        <b>Udstedt:</b> <c:out value="${requestScope.utils.formattedDateTime(requestScope.order.createdAt)}" />
        <br>
        <b>Bruger:</b> <c:out value="${requestScope.order.user.email}" />
    </p>
</div>
<table class="table table-bordered">
    <thead class="thead-light">
    <tr>
        <th scope="col">Bund</th>
        <th scope="col">Topping</th>
        <th scope="col">Antal</th>
        <th scope="col">Pris</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${requestScope.order.items}" var="item" varStatus="loop">
            <tr>
                <td><c:out value="${item.cupcake.cake.name}" /></td>
                <td><c:out value="${item.cupcake.topping.name}" /></td>
                <td><c:out value="${item.quantity}" /></td>
                <td><c:out value="${requestScope.utils.formattedPrice(item.totalPrice)}" /> DKK</td>
            </tr>
        </c:forEach>
        <tr>
            <td class="w-50 text-nowrap border-0"></td>
            <td class="w-50 text-nowrap border-0"></td>
            <th scope="col" class="text-nowrap border-0">Total pris:</th>
            <th scope="col" class="text-nowrap border-0"><c:out value="${requestScope.utils.formattedPrice(requestScope.order.totalPrice)}" /> DKK</th>
        </tr>
    </tbody>
</table>