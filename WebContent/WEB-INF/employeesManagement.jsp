<%@ include file="/WEB-INF/parts/header.jsp" %>
<div id="main-content">
	
	<div id="employees_container" class="container">
		<div class="row">
			<div class="col-md-6">
				<h2>Your employees</h2><br>
		
				<div class="row">
					<div class="col-md-4">
					<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/healer.png" alt="logo" /></span>
					</div>
					<div class="col-md-4">
					<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/cleaner.png" alt="logo" /></span>
					</div>
					<div class="col-md-4">
					<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/security.png" alt="logo" /></span>
					</div>
				</div>
				<br>
		
		
				<div class="row">
					<div class="col-md-4">
						<div id="employees_container"><p>Assigned</p><span class="assigned_healer"></span>
						</div>
					</div>
					<div class="col-md-4">
						<div id="employees_container"><p>Assigned</p><span class="assigned_cleaner"></span>
						</div>
					</div>
					<div class="col-md-4">
						<div id="employees_container"><p>Assigned</p><span class="assigned_security"></span>
						</div>
					</div>
				</div>
				<br>
		
				<div class="row">
					<div class="col-md-4">		
						<div id="employees_container"><p>Unassigned</p><span class="unassigned_healer"></span>
						</div>
					</div>
					<div class="col-md-4">
						<div id="employees_container"><p>Unassigned</p><span class="unassigned_cleaner"></span>
						</div>
					</div>
					<div class="col-md-4">
						<div id="employees_container"><p>Unassigned</p><span class="unassigned_security"></span>
						</div>
					</div>
				</div>
				<br>
		
		
				<div class="row">
					<div class="col-md-4">
						<div id="employees_container"><p>Total</p><span class="total_healer"></span>
						</div>
					</div>
					<div class="col-md-4">
						<div id="employees_container"><p>Total</p><span class="total_cleaner"></span>
						</div>
					</div>
					<div class="col-md-4">
						<div id="employees_container"><p>Total</p><span class="total_security"></span>
						</div>
					</div>
				</div>
				<br>
			</div>
		
			
		<div class="row">
			<div class="col-md-6">
				<h2>Engage / Dismiss</h2>
				<br>
					
						<div class="row">
							<div class="col-md-4">
								<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/healer.png" alt="logo" /></span>
							</div>
							<div class="col-md-4">
								<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/cleaner.png" alt="logo" /></span>
							</div>
							<div class="col-md-4">
								<span class="img_container"><img src="${pageContext.request.contextPath}/assets/images/security.png" alt="logo" /></span>
							</div>
						</div>
						<br>
					<form action="" method="post" id="FormEngageDismiss">
							<div class="row">
								<div class="col-md-4">
									<span><input type="number" max="0" min="0" value="0" name="quantityHeal" class="mod" id="heal_quantity" /></span>
								</div>
								<div class="col-md-4">
									<span><input type="number" max="0" min="0" value="0" name="quantityClean" class="mod" id="clean_quantity" /></span>
								</div>
								<div class="col-md-4">
									<span><input type="number" max="0" min="0" value="0" name="quantitySecurity" class="mod" id="secu_quantity" /></span>
								</div>
							</div>
					
			<br><br>
			<h3>Price</h3>
			<br>
				<div class="row">
					<div class="col-md-6">
						<div class="showPrice">
						<div class="emp_price">0</div>
						</div>
					</div>
				
				<br>
				
					<div class="col-md-6">
						<div class="decision_menu">
							<span id="decision"> 
							<input type="submit" value="" class="validate_img">
							<a href="home"><img src="${pageContext.request.contextPath}/assets/images/cancel.png" alt="logo" /></a>
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