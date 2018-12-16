<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section>

<h1>Java Error</h1>
<p>Sorry, Java has thrown an exception.</p>
<p>To continue, click the back button or select a link from this page.</p>

<h2>Details</h2>
<code>
${pageContext.errorData.servletName} threw a <br>
${pageContext.exception}<br>

<c:forEach var="line" items="{pageContext.errorDate.throwable.stackTrace}">
	&nbsp;&nbsp;&nbsp;&nbsp;at ${line}<br>
</c:forEach>
</code>

</section>

<jsp:include page="/includes/footer.jsp" />