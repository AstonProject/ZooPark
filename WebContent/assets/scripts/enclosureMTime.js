
	function refreshGauges(){
		let enclosureOk = "ok";
		let refreshMoney = "ok";
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
			"enclosureM": enclosureOk
		};
		console.log(enclosureObj);
		server.monAjax(enclosureObj, "newturn", callback, 'POST');
	}
	
	function refreshGaugesOnEnclosure(){
		let enclosureOk = "ok";
		let onEnclosure = "ok";
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
			"enclosureM": enclosureOk,
			"onEnclosure": onEnclosure
		};
		console.log(enclosureObj);
		server.monAjax(enclosureObj, "newturn", callback, 'POST');
	}
		
		



