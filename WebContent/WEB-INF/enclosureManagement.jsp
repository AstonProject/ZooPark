<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/styles/enclosureManagement.css" />
<title>Enclosure_management</title>
</head>
<body>
	<h1>Enclosure Management</h1>
	<br>
	<h2>Status</h2>
	<br>

	<div id="animal_status">
		<span class="animal">test image animal</span> <span
			class="currentQuantity">test quantity</span> <span
			class="maxQuantity">test capacity</span>

	</div>
	<br>

	<div id="staff_status">
		<form action="enclosureManagement" method="post">
			<span class="staff_img"><img
			src="${pageContext.request.contextPath}/assets/images/worker.png"
			alt="logo" />	
			</span>
			<span class="slot1">
				<select name="employee1" size="1" class="selectEmployee">
				 	<option value="e0"></option>
				 	
				</select>
			</span>
			<span class="slot2">
				<select name="employee2" size="1" class="selectEmployee">
				 	<option value="e0"></option>
				 	
				</select>
			</span>
		</form>
		
	</div>
	<br>

	<div id="food_status">
		<span class="food_img"><img
			src="${pageContext.request.contextPath}/assets/images/meet.png"
			alt="logo" /></span>

		<meter low="0" high="75" max="100" min="0" value="10" class="hungry"></meter>
		<span class="food_gauge"></span>
	</div>
	<br>

	<div id="health_status">
		<span class="health_img"><img
			src="${pageContext.request.contextPath}/assets/images/health.png"
			alt="logo" /></span>
		<meter low="25" high="100" max="100" min="0" value="50" class="health"></meter>
		<span class="health_gauge"></span>
	</div>
	<br>

	<div id="cleanness_status">
		<span class="cleanness_img"><img
			src="${pageContext.request.contextPath}/assets/images/cleanness.png"
			alt="logo" /></span>
		<meter low="25" high="100" max="100" min="0" value="100"
			class="cleanness"></meter>
		<span class="cleanness_gauge"></span>
	</div>
	<br>

	<h2>Purchase / Resale</h2>
	<form action="enclosureManagement" method="post"
		id="FormPurchaseAnimals">
		<div id="animal_purchase">
			<span class="animal"></span><span class="buyingQuantity"><input
				type="number" max="15" min="-15" value="0" name="quantity"
				class="mod" id="a_quantity" /></span>
		</div>
		<br>
		<div id="enclosure_resale">
			Resale enclosure? <input type="checkbox" name="ecl_resale"
				id="resale_all">
		</div>
		<br>
		<h3>Price</h3>
		<div class="showPrice">
			<div class="ae_price">0</div>
		</div>
		<br>
		<div class="decision_menu">
			<span id="decision"> <input type="submit" value=""
				class="validate_img"><a href="home"><img
					src="${pageContext.request.contextPath}/assets/images/cancel.png"
					alt="logo" /></a>
			</span>
			<div class="error"></div>
		</div>
	</form>
</body>
<script
	src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
<script src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/scripts/managementEnclosure.js"></script>
</html>