(function($) {
	"use strict";

	function showEmployeesAssignment(){
		var statusSEA = "okSEA";

		var callback = function(donnees) {
			$('.assigned_healer').prepend((donnees.countHealer_total) - (donnees.countHealer_e0));
			$('.assigned_cleaner').prepend((donnees.countCleaner_total) - (donnees.countCleaner_e0));
			$('.assigned_security').prepend((donnees.countSecurity_total) - (donnees.countSecurity_e0));
			
			$('.unassigned_healer').prepend(donnees.countHealer_e0);
			$('.unassigned_cleaner').prepend(donnees.countCleaner_e0);
			$('.unassigned_security').prepend(donnees.countSecurity_e0);
			
			$('.total_healer').prepend(donnees.countHealer_total);
			$('.total_cleaner').prepend(donnees.countCleaner_total);
			$('.total_security').prepend(donnees.countSecurity_total);
			
			sessionStorage.setItem("countHlT", donnees.countHealer_e0);
			sessionStorage.setItem("countClT", donnees.countCleaner_e0);
			sessionStorage.setItem("countSeT", donnees.countSecurity_e0);
			
			setRestMinEmQty();
		};
		var monObj = {
			"statusSEA" : statusSEA
		};
		server.monAjax(monObj, "employeesManagement", callback, 'POST');
		
		
	}
	
	// fonction pour afficher la quantite d'employees (Max) pouvant etre recrutes
	// et le maximum pouvant etre licencies (Min)
	function setRestMinEmQty() {
		var statusSEQ = 'okSEQ'
		console.log('okSEQ');
			var $inputQuantity = $(".mod");
			var $imputHl = $('#heal_quantity');
			var $imputCl = $('#clean_quantity');
			var $imputSe = $('#secu_quantity');
			var countHlT = 0;
			var countClT = 0;
			var countSeT = 0;
			
			var healerQty = 0;
			var cleanerQty = 0;
			var securityQty = 0;
			
			// Recuperation des valeurs des 3 imputs
			healerQty = $('input[name=quantityHeal]').val();
			cleanerQty = $('input[name=quantityClean]').val();
			securityQty = $('input[name=quantitySecurity]').val();
			
				countHlT = sessionStorage.getItem("countHlT");
				countClT = sessionStorage.getItem("countClT");
				countSeT = sessionStorage.getItem("countSeT");
			
			var callback = function(donnees) {
				
				$imputHl.attr("max", donnees.maxQty - donnees.employeesQty - cleanerQty - securityQty);
				$imputCl.attr("max", donnees.maxQty - donnees.employeesQty - healerQty - securityQty);
				$imputSe.attr("max", donnees.maxQty - donnees.employeesQty - healerQty - cleanerQty);
				$imputHl.attr("min", (countHlT * (-1)));
				$imputCl.attr("min", (countClT * (-1)));
				$imputSe.attr("min", (countSeT * (-1)));
			};
			var object = {
					"statusSEQ" : statusSEQ
				};
				server.monAjax(object, "employeesManagement", callback, 'POST');		
				
	}
	
	function refreshRestMinEmQty($selectedEmQty){
		$($selectedEmQty).on('mouseup', function() {
			setRestMinEmQty();
		
		});
	}
	
	function showEmployeePrice(){
		var statusEmP = "okEmP";
		var $blockEmPrice = $('.emp_price');
		var employees_quantity = 0;
		var employeeType;

			// Reinitialise le contenu du blockPrice
			$blockEmPrice.empty();
			
			var healerQty = 0;
			var cleanerQty = 0;
			var securityQty = 0;
			
			// Recuperation des valeurs des 3 imputs
			healerQty = $('input[name=quantityHeal]').val();
			cleanerQty = $('input[name=quantityClean]').val();
			securityQty = $('input[name=quantitySecurity]').val();
			
			var total_SlE = healerQty + cleanerQty + securityQty;
			var employees_price = 0;
			
			sessionStorage.setItem("total_SlE", total_SlE);	
			sessionStorage.setItem("employees_price", employees_price);
			
			var callback = function(donnees) {
				
				if(healerQty >0){
					employees_price += (donnees.healerCosts * healerQty);
				} 
				if(cleanerQty >0){
					employees_price += (donnees.cleanerCosts * cleanerQty);
				} 
				if (securityQty >0){
					employees_price += (donnees.securityCosts * securityQty);
				}
				employees_price *= (-1);

				sessionStorage.setItem("employees_price", employees_price);
				$blockEmPrice.prepend("<div>"+ employees_price + "</div>");	
			};

			var object = {
				"statusEmP" :  statusEmP
			};
			server.monAjax(object, "employeesManagement", callback, 'POST');
	}
	
	function refreshPriceOnChange($selectedEmQty){
		$($selectedEmQty).on('change', function() {
			showEmployeePrice();
		});
	}
	
	function engageDismiss() {
		var statusEmED = 'okEmED';
		$('#FormEngageDismiss').on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();
			var priceEmED = 0;
			var healerQty = 0;
			var cleanerQty = 0;
			var securityQty = 0;
			
			// Recuperation des valeurs des 3 imputs et du prix (session)
			healerQty = $('input[name=quantityHeal]').val();
			cleanerQty = $('input[name=quantityClean]').val();
			securityQty = $('input[name=quantitySecurity]').val();
			priceEmED = sessionStorage.getItem("employees_price");
			
			if(healerQty == ""){
				healerQty = 0;
			}
			if(cleanerQty == ""){
				cleanerQty = 0;
			}
			if(securityQty == ""){
				securityQty = 0;
			}
			
			var callback = function(donnees) {
				if (donnees.code == "OK") {
					window.location.href = "home";
				} else {
					failed();
				}
			};
			
			var object = {
				"statusEmED" : statusEmED,
				"priceEmED" : priceEmED,
				"healerQty" : healerQty,
				"cleanerQty" : cleanerQty,
				"securityQty" : securityQty
			};

			server.monAjax(object, "employeesManagement", callback, 'POST');
		});
	}
	
	$(document).ready(function() {
		var $heal_button= $('#heal_quantity');
		var $cleaner_button = $('#clean_quantity');
		var $security_button = $('#secu_quantity');
		
		//Attention cet ordre d'appel est important
		showEmployeesAssignment();
		
		refreshPriceOnChange($('#heal_quantity'));
		refreshPriceOnChange($('#clean_quantity'));
		refreshPriceOnChange($('#secu_quantity'));

		engageDismiss();
		refreshRestMinEmQty($heal_button);
		refreshRestMinEmQty($cleaner_button);
		refreshRestMinEmQty($security_button);
	})
})(jQuery);
