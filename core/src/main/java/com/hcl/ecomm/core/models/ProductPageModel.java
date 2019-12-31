package com.hcl.ecomm.core.models;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hcl.ecomm.core.services.ProductService;
import com.hcl.ecomm.core.services.impl.ProductServiceImpl;
import com.hcl.ecomm.core.utility.ProductUtility;

@Model(adaptables ={ Resource.class, SlingHttpServletRequest.class})
public class ProductPageModel {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@OSGiService
	private ProductService productService;
	
	@Inject
	SlingHttpServletRequest slingRequest;
	
	private Map<String, String> productMap;
	
	@PostConstruct
	protected void init()
	{
		LOG.info("Inside ProductPageModel init() method"); 
		try{
			String sku = slingRequest.getParameter("pid");
			LOG.info("Product SKU = " + sku);
			if(null!=sku && !sku.equals(""))
			{
				JsonArray productArray = productService.getProductDetail(sku);
				productMap = ProductUtility.fromJsonArrayToMap(productArray);
			}
		}
		catch (Exception e) {
			LOG.info(e.getMessage());
		}
	}

	public Map<String, String> getProductMap() {
		return productMap;
	}

}
