<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="path" required="true" type="java.lang.String" description="Url path" %>
<%@ attribute name="exact" required="false" type="java.lang.Boolean" description="If true, do not include sub-paths" %>
<%@ attribute name="identifier" required="false" type="java.lang.String" description="Identifier for match, default usage is path." %>

<%
    String subPaths;

    String fullPath = (String) request.getAttribute("javax.servlet.forward.request_uri");
    int qI = fullPath.indexOf("?");
    if(qI > 0) {
        fullPath = fullPath.substring(0, qI);
    }
    subPaths = fullPath.substring(request.getContextPath().length());
    request.setAttribute("URL_SubPaths", subPaths);

%>

<c:choose>
    <c:when test="${exact != null && exact}">
        <a href="<c:url value="${path}"/>" class="nav-link${requestScope.URL_SubPaths.equals(path) || (identifier != null && requestScope.URL_SubPaths.equals(identifier)) ? ' active' : ""}"><jsp:doBody/></a>
    </c:when>
    <c:otherwise>
        <a href="<c:url value="${path}"/>" class="nav-link${requestScope.URL_SubPaths.startsWith(path) || (identifier != null && requestScope.URL_SubPaths.startsWith(identifier)) ? ' active' : ""}"><jsp:doBody/></a>
    </c:otherwise>
</c:choose>
