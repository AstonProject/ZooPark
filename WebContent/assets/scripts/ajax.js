var irc={};

(function($) {
    "use strict";

    irc.monAjax = function(dataObj, url, callback, method) {
        var objAjax = {
            url: "http://localhost:8080/zoopark/", 
            data: dataObj, 
            type: method || 'post', 
            dataType: 'json',
            xhrFields:{
                withCredentials:true,
            },

            complete: function(xhr, status, msg) {
                console.log("Requête bien envoyée");
            },
            success: function(donnees, textStatus) {
                callback(donnees);
            },
            error: function(xhr, status, msgErreur) {
                console.log("Une erreur de type 4xx ou 5xx ou timeout");
            },
        };
        $.ajax(objAjax);
    }
})(jQuery);
