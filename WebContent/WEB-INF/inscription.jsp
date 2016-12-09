<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="inc/style.css" media="screen">
		<title>Inscription au serveur</title>
	</head>
	<body>
		<form method="post" action="inscription">
			<fieldset>
				<legend>Veuillez vous identifier</legend>
				<p>Formulaire d'inscription</p>
				
				<label for="pseudo"> Pseudo Joueur <span class="requis">*</span></label>
				<input type="text" name="pseudo" value="" size="20" maxlength="20" placeholder="Votre pseudo" /> <br />	
				
				<label for="email"> Adresse email <span class="requis">*</span></label>
				<input type="text" name="email" value="" size="20" maxlength="60" placeholder="Votre email" /> <br />
				
				<label for="password"> Mot de passe <span class="requis">*</span></label>
				<input type="password" name="password" value="" size="20" maxlength="20" /> <br />
				
				<label for="confirmation"> Confirmation du mot de passe <span class="requis">*</span></label>
				<input type="password" name="confirmation" value="" size="20" maxlength="20" /> <br />
				
				<input type="submit" value="Valider" class="sansLabel" /> <br />
			</fieldset>
		</form>
	</body>
</html>