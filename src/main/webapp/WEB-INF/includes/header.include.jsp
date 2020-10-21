<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 21/10/2020
  Time: 11.25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>${requestScope.get("title")}</title>
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
            <li class="nav-item"><a href="" class="nav-link">Ordre</a></li>
            <li class="nav-item"><a href="" class="nav-link">Kunder</a></li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item"><a href="" class="nav-link">bruger@cphbusiness.dk</a></li>
            <li class="nav-item"><a href="" class="nav-link"><span class="material-icons-round">shopping_basket</span></a></li>
        </ul>
    </nav>

    <!--content-->
    <main class="bg-light rounded p-3">
