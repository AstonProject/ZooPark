<c:if test="${ !empty sessionScope.user }">
	<footer id="bottom-menu">
		<button name="construction" id="construction">Build</button>
		<button name="gestion_personnel" id="gestion_personnel">Staff</button>
		<button name="consommables" id="consommables">Consumables</button>
		<button name="finances" id="finances">Finances</button>
		<button name="detail_enclos" id="detail_enclos">Enclosures details</button>
		<button name="aide" id="aide">Help</button>
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
	<c:if test="${ pageContext.request.servletPath == '/WEB-INF/managementEnclosure.jsp' }">
	<script src="${pageContext.request.contextPath}/assets/scripts/managementEnclosure.js"></script>
	</c:if>
</body>

</html>