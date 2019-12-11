
$(document).on("update", function(){
	if(productDetail!==undefined || productDetail!==null || productDetail!==""){
		var pName = document.getElementsByClassName("pName");
        var pDescription = document.getElementsByClassName("pDescription");
		var pPrice =  document.getElementsByClassName("pPrice");
		var skus = $("[data-product-sku]");
		for(var a=0; a< skus.length; a++){
			for(var i=0; i< productDetail.items.length; i++){
				if(skus[a].attributes[1].nodeValue===productDetail.items[i].sku){
					for(var b=0; b<pName.length; b++){
                        if(a===b){
                            pName[b].innerHTML=productDetail.items[i].name;
                            pPrice[b].innerHTML=productDetail.items[i].price;
                            for(var z=0; z<productDetail.items[i].custom_attributes.length;z++){
                                    if(productDetail.items[i].custom_attributes[z].attribute_code=="description"){
    									pDescription[b].innerHTML=productDetail.items[i].custom_attributes[z].value;
                                        break;
                                    }
                                }
                            break;
						}
					}
                }
			}
		}
	}
});

