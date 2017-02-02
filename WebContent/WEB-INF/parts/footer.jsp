<c:if test="${ !empty sessionScope.user }">
	<footer id="bottom-menu">
		<div class="container">
			<div class="row">
				<div class="col-md-4">
					<button name="gestion_personnel" id="employeesManagement">Staff</button>
				</div>
		
		
				<div class="col-md-4">
					<button name="consommables" id="consommables">Consumables</button>
				</div>
		
			
				<div class="col-md-4">
					<button name="finances" id="finances">Finances</button>
				</div>
			</div>
		</div>
	</footer>
</c:if>
<c:if test="${ empty sessionScope.user }">
	<footer id="bottom-menu"> 
	</footer>
</c:if>
</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/visitorsManagement.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/enclosureMTime.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/time.js"></script>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/home.jsp' }">
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/home.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/buildEnclosure.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/buildEnclosure.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/enclosureManagement.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/managementEnclosure.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/employeesManagement.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/employeesManagement.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/financeManagement.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/financeManagement.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/consumableManagement.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/consumableManagement.js"></script>
	</c:if>
</body>

</html>