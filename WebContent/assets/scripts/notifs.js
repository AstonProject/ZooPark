(function($) {
	"use strict";
	
	$(document).ready(function() {
		var $mainContent = $("#main-content");
		var callback=function(donnees){
			$mainContent.empty();
			for(let message in donnees){
				console.log(donnees);
				$mainContent.prepend("<div class=\"message\"><span class=\"titre\">"+donnees[message]["title"]+":</span> <span class=\"contenu\">"+donnees[message]["content"]+"</span> <button id='"+donnees[message]["id"]+"' class='delete'>Supprimer</button></div>");
			};
			var $buttons = $(".delete");
			$buttons.on("click", function(event){
				var $clicked_id = event.target.id;
				var obj = {action: "delete", id: $clicked_id};
				server.monAjax(obj, "notifications", callback);
			});
		};
		setInterval(function(){
			var obj = {};
			server.monAjax(obj, "notifications", callback);
			
		}, 2000);
	});
})(jQuery);