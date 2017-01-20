<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/financeManagement.css" />
		<title>Finances_management</title>
	</head>
	
	<body>
		<div id="finance_status">
			<h1>Finances menu</h1><br>
			<div>
				<h2>Current money :</h2><br>
				<span class="money"><img src="${pageContext.request.contextPath}/assets/images/coins.png" alt="logo" /></span>
				<span class="currentMoney">${ sessionScope.user.money } Z</span>
			</div>
			<div>
				<h2>Current loan :</h2><br>
				<span class="money"><img src="${pageContext.request.contextPath}/assets/images/coins.png" alt="logo" /></span>
				<span class="currentLoan"><!-- current loan -->Z</span>
			</div>
			
			<form action="financeManagement" method="post" id="FormFinanceValidation">
				<div>
					<h2>Want to loan ?</h2><br>
					<span><img src="${pageContext.request.contextPath}/assets/images/loan.png" alt="logo" /></span>
					<span class="newLoan"><input type="number" value="0" min="0" max="1000000" step="10000" name="loanValue" class="mod"/></span>
				</div>
				<div id="transactions_details">
					<h2>Transactions Details</h2><br>
					<div id="transactions-list"> <!-- zone d'affichage des transactions -->
					</div>
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
	<script src="${pageContext.request.contextPath}/assets/scripts/financeManagement.js"></script>

</html>