package com.hcl.ecomm.core.services;

import java.util.List;

import com.google.gson.JsonArray;

public interface ProductService {

	public String getDomainName();

	public String getServicePath();

	public String getSearchCriteriaField();

	public String getSearchCriteriaValue();

	public JsonArray getAllProductDetails();

	public List<String> getAllProductSkus(JsonArray productJson);

	public JsonArray getProductDetail(String sku);

}
