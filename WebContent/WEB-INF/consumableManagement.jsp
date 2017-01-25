<%@ include file="/WEB-INF/parts/header.jsp" %>
	<div id="main-content">
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
	</div>
<%@ include file="/WEB-INF/parts/footer.jsp" %>