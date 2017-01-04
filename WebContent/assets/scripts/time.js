(function($) {
	"use strict";
	var tick = null;
	var phase = "day";
	var hour = 0;
	var day = 1;
	
	var time = function(millis){
		var $gamedate = $("#gamedate");
		if(tick != null){
			clearInterval(tick);
		}
		tick = setInterval(function(){
			console.log("ça tourne !");
			hour++;
			if(hour == 10){
				hour = 0;
				$("#body").css("background-color","lightblue");
				day++;
				var callback=function(donnees){
					
				}
				var monObjet ={"day": day};
				server.monAjax(monObjet, "newturn", callback, 'POST');
				phase = "day";
			}
			else if(hour == 5){
				phase = "night";
				$("#body").css("background-color","darkblue");
			}
			$gamedate.empty();
			$gamedate.append("HEURE : "+hour+" JOUR : "+day);
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
