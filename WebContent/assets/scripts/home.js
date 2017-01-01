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
					console.log(thisData);
					for (capacity in donnees.thisData) {
						console.log(capacity);
						if (capacity == 0) {
							$blockEnclosure.empty();
							$blockEnclosure.prepend("construire");
						} else if (capacity > 0) {
							$blockEnclosure.empty();
							$blockEnclosure.prepend("deja construit");
						}
					}
				}
			}
		};

		var monObjet = {};
		server.monAjax(monObjet, "home", callback, 'POST');
	}

	$(document).ready(function() {
		showEnclosures();
	})
})(jQuery);
