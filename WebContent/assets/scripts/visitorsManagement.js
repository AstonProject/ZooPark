
			
(function($) {
	"use strict";
	var speed = null;
	var speedT = null;
	
	speedT = sessionStorage.getItem("speedS");
	console.log("speedT 1er chargement " +speedT)
	
	function getSpeedT(millis) {
		
			speed = setInterval(function(){ 
				var statusGST = "okGST";
				console.log("function gerer des visiteurs");
				var callback = function(donnees) {
					
				};
				
				var object = {
					"statusGST" : statusGST
				};
					 server.monAjax(object, "visitorsManagement", callback, 'POST');
			}, millis);
		
	}

	function setSpeedT($speedButton){
		$($speedButton).on("click", function(){
			speedT = sessionStorage.getItem("speedS"); 
			console.log("speedT de rappel " +speedT)
		
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
