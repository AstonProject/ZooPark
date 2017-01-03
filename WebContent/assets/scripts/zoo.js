var server = {};
(function($) {
	 "use strict";

	server.monAjax = function(dataObj, url, callback, method) {
		var objAjax = {
			url : "http://localhost:8080/zoopark/" + url, // URL où envoyer la requête
													// (correspond à l'action
													// d'un form
			data : dataObj, // données envoyées
			type : method || 'post', // méthode HTTP de la requête//type des
										// données attendues en retour : xml,
										// json, script, html
			dataType : 'json',

			complete : function(xhr, status, msg) {
				console.log("Requête bien envoyée");
			},
			success : function(donnees, textStatus) {
				callback(donnees);
			},
			error : function(xhr, status, msgErreur) {
				console.log(msgErreur);
			},
		};
		$.ajax(objAjax);
	}

	$(document).ready(function() {
		var $constructionButton = $("#construction");
		$constructionButton.on("click", function(){
			window.location.replace("createEnclosure");
		});
	});
})(jQuery);
