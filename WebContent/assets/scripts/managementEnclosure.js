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
		$selectE.empty();
		$selectE.append('<option value=1>Assign / Remove</option>');
		var callback = function(donnees) {
			
			
			if(donnees.employeeQty != 2){
					if(donnees.isHealerOut == "true"){
						$selectE.append('<option value=2>Assign healer</option>');
					} 
					if(donnees.isCleanerOut == "true"){
						$selectE.append('<option value=3>Assign cleaner</option>');
					} 
					if(donnees.isSecurityOut == "true"){
						$selectE.append('<option value=4>Assign security</option>');
					}
			}
			if (donnees.employeeQty != 0){
				if(donnees.isHealerIn == "true"){
					$selectE.append('<option value=5>Remove healer</option>');
				}
				if(donnees.isCleanerIn == "true"){
					$selectE.append('<option value=6>Remove cleaner</option>');
				} 
				if(donnees.isSecurityIn == "true"){
					$selectE.append('<option value=7>Remove security</option>');
				}
			}
		};
			var monObj = {
					"statusSLE" : statusSLE
			};
			server.monAjax(monObj, "enclosureManagment", callback, 'POST');
		
	}
	
	// Fonction pour retirer/ajoutter un employee a l'enclos
	
function mooveEmployees(){
	$('.selectEmployee').on('change', function () {
		var statusME = "okME";

		var actionME = $('.selectEmployee').val();
		console.log(actionME);
		
		var callback = function(donnees) {
			if(donnees.code == "OK"){
				//Apres deplacement recharger la nouvelle liste d'employes 
				//et affichage des employees actuellement dans l'enclos
				showListEmployees();
				showEmployees();
           }else {
           	 failed();
           }	
		};
		
		var object = {
			"statusME" : statusME,
			"actionME" : actionME
		};
		server.monAjax(object, "enclosureManagment", callback, 'POST');
		
	});
}

	// Fonction pour afficher les employes de l'enclos
	function showEmployees() {
		var statusSE = "okSE";
		//Initialisation des slot 1 et 2
		$(".slot1").empty();
		$(".slot2").empty();
		
		var callback = function(donnees) {
			//Si il ya des employees, leur nombre est determine
			for (var i = 1; i < ((Object.keys(donnees).length) + 1); i++) {
				var type = null;
				//L'employee i sera assigne au slot i
				var img = "<img src=/zoopark/assets/images/";
				var $block_slot = $(".slot" + i);
				
				//Determiner le type de l'employee i pour reconstituer la source 
				//de l'image correspondante
				type = "type" + i;

				if (donnees[type] == "healer") {
					img += "healer"
				} else if (donnees[type] == "security") {
					img += "security"
				} else if (donnees[type] == "cleaner") {
					img += "cleaner"
				}
				
				img += ".png alt=\"logo\" class=\"current_employees\"/>"
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
		mooveEmployees();
		showEmployees();
		setRestQuantity();
		showGauges();
		showAEPrice();
		purshaseResale();
	})
})(jQuery);
