(function($) {
	"use strict";

	function showEnclosures() {

		var callback = function(donnees) {

			for (var i = 0; i < 5; i++) {

				for (var j = 0; j < 5; j++) {
					var blockId = "#zoo-site-" + (i + 1) + "-" + (j + 1);
					var $blockEnclosure = $(blockId);
					$blockEnclosure.empty();
					var thisData = "data" + (i + 1) + (j + 1);

					if(donnees[thisData].capacity == 0) {
						$blockEnclosure.empty();
						$blockEnclosure.prepend("<a href=\"createEnclosure?x="+(i+1)+"&y="+(j+1)+"\"> <img src=/zoopark/assets/images/crane.png alt=\"logo\" /> </a>");
					} else if (donnees[thisData].capacity > 0) {
						$blockEnclosure.empty();
						var img="<img src=/zoopark/assets/images/";
						if(donnees[thisData].specie_id == 1){
							img += "enclosure_elephant";
						}else if(donnees[thisData].specie_id == 2){
							img +="enclosure_giraffe";
						}else if (donnees[thisData].specie_id == 3){
							img +="enclosure_lion";
						}else if (donnees[thisData].specie_id == 4){
							img +="enclosure_camel";
						}
						
						if(donnees[thisData].capacity == 5){
							img += ".png alt=\"logo\" /> </a>";
						}else if(donnees[thisData].capacity == 10){
							img += "2.png alt=\"logo\" /> </a>";
						}else if(donnees[thisData].capacity == 15){
							img += "3.png alt=\"logo\" /> </a>";
						}
						
						$blockEnclosure.prepend("<a href=\"enclosureManagment?x="+(i+1)+"&y="+(j+1)+"\">"+ img+ "</a>");
					}
				}
			}
		};

		var monObjet = {};
		server.monAjax(monObjet, "home", callback, 'POST');
	}

	function showEmployeeMangement(){
		$('#employeesManagement').on('click', function(){
			window.location.replace("employeesManagement");
		});
	}
	
	function showFinances() {
		$('#finances').on('click', function() {
			window.location.replace("financeManagement");
		});
	}
	
	
	$(document).ready(function() {
		showEnclosures();
		showEmployeeMangement();
		showFinances();
	})
})(jQuery);
