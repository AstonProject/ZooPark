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
		};
		var monObj = {
			"statusSEA" : statusSEA
		};
		server.monAjax(monObj, "employeesManagement", callback, 'POST');	
	}
	
	function showEmployeePrice($selectedEmQty){
		var statusEmP = "okEmP";
		// attribution d'une variable selectionnant la div d'affichage des prix

		var $blockEmPrice = $('.emp_price');
		var employees_quantity = 0;
		var employeeType;
		
		
		

		$($selectedEmQty).on('click', function() {
			// Reinitialise le contenu du blockPrice
			$blockEmPrice.empty();
			
			var healerQty = 0;
			var cleanerQty = 0;
			var securityQty = 0;
			
			// Recuperation des valeurs des 3 imputs
			healerQty = $('input[name=quantityHeal]').val();
			cleanerQty = $('input[name=quantityClean]').val();
			securityQty = $('input[name=quantitySecurity]').val();
			
			var employees_price = 0;
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
			console.log('AjaxObject'+ object);
			server.monAjax(object, "employeesManagement", callback, 'POST');
		});
	}
	
	
	$(document).ready(function() {
		var $heal_button= $('#heal_quantity');
		var $cleaner_button = $('#clean_quantity');
		var $security_button = $('#secu_quantity');
		
		showEmployeesAssignment();
		
		showEmployeePrice($heal_button);
		showEmployeePrice($cleaner_button);
		showEmployeePrice($security_button);
	})
})(jQuery);
