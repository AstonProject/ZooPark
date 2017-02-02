<%@ include file="/WEB-INF/parts/header.jsp" %>
	<div id="main-content">
		<div id="finance_status">
			<h1>Consumable menu</h1><br>
			<div>
				<h2>Current stock :</h2><br>
				<span class="meat"><img src="${pageContext.request.contextPath}/assets/images/meat.png" alt="logo" /></span>
				<span class="currentMeatStock"><!-- quantite en stock --></span>
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
	</div>
	</div>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
