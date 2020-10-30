<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="to" required="true" type="java.lang.String" description="Url path" %>
<%@ attribute name="exact" required="false" type="java.lang.Boolean" description="If true, do not include sub-paths" %>
<%@ attribute name="identifier" required="false" type="java.lang.String" description="Identifier for match, default usage is path." %>

<%@ attribute name="className" required="false" type="java.lang.String" description="Any style classes." %>


<%
    String subPaths;

    String fullPath = (String) request.getAttribute("javax.servlet.forward.request_uri");

    subPaths = fullPath.substring(request.getContextPath().length());
    request.setAttribute("URL_SubPaths", subPaths);

    /*
    *
    * Are we on the active link?
    *
    * */
    boolean active;
    if(exact != null && exact) {
        if (identifier != null) {
            active = subPaths.equals(identifier);
        } else {
            active = subPaths.equals(to);
        }
    }
    else {
        if (identifier != null) {
            active = subPaths.startsWith(identifier);
        } else {
            active = subPaths.startsWith(to);
        }
    }

    request.setAttribute("router_link_active", active);
%>

<a href="<c:url value="${to}"/>" class="${className != null ? className : ""}  ${requestScope.router_link_active ? "active" : ""}"><jsp:doBody/></a>
