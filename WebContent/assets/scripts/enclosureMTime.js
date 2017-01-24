(function($) {
	"use strict";
	let tickEnclosure = null;
	
	var time = function(millis){
		if(tickEnclosure != null){
			clearInterval(tick);
		}
		tickEnclosure = setInterval(function(){
			let callback=function(donnees){
				$(".hungry").attr("value", donnees.hungry);
				$(".health").attr("value", donnees.health);
				$(".cleanness").attr("value", donnees.clean);

				$(".food_gauge").empty();
				$(".health_gauge").empty();
				$(".cleanness_gauge").empty();
				$(".food_gauge").prepend(donnees.hungry);
				$(".health_gauge").prepend(donnees.health);
				$(".cleanness_gauge").prepend(donnees.clean);
			}
			let enclosureObj = {
				"enclosureM": "ok"
			};
			console.log(enclosureObj);
			server.monAjax(enclosureObj, "newturn", callback, 'POST');
		}, millis);
	}

	$(document).ready(function() {
		let speedT = sessionStorage.getItem("speedS");
		if (speedT == 10000) {
			getSpeedT(10000);
		}else if(speedT == 3000) {
			getSpeedT(2000);
		}else if (speedT == 0) {
			clearInterval(speed);
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
			if(tickEnclosure != null){
				clearInterval(tickEnclosure);
				sessionStorage.setItem("speedS", 0);
			}
		});
		$("#disconnect").on("click", function(){
			clearInterval(tickEnclosure);
			sessionStorage.setItem("speedS", 0);
		});
	})
})(jQuery);
