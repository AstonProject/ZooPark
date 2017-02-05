<%@ include file="/WEB-INF/parts/header.jsp" %>
<c:if test="${ !empty sessionScope.user && empty perdu }">
	<div id="main-content">
		<div id="zoo-container">
			<%-- Parcours abscisse i + ordonnée j --%>
			<c:forEach var="i" begin="1" end="5">
				<c:forEach var="j" begin="1" end="5">
					<%-- 1 emplacement du zoo --%>
					<span class="zoo-site" id="zoo-site-${ i }-${ j }">
					</span>
				</c:forEach>
				<br>
			</c:forEach>
		</div>
	</div>
</c:if>
<c:if test="${ !empty sessionScope.user && !empty perdu }">
	<div id="main-content">
		Vous avez perdu trop d'argent, le parc a été fermé.<br><br>
		Cordialement,<br>
		Les investisseurs.
	</div>
</c:if>
<%-- Si pas de joueur en session --%>
<c:if test="${ empty sessionScope.user }">
	<div id="main-content">
	
		<div id="zoo-container" class="container">
		<div class="row">
			<div id="register" class="col-md-6">
				<h2>Inscription</h2>
				<form method="post" id="register_form" action="user?action=register">
					<ul>
						<li> ${ valid.resultat } </li>
						<li><label for="reg_pseudo">Pseudo :</label></li>
						<li><input type="text" name="reg_pseudo" id="reg_pseudo">
						<br><span>${valid.erreurs['pseudo']}</span></li>

						<li><label for="reg_password">Password :</label></li>
						<li><input type="password" name="reg_password" id="reg_password">
						<br><span>${valid.erreurs['password']}</span></li>

						<li><label for="reg_confirmation">Confirm password :</label></li>
						<li><input type="password" name="reg_confirmation" id="reg_confirmation">
						<br><span>${valid.erreurs['confirmation']}</span></li>
		
						<li><label for="reg_email">Email :</label></li>
						<li><input type="text" name="reg_email" id="reg_email">
						<br><span>${valid.erreurs['email']}</span></li>
						<br><li><input type="submit" value="inscription"></li>
					</ul>
				</form>
			</div>
			<div id="connect" class="col-md-6">
				<h2>Connexion</h2>
				<form method="post" id="connection_form" action="user?action=connect">
					<ul>
						<li>${ erreur }</li>
						<li><label for="con_pseudo">Pseudo :</label></li>
						<li><input type="text" name="con_pseudo" id="con_pseudo"></li>
						<li><label for="con_password">Password :</label></li>
						<li><input type="text" name="con_password" id="con_password"></li>
						<br><li><input type="submit" value="connexion"></li>
					</ul>
				</form>
			</div>
			</div>
		</div>
	</div>
</c:if>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
