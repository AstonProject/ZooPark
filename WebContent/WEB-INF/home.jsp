<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="fr">

<head>
<meta charset="utf-8">
<title>ZooPark</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/global.css">
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
								<li><a href="notifications">Messages</a></li>
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
				<div id="main-content">
					<h1>ZooPark</h1>
					<div id="zoo-container">
					<%-- Parcours abscisse i + ordonnée j --%>
						<c:forEach var="i" begin="1" end="5">
							<c:forEach var="j" begin="1" end="5">
								<%-- 1 emplacement du zoo --%>
								<span class="zoo-site" id="zoo-site-${ i }-${ j }">
								</span>
							</c:forEach>
							<br>
						</c:forEach>
					</div>
				</div>
				<footer id="bottom-menu">
					<button name="construction" id="construction">Build</button>
					<button name="gestion_personnel" id="enclosureManagement">Staff</button>
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
					<h1>ZooPark</h1>
					<div id="zoo-container">
						<div id="register">
						<h2>Inscription</h2>
							<form method="post" id="register_form" action="user?action=register">
								<ul>
									<li> ${ valid.resultat } </li>
									<li><label for="reg_pseudo">Pseudo :</label></li>
									<li><input type="text" name="reg_pseudo" id="reg_pseudo">
									<br><span>${valid.erreurs['pseudo']}</span></li>

									<li><label for="reg_password">Password :</label></li>
									<li><input type="text" name="reg_password" id="reg_password">
									<br><span>${valid.erreurs['password']}</span></li>

									<li><label for="reg_confirmation">Confirm password :</label></li>
									<li><input type="text" name="reg_confirmation" id="reg_confirmation">
									<br><span>${valid.erreurs['confirmation']}</span></li>
		
									<li><label for="reg_email">Email :</label></li>
									<li><input type="text" name="reg_email" id="reg_email">
									<br><span>${valid.erreurs['email']}</span></li>
		
									<li><input type="submit" value="inscription"></li>
								</ul>
							</form>
						</div>
						<div id="connect">
						<h2>Connexion</h2>
							<form method="post" id="connection_form" action="user?action=connect">
								<ul>
									<li>${ erreur }</li>
									<li><label for="con_pseudo">Pseudo :</label></li>
									<li><input type="text" name="con_pseudo" id="con_pseudo"></li>
									<li><label for="con_password">Password :</label></li>
									<li><input type="text" name="con_password" id="con_password"></li>
									<li><input type="submit" value="connexion"></li>
								</ul>
							</form>
						</div>
					</div>
				</div>
				<footer id="bottom-menu"> 
				</footer>
			</c:if>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/home.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/time.js"></script>
</body>

</html>
