
	function visitors() {
		let object ={};
		let hourT = null;
				hourT = sessionStorage.getItem("hour");
				
					//Generer des visiteurs a l'heure 1
					if(hourT == 1){
						var statusGV = "okGV";
						var callback = function(donnees) {
							$('#visitors').empty();
							$('#money').empty();
							$('#visitors').append("Visitors : "+ donnees.visitors);
							$('#money').append("Money : " + donnees.money + " Z");
						};
						
						object = {"statusGV" : statusGV};
						server.monAjax(object, "visitorsManagement", callback, 'POST');
						
					}
					//La journée des visiteurs
					 if(hourT > 1 && hourT <7){
						 var statusDV = "okDV";
						 var callback = function(donnees) {
							 $('#money').empty();
							$('#satisfaction').empty();
							$('#satisfaction').append("Satisfaction : " + donnees.satisfaction);
							$('#money').append("Money : " + donnees.money + " Z");
						 };
							
							object = {"statusDV" : statusDV};
							server.monAjax(object, "visitorsManagement", callback, 'POST');	 
					 }
					 
					 //Le Depart des visiteurs la nuit
					 if(hourT == 7){
						 var statusNV = "okNV";
							var callback = function(donnees) {
								$('#visitors').empty();
								$('#visitors').append("Visitors : "+ donnees.visitors);
							};
							
							object = {"statusNV" : statusNV};
							server.monAjax(object, "visitorsManagement", callback, 'POST');
					 }			
			}	