
(function($) {
	"use strict";
	// Fonction pour afficher les animaux de l'enclos
	function showAnimals() {
		var statusSA = "okSA";
		var $block_animal_img = $('.animal');
		var $block_animal_qty = $('.currentQuantity');
		var $block_animal_max_qty = $('.maxQuantity');

		var callback = function(donnees) {
			$block_animal_img.empty();
			$block_animal_qty.empty();
			$block_animal_max_qty.empty();

			if (donnees.specie_id == 1) {
				$block_animal_img
						.prepend("<img src=/zoopark/assets/images/animal_elephant.png alt=\"logo\" />");
			} else if (donnees.specie_id == 2) {
				$block_animal_img
						.prepend("<img src=/zoopark/assets/images/animal_giraffe.png alt=\"logo\" />");
			} else if (donnees.specie_id == 3) {
				$block_animal_img
						.prepend("<img src=/zoopark/assets/images/animal_lion.png alt=\"logo\" />");
			} else if (donnees.specie_id == 4) {
				$block_animal_img
						.prepend("<img src=/zoopark/assets/images/animal_camel.png alt=\"logo\" />");
			}

			$block_animal_qty.prepend(donnees.animal_quantity);

			$block_animal_max_qty.prepend("(Max: " + donnees.capacity + ")");

		};
		var monObj = {
			"statusSA" : statusSA
		};
		server.monAjax(monObj, "enclosureManagment", callback, 'POST');
	}

	// Fonction pour afficher les employes de l'enclos
	function showEmployees() {
		var statusSE = "okSE";

		var callback = function(donnees) {

			for (var i = 1; i < ((Object.keys(donnees).length) + 1); i++) {
				var type = null;

				var img = "<img src=/zoopark/assets/images/";
				var $block_slot = $(".slot" + i);
				$block_slot.empty();

				type = "type" + i;

				if (donnees[type] == "healer") {
					img += "healer.png alt=\"logo\" />"
				} else if (donnees[type] == "security") {
					img += "security.png alt=\"logo\" />"
				} else if (donnees[type] == "cleaner") {
					img += "cleaner.png alt=\"logo\" />"
				}

				$block_slot.prepend(img);
			}
		};
		var object = {
			"statusSE" : statusSE
		};
		server.monAjax(object, "enclosureManagment", callback, 'POST');
	}

	// Fonction achat des animaux
	function purshaseAnimals() {
		var statusPA = "okPA";
		var quantity = null;
		var $formPA = $('#FormPurchaseAnimals');
		
		$formPA.on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();
			
			quantity = $('input[name=quantity]').val();
			
			var callback = function(donnees) {
				if(donnees.code == "OK"){
					 window.location.href = "home";
               }else {
               	 failed();
               }
			};

			var object = {
				"statusPA" : statusPA,
				"quantity" : quantity
			};
			server.monAjax(object, "enclosureManagment", callback, 'POST');
		});
	}

	$(document).ready(function() {
		showAnimals();
		showEmployees();
		purshaseAnimals();
	})
})(jQuery);
