<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 21/10/2020
  Time: 11.36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="title" scope="request">Shop</c:set>

<jsp:include page="/WEB-INF/includes/header.include.jsp" />

<h1>Start shopping</h1>
<form>
    <input type="hidden" name="target" value="order">

</form>

<jsp:include page="/WEB-INF/includes/footer.include.jsp" />