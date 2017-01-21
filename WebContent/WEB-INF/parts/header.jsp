<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="fr">

<head>
<meta charset="utf-8">
<title>ZooPark</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/global.css">
<c:if test="${ pageContext.request.servletPath == '/WEB-INF/buildEnclosure.jsp' }">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/buildEnclosure.css" />
</c:if>
<c:if test="${ pageContext.request.servletPath == '/WEB-INF/enclosureManagement.jsp' }">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/enclosureManagement.css" />
</c:if>
<c:if test="${ pageContext.request.servletPath == '/WEB-INF/employeesManagement.jsp' }">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/employeesManagement.css" />
</c:if>
</head>

<body>
	<div id="body">
		<div id="main-wrapper">
			<c:if test="${ !empty sessionScope.user }">
				<header id="main-header">
					<div id="page-header">
						Welcome, ${ sessionScope.user.pseudo }<br>
						<a href="user?action=disconnect">Disconnect</a>
					</div>
					<div id="top-menu">
						<nav>
							<ul>
								<li>Money : ${ sessionScope.user.money } Z</li>
								<li>Visitors : 0</li>
								<li>Satisfaction: </li>
								<li><a href="#">Messages</a></li>
								<li>
									<img id="play" src="/zoopark/assets/images/play-button.png"> | <img id="speedup" src="/zoopark/assets/images/fast-forward.png"> | <img id="pause" src="/zoopark/assets/images/pause.png"> 
									<span id="gamedate">
										<c:set var="ht" value="${fn:split(sessionScope.user.turn, ',')}" />
										HEURE : <span class="heure">${ ht[0] }</span> JOUR : <span class="jour">${ ht[1] }</span>
									</span>
								</li>
							</ul>
						</nav>
					</div>
				</header>
			</c:if>
			<c:if test="${ empty sessionScope.user }">
				<header id="main-header">
					<div id="page-header"></div>
				</header>
			</c:if>