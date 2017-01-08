(function($) {
	"use strict";
	
	$(document).ready(function() {
		var $mainContent = $("#main-content");
		setInterval(function(){
			
			var callback=function(donnees){
				console.log(donnees);
				var count = 0;
				$mainContent.empty();
				for(var message of donnees){
					$mainContent.prepend("<div class=\"message\">"+message.title+":<br>"+message.content+"</div>");
					count++;
				};
			};
			var obj = {};
			server.monAjax(obj, "notifications", callback);
			
		}, 2000);
	});
})(jQuery);