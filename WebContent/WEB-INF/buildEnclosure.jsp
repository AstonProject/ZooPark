<%@ include file="/WEB-INF/parts/header.jsp" %>
<div id="main-content">
<h2>Building menu</h2>
	<div id="zoo-container" class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-7">
						<div class="enclosureChoice">
						<form action="createEnclosure" method="post" id="FormCreateEnclosure">
						<h3>Enclosure type</h3>
						</div>
						<div id="radio1">
							<input type="radio" name="enclosureType" value="Elephant" id="radio_elephant"> <img src="${pageContext.request.contextPath}/assets/images/Contruction_menu_elephant.png" alt="logo" /> <input	type="radio" name="enclosureType" value="Giraffe" id="radio_giraffe"> <img src="${pageContext.request.contextPath}/assets/images/Contruction_menu_giraffe.png" alt="logo" /> <input	type="radio" name="enclosureType" value="Lion" id="radio_lion"><img src="${pageContext.request.contextPath}/assets/images/Contruction_menu_lion.png" alt="logo" /> <input type="radio" name="enclosureType" value="Camel" id="radio_camel"> <img src="${pageContext.request.contextPath}/assets/images/Contruction_menu_camel.png" alt="logo" />
						</div>
						<h3 class="Enclosure-Description-Title">Description</h3>
						<div class="enclosureDescription"></div>
					</div>
					<div class="col-md-1">
					</div>					
					<div class="col-md-4">
						<h3>Enclosure size</h3>
						<br>
						<div id="radio2">
							<input type="radio" name="enclosureSize" value="1" id="size_1"> 1 <input type="radio" name="enclosureSize" value="2" id="size_2"> 2 <input type="radio" name="enclosureSize" value="3" id="size_3"> 3</div>
						<br><br>
						<h3>Price</h3>
						<br>
							<div class="row">
								<div class="col-md-2">
								</div>
								<div class="col-md-4">
									<div class="showPrice">
										<div class="price"></div>
									</div>
								</div>
								<div class="col-md-4">
									<div class= "decision_menu">
										<span id="decision">
										<input type="submit" value="" class="validate_img"><a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" class="cancel" /></a>		
										</span>
										<div class="error"></div>
									</div>
								</div>
							</div>		
					</div>
			</div>
						</form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
