<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" type="java.lang.String" %>

<spring:message code="title" var="defaultTitle"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:if test="${not empty title}">${title} - </c:if>${defaultTitle}</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/css/index.css">
</head>
<body>
    <div class="container">
        <header>
            <nav>
                <ul class="list-inline">
                    <c:forEach items="${boards}" var="board">
                        <li class="list-inline-item"><a href="${pageContext.servletContext.contextPath}/${board.name}">${board.name}</a></li>
                    </c:forEach>
                    <c:forTokens items="mythreads,repliedthreads" delims="," var="x">
                        <li class="list-inline-item"><a href="${pageContext.servletContext.contextPath}/${x}"><spring:message code="${x}"/></a></li>
                    </c:forTokens>
                    <sec:authorize access="!isAuthenticated()">
                        Login
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        Logout
                    </sec:authorize>
                </ul>
            </nav>
        </header>
        <main>
            <jsp:doBody/>
        </main>
    </div>
</body>
</html>
