<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Din kurv</h1>
<c:if test="${sessionScope.cart.items.size() > 0}">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Bund</th>
            <th scope="col">Topping</th>
            <th scope="col">Pris</th>
            <th scope="col" class="text-right"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.cart.items}" var="item">
        <tr>
            <td><c:out value="${item.cupcake.cake.name}"/></td>
            <td><c:out value="${item.cupcake.topping.name}"/></td>
            <td><c:out value="${requestScope.utils.formattedPrice(item.totalPrice)} kr."/></td>
            <td class="text-right d-flex">
                <p class="mr-5 flex-fill font-weight-bold">Antal: ${item.quantity}</p>
                <a href="#" class="d-flex">FJERN</a>
            </td>

        </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
