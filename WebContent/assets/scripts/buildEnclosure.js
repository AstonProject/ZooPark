//sessionStorage.setItem('clé', 'valeur');
(function($) {
	 "use strict";
	
	// Fonction d'affichage des descriptions associées à la selection des radios EnclosureType
	function showDescription($selectedRadioEnclosure) {
		var $blockDescription=$('.enclosureDescription');
		var statusD = "okD";
			$selectedRadioEnclosure.on('click', function() {
				// Reinitialise le contenu du blockDescription si un radio EnclosureType est selectionne
				$blockDescription.empty();
				// Affichage des descriptions selon le radio selectionne
					var callback1=function(donnees){
							if ($('#radio_elephant').is(':checked')){
								$blockDescription.prepend("<div>"+ donnees.description0 + "</div>");
							} else if ($('#radio_giraffe').is(':checked')){
								$blockDescription.prepend("<div>"+ donnees.description1 + "</div>");	
							} else if ($('#radio_lion').is(':checked')){
								$blockDescription.prepend("<div>"+ donnees.description2 + "</div>");	
							} else if ($('#radio_camel').is(':checked')){
								$blockDescription.prepend("<div>"+ donnees.description3 + "</div>");	
							}
					};
					 var object ={"statusDescriptions":statusD};
					 server.monAjax(object, "createEnclosure", callback1, 'POST');
		});
	}
	
	// Fonction d'affichage des prix
	function showEnclosurePrice($selectedRadioEnclosure, $selectedRadioSize){
		//attribution d'une variable selectionnant la div d'affichage des prix
		var $blockPrice=$('.price');
    	var statusP = "okP";
		$selectedRadioEnclosure.on('click', function() {
			// Reinitialise le contenu du blockPrice si un radio EnclosureType est selectionne
			$blockPrice.empty();

			// Réinitialise les radioSize si une radio enclosureType est selectionnee
        	$('input:radio[name=enclosureSize]').each(function () { $(this).prop('checked', false); });
			 $selectedRadioSize.on('change', function() {
				// Reinitialise le contenu du blockPrice si un radio EnclosureSize est selectionne
				 $blockPrice.empty();
				 var callback2 = function(donnees) {
					 var EnclosurePrice;
					 
					//Verificaation du radio selectionne et correlation avec les prix de CostsDAO 
					 if ($('#radio_elephant').is(':checked')){
						EnclosurePrice= donnees.enclosureCosts_elephant;
					 } else if ($('#radio_giraffe').is(':checked')){
						EnclosurePrice= donnees.enclosureCosts_giraffe;
					 }else if ($('#radio_lion').is(':checked')){
						EnclosurePrice= donnees.enclosureCosts_lion;
					 }else if ($('#radio_camel').is(':checked')){
						EnclosurePrice= donnees.enclosureCosts_camel;
					 }
					// Reinitialise le contenu du blockPrice (necessaire)
					 $blockPrice.empty();
					
					if(($('#size_1').is(':checked'))){
						 $blockPrice.prepend("<div>"+ (EnclosurePrice*(-1)) + "</div>");
					} else if (($('#size_2').is(':checked'))){
						$blockPrice.prepend("<div>"+ (EnclosurePrice*(-2)) + "</div>");
					}
					 else if (($('#size_3').is(':checked'))){
						$blockPrice.prepend("<div>"+ (EnclosurePrice*(-3)) + "</div>");	
					}
				 };
				 
				 var object ={"statusPrices":statusP};
				 server.monAjax(object, "createEnclosure", callback2, 'POST');
			 });
		 });
		
	}
	
	
	//Fonction de récupération des données: type d'enclos d'enclos, taille et de son coût
	function getForm(){
			var $formCE = $('#FormCreateEnclosure');
			var $radioType = $formCE.find('#radio1');
			var $radioSize = $formCE.find('#radio2');
			var $blockError= $('.error');
			var type= null;
			var size= null;
			var capacity=null;
			var specie_id= null;
			var statusF = "okF";
				
			
		$formCE.on('submit', function(event) {
			// Bypass du submit pour la fonction callback
			event.preventDefault();
			
			//Recuperation des valeurs des radios EnclosureType & EnclosureSize
			type= $('input[name=enclosureType]:checked').val();
			size=$('input[name=enclosureSize]:checked').val();
			
			//Modification de la capacité de l'enclos  selon le radio EnclosureSize selectionne
			if(size == 1){
				capacity = 5;
			}else if(size == 2){
				 capacity = 10;
			}else if (size == 3){
				 capacity = 15;
			} 
			
			//Modification de FK_specie_id de l'enclos selon le radio EnclosureType selectionne
			if(type == "Elephant"){
				specie_id = 1;
			}else if(type == "Giraffe"){
				specie_id = 2;
			} else if (type == "Lion"){
				specie_id = 3;
			}else if (type == "Camel"){
				specie_id = 4;
			}
			
			var callback=function(donnees){
				if(donnees.code == "OK"){
					 window.location.href = "home";
                }else {
                	 failed();
                }
			};
			
			//Remplissage de l'objet a envoyer
			var object ={
					"specie_id":specie_id,
					"capacity":capacity,
					"statusForm":statusF
			};
			if(specie_id = null || capacity != null){
				server.monAjax(object, "createEnclosure", callback, 'POST');
			}else{
				$blockError.empty();
				$blockError.prepend("<h2> Please, select a type and a size to bluid an enclosure... </h2>");
			}
			
		});
			
		
	}
	
	$(document).ready(
			function() {

				// Creation d'objets jQuery referancant les 4 id radio EnclosureType
				var $radioE1 = $('#radio_elephant');
				var $radioE2 = $('#radio_giraffe');
				var $radioE3 = $('#radio_lion');
				var $radioE4 = $('#radio_camel');

				// Creation d'objet jQuery referancant les 3 id radio EnclosureSize
				var $radioS1 = $('#size_1');
				var $radioS2 = $('#size_2');
				var $radioS3 = $('#size_3');

				/**Execution des fonctions **/
				// Execution des fonctions d'affichage de description des
				// enclos selon le radio "enclosureType" selectionnée
				showDescription($radioE1);
				showDescription($radioE2);
				showDescription($radioE3);
				showDescription($radioE4);
				
				// Execution des fonctions d'affichage des prix des
				// enclos selon les selections des radios EnclosureType et EnclosureSize
				showEnclosurePrice($radioE1, $radioS1);
				showEnclosurePrice($radioE1, $radioS2);
				showEnclosurePrice($radioE1, $radioS3);
				
				showEnclosurePrice($radioE2, $radioS1);
				showEnclosurePrice($radioE2, $radioS2);
				showEnclosurePrice($radioE2, $radioS3);
				
				showEnclosurePrice($radioE3, $radioS1);
				showEnclosurePrice($radioE3, $radioS2);
				showEnclosurePrice($radioE3, $radioS3);
				
				showEnclosurePrice($radioE4, $radioS1);
				showEnclosurePrice($radioE4, $radioS2);
				showEnclosurePrice($radioE4, $radioS3);
				
				//Execution de la fonction de recuperation des données d'enclos 
				//pour sa création depuis le controleur BuildEnclosureMenu
				getForm();
			})
})(jQuery);
