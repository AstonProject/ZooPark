(function($) {
	"use strict";

	// Fonction d'affichage la description associée au radio coché
	function showDescription($selectedRadio, $descriptionShown,
			$descriptionHide1, $descriptionHide2, $descriptionHide3) {
		$($selectedRadio).click(function() {
			$descriptionShown.css("display", "inline");
			$descriptionHide1.css("display", "none");
			$descriptionHide2.css("display", "none");
			$descriptionHide3.css("display", "none");
		});
	}

	// Fonction d'affichage des prix
	function showPrice($selectedRadioSize){
		$($selectedRadioSize).click(function() {
			var callback=function(donnees){
	            var $blockPrice=$('.showPrice');
	            $blockPrice.html("");
	            if(donnees.data){
	                for (let prices of donnees.data) {
	                    console.log(prices);
	                    $blockPrice.prepend("<div>"+ prices.enclosureCosts_lion + "</div>");
	                }
	            }
	        };
		});   

	        var monObjet ={};
	        irc.monAjax(monObjet, "createEnclosure", callback, 'POST');
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
				showPrice($radioS1);
			})
})(jQuery);
