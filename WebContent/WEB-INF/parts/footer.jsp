<c:if test="${ !empty sessionScope.user }">
	<footer id="bottom-menu">
		<button name="gestion_personnel" id="employeesManagement">Staff</button>
		<button name="consommables" id="consommables">Consumables</button>
		<button name="finances" id="finances">Finances</button>
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
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/home.jsp' }">
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/home.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/time.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/buildEnclosure.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/buildEnclosure.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/enclosureManagement.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/managementEnclosure.js"></script>
	</c:if>
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/consumableManagement.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/consumableManagement.js"></script>
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