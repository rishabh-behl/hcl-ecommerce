package com.hcl.ecomm.core.models;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hcl.ecomm.core.services.ProductService;
import com.hcl.ecomm.core.services.impl.ProductServiceImpl;

@Model(adaptables ={ Resource.class, SlingHttpServletRequest.class, SlingHttpServletResponse.class})
public class ProductPageModel {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@OSGiService
	private ProductService productService;
	
	@Inject
	SlingHttpServletRequest slingRequest;
	
	@Inject
	SlingHttpServletResponse slingResponse;
	
	private HashMap<String, String> productMap;
	
	@PostConstruct
	protected void init()
	{
		try{
			//productService.getProductDetails(slingRequest, slingResponse);
			JsonArray productArray = new JsonArray();
			String responseStream = "[{\n" +
                    "\t\t\"name\": 1,\n" +
                    "\t\t\"price\": \"24-MB01\"\n" +
                    "\t}]";
			JsonParser parser = new JsonParser();
            productArray   = parser.parse(responseStream).getAsJsonArray();
            //productMap = getProductMap(productArray);
		}
		catch (Exception e) {
			
		}
	}
	
	//public HashMap<String, String> getProductMap(JsonArray productArray)
	public HashMap<String, String> getProductMap()
	{
		HashMap<String, String> productMapp =  new HashMap<>();
		/*for (int i = 0, size = productArray.size(); i < size; i++)
		{
			JsonObject object = productArray.get(i).getAsJsonObject();
			productMapp.put(object.keySet(), value)
		}*/
		productMapp.put("name", "Joset Travel Bag");
		productMapp.put("price", "25.4");
		productMapp.put("description", "This bag has all the qualities required for the travel. It has great spcae");
		return productMapp;
	}
	
	
}
