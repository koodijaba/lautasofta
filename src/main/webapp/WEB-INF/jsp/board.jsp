<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<ui:layout>
    <section>
        <form:form modelAttribute="thread" method="POST">
            <div class="form-group">
                <form:label path="content"><spring:message code="post.content"/></form:label>
                <form:textarea path="content" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
                <form:errors path="content" cssClass="invalid-feedback" element="div"/>
            </div>
            <button type="submit" class="btn btn-primary"><spring:message code="submit"/></button>
        </form:form>
    </section>
    <section>
        <c:forEach items="${threads.content}" var="thread" varStatus="loop">
            <div class="card mt-4">
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="${pageContext.servletContext.contextPath}/${board.name}/${thread.id}" class="stretched-link">${thread.createdBy}</a>
                        <small class="text-muted">${thread.createdAt}</small>
                    </h5>
                    <p class="card-text">${thread.content}</p>
                </div>
                <img src="https://via.placeholder.com/700" class="card-img-top" alt="...">
            </div>
            <c:if test="${loop.last and not threads.last}">
                <div class="my-4 text-center">
                    <a href="?before=${thread.modifiedAt}"><spring:message code="loadMore"/></a>
                </div>
            </c:if>
        </c:forEach>
    </section>
</ui:layout>