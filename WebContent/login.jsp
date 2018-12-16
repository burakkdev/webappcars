<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<section>

<h1>Login Form</h1>
<p>Please enter a username and password to continue.</p>
<form action="j_security_check" method="POST">
	<label>Username</label>
	<input type="text" name="j_username"><br>
	<label>Password</label>
	<input type="password" name="j_password"><br>
	<label>&nbsp;</label>
	<input type="submit" value="Login">
</form>

</section>

<jsp:include page="/includes/column_right_news.jsp"/>
<jsp:include page="/includes/footer.jsp"/>