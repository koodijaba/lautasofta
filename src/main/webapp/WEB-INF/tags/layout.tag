<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" type="java.lang.String" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:if test="${not empty title}">${title} - </c:if><spring:message code="title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
</head>
<body>
    <div class="container">
        <header>
            <nav>
                <ul class="list-inline">
                    <%--@elvariable id="boards" type="java.lang.Iterable<io.github.koodijaba.lautasofta.domain.Board>"--%>
                    <c:forEach items="${boards}" var="board">
                        <li class="list-inline-item"><a href="${pageContext.servletContext.contextPath}/${board.name}">${board.name}</a></li>
                    </c:forEach>
                    <sec:authorize access="!isAuthenticated()">
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/login">Login</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <c:forTokens items="mythreads,repliedthreads" delims="," var="x">
                            <li class="list-inline-item"><a href="${pageContext.servletContext.contextPath}/${x}"><spring:message code="${x}"/></a></li>
                        </c:forTokens>
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
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
