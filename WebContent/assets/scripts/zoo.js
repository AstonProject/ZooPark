var server = {};
(function($) {
	 "use strict";

	server.monAjax = function(dataObj, url, callback, method) {
		var objAjax = {
			url : "http://localhost:8080/zoopark/" + url, 
			data : dataObj, 
			type : method || 'post', 
			dataType : 'json',

			complete : function(xhr, status, msg) {
				console.log("Requête bien envoyée");
			},
			success : function(donnees, textStatus) {
				console.log(textStatus);
				callback(donnees);
			},
			error : function(xhr, status, msgErreur) {
				console.log(msgErreur);
			},
		};
		$.ajax(objAjax);
	}
	
	function showEmployeeMangement(){
		$('#employeesManagement').on('click', function(){
			window.location.replace("employeesManagement");
		});
	}
	
	function showFinances() {
		$('#finances').on('click', function() {
			window.location.replace("financeManagement");
		});
	}
	
	function showConsumables() {
		$('#consommables').on('click', function() {
			window.location.replace("consumableManagement");
		});
	}

	$(document).ready(function() {
		var $constructionButton = $("#construction");
		$constructionButton.on("click", function(){
			window.location.replace("createEnclosure");
		});
		
		showEmployeeMangement();
		showFinances();
		showConsumables();
	});
})(jQuery);
