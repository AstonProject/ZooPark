(function($) {
	"use strict";
	
	// fonction pour valider l'achat + mise a jour bdd
	function getConsumableForm() {
		var statusGCF = "okGCF";
		var $formConsumable = $('#FormConsumableValidation');
		
		$formConsumable.on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();
			
			// recuperation de la valeur
			var $inputNumber = $formConsumable.find('[name=""]');
			
			var quantity = $inputNumber.val();
			$inputNumber.val('');
			console.log("quantité : " + quantity);
			
			// redirection sur le controller home
			var callback=function(donnees){
				if(donnees.code == "OK"){
					 window.location.href = "home";
				}else if (donnees.code == "ERROR"){
					failed();
				}
			};
			
			// creation de l'objet emprunt
			var monObj = {
				"statusGCF": statusGCF
				
			};
			
			console.log(monObj);
			
			server.monAjax(monObj, "consumableManagement", callback, 'POST');
			console.log("envoie des données");
		});
	}
	
	$(document).ready(function() {
		// fonctions à appeler

		//getConsumableForm();
	})
	
})(jQuery);