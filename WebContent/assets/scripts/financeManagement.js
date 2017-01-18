


(function($) {
	"use strict";

	//fonction pour valider le pret
	
	//fonction pour calculer le cout total du pret
		
	//fonction pour decompter les interets jusqu'au remboursement final
	
	//fonction pour recuperer les actions de la db
	function getTransactions() {
		var statusGT = "okGT";
		
		var callback = function(donnees) {
			console.log(donnees)
			
			var $blockMessages = $('#transactions-list');
			if (donnees.data) {
				for (let finance of donnees.data) {
					console.log(finance);
					$blockMessages.prepend("<div> Le" + finance.date + ", " + finance.type_action + " de " + finance.libelle + "d'une valeur de " + finance.somme + " Z.</div>"); // append -> affiche en dernier ; prepend -> affiche en premier
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

	function validLoan() {
		var statusL = "okL";
		
		var callback = function(donnees) {
			console.log(donnees)
			
		}
		
		var monObj = {
			"statusL" : statusL
		};
	}
	
	$(document).ready(function() {
		//fonctions à appeler
		getTransactions();
	})
})(jQuery);
