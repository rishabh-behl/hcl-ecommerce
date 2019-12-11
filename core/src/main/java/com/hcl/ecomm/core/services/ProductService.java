package com.hcl.ecomm.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

public interface ProductService {

	public String getDomainName();
	
	public String getServicePath();
	
	public String getSearchCriteriaField();
	
	public String getSearchCriteriaValue();
	
	public String getProductDetails(SlingHttpServletRequest request, SlingHttpServletResponse response);
}
