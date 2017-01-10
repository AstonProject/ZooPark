<%@ include file="/WEB-INF/parts/header.jsp" %>
<div id="main-content">
<h1>Building menu</h1>
<br>
<h2>Status</h2>
<br>

<div id="animal_status">
<span class="animal">test image animal</span>
<span class="currentQuantity">test quantity</span>
<span class="maxQuantity">test capacity</span>

</div>
<br>

<div id="staff_status">
<span class="staff_img"><img src="${pageContext.request.contextPath}/assets/images/worker.png" alt="logo" /></span>
<span class="slot1">slot1</span><span class="slot2">slot2</span>
</div>
<br>

<div id="food_status">
<span class="food_img"><img src="${pageContext.request.contextPath}/assets/images/meet.png" alt="logo" /></span>
<span class="food_gauge">test food gauge</span>
</div>
<br>

<div id="health_status">
<span class="health_img"><img src="${pageContext.request.contextPath}/assets/images/health.png" alt="logo" /></span>
<span class="health_gauge">test health gauge</span>
</div>
<br>

<div id="cleanness_status">
<span class="cleanness_img"><img src="${pageContext.request.contextPath}/assets/images/cleanness.png" alt="logo" /></span>
<span class="cleanness_gauge">test health gauge</span>
</div>
<br>

<h2>Purchase</h2>
<form action="enclosureManagement" method="post" id="FormPurchaseAnimals">
	<div id="animal_purchase">
	<span class="animal">v</span>          <span class="buyingQuantity"><input type="number" max="15" min="1"  name ="" class="mod"/></span>
	</div>
	<br>
	<div class= "decision_menu">
		<span id="decision">
			<input type="submit" value="" class="validate_img"><a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" /></a>		
		</span>
		<div class="error"></div>
	</div>
</form>
</div>
<%@ include file="/WEB-INF/parts/footer.jsp" %>