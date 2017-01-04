


(function($) {
	"use strict";
	//Fonction pour afficher les animaux de l'enclos
	function showAnimals(){
		var statusSA="okSA";
		var $block_animal_img= $('.animal');
		var $block_animal_qty= $('.currentQuantity');
		var $block_animal_max_qty= $('.maxQuantity');
		
		
		var callback = function(donnees) {
			$block_animal_img.empty();
			$block_animal_qty.empty();
			$block_animal_max_qty.empty();
			
			if(donnees.specie_id == 1){
				$block_animal_img.prepend("<img src=/zoopark/assets/images/animal_elephant.png alt=\"logo\" />");
			} else if (donnees.specie_id == 2){
				$block_animal_img.prepend("<img src=/zoopark/assets/images/animal_giraffe.png alt=\"logo\" />");
			} else if (donnees.specie_id == 3){
				$block_animal_img.prepend("<img src=/zoopark/assets/images/animal_lion.png alt=\"logo\" />");
			} else if (donnees.specie_id == 4){
				$block_animal_img.prepend("<img src=/zoopark/assets/images/animal_camel.png alt=\"logo\" />");
			} 
			
			$block_animal_qty.prepend(donnees.animal_quantity );
			
			$block_animal_max_qty.prepend("(Max: "+ donnees.capacity +")");
			
			
         };
         var monObj = {"statusSA":statusSA};
         server.monAjax(monObj, "enclosureManagment", callback,'POST');
	}
	
	//Fonction pour afficher les employes de l'enclos
	function showEmployees(){
		var statusSE="okSE";
		var $block_slot1= $('.slot1');
		var $block_slot2= $('.slot2');
		var type= null;
		var img= "<img src=/zoopark/assets/images/";
		$block_slot1.empty();
		$block_slot2.empty();
		
		var callback = function(donnees) {
			for (var i = 0; i < donnees.length; i++){
				type = "type" + i; 
				
				if(donnees.type == "healer"){
					img += "healer.png alt=\"logo\" />"
				}else if(donnees.type == "security"){
					img += "security.png alt=\"logo\" />"
				}else if(donnees.type == "cleaner"){
					img += "cleaner.png alt=\"logo\" />"
				}
			}
			
			
			
			
			
        };
         var monObj = {"statusSE":statusSE};
         server.monAjax(monObj, "enclosureManagment", callback,'POST');
	}

	$(document).ready(function() {
		showAnimals();
		showEmployees();
	})
})(jQuery);
