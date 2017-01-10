(function($) {
	"use strict";
	
	$(document).ready(function() {
		var $mainContent = $("#main-content");
		var callback=function(donnees){
			$mainContent.empty();
			for(let message in donnees){
				console.log(donnees);
				$mainContent.prepend("<div class=\"message\">"+donnees[message]["title"]+":<br>"+donnees[message]["content"]+"</div>");
			};
		};
		setInterval(function(){
			var obj = {};
			server.monAjax(obj, "notifications", callback);
			
		}, 2000);
	});
})(jQuery);