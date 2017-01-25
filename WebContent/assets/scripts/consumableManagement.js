(function($) {
	"use strict";
	
	// fonction pour afficher les stocks
	function showConsumableStock() {
		var statusSCS = "okSCS";
		
		var callback = function(donnees) {
			
			console.log(donnees);
			
			$('.meat_stock').prepend(donnees.countMeatStock);
			$('.fish_stock').prepend(donnees.countFishStock);
			$('.straw_bale_stock').prepend(donnees.countStrawBaleStock);
			
			sessionStorage.setItem("countMtS", donnees.countMeatStock);
			sessionStorage.setItem("countFhS", donnees.countFishStock);
			sessionStorage.setItem("countSBS", donnees.countStrawBaleStock);
			
			console.log("countMtS", donnees.countMeatStock);
			console.log("countFhS", donnees.countFishStock);
			console.log("countSBS", donnees.countStrawBaleStock);
			
		};
		
		var monObj = {
			"statusSCS": statusSCS
		};
		
		server.monAjax(monObj, "consumableManagement", callback, 'POST');
	}
	
	// fonction pour afficher la quantite de food pouvant etre revendue (min)
	function setRestMinConsumableQuantity($selectedConsumableQuantity) {
		var statusSCQ = "okSCQ";
		
		var $inputQuantity = $(".mod");
		var $inputMeat = $('#meat_quantity');
		var $inputFish = $('#fish_quantity');
		var $inputStrawBale = $('#straw_bale_quantity');
		var countMtS = 0;
		var countFhS = 0;
		var countSBS = 0; 
		
		var meatQuantity = 0;
		var fishQuantity = 0;
		var strawBaleQuantity = 0;
		
		// recuperation de la valeur des input
		meatQuantity = $('input[name=quantityMeat]').val();
		fishQuantity = $('input[name=quantityFish]').val();
		strawBaleQuantity = $('input[name=quantityStrawBale]').val();
		
		try {
			countMtS = sessionStorage.getItem("countMtS");
			countFhS = sessionStorage.getItem("countFhS");
			countSBS = sessionStorage.getItem("countSBS");
			
		} catch (error) {
			console.log(error);
		}
		
		var callback = function(donnees) {
			$inputMeat.attr("min", (countMtS * (-1)));
			$inputFish.attr("min", (countFhS * (-1)));
			$inputStrawBale.attr("min", (countSBS * (-1)));
		};
		
		var monObj = {
			"statusSCQ": statusSCQ
		};
		
		server.monAjax(monObj, "consumableManagement", callback, 'POST');
		
	}
	
	// fonction pour rafraichir l'affichage
	function refreshRestMinConsumableQuantity($selectedConsumableQuantity){
		$($selectedConsumableQuantity).on('click', function() {
			setRestMinConsumableQuantity($selectedConsumableQuantity);
		});
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
		
		//refreshConsumablePriceOnChange($meat_button);
		//refreshConsumablePriceOnChange($fish_button);
		//refreshConsumablePriceOnChange($straw_bale_button);
		
		//setRestMinConsumableQuantity($meat_button);
		//setRestMinConsumableQuantity($fish_button);
		//setRestMinConsumableQuantity($straw_bale_button);
		
		//getConsumableForm();
		
		//refreshRestMinConsumableQuantity($meat_button);
		//refreshRestMinConsumableQuantity($fish_button);
		//refreshRestMinConsumableQuantity($straw_bale_button);
		
	})
	
})(jQuery);