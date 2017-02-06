<%@ include file="/WEB-INF/parts/header.jsp" %>
	<div id="main-content">
	<h2>Consumable menu</h2>
	
		<div id="finance_status" class="container">
			<div class="row">
				<div class="col-md-6">
					<h3>Current stock :</h3><br>
					
						<div class="row">
							<div class="col-md-4">
							<span class="meat"><img src="${pageContext.request.contextPath}/assets/images/meat.png" alt="logo" /></span>
							</div>
							<div class="col-md-4">
							<span class="fish"><img src="${pageContext.request.contextPath}/assets/images/fish.png" alt="logo" /></span>
							</div>
							<div class="col-md-4">
							<span class="straw_bale"><img src="${pageContext.request.contextPath}/assets/images/straw_bale.png" alt="logo" /></span>
							</div>
						</div>
						<br>
			
						<div id="consumables_container" class="row">
							<div class="col-md-4">
								<span class="meat_stock"></span>
							</div>
							<div class="col-md-4">
								<span class="fish_stock"></span>
							</div>
							<div class="col-md-4">
								<span class="straw_bale_stock"></span>
							</div>
						</div>
				</div>
			
		<div class="row">	
			<div class="col-md-6">
				<h3>Purchase / Sale</h3>
				<br>
						<div class="row">
							<div class="col-md-3">
							</div>
							<div class="col-md-3">
								<span class="meat"><img src="${pageContext.request.contextPath}/assets/images/meat.png" alt="logo" /></span>
							</div>
							<div class="col-md-3">
								<span class="fish"><img src="${pageContext.request.contextPath}/assets/images/fish.png" alt="logo" /></span>
							</div>
							<div class="col-md-3">
								<span class="straw_bale"><img src="${pageContext.request.contextPath}/assets/images/straw_bale.png" alt="logo" /></span>
							</div>
						</div>
						<br>
					<form action="" method="post" id="FormConsumableValidation">
						<div id="consumables_container" class="cc3">
							<div class="col-md-4">
								<span><input type="number" min="0" value="0" name="quantityMeat" class="mod" id="meat_quantity" /></span>
							</div>
							<div class="col-md-4">
								<span><input type="number" min="0" value="0" name="quantityFish" class="mod" id="fish_quantity" /></span>
							</div>
							<div class="col-md-4">
								<span><input type="number" min="0" value="0" name="quantityStrawBale" class="mod" id="straw_bale_quantity" /></span>
							</div>
						</div>
						
					<br><br>
					<h3>Price</h3>
					<br>
						<div class="row">
							<div class="col-md-6">
								<div class="showPrice">
								<div class="cons_price">0</div>
								</div>
							</div>
		
						
						<div class="col-md-6">
							<div class="decision_menu">
								<span id="decision">
								<input type="submit" value="" class="validate_img">
								<a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" class="cancel" /></a>
								</span>
						<div class="error">
						</div>
						</div>
					</div>
				</div>
					</form>
			</div>
		</div>
	</div>
</div>
</div>
<%@ include file="/WEB-INF/parts/footer.jsp" %>