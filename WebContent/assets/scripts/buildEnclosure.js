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
	$(document).on("click", "#size_1", function() {               		// When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
	    $.post("createEnclosure", function(responseJson) {            	 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
	        var $select = $(".showPrice");                          	 // Locate HTML DOM element with ID "someselect".
	        $select.find("prices").remove();                         	 // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
	        $.each(responseJson, function(key, value) {              	 // Iterate over the JSON object.
	            $("<prices>").val(key).text(value).appendTo($select);	 // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
	        });
	    });
	});

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
			})
})(jQuery);
