package com.hcl.ecomm.core.services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ProductUtility {

	public static JsonArray fromStringToJsonArray(String responseStream) {

		JsonArray j_array = null;
		JsonObject jobj = new Gson().fromJson(responseStream, JsonObject.class);
		if (jobj.has("items")) {
			j_array = jobj.getAsJsonArray("items");
		} else {
			j_array = jobj.getAsJsonArray();
		}

		return j_array;
	}

	public static Map<String, String> fromJsonArrayToMap(JsonArray productJsonArray) {

		HashMap<String, String> productMap = new HashMap<>();

		try {

			JsonObject productObject = productJsonArray.get(0).getAsJsonObject();
			Set<String> keys = productObject.keySet();

			for (String key : keys) {
				productMap.put(key, productObject.get(key).getAsString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return productMap;
	}

	public static List<Map<String, String>> fromJsonArrayToList(JsonArray productJsonArray) {

		List<Map<String, String>> productList = new LinkedList<Map<String, String>>();

		for (JsonElement jel : productJsonArray) {
			Map<String, String> productMap = new HashMap<>();

			productMap = fromJsonArrayToMap(jel.getAsJsonArray());
			productList.add(productMap);
		}

		return productList;
	}
}
