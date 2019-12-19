$(document).ready(function(){

	var ajaxOpts = {
		url: '/bin/hclecomm/products.json',
        type: 'GET', 
        contenType: 'application/json',
        cache: false, 
        dataType: 'json', 
	};

	return $.ajax(ajaxOpts)
		.done(function(data){
			if(data!==undefined || data!==null || data!==""){
				pName.innerHTML=data.items.name;




            }
        });




}

