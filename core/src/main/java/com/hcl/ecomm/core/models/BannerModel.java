package com.hcl.ecomm.core.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.hcl.ecomm.core.services.ProductService;
import com.hcl.ecomm.core.utility.ProductUtility;

@Model(adaptables = Resource.class)
public class BannerModel {

	private static final Logger LOG = LoggerFactory.getLogger(BannerModel.class);

	@Inject
	@Default(values = "24-MB02")
	private String productSku;

	public String getProductSku() {
		return productSku;
	}

	@OSGiService
	ProductService productService;

	Map<String, String> productMap = new HashMap<>();

	@PostConstruct
	protected void init() {
		try {
			LOG.info("inside init() method");
			JsonArray productJsonArray = productService.getProductDetail(productSku);
			productMap = ProductUtility.fromJsonArrayToMap(productJsonArray);

		} catch (Exception e) {
			LOG.error("Exception caught in init() method : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Map<String, String> getProductMap() {
		return productMap;
	}

	public List<Map<String, String>> getAllProductList() {
		List<Map<String, String>> productList = new LinkedList<Map<String, String>>();
		JsonArray productJsonArray = productService.getAllProductDetails();
		productList = ProductUtility.fromJsonArrayToList(productJsonArray);
		return productList;
	}

}
