<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 04-11-2020
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="totalPris" scope="session" value="0" />
<c:if test="${sessionScope.user.id == sessionScope.order.user.id}">
    <h2>Tak fordi du handlede hos Olsker Cupcakes!</h2>
    <h4 class="mb-4">Her er din ordre</h4>
    <div class="mb-4">
        <span class="font-weight-bold w-100 d-inline-block">Bruger: <c:out value="${sessionScope.order.user.email}" /></span><br>
        <span class="font-weight-bold w-100 d-inline-block">Tid ved bestilling: <c:out value="${sessionScope.createdAt}" /></span>
    </div>
    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Bund</th>
            <th scope="col">Topping</th>
            <th scope="col">Antal</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${sessionScope.order.items}" var="item" varStatus="loop">
            <tr>
                <td scope="col"><c:out value="${item.cupcake.cake.name}" /></td>
                <td scope="col"><c:out value="${item.cupcake.topping.name}" /></td>
                <td scope="col"><c:out value="${item.quantity}" /></td>
                <%--@elvariable id="totalPris" type="java"--%>
                <c:set var="totalPris" scope="session" value="${totalPris + item.totalPrice}"/>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <div class="d-flex justify-content-end">
        <p>Total pris: DKK <c:out value=" ${requestScope.utils.formattedPrice(totalPris)}" />
        </p>
    </div>
</c:if>