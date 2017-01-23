
			
(function($) {
	"use strict";
	var speed = null;
	var hourT = null;

	
	function getSpeedT(millis) {
		var object ={};
			speed = setInterval(function(){ 
				
				hourT = sessionStorage.getItem("hour");
				
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
					 if(hourT > 1 && hourT <7){
						 var statusDV = "okDV";
						 var callback = function(donnees) {
							 $('#money').empty();
							$('#satisfaction').empty();
							$('#satisfaction').append("Satisfaction : " + donnees.satisfaction);
							$('#money').append("Money : " + donnees.money + " Z");
						 };
							
							object = {"statusDV" : statusDV};
							server.monAjax(object, "visitorsManagement", callback, 'POST');	 
					 }
					 
					 //Le Depart des visiteurs la nuit
					 if(hourT == 7){
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
			clearInterval(speed);
			getSpeedT(10000);
			 sessionStorage.setItem("speedS", 10000);
		});
		$("#speedup").on("click", function(){
			clearInterval(speed);
			getSpeedT(2000);
			 sessionStorage.setItem("speedS", 3000);
		});
		$("#pause").on("click", function(){
			if(speed != null){
				clearInterval(speed);
				sessionStorage.setItem("speedS", 0);
			}
		});
		$("#disconnect").on("click", function(){
			clearInterval(speed);
			sessionStorage.setItem("speedS", 0);
		});
	})
})(jQuery);
