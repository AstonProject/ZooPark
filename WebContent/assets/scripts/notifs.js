(function($) {
	"use strict";
	
	$(document).ready(function() {
		var $mainContent = $("#main-content");
		var callback=function(donnees){
			$mainContent.empty();
			for(let message in donnees){
				console.log(donnees);
				$mainContent.prepend("<div class=\"message\"><span class=\"titre\">"+donnees[message]["title"]+":</span> <span class=\"contenu\">"+donnees[message]["content"]+"</span> <a href=\"?action=delete&id="+donnees[message]["id"]+"\">Supprimer</a></div>");
			};
		};
		setInterval(function(){
			var obj = {};
			server.monAjax(obj, "notifications", callback);
			
		}, 2000);
	});
})(jQuery);