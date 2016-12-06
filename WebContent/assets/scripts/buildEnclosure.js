(function($) {
	"use strict";
	
	//Fonction d'affichage la description associée au radio coché
	function showDescription($selectedRadio, $descriptionShown,
			$descriptionHide1, $descriptionHide2, $descriptionHide3) {
		$($selectedRadio).click(function() {
			$descriptionShown.css("display", "inline");
			$descriptionHide1.css("display", "none");
			$descriptionHide2.css("display", "none");
			$descriptionHide3.css("display", "none");
		});
	}
	
	$(document).ready(function() {
		//Creation d'objets jQuery referancant les classes des 4 descriptions 	
		var $Description1 = $('.elephantDiscription');
		var $Description2 = $('.giraffeDiscription');
		var $Description3 = $('.lionDiscription');
		var $Description4 = $('.camelDiscription');
		
		//Creation d'objets jQuery referancant les 4 Id radios d'enclos	
		var $radio1 = $('#radio_elephant');
		var $radio2 = $('#radio_giraffe');
		var $radio3 = $('#radio_lion');
		var $radio4 = $('#radio_camel');
		
		//Execution de la fonction d'affichage de la description des enclos
		showDescription($radio1, $Description1, $Description2,
			$Description3, $Description4);
		showDescription($radio2, $Description2, $Description1,
			$Description3, $Description4);
		showDescription($radio3, $Description3, $Description1,
			$Description2, $Description4);
		showDescription($radio4, $Description4, $Description1,
			$Description2, $Description3);
			})
})(jQuery);
