(function($) {
	"use strict";

	// Fonction d'affichage la description associée au radio coché
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
	function showPrice($selectedRadioEnclosure, $selectedRadioSize){
		
		var callback=function(donnees){
			var $blockPrice=$('.showPrice');
	            
	            $($selectedRadioEnclosure).click(function() {
	            	$blockPrice.empty();
	            	$($selectedRadioSize).change(function() {
	            		$blockPrice.empty();
	            		if ($('#radio_elephant').is(':checked')){
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_elephant + "</div>");
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_elephant*2 + "</div>");
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_elephant*3 + "</div>");
	            			}
	            		}else if ($('#radio_giraffe').is(':checked')){
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_giraffe + "</div>");
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_giraffe*2 + "</div>");
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_giraffe*3 + "</div>");
	            			}
	            		}else if ($('#radio_lion').is(':checked')){
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_lion + "</div>");
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_lion*2 + "</div>");
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_lion*3 + "</div>");
	            			}
	            		}else if($('#radio_camel').is(':checked')) {
	            			if(($('#size_1').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_camel + "</div>");
	            			} else if (($('#size_2').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_camel*2 + "</div>");
	            			}
	            			else if (($('#size_3').is(':checked'))){
	            				$blockPrice.prepend("<div>"+ donnees.enclosureCosts_camel*3 + "</div>");
	            			}
	            		}
	            	});
	            });
	        };

	        var monObjet ={};
	        server.monAjax(monObjet, "createEnclosure", callback, 'POST');
    }
	
	$(document).ready(
			function() {
				// Creation d'objets jQuery referancant les classes des 4
				// descriptions
				var $Description1 = $('.elephantDiscription');
				var $Description2 = $('.giraffeDiscription');
				var $Description3 = $('.lionDiscription');
				var $Description4 = $('.camelDiscription');

				// Creation d'objets jQuery referancant les 4 id radios d'enclos
				var $radioE1 = $('#radio_elephant');
				var $radioE2 = $('#radio_giraffe');
				var $radioE3 = $('#radio_lion');
				var $radioE4 = $('#radio_camel');

				// Creation d'objet jQuery referancant les 3 id radio de taille
				var $radioS1 = $('#size_1');
				var $radioS2 = $('#size_2');
				var $radioS3 = $('#size_3');
				
//				// Prix enclos
//				var ElephantEnclosurePrice= 'enclosureCosts_elephant';
//				var GiraffeEnclosurePrice= 'enclosureCosts_giraffe';
//				var LionEnclosurePrice= 'enclosureCosts_lion';
//				var CamelEnclosurePrice= 'enclosureCosts_camel';

				// Execution de la fonction d'affichage de la description des
				// enclos
				showDescription($radioE1, $Description1, $Description2,
						$Description3, $Description4);
				showDescription($radioE2, $Description2, $Description1,
						$Description3, $Description4);
				showDescription($radioE3, $Description3, $Description1,
						$Description2, $Description4);
				showDescription($radioE4, $Description4, $Description1,
						$Description2, $Description3);
				
				// affichage des prix
				showPrice($radioE1, $radioS1);
				showPrice($radioE1, $radioS2);
				showPrice($radioE1, $radioS3);
				
				showPrice($radioE2, $radioS1);
				showPrice($radioE2, $radioS2);
				showPrice($radioE2, $radioS3);
				
				showPrice($radioE3, $radioS1);
				showPrice($radioE3, $radioS2);
				showPrice($radioE3, $radioS3);
				
				showPrice($radioE4, $radioS1);
				showPrice($radioE4, $radioS2);
				showPrice($radioE4, $radioS3);
			})
})(jQuery);
