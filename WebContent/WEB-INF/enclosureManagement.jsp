<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/enclosureManagement.css" />
<title>Enclosure_management</title>
</head>
<body>
<h1>Building menu</h1>
<br>
<h2>Status</h2>
<br>

<div class="animal_status">
<span id="animal"></span>test image animal<span id="currentQuantity">test quantity</span>
</div>
<br>

<div class="staff_status">
<span id="staff_img"><img src="${pageContext.request.contextPath}/assets/images/worker.png" alt="logo" /></span>
<span id="slot1">slot1</span><span id="slot2">slot2</span>
</div>
<br>

<div class="food_status">
<span id="food_img"><img src="${pageContext.request.contextPath}/assets/images/meet.png" alt="logo" /></span>
<span id="food_gauge">test food gauge</span>
</div>
<br>

<div class="health_status">
<span id="health_img"><img src="${pageContext.request.contextPath}/assets/images/health.png" alt="logo" /></span>
<span id="health_gauge">test health gauge</span>
</div>
<br>

<div class="cleanness_status">
<span id="cleanness_img"><img src="${pageContext.request.contextPath}/assets/images/cleanness.png" alt="logo" /></span>
<span id="cleanness_gauge">test health gauge</span>
</div>
<br>

<h2>Purchase</h2>
<form action="enclosureManagement" method="post" id="FormPurchaseAnimals">
	<div class="animal_purchase">
	<span id="animal"></span>test image animal           <span id="buyingQuantity"><input type="number" max="15" min="1" value="1" class="mod"/></span>
	</div>
	<br>
	<div class= "decision_menu">
		<span id="decision">
			<input type="submit" value="" class="validate_img"><a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" /></a>		
		</span>
		<div class="error"></div>
	</div>
</form>


</body>
	<script src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
	<script src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
	<script src="${pageContext.request.contextPath}/assets/scripts/managementEnclosure.js"></script>
</html>