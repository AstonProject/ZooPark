<%@ include file="/WEB-INF/parts/header.jsp" %>
<div id="main-content">
	<h2>Enclosure Management</h2>
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<h3>Status</h3>
				<div class="row">
					<div class="col-md-6">
					<div id="animal_status" >
					<span class="animal">test image animal</span>
					<span class="currentQuantity">test quantity</span>
					<span class="maxQuantity">test capacity</span>
					</div>
					</div>
				
					<div class="col-md-6">
					<div id="staff_status">
					<span class="staff_img"><img src="${pageContext.request.contextPath}/assets/images/worker.png" alt="logo" /></span>
					<span id="slotE" class="slot1"></span>
					<span id="slotE" class="slot2"></span>
					<span class="EmployeeSlot">
						<select name="employee" size="1" class="selectEmployee">
				 		<option value=1>Assign / Remove</option>
						</select>
					</span>
					</div>
					</div>
				</div>

					<div id="food_status">
						<span class="food_img"><img src="${pageContext.request.contextPath}/assets/images/meat.png" alt="logo" /></span>
						<meter low="0" high="75" max="100" min="0" value="10" class="hungry"></meter>
						<span class="food_gauge"></span>
					</div>
					<br>
					<div id="health_status">
					<span class="health_img"><img src="${pageContext.request.contextPath}/assets/images/health.png" alt="logo" /></span>
					<meter low="25" high="100" max="100" min="0" value="50" class="health"></meter>
					<span class="health_gauge"></span>
					</div>
					<br>
					<div id="cleanness_status">
					<span class="cleanness_img"><img src="${pageContext.request.contextPath}/assets/images/cleanness.png" alt="logo" /></span>
					<meter low="25" high="100" max="100" min="0" value="100" class="cleanness"></meter>
					<span class="cleanness_gauge"></span>
					</div>
			</div>
		

	
		<div class="row">
			<div class="col-md-6">
				<h3>Purchase / Resale</h3>
					<form action="enclosureManagement" method="post" id="FormPurchaseAnimals">
						<div id="animal_purchase">
							<span class="animal"></span>
							<span class="buyingQuantity"><input type="number" max="15" min="-15" value="0" name="quantity" class="mod" id="a_quantity" /></span>
						</div>
					
						<br>
						<div id="enclosure_resale"> Resale enclosure? <input type="checkbox" name="ecl_resale" id="resale_all">
						</div>
						<div id="enclosure_upgrade"> Upgrade enclosure? <input type="checkbox" name="ecl_upgrade" id="upgrade_ecl">
						</div>		
						<br>
				<h3>Price</h3>
				<div class="row">
					<div class="col-md-5">
					</div>
					<div class="col-md-2">
					<div class="showPrice">
					<div class="ae_price">0</div>
					</div>
					</div>
				
					<div class="col-md-4">
					<div class="decision_menu">
					<span id="decision"> <input type="submit" value="" class="validate_img"><a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" /></a></span>
					<div class="error"></div>
					</div>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/parts/footer.jsp" %>
