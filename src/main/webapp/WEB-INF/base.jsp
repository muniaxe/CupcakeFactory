<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 26/10/2020
  Time: 13.24
  To change this template use File | Settings | File Templates.
--%>
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
    <!--Header-->
    <header class="mb-2">
        <img class="img-fluid" src="${pageContext.request.contextPath}/assets/images/olskercupcakes.png">
    </header>

    <!--Nav Bar-->
    <nav class="navbar navbar-expand-lg navbar-light bg-light mb-2">
        <ul class="navbar-nav">
            <li class="nav-item"><a href="" class="nav-link">Shop</a></li>
            <li class="nav-item"><a href="" class="nav-link">Undefined</a></li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdown" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">bruger@cphbusiness.dk</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="#">Ordre historik </a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item text-danger d-flex" href="#">
                        <span>Log ud</span>
                        <span class="material-icons-round align-text-bottom ml-auto">exit_to_app</span>
                    </a>
                </div>
            </li>
            <li class="nav-item"><a href="" class="nav-link"><span class="material-icons-round">shopping_basket</span></a></li>
        </ul>
    </nav>

    <!--content-->
    <main class="bg-light rounded p-3">
        <jsp:include page="/WEB-INF/pages/${requestScope.content}.jsp" />
    </main>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>

</html>
