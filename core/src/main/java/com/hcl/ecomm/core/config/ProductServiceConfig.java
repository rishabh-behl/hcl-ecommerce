package com.hcl.ecomm.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Product Service Configuration")
public @interface ProductServiceConfig {

	@AttributeDefinition(
			name="DOMAIN NAME",
			description="This ia domain name",
			defaultValue="localhost:8081/magento/rest",
			type=AttributeType.STRING
			)
	String productService_domainName();
	
	@AttributeDefinition(
			name="SERVICE_PATH",
			description="This is the API path",
			defaultValue="/V1/products",
			type=AttributeType.STRING
			)
	String productService_servicePath();
	
	@AttributeDefinition(
			name="Search Criteria Field",
			description="This is the field for search criteria",
			defaultValue="store_id",
			type=AttributeType.STRING
			)
	String productService_searchCriteriaField();
	
	@AttributeDefinition(
			name="Search Criteria Value",
			description="This is the value for search criteria",
			defaultValue="2",
			type=AttributeType.STRING
			)
	String productService_searchCriteriaValue();
	
}
