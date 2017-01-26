<%@ include file="/WEB-INF/parts/header.jsp" %>
	
	<div id="main-content">
		<div id="finance_status" class="container">
		<div class="row">
		<div class="col-md-6">
			<h2>Finances menu</h2><br>
			<div>
				<h3>Current money :</h3>
				<span class="money"><img src="${pageContext.request.contextPath}/assets/images/coins.png" alt="logo" /></span>
				<span class="currentMoney">${ sessionScope.user.money } Z</span>
			</div>
			<div>
				<h3>Current loan :</h3>
				<span class="money"><img src="${pageContext.request.contextPath}/assets/images/coins.png" alt="logo" /></span>
				<span class="currentLoan"><!-- current loan -->Z</span>
			</div>
			
			<form action="financeManagement" method="post" id="FormFinanceValidation">
				<div>
					<h3>Want to loan ?</h3>
					<span><img src="${pageContext.request.contextPath}/assets/images/loan.png" alt="logo" /></span>
					<span class="newLoan"><input type="number" value="0" min="0" max="1000000" step="10000" name="loanValue" class="mod"/></span>
				</div>
		</div>
				<div class="col-md-6">
				<div id="transactions_details">
					<h3>Transactions Details</h3><br>
					<div id="transactions-list"> <!-- zone d'affichage des transactions -->
					</div>
				</div>
				</div>
				
				<div>
					<h3>Validate / Cancel</h3>
					<div class= "decision_menu">
						<span id="decision">
							<input type="submit" value="" class="validate_img">
							<a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" /></a>
						</span>
					</div>
					<div class="error">
						
					</div>
				</div>
		</div>
			</form>
		</div>
	
	</div>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
	
