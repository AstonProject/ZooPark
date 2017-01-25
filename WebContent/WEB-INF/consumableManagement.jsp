<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/consumableManagement.css" />
		<title>ConsumablesManagement</title>
	</head>

	<body>
		<h2>Your Consumables</h2>
		<div id="consumables_container" class="cc1">
			<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/meat.png" alt="logo" /></span>
			<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/fish.png" alt="logo" /></span>
			<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/straw_bale.png" alt="logo" /></span>
		</div>

		<div id="consumables_container" class="cc2">
			<p>Current Stock</p>
			<span class="meat_stock"></span>
			<span class="fish_stock"></span>
			<span class="straw_bale_stock"></span>
		</div>

		<h2>Purchase / Sale</h2>
		<form action="" method="post" id="FormConsumableValidation">
			<div id="consumables_container" class="cc3">
				<span><input type="number" min="0" value="0" name="quantityMeat" class="mod" id="meat_quantity" /></span>
				<span><input type="number" min="0" value="0" name="quantityFish" class="mod" id="fish_quantity" /></span>
				<span><input type="number" min="0" value="0" name="quantityStrawBale" class="mod" id="straw_bale_quantity" /></span>
			</div>
	
			<br><h3>Price</h3>
			<div class="showPrice">
				<div class="cons_price">0</div>
			</div>
			
			<br><div class="decision_menu">
				<span id="decision"> 
					<input type="submit" value="" class="validate_img">
					<a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" /></a>
				</span>
				<div class="error"></div>
			</div>
		</form>

		<script src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
		<script src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
		<script src="${pageContext.request.contextPath}/assets/scripts/consumableManagement.js"></script>
	</body>

</html>