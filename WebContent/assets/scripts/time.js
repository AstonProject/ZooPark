(function($) {
	"use strict";
	var tick = null;
	var phase = "day";
	var hour = 0;
	
	var time = function(millis){
		var $turn = $("#turn");
		if(tick != null){
			clearInterval(tick);
		}
		tick = setInterval(function(){
			console.log("ça tourne !");
			hour++;
			if(hour == 10){
				hour = 0;
				var callback=function(donnees){
					
				}
				var monObjet ={};
				server.monAjax(monObjet, "newturn", callback, 'POST');
			}
			$turn.empty();
			$turn.append("HEURE : "+hour);
		}, millis);
	}

	$(document).ready(function() {
		$("#play").on("click", function(){
			time(10000);
		});
		$("#speedup").on("click", function(){
			time(5000);
		});
		$("#pause").on("click", function(){
			if(tick != null){
				console.log("ça s'arrête !");
				clearInterval(tick);
			}
		});
	})
})(jQuery);
