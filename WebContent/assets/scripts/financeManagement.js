


(function($) {
	"use strict";
	
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
					$blockMessages.prepend("<div> Tour " + finance.turn + ", " + finance.type_action + " de " + finance.libelle + " d'une valeur de " + finance.somme + " Z.</div>"); // append -> affiche en dernier ; prepend -> affiche en premier
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

	// fonction pour valider le pret
	function validLoan() {
		var statusL = "okL";
		
		var callback = function(donnees) {
			console.log(donnees)
			
		}
		
		var monObj = {
			"statusL" : statusL
		};
	}
	
	//Fonction de récupération des données : 
	function getForm(){
		var $formCE = $('#FormFinanceValidation');
		var newLoan = 0;
		var toRefund = 0;
		var payMonthly = 0;
		
		var type= null;
		var size= null;
		var capacity=null;
		var specie_id= null;
		var statusF = "okF";
				
		$formCE.on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();
			
			// Recuperation des valeurs de newLoan
			newLoan = $('input[name=loanValue]').val();
			
			// Creation de la somme à rembourser
			toRefund = newLoan*1.25;
			payMonthly = toRefund/10;
			
			//Modification de FK_specie_id de l'enclos selon le radio EnclosureType selectionne
			if(type == "Elephant"){
				specie_id = 1;
			}else if(type == "Giraffe"){
				specie_id = 2;
			} else if (type == "Lion"){
				specie_id = 3;
			}else if (type == "Camel"){
				specie_id = 4;
			}
			
			var callback=function(donnees){
				if(donnees.code == "OK"){
					 window.location.href = "home";
                }else if (donnees.code == "ERROR"){
                	 failed();
                }
			};
			
			//Remplissage de l'objet a envoyer
			var monObjet ={
					"specie_id":specie_id,
					"capacity":capacity,
					"statusForm":statusF
			};
			if(specie_id = null || capacity != null){
				server.monAjax(monObjet, "createEnclosure", callback, 'POST');
			}else{
				$blockError.empty();
				$blockError.prepend("<h2> Please, select a type and a size to bluid an enclosure... </h2>");
			}
			
		});
	
	
	$(document).ready(function() {
		//fonctions à appeler
		getTransactions();
	})
})(jQuery);
