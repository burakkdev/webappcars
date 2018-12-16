<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_email.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section>
<h1>Cheap Car Parts Family</h1>
<p>Plese join our family by typing your informations
to get the new cheapest parts</p>

<p><i>${message}</i></p>

<form action="<c:url value='/userPanel/subscribeToEmail' />" method=post>
	<label>Email</label>
	<input type="email" name="email" required><br>
	<label>First Name</label>
	<input type="text" name="firstName" required><br>
	<label>Last Name</label>
	<input type="text" name="lastName" required><br>
	<label>&nbsp;</label>
	<input type="submit" value="Join Now" id="submit">
</form>
</section>

<jsp:include page="/includes/column_right_news.jsp" flush="true" />
<jsp:include page="/includes/footer.jsp" />