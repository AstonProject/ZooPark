<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Notifications</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/notifs.css">
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
								<li><a href="#">Satisfaction</a></li>
								<li><a href="#">Messages</a></li>
								<li>
									<img id="play" src="/zoopark/assets/images/play-button.png"> | <img id="speedup" src="/zoopark/assets/images/fast-forward.png"> | <img id="pause" src="/zoopark/assets/images/pause.png"> 
									<span id="gamedate">
										<c:set var="ht" value="${fn:split(sessionScope.user.turn, ',')}" />
										HEURE : ${ ht[0] } JOUR : ${ ht[1] }
									</span>
								</li>
							</ul>
						</nav>
					</div>
				</header>
				<div id="main-content">
					
				</div>
				<footer id="bottom-menu">
					<button name="construction" id="construction">Build</button>
					<button name="gestion_personnel" id="gestion_personnel">Staff</button>
					<button name="consommables" id="consommables">Consumables</button>
					<button name="finances" id="finances">Finances</button>
					<button name="detail_enclos" id="detail_enclos">Enclosures details</button>
					<button name="aide" id="aide">Help</button>
				</footer>
			</c:if>
			<%-- Si pas de joueur en session --%>
			<c:if test="${ empty sessionScope.user }">
				<header id="main-header">
					<div id="page-header"></div>
				</header>
				<div id="main-content">
					
				</div>
				<footer id="bottom-menu"> 
				</footer>
			</c:if>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/time.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/notifs.js"></script>
</body>

</html>