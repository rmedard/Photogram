<%@page import="be.kayiranga.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Photogram</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="/Photogram/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
		<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
<link href="/Photogram/resources/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header class="navbar navbar-default navbar-static-top"
		role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button class="navbar-toggle" type="button" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a href="/Photogram" class="navbar-brand">Photogram</a>
			</div>
			<nav class="collapse navbar-collapse" role="navigation">
				<ul class="nav navbar-nav">
					<li><a href="/Photogram/pages/private/displayUserProfile.jsp">Mon
							profil</a></li>
					<li><a href="/Photogram/pages/private/friends.jsp">Mes amis</a></li>
					<li><a href="/Photogram/pages/private/photos.jsp">Mes photos</a></li>
					<li><a href="/Photogram/pages/public/apropos.jsp">A propos</a></li>
				</ul>
				<c:choose>
					<c:when test="${user.username eq null}">
						<form class="navbar-form navbar-right" role="form" method="post"
							action="${pageContext.request.contextPath}/login?action=login">
							<div class="form-group">
								<input type="text" class="form-control" name="usernameTxt"
									placeholder="Username">
							</div>
							<div class="form-group">
								<input type="password" class="form-control" name="passwordTxt"
									placeholder="Password">
							</div>
							<button type="submit" class="btn btn-default">Login</button>
						</form>
					</c:when>
					<c:otherwise>
						<ul class="pull-right nav navbar-nav">
							<li><a
								href="<c:url value="/pages/private/displayUserProfile.jsp"/>">Bienvenu
									${user.username}</a></li>
							<li><a href="<c:url value="/login?action=logout"/>">Logout</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</nav>
		</div>
	</header>