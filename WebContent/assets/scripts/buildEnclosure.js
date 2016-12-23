(function($) {
	"use strict";

	// Fonction d'affichage des descriptions associées à la selection des radios EnclosureType
	function showDescription($selectedRadioEnclosure, $descriptionShown,
			$descriptionHide1, $descriptionHide2, $descriptionHide3) {
		$($selectedRadioEnclosure).click(function() {
			$descriptionShown.css("display", "inline");
			$descriptionHide1.css("display", "none");
			$descriptionHide2.css("display", "none");
			$descriptionHide3.css("display", "none");
		});
	}

	// Fonction d'affichage des prix
	function showPrice($selectedRadioEnclosure, $selectedRadioSize, $price){
		
		var callback=function(donnees){
			var $blockPrice=$('.price');
	            $($selectedRadioEnclosure).click(function() {
	            	// Réinitialise le contenu du blockPrice si un radio EnclosureType est selectionné
	            	$blockPrice.empty();
	            	// Réinitialise les radioSize si une radio enclosureType est selectionnée
	            	$('input:radio[name=enclosureSize]').each(function () { $(this).prop('checked', false); });
	            	
	            	$($selectedRadioSize).change(function() {
	            		// Réinitialise le contenu du blockPrice si un radio EnclosureSize est selectionné
	            		$blockPrice.empty();
	            		
	            		// Verification des 2 radios selectionnes puis affichage du prix selon les cas 
	            		//et son enregistrement dans la variable $price 
	            		if ($('#radio_elephant').is(':checked')){
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_elephant + "</div>");
	            				$price = donnees.enclosureCosts_elephant;
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_elephant*2 + "</div>");
	            				$price = donnees.enclosureCosts_elephant*2;
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_elephant*3 + "</div>");
	            				$price = donnees.enclosureCosts_elephant*3;
	            			}
	            		}else if ($('#radio_giraffe').is(':checked')){
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_giraffe + "</div>");
	            				$price = donnees.enclosureCosts_giraffe;
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_giraffe*2 + "</div>");
	            				$price = donnees.enclosureCosts_giraffe*2;
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_giraffe*3 + "</div>");
	            				$price =  donnees.enclosureCosts_giraffe*3;
	            			}
	            		}else if ($('#radio_lion').is(':checked')){
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_lion + "</div>");
	            				$price = donnees.enclosureCosts_lion;
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_lion*2 + "</div>");
	            				$price = donnees.enclosureCosts_lion*2;
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_lion*3 + "</div>");
	            				$price = donnees.enclosureCosts_lion*3;
	            			}
	            		}else if($('#radio_camel').is(':checked')) {
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_camel + "</div>");
	            				$price = donnees.enclosureCosts_camel;
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_camel*2 + "</div>");
	            				$price = donnees.enclosureCosts_camel*2;
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_camel*3 + "</div>");
	            				$price = donnees.enclosureCosts_camel*3;
	            			}
	            		}
	            	});
	            });
	        };

	        var monObjet ={"price": $price };
	        server.monAjax(monObjet, "createEnclosure", callback, 'POST');
    }
	
	//Fonction de récupération des données: type d'enclos d'enclos, taille et de son coût
	function getForm(){
		var callback=function(donnees){
			var $formCE = $('FormCreateEnclosure');
			var $radioType = $formCE.find('#radio1');
			var $radioSize = $formCE.find('#radio2');
			var type= null;
			var size= null;
			var capacity=null;
			var specie_id= null;
			var status=null;
				
			
		$formCE.on('submit', function(event) {
			// Bypass du submit par la fonction callback
			event.preventDefault();
			
			//Recuperation des valeurs des radios EnclosureType & EnclosureSize
			type=$radioType.val();
			size=$radioSize.val();
			
			//Attribution de la capacité et de la FK_specie_id par défaut a assigner a l'enclos
			capacity = "5";
			specie_id = "1";
			
			//Modification de la capacité de l'enclos  selon le radio EnclosureSize selectionne
			if(size == "2"){
				 capacity = "10";
			}else if (size == "3"){
				 capacity = "15";
			} 
			
			//Modification de FK_specie_id de l'enclos selon le radio EnclosureType selectionne
			if(type == "Giraffe"){
				specie_id = "2";
			} else if (type == "Lion"){
				specie_id = "3";
			}else if (type == "Camel"){
				specie_id = "4";
			}
			status = "ok";
		});
			
			};
			var monObjet ={
					"specie_id": specie_id,
					"capacity": capacity,
					"status": status
			}
			server.monAjax(monObjet, "createEnclosure", callback, 'POST');
		
	}
	$(document).ready(
			function() {
				
				/**Declaration des variables **/
				// Creation d'objets jQuery referancant les classes des 4
				// descriptions de la jsp
				var $Description1 = $('.elephantDiscription');
				var $Description2 = $('.giraffeDiscription');
				var $Description3 = $('.lionDiscription');
				var $Description4 = $('.camelDiscription');

				// Creation d'objets jQuery referancant les 4 id radio EnclosureType
				var $radioE1 = $('#radio_elephant');
				var $radioE2 = $('#radio_giraffe');
				var $radioE3 = $('#radio_lion');
				var $radioE4 = $('#radio_camel');

				// Creation d'objet jQuery referancant les 3 id radio EnclosureSize
				var $radioS1 = $('#size_1');
				var $radioS2 = $('#size_2');
				var $radioS3 = $('#size_3');
				
				var $price = 0;
				
				/**Execution des fonctions **/
				// Execution des fonctions d'affichage de description des
				// enclos selon le radio "enclosureType" selectionnée
				showDescription($radioE1, $Description1, $Description2,
						$Description3, $Description4);
				showDescription($radioE2, $Description2, $Description1,
						$Description3, $Description4);
				showDescription($radioE3, $Description3, $Description1,
						$Description2, $Description4);
				showDescription($radioE4, $Description4, $Description1,
						$Description2, $Description3);
				
				// Execution des fonctions d'affichage des prix des
				// enclos selon les selections des radios EnclosureType et EnclosureSize
				showPrice($radioE1, $radioS1, $price);
				showPrice($radioE1, $radioS2, $price);
				showPrice($radioE1, $radioS3, $price);
				
				showPrice($radioE2, $radioS1, $price);
				showPrice($radioE2, $radioS2, $price);
				showPrice($radioE2, $radioS3, $price);
				
				showPrice($radioE3, $radioS1, $price);
				showPrice($radioE3, $radioS2, $price);
				showPrice($radioE3, $radioS3, $price);
				
				showPrice($radioE4, $radioS1, $price);
				showPrice($radioE4, $radioS2, $price);
				showPrice($radioE4, $radioS3, $price);
				
				//Execution de la fonction de recuperation des données d'enclos 
				//pour sa création depuis le controleur BuildEnclosureMenu
				//getForm();
			})
})(jQuery);
