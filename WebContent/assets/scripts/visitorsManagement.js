
			
(function($) {
	"use strict";
	var speed = null;
	var speedT = null;
	var hourT = null;

	speedT = sessionStorage.getItem("speedS");
	console.log("speedT 1er chargement " +speedT)
	
	hourT = sessionStorage.getItem("hour");
	console.log("hourT 1er chargement " +hourT)
	
	function getSpeedT(millis) {
		var object ={};
			speed = setInterval(function(){ 
				hourT = sessionStorage.getItem("hour");
				
				console.log("function gerer des visiteurs");
				console.log("hourT 1er chargement " +hourT);
				//Generer des visiteurs a l'heure 1
				if(hourT == 1){
					var statusGV = "okGV";
					var callback = function(donnees) {
						$('#visitors').empty();
						$('#money').empty();
						$('#visitors').append("Visitors : "+ donnees.visitors);
						$('#money').append("Money : " + donnees.money + " Z");
					};
					
					object = {"statusGV" : statusGV};
					server.monAjax(object, "visitorsManagement", callback, 'POST');
				}
				//La journée des visiteurs
				 if(hourT > 1 && hourT <5){
					 var statusDV = "okDV";
					 var callback = function(donnees) {
						 
					 };
						
						object = {"statusDV" : statusDV};
						server.monAjax(object, "visitorsManagement", callback, 'POST');	 
				 }
				 
				 //Le Depart des visiteurs la nuit
				 if(hourT == 5){
					 var statusNV = "okNV";
						var callback = function(donnees) {
							$('#visitors').empty();
							$('#visitors').append("Visitors : "+ donnees.visitors);
						};
						
						object = {"statusNV" : statusNV};
						server.monAjax(object, "visitorsManagement", callback, 'POST');
				 }
					
			}, millis);	
	}

	function setSpeedT($speedButton){
		$($speedButton).on("click", function(){
			speedT = sessionStorage.getItem("speedS"); 
			hourT = sessionStorage.getItem("hour");
			console.log("speedT de rappel " +speedT);
			console.log("hourT de rappel " +hourT);
		
			if (speedT != 0) {
				getSpeedT(speedT);
			} else {
				clearInterval(speed);
			}			
		});
	}
	
	
	$(document).ready(function() {
		
		if((speedT != 0) && (speedT !=null)){
			getSpeedT(speedT);
		}else {
			clearInterval(speed);
		}
		
		 setSpeedT($("#play"));
		 setSpeedT($("#speedup"));
		 setSpeedT($("#pause"));	
	})
})(jQuery);
