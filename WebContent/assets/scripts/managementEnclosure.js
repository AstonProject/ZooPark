


(function($) {
	"use strict";
	function showAnimals(){
		 var callback = function(donnees) {
         };
         var monObj = {};
         server.monAjax(monObj, "enclosureManagment", callback);
	}

	$(document).ready(function() {
		showAnimals();
	})
})(jQuery);
