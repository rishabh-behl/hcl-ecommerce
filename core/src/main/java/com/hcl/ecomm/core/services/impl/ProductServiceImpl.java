package com.hcl.ecomm.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.hcl.ecomm.core.config.ProductServiceConfig;
import com.hcl.ecomm.core.services.ProductService;

@Component(
		immediate=true,
		enabled=true,
		service=ProductService.class
		)
@Designate(ocd=ProductServiceConfig.class)
public class ProductServiceImpl implements ProductService{

	private ProductServiceConfig config;
	
	@Activate
	protected void activate(ProductServiceConfig config){
		this.config=config;
	}
	
	@Override
	public String getDomainName() {
		return config.productService_domainName();
	}

	@Override
	public String getServicePath() {
		// TODO Auto-generated method stub
		return config.productService_servicePath();
	}

	@Override
	public String getSearchCriteriaField() {
		// TODO Auto-generated method stub
		return config.productService_searchCriteriaField();
	}

	@Override
	public String getSearchCriteriaValue() {
		// TODO Auto-generated method stub
		return config.productService_searchCriteriaValue();
	}

}
