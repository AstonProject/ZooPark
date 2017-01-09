(function($) {
	"use strict";
	
	$(document).ready(function() {
		var $mainContent = $("#main-content");
		var callback=function(donnees){
			$mainContent.empty();
			var count = 0;
			for(var message in donnees){
				console.log(message);
				$mainContent.prepend("<div class=\"message\">"+message.title+":<br>"+message.content+"</div>");
				count++;
			};
		};
		setInterval(function(){
			var obj = {};
			server.monAjax(obj, "notifications", callback);
			
		}, 2000);
	});
})(jQuery);