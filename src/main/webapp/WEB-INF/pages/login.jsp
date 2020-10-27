<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 21/10/2020
  Time: 09:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<h1 class="mb-3">Log ind eller registrer ny bruger</h1>
<c:if test="${param.err!=null}">
    ${param.err}
</c:if>
<div class="row">
    <form class="col" name="login" action="${pageContext.request.contextPath}/authentication" method="POST">
        <input hidden value="login" name="action">
        <h4>Log ind</h4>
        <div class="form-group">
            <div class="floating-label">
                <input class="form-control" id="login_email" placeholder=" " type="text" name="email" required>
                <label class="" for="login_email">Email</label>
            </div>
        </div>
        <div class="form-group">
            <label class="w-100">Password:
                <input class="form-control" type="password" name="password">
            </label>
        </div>
        <input class="btn btn-primary" type="submit" value="Log ind">
    </form>

    <form class="col" name="registrer" action="${pageContext.request.contextPath}/authentication" method="POST">
        <input hidden value="register" name="action">
        <h4>Registrer ny bruger</h4>
        <div class="form-group">
            <label class="w-100">Email:
                <input class="form-control" type="text" name="email">
            </label>
        </div>
        <div class="form-group">
            <label class="w-100">Password:
                <input class="form-control" type="password" name="password1">
            </label>
        </div>
        <div class="form-group">
            <label class="w-100">Retype Password:
                <input class="form-control" type="password" name="password2">
            </label>
        </div>
        <input class="btn btn-primary" type="submit" value="Registrer">
    </form>
</div>