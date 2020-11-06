<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 21/10/2020
  Time: 11.36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Start shopping</h1>
<form method="post" action="${pageContext.request.contextPath}/cart" id="order_form">
    <input name="action" value="addItem" hidden>
    <div class="row">
        <div class="form-group col-5">
            <div class="floating-label">
                <select class="form-control" id="bund" required name="cake">
                    <option disabled selected hidden></option>
                    <c:forEach var="cake" items="${requestScope.cakes}">
                        <option value="${cake.id}" data-price="${cake.price}">
                            <c:out value="${cake.name} + (${requestScope.utils.formattedPrice(cake.price)} kr.)"/>
                        </option>
                    </c:forEach>
                </select>
                <label for="bund">Vælg bund</label>
            </div>
        </div>
        <div class="form-group col-5">
            <div class="floating-label">
                <select class="form-control" id="topping" required name="topping">
                    <option disabled selected hidden></option>
                    <c:forEach var="topping" items="${requestScope.toppings}">
                        <option value="${topping.id}" data-price="${topping.price}">
                            <c:out value="${topping.name} + (${requestScope.utils.formattedPrice(topping.price)} kr.)"/>
                        </option>
                    </c:forEach>
                </select>
                <label for="topping">Vælg topping</label>
            </div>
        </div>
        <div class="form-group col-2">
            <div class="floating-label">
                <input type="number" placeholder=" " name="quantity" class="form-control" max="100" min="1" id="quantity" required>
                <label for="quantity">Vælg antal</label>
            </div>
        </div>
    </div>
    <div class="d-flex justify-content-end align-items-center">
        <p class="total-price m-0 mr-4">Pris: <span>0,00</span> DKK</p>
        <input class="btn btn-primary" type="submit" value="Læg i kurv">
    </div>
</form>