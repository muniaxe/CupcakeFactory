<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 21/10/2020
  Time: 09:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Log ind</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons+Round" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/style/app.css" rel="stylesheet">
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
            <li class="nav-item"><a class="nav-link">Ordrer</a></li>
            <li class="nav-item"><a class="nav-link">Kunder</a></li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item"><a class="nav-link">bruger@cphbusiness.dk</a></li>
            <li class="nav-item"><a class="nav-link"><span class="material-icons-round">shopping_basket</span></a></li>
        </ul>
    </nav>

    <!--content-->
    <main class="bg-light rounded p-3">
        <h1 class="mb-3">Log ind eller registrer ny bruger</h1>
        <div class="row">
            <form class="col" name="login" action="FrontController" method="POST">
                <h4>Log ind</h4>
                <input type="hidden" name="target" value="login">
                <div class="form-group">
                    <label>Email:</label>
                    <input class="form-control" type="text" name="email" value="someone@nowhere.com">
                </div>
                <div class="form-group">
                    <label>Password:</label>
                    <input class="form-control" type="password" name="password" value="sesam">
                </div>
                <input class="btn btn-primary" type="submit" value="Log ind">
            </form>

            <form class="col" name="registrer" action="FrontController" method="POST">
                <h4>Registrer ny bruger</h4>
                <input type="hidden" name="target" value="register">
                <div class="form-group">
                    <label>Email:</label>
                    <input class="form-control" type="text" name="email" value="someone@nowhere.com">
                </div>
                <div class="form-group">
                    <label>Password:</label>
                    <input class="form-control" type="password" name="password1" value="sesam">
                </div>
                <div class="form-group">
                    <label>Retype Password:</label>
                    <input class="form-control" type="password" name="password2" value="sesam">
                </div>
                <input class="btn btn-primary" type="submit" value="Registrer">
            </form>
        </div>
    </main>
</div>

</body>

</html>
