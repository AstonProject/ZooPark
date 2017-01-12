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

	// Fonction pour afficher les employee assignables a l'enclos
	function showListEmployees() {
		var statusSLE = "okSLE";
		var $selectE = $('.selectEmployee');
		
		var callback = function(donnees) {
		if (donnees.employeeQty != 0) {
				if(donnees.isHealer == true){
					$selectE.append('<option value=1>Add healer</option>');
				} else if(donnees.isCleaner == true){
					$selectE.append('<option value=1>Add cleaner</option>');
				} else if(donnees.isSecurity == true){
					$selectE.append('<option value=1>Add security</option>');
				}						
			}
		};
			var monObj = {
					"statusSLE" : statusSLE
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

	// Fonction pour afficher les jauges de l'enclos
	function showGauges() {
		var statusG = "okG";

		var callback = function(donnees) {
			$(".hungry").attr("value", donnees.hungry);
			$(".health").attr("value", donnees.health);
			$(".cleanness").attr("value", donnees.cleanness);

			console.log("hungry" + donnees.hungry);
			console.log("health" + donnees.health);
			console.log("cleanness" + donnees.cleanness);

			$(".food_gauge").prepend(donnees.hungry);
			$(".health_gauge").prepend(donnees.health);
			$(".cleanness_gauge").prepend(donnees.cleanness);
		};

		var object = {
			"statusG" : statusG
		};
		server.monAjax(object, "enclosureManagment", callback, 'POST');
	}

	// Fonction d'affichage des prix
	function showAEPrice() {
		var statusAP = "okAP";
		var statusEP = "okEP";
		// attribution d'une variable selectionnant la div d'affichage des prix

		var $blockAnimalPrice = $('.ae_price');
		var animals_quantity = 0;
		var animals_price = 0;

		// Stockage du prix dans la session
		sessionStorage.setItem("animals_price", animals_price);

		$('#a_quantity')
				.on(
						'click',
						function() {
							// Reinitialise le contenu du blockPrice si un radio
							// EnclosureType
							// est selectionne
							$blockAnimalPrice.empty();

							// Deselectionne la checkbox permettant la revente
							// d'enclos
							$('input:checkbox[name=ecl_resale]').each(
									function() {
										$(this).prop('checked', false);
									});

							// Recuperation de la quantite d'animaux choisie
							animals_quantity = $('input[name=quantity]').val();

							var callback = function(donnees) {
								if (animals_quantity < 0) {
									animals_price = (((donnees.unit_price) * animals_quantity)
											* (-1) * (0.75));
									sessionStorage.setItem("animals_price",
											animals_price);
									$blockAnimalPrice.prepend("<div>"
											+ animals_price + "</div>");
								} else {
									animals_price = (((donnees.unit_price) * animals_quantity) * (-1));
									$blockAnimalPrice.prepend("<div>"
											+ animals_price + "</div>");
									sessionStorage.setItem("animals_price",
											animals_price);
								}

							};

							var object = {
								"statusAPrices" : statusAP
							};
							server.monAjax(object, "enclosureManagment",
									callback, 'POST');
						});

		$('#resale_all').on(
				'click',
				function() {
					// Reinitialise le contenu du blockPrice si un radio
					// EnclosureType
					// est selectionne

					$blockAnimalPrice.empty();

					if ($('#resale_all').is(':checked')) {
						var callback = function(donnees) {
							$blockAnimalPrice.prepend("<div>"
									+ donnees.total_price + "</div>");
						};

						var object = {
							"statusEPrices" : statusEP
						};
						server.monAjax(object, "enclosureManagment", callback,
								'POST');
					} else {
						$blockAnimalPrice.prepend("<div>"
								+ sessionStorage.getItem("animals_price")
								+ "</div>");
					}
				});

	}

	// Fonction achat des animaux
	function purshaseResale() {
		var statusPRA = "okPRA";
		var statusRE = "okRE";

		var quantity = null;
		var $formPA = $('#FormPurchaseAnimals');

		$formPA.on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();

			// Si il faut revendre l'enclos
			if ($('#resale_all').is(':checked')) {

				var callback = function(donnees) {
					if (donnees.code == "OK") {
						window.location.href = "home";
					} else {
						failed();
					}
				};

				var object = {
					"statusRE" : statusRE
				};

				server.monAjax(object, "enclosureManagment", callback, 'POST');

			}// Si il faut revendre ou acheter des animaux
			else {

				quantity = $('input[name=quantity]').val();

				var callback = function(donnees) {
					if (donnees.code == "OK") {
						window.location.href = "home";
					} else {
						failed();
					}
				};

				var object = {
					"statusPRA" : statusPRA,
					"quantity" : quantity
				};
				server.monAjax(object, "enclosureManagment", callback, 'POST');
			}
		});
	}
	// fonction pour afficher la quantite d'animaux pouvant encore rentrer dans
	// l'enclos
	function setRestQuantity() {
		var statusSQ = "okSQ";

		var callback = function(donnees) {
			console.log(donnees);

			var $inputQuantity = $(".mod");
			$inputQuantity.attr("max", donnees.rest);
			$inputQuantity.attr("min", donnees.min);
		};

		var monObj = {
			"statusSQ" : statusSQ
		};
		server.monAjax(monObj, "enclosureManagment", callback, 'POST');
	}

	$(document).ready(function() {
		showAnimals();
		showListEmployees();
		showEmployees();
		setRestQuantity();
		showGauges();
		showAEPrice();
		purshaseResale();
	})
})(jQuery);
