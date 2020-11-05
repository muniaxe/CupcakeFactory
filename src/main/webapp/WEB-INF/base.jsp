<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 26/10/2020
  Time: 13.24
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="d" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>${requestScope.get("title")}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons+Round" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/style/app.css?cacheForce<%= Math.random() %>" rel="stylesheet">
</head>

<body>
<div class="container">
    <!--Admin Bar -->
    <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
        <nav class="navbar navbar-expand-lg navbar-light m-0">
            <p class="d-flex m-0 text-dark"><span class="material-icons-round align-text-bottom mr-2">admin_panel_settings</span> Logget ind som admin</p>
            <ul class="ml-auto navbar-nav">
                <li class="nav-item"><d:router-link className="nav-link" to="/admin/orders" exact="true">Alle ordrer</d:router-link></li>
                <li class="nav-item"><d:router-link className="nav-link" to="/admin/users">Alle brugere</d:router-link></li>
            </ul>
        </nav>
    </c:if>

    <!--Header-->
    <header class="mb-2">
        <img class="img-fluid" src="${pageContext.request.contextPath}/assets/images/olskercupcakes.png">
    </header>

    <!--Nav Bar-->
    <nav class="navbar navbar-expand-lg navbar-light bg-light mb-2">
        <ul class="navbar-nav">
            <li class="nav-item"><d:router-link className="nav-link" to="/" exact="true">Shop</d:router-link></li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link disabled text-primary">
                        DKK: <c:out value="${requestScope.utils.formattedPrice(sessionScope.user.balance)}"/>
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="navbarDropdown" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><c:out value="${sessionScope.user.email}"/></a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/user/my-orders">Ordre historik</a>
                            <div class="dropdown-divider"></div>
                            <div class="dropdown-item">
                                <form method="post" class="m-0" action="${pageContext.request.contextPath}/authentication">
                                    <input hidden value="logout" name="action">
                                    <button class="btn text-danger d-flex w-100 border-0 bg-transparent p-0" type="submit">
                                        <span class="">Log ud</span>
                                        <span class="material-icons-round align-text-bottom ml-auto">exit_to_app</span>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item"><d:router-link className="nav-link" to="/authentication">Log ind</d:router-link></li>
                </c:otherwise>
            </c:choose>

            <li class="nav-item">
                <d:router-link to="/cart" className="nav-link cart">
                    <span class="material-icons-round">shopping_basket</span>
                    <c:if test="${sessionScope.cart.items.size() > 0}">
                        <span class="cart-notifier"></span>
                    </c:if>
                </d:router-link>
            </li>
        </ul>
    </nav>

    <!--content-->
    <main class="bg-light rounded p-3">
        <c:if test="${sessionScope.notification != null}">
            <div class="alert alert-${sessionScope.notification.type}" role="alert">
                    ${sessionScope.notification.message}
            </div>
        </c:if>
        <jsp:include page="/WEB-INF/pages/${requestScope.content}.jsp" />
    </main>
</div>

<% if(session.getAttribute("notification") != null) session.setAttribute("notification", null); %>

<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/assets/script/app.js"></script>
</body>

</html>
