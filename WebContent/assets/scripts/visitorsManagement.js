
			
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
				var statusGV = "okGV";
				console.log("function gerer des visiteurs");
				console.log("hourT 1er chargement " +hourT);
				if(hourT == 1){
					var callback = function(donnees) {
						
					};
					
					object = {"statusGV" : statusGV};
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
