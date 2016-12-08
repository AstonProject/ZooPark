<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
			<header id="main-header">
				<div id="page-header"></div>
				<div id="top-menu">
					<nav>
						<ul>
							<li>Argent : 0 Z</li>
							<li>Employés : 0</li>
							<li><a href="#">Satisfaction</a></li>
							<li><a href="#">Messages</a></li>
							<li>lecture | avance rapide | stop</li>
							<li>Date</li>
						</ul>
					</nav>
				</div>
			</header>
			<div id="main-content">
				<h1>ZooPark</h1>
				<div id="zoo-container">
					<c:forEach var="i" begin="1" end="25">
						<span class="zoo-site" id="zoo-site-${ i }">
							<c:if test="${ empty construction }"><a href="createEnclosure">Construire</a></c:if>
							<c:if test="${ !empty construction }">Bâtiment</c:if>
						</span>
						<c:if test="${ i % 5 == 0 }">
							<br>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<footer id="bottom-menu">
				<button name="construction" id="construction">Construction</button>
				<button name="gestion_personnel" id="gestion_personnel">Gestion
					du personnel</button>
				<button name="consommables" id="consommables">Consommables</button>
				<button name="finances" id="finances">Finances</button>
				<button name="detail_enclos" id="detail_enclos">Détail des
					Enclos</button>
				<button name="aide" id="aide">Aide</button>
			</footer>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
</body>

</html>
