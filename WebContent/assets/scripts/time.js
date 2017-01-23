(function($) {
	"use strict";
	let tick = null;
	let phase = "day";
	let hour = 0;
	let day = 1;
	let month = 1;
	
	var time = function(millis){
		let $heure = $(".heure");
		let $jour = $(".jour");
		let $mois = $(".mois");
		if(tick != null){
			clearInterval(tick);
		}
		tick = setInterval(function(){
			hour++;
			console.log("hour++"+ hour);
			sessionStorage.setItem("hour", hour);
			let callback=function(donnees){
			}
			if(hour == 10){
				hour = 0;
				$("#body").css("background-color","lightblue");
				day++;
				if(day == 30){
					day = 1;
					month++;
				}
				phase = "day";
			}
			else if(hour == 7){
				phase = "night";
				
				$("#body").css("background-color","darkblue");
			}
			let updatePlayer = {"newTime": hour+","+day};
			server.monAjax(updatePlayer, "newturn", callback, 'POST');
			$heure.empty();
			$jour.empty();
			$mois.empty();
			$heure.append(hour);
			$jour.append(day);
			$mois.append(month);
			
		}, millis);
	}

	$(document).ready(function() {
		let obj = {};
		let speed = sessionStorage.getItem("speedS");
		
		let callback = function(donnees){
			hour = parseInt(donnees.hour);
			sessionStorage.setItem("hour", hour);
			day = parseInt(donnees.day);
			if(hour >= 7) {
				phase = "night";
				$("body, #body").css("background-color","darkblue");
			}
			else {
				phase = "day";
				$("body, #body").css("background-color","lightblue");
			}
		};
		server.monAjax(obj, "newturn", callback, 'GET');
		
		if (speed == 10000) {
			time(10000);
		}else if(speed == 3000) {
			time(2000);
		}else if (speed == 0) {
			clearInterval(tick);
		}
		
		$("#play").on("click", function(){
			time(10000);
			 sessionStorage.setItem("speedS", 10000);
		});
		$("#speedup").on("click", function(){
			time(2000);
			 sessionStorage.setItem("speedS", 3000);
		});
		$("#pause").on("click", function(){
			if(tick != null){
				clearInterval(tick);
				sessionStorage.setItem("speedS", 0);
			}
		});
		$("#disconnect").on("click", function(){
			clearInterval(tick);
			sessionStorage.setItem("speedS", 0);
		});
		
	})
})(jQuery);
