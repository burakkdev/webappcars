<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside id="sideBar">
	&nbsp;&nbsp;
	<form method="post" action="<c:url value='/orderPanel/addItem' />">
		<input type="hidden" name="productCode" value="${product.code}">
		<input type="image" src="<c:url value='/images/buy_nom.gif' />"
					width="113" align="middle" alt="Buy Now">
	</form>	
</aside>