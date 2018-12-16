<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Cheap Car Parts</title>
<link rel="stylesheet" href="<c:url value='/styles/main.css'/>">
<!-- <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script> -->
</head>
<body>
	<header>
	<h1>Cheap Car Parts</h1>
	<h2>Best prices in here on the Web</h2>
	</header>
	<nav id="nav_bar">
	<ul>
		<li><a href="<c:url value='/admin'/>"> Admin</a></li>
		<li><a href="<c:url value='/userPanel/deleteCookies'/>"> Delete Cookies</a></li>
		<li><a href="<c:url value='/orderPanel/showCart'/>"> Show Cart</a></li>
		<%-- <a href="<c:url value='/admin'/>"> Admin</a>
		<a href="<c:url value='/user/deleteCookies'/>"> Delete Cookies</a>
		<a href="<c:url value='/order/showCart'/>"> Show Cart</a> --%>
	</ul>
	</nav>