(function($) {
	"use strict";
	
	// fonction pour afficher les stocks et initialiser les maximums de revente
	function showConsumableStock() {
		var statusSCS = "okSCS";
		
		var $inputMeat = $('#meat_quantity');
		var $inputFish = $('#fish_quantity');
		var $inputStrawBale = $('#straw_bale_quantity');
		
		var countMeatStock = 0;
		var countFishStock = 0;
		var countStrawBaleStock = 0; 
		
		var meatQuantity = 0;
		var fishQuantity = 0;
		var strawBaleQuantity = 0;
		
		var callback = function(donnees) {
			
			console.log(donnees);
			
			// enregistrement des stocks en local
			countMeatStock = donnees.countMeatStock;
			countFishStock = donnees.countFishStock;
			countStrawBaleStock = donnees.countStrawBaleStock; 
			
			console.log("stock meat", countMeatStock);
			console.log("stock fish", countFishStock);
			console.log("stock bale", countStrawBaleStock);
			
			// affichage des stocks sur la jsp
			$('.meat_stock').prepend(countMeatStock);
			$('.fish_stock').prepend(countFishStock);
			$('.straw_bale_stock').prepend(countStrawBaleStock);
			
			// recuperation de la valeur des input
			meatQuantity = $('input[name=quantityMeat]').val();
			fishQuantity = $('input[name=quantityFish]').val();
			strawBaleQuantity = $('input[name=quantityStrawBale]').val();
			
			// setting des maximums de revente
			$inputMeat.attr("min", (countMeatStock * (-1)));
			$inputFish.attr("min", (countFishStock * (-1)));
			$inputStrawBale.attr("min", (countStrawBaleStock * (-1)));
		};
		
		var monObj = {
			"statusSCS": statusSCS
		};
		
		server.monAjax(monObj, "consumableManagement", callback, 'POST');
	}
	
	// fonction pour afficher le prix
	function showConsumablePrice() {
		var statusSCP = "okSCP";
		var $blockConsPrice = $('.cons_price');
		
		// reinitialise le contenu du blockPrice
		$blockConsPrice.empty();
		
		var meatQuantity = 0;
		var fishQuantity = 0;
		var strawBaleQuantity = 0;
		
		// recuperation de la valeur des input
		meatQuantity = $('input[name=quantityMeat]').val();
		fishQuantity = $('input[name=quantityFish]').val();
		strawBaleQuantity = $('input[name=quantityStrawBale]').val();
		
		var consumables_price = 0;
		
		sessionStorage.setItem("consumables_price", consumables_price);
		
		var callback = function(donnees) {
			
			if (meatQuantity > 0) {
				consumables_price += (donnees.meatCosts * meatQuantity);
			}
			if (fishQuantity > 0) {
				consumables_price += (donnees.fishCosts * fishQuantity);
			}
			if (strawBaleQuantity > 0) {
				consumables_price += (donnees.strawBaleCosts * strawBaleQuantity);
			}
			if (meatQuantity < 0) {
				consumables_price += (donnees.meatCosts * meatQuantity * 0.75);
			}
			if (fishQuantity < 0) {
				consumables_price += (donnees.fishCosts * fishQuantity * 0.75);
			}
			if (strawBaleQuantity < 0) {
				consumables_price += (donnees.strawBaleCosts * strawBaleQuantity * 0.75);
			}
			
			consumables_price *= (-1);

			sessionStorage.setItem("consumables_price", consumables_price);
			$blockConsPrice.prepend("<div>"+ consumables_price + "</div>");	
		};
		
		var monObj = {
			"statusSCP": statusSCP
		};
		
		server.monAjax(monObj, "consumableManagement", callback, 'POST');
		
	}
	
	function refreshConsumablePriceOnChange($selectedConsumableQuantity) {
		$($selectedConsumableQuantity).on('change', function() {
			showConsumablePrice();
		});
	}
	
	// fonction pour valider l'achat/vente
	function getConsumableForm() {
		var statusGCF = "okGCF";
		var $formConsumable = $('#FormConsumableValidation');
		
		$formConsumable.on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();
			
			var priceConsumable = 0;
			var meatQuantity = 0;
			var fishQuantity = 0;
			var strawBaleQuantity = 0;
			
			// recuperation des valeurs et du prix
			meatQuantity = $('input[name=quantityMeat]').val();
			fishQuantity = $('input[name=quantityFish]').val();
			strawBaleQuantity = $('input[name=quantityStrawBale]').val();

			console.log("add meat", meatQuantity);
			console.log("add fish", fishQuantity);
			console.log("add bale", strawBaleQuantity);
			
			priceConsumable = sessionStorage.getItem("consumables_price");
			console.log(priceConsumable);
			
			if (meatQuantity == "") {
				meatQuantity = 0;
			}
			
			if (fishQuantity == "") {
				fishQuantity = 0;
			}
			
			if (strawBaleQuantity == "") {
				strawBaleQuantity = 0;
			}
			
			// redirection sur le controller home
			var callback = function(donnees){
				if (donnees.code == "OK") {
					 window.location.href = "home";
				}else if (donnees.code == "ERROR"){
					failed();
				}
			};
			
			// creation de l'objet foodQuantity
			var monObj = {
				"statusGCF": statusGCF,
				"priceConsumable": priceConsumable,
				"meatQuantity": meatQuantity,
				"fishQuantity": fishQuantity,
				"strawBaleQuantity": strawBaleQuantity
				
			};
			
			console.log(monObj);
			
			server.monAjax(monObj, "consumableManagement", callback, 'POST');
			console.log("envoie des données");
		});
	}
	
	$(document).ready(function() {
		var $meat_button = $('#meat_quantity');
		var $fish_button = $('#fish_quantity');
		var $straw_bale_button = $('#straw_bale_quantity');
		
		// fonctions à appeler
		// attention cet ordre est important
		showConsumableStock();
		
		refreshConsumablePriceOnChange($meat_button);
		refreshConsumablePriceOnChange($fish_button);
		refreshConsumablePriceOnChange($straw_bale_button);
		
		getConsumableForm();
		
	})
	
})(jQuery);