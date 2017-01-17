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
	
	$(document).ready(function() {
		showEmployeesAssignment();
	})
})(jQuery);
