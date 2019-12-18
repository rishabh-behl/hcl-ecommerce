package com.hcl.ecomm.core.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.google.gson.JsonArray;
import com.hcl.ecomm.core.services.ProductService;
import com.hcl.ecomm.core.utility.ProductUtility;

@Model(adaptables = Resource.class)
public class BannerModel {

	@Inject
	private String productSku;

	public String getProductSku() {
		return productSku;
	}

	@OSGiService
	ProductService productService;

	public Map<String, String> getProductMap() {
		Map<String, String> productMap = new HashMap<>();
		JsonArray productJsonArray = productService.getProductDetail(productSku);
		productMap = ProductUtility.fromJsonArrayToMap(productJsonArray);

		return productMap;
	}

	public List<Map<String, String>> getAllProductList() {
		List<Map<String, String>> productList = new LinkedList<Map<String, String>>();
		JsonArray productJsonArray = productService.getAllProductDetails();
		productList = ProductUtility.fromJsonArrayToList(productJsonArray);
		return productList;
	}

}
