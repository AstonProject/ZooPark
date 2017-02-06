(function($) {
	"use strict";
	
	// fonction pour recuperer les transactions de la bdd
	function getTransactions() {
		var statusGT = "okGT";
		var transaction = "";
		
		var callback = function(donnees) {
			console.log(donnees)
			
			var $blockMessages = $('#transactions-list');
			$blockMessages.empty();
			if (donnees.data) {
				for (let finance of donnees.data) {
					console.log(finance);
					
					switch(finance.type_action) {
						
					// cas d'une destruction d'enclos
					case "destruction":
						switch(finance.animals_number) {
						
							// destruction d'un enclos vide
							case "0":
								transaction = "<div> Turn " + finance.turn + " : " + finance.type_action + " of " + finance.libelle + " (value : " + finance.somme + " Z).</div>";
								break;
						
							// destruction d'un enclos non vide
							default:
								transaction = "<div> Turn " + finance.turn + " : " + finance.type_action + " of " + finance.libelle + " and sale of " + finance.animals_number + " " + finance.animals + " (value : " + finance.somme + " Z).</div><div>";
						}
						break;
					// autres cas
					default:
						switch(finance.animals_number) {
						
							// cas des transactions sans unite (construction, upgrade, loan, refund, income)
							case "0":
								transaction = "<div> Turn " + finance.turn + " : " + finance.type_action + " of " + finance.libelle + " (value : " + finance.somme + " Z).</div>";
								break;
						
							// cas des transactions avec 1 unite (purchase, sale, recruitment)
							case "1":
								transaction = "<div> Turn " + finance.turn + " : " + finance.type_action + " of " + finance.animals_number + " " + finance.libelle + " (value : " + finance.somme + " Z).</div>";
								break;
						
							// cas des transactions avec plusieurs unites (purchase, sale, recruitment)
							default:
								transaction = "<div> Turn " + finance.turn + " : " + finance.type_action + " of " + finance.animals_number + " " + finance.libelle + "s (value : " + finance.somme + " Z).</div>";
						}
					}
					
					// affichage : append (en dernier), prepend (en premier)
					$blockMessages.prepend(transaction);
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
	function showCurrentLoans() {
		var statusSCL = "okSCL";
		
		var $blockMessages = $('.currentLoan');
		
		var callback = function(donnees) {
			
			console.log("current_loans_value : " + donnees.currentLoansValue);
			
			$blockMessages.empty();
			$blockMessages.prepend(donnees.currentLoansValue + " Z");
		};
		
		var monObj = {
			"statusSCL" : statusSCL
		};
	
		server.monAjax(monObj, "financeManagement", callback, 'POST');
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
			var callback=function(donnees) {
				if (donnees.code == "OK") {
					 window.location.href = "financeManagement";
				} else if (donnees.code == "ERROR") {
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
	
	// fonction pour le remboursement
	function refundLoanMonthly() {
		var statusRLM = "okRLM";
		
		var callback = function(donnees) {
			
		}
		
		var monObj = {
				"statusRLM" : statusRLM
			};
		
			server.monAjax(monObj, "financeManagement", callback, 'POST');
		
	}
	
	$(document).ready(function() {
		// fonctions à appeler
		showCurrentLoans();
		refundLoanMonthly()
		getTransactions();
		getFinanceForm();
	})
	
})(jQuery);
