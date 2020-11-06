<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Din kurv</h1>
<c:choose>
    <c:when test="${sessionScope.cart.items.size() > 0}">
        <table class="table no-sort">
            <thead>
            <tr>
                <th scope="col">Bund</th>
                <th scope="col">Topping</th>
                <th scope="col">Pris</th>
                <th scope="col" class="text-right"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.cart.items}" var="item" varStatus="loop">
            <tr>
                <td><c:out value="${item.cupcake.cake.name}"/></td>
                <td><c:out value="${item.cupcake.topping.name}"/></td>
                <td><c:out value="${requestScope.utils.formattedPrice(item.totalPrice)} kr."/></td>
                <td class="text-right d-flex">
                    <p class="mr-5 flex-fill font-weight-bold">Antal: ${item.quantity}</p>
                    <form method="post" action="${pageContext.request.contextPath}/cart">
                        <input name="action" value="removeItem" hidden>
                        <input name="index" value="${loop.index}" hidden>
                        <button type="submit" class="btn text-danger d-flex w-100 border-0 bg-transparent p-0">
                            FJERN
                        </button>
                    </form>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <form action="${pageContext.request.contextPath}/order" method="post" class="d-flex justify-content-end">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <input type="submit" value="Bestil" class="btn btn-primary">
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/authentication?redirect=/cart">
                            Log ind for at bestille.
                        </a>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <p>Din indkøbskurv er tom.</p>
    </c:otherwise>
</c:choose>


