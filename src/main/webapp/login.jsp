<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 21/10/2020
  Time: 09:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="title" scope="request">Log ind</c:set>

<jsp:include page="/WEB-INF/includes/header.include.jsp" />

<h1 class="mb-3">Log ind eller registrer ny bruger</h1>
<div class="row">
    <form class="col" name="login" action="FrontController" method="POST">
        <h4>Log ind</h4>
        <input type="hidden" name="target" value="login">
        <div class="form-group">
            <label class="w-100">Email:
                <input class="form-control" type="text" name="email" value="someone@nowhere.com">
            </label>
        </div>
        <div class="form-group">
            <label class="w-100">Password:
                <input class="form-control" type="password" name="password" value="sesam">
            </label>
        </div>
        <input class="btn btn-primary" type="submit" value="Log ind">
    </form>

    <form class="col" name="registrer" action="FrontController" method="POST">
        <h4>Registrer ny bruger</h4>
        <input type="hidden" name="target" value="register">
        <div class="form-group">
            <label class="w-100">Email:
                <input class="form-control" type="text" name="email" value="someone@nowhere.com">
            </label>
        </div>
        <div class="form-group">
            <label class="w-100">Password:
                <input class="form-control" type="password" name="password1" value="sesam">
            </label>
        </div>
        <div class="form-group">
            <label class="w-100">Retype Password:
                <input class="form-control" type="password" name="password2" value="sesam">
            </label>
        </div>
        <input class="btn btn-primary" type="submit" value="Registrer">
    </form>
</div>

<jsp:include page="/WEB-INF/includes/footer.include.jsp" />