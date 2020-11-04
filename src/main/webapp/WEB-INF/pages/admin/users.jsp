<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 03-11-2020
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${sessionScope.user != null && sessionScope.user.admin}">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Email</th>
                <th scope="col">Bruger oprettet</th>
                <th scope="col">Balance</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${requestScope.users}">
                <tr class="d-lg-table-row">
                    <td><c:out value="${user.id}"></c:out></td>
                    <td><c:out value="${user.email}"></c:out></td>
                    <td><c:out value="${user.createdAt}"></c:out></td>
                    <td><c:out value="${user.balance}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="text-danger ml-2">Log ind som administrator for at se denne side!</p>
        <a class="ml-2" href="${pageContext.request.contextPath}/">Retuner til shoppen</a>
    </c:otherwise>
</c:choose>


