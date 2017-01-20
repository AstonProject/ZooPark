(function($) {
	"use strict";
	let tick = null;
	let phase = "day";
	let hour = 0;
	let day = 1;
	
	var time = function(millis){
		let $heure = $(".heure");
		let $jour = $(".jour");
		if(tick != null){
			clearInterval(tick);
		}
		tick = setInterval(function(){
			hour++;
			let callback=function(donnees){
			}
			if(hour == 10){
				hour = 0;
				$("#body").css("background-color","lightblue");
				day++;
				phase = "day";
			}
			else if(hour == 5){
				phase = "night";
				$("#body").css("background-color","darkblue");
			}
			let updatePlayer = {"newTime": hour+","+day};
			server.monAjax(updatePlayer, "newturn", callback, 'POST');
			$heure.empty();
			$jour.empty();
			$heure.append(hour);
			$jour.append(day);
		}, millis);
	}

	$(document).ready(function() {
		let obj = {};
		let callback = function(donnees){
			hour = parseInt(donnees.hour);
			day = parseInt(donnees.day);
			if(hour >= 5) {
				phase = "night";
				$("body, #body").css("background-color","darkblue");
			}
			else {
				phase = "day";
				$("body, #body").css("background-color","lightblue");
			}
		};
		server.monAjax(obj, "newturn", callback, 'GET');
		$("#play").on("click", function(){
			time(60000);
		});
		$("#speedup").on("click", function(){
			time(10000);
		});
		$("#pause").on("click", function(){
			if(tick != null){
				clearInterval(tick);
			}
		});
	})
})(jQuery);
