(function($) {
	"use strict";
	
	// fonction pour recuperer les transactions de la bdd
	function getTransactions() {
		var statusGT = "okGT";
		
		var callback = function(donnees) {
			console.log(donnees)
			
			var $blockMessages = $('#transactions-list');
			$blockMessages.empty();
			if (donnees.data) {
				for (let finance of donnees.data) {
					console.log(finance);
					// affichage : append (en dernier), prepend (en premier)
					$blockMessages.prepend("<div> Turn " + finance.turn + " : " + finance.type_action + " of " + finance.libelle + " (value : " + finance.somme + " Z).</div>");
				}
			}
		};

		var monObj = {
			"statusGT" : statusGT
		};

		setInterval(function() {

			server.monAjax(monObj, "financeManagement", callback, 'POST');

		}, 1500); // durée en millisecondes

	}

	// fonction pour l'affichage du total des prets en cours
	function showTotalLoans() {
		
		
		
	}
	
	
	// fonction pour le remboursement
	function refund() {
		
		// faire un retrait de la somme(payMonthly) tous les mois
		// mettre a jour chaque pret tous les mois
		// supprimer les prets quand ils sont rembourses
		
	}
	
	// fonction pour vider la bdd finance tous les mois
	function clearFinance() {
		
		// supprimer toutes les lignes SAUF les emprunts non rembourses
		
	}
	
	// fonction pour valider le pret + mise a jour compte + insertion bdd
	function getFinanceForm() {
		var statusGFF = "okGFF";
		var $formLoan = $('#FormFinanceValidation');
		
		$formLoan.on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();
			
			// recuperation de la valeur
			var $inputNumber = $formLoan.find('[name="loanValue"]');
			
			var loan = $inputNumber.val();
			$inputNumber.val('');
			console.log("valeur du pret : " + loan);
			
			// creation de la somme et de la mensualite
			var somme = loan*1.25;
			var payMonthly = somme/10;
			
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
				"statusGFF": statusGFF,
				"loan": loan,
				"type_action": "loan",
				"somme": somme,
				"payMonthly": payMonthly
			};
			
			console.log(monObj);
			
			server.monAjax(monObj, "financeManagement", callback, 'POST');
			console.log("envoie des données");
		});
	}
	
	$(document).ready(function() {
		// fonctions à appeler
		getTransactions();
		getFinanceForm();
	})
	
})(jQuery);
