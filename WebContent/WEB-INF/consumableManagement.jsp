<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/consumableManagement.css" />
		<title>Consumable_management</title>
	</head>
	
	<body>
		<div id="finance_status">
			<h1>Consumable menu</h1><br>
			<div>
				<h2>Current stock :</h2><br>
				<span class="meat"><img src="${pageContext.request.contextPath}/assets/images/meat.png" alt="logo" /></span>
				<span class="currentMeatStock"><!-- quantite en stock --></span>
			</div>

			<form action="consumableManagement" method="post" id="FormConsumableValidation">
				<div>
					<h2>Want to buy ?</h2><br>
					<span><img src="${pageContext.request.contextPath}/assets/images/meat.png" alt="logo" /></span>
					<span class="newLoan"><input type="number" value="0" min="<!-- en stock -->" name="meat_quantity" class="mod"/></span>
				</div>
				<div>
					<h2>Validate / Cancel</h2>
					<div class= "decision_menu">
						<span id="decision">
							<input type="submit" value="" class="validate_img">
							<a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" /></a>
						</span>
					</div>
					<div class="error">
						
					</div>
				</div>
			</form>
		</div>
	</body>
	
	<script src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
	<script src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
	<script src="${pageContext.request.contextPath}/assets/scripts/consumableManagement.js"></script>

</html>