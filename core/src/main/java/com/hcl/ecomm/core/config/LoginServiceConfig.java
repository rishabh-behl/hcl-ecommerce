package com.hcl.ecomm.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Login Service Configuration")
public @interface LoginServiceConfig {

	@AttributeDefinition(
			name="DOMAIN_NAME",
			description="This is domain name",
			defaultValue="localhost:8081/magento/rest",
			type=AttributeType.STRING
			)	
	String loginservice_domainName_string();
	
	@AttributeDefinition(
			name="SERVICE_PATH",
			description="This is the API path",
			defaultValue="/V1/integration/admin/token",
			type=AttributeType.STRING
			)
	String loginservice_servicePath_string();
	
	@AttributeDefinition(
			name="USERNAME",
			description="USERNAME",
			defaultValue="admin",
			type=AttributeType.STRING
			)	
	String loginservice_username_string();
	
	@AttributeDefinition(
			name="PASSWORD",
			description="PASSWORD",
			defaultValue="Admin123",
			type=AttributeType.STRING
			)	
	String loginservice_password_string();
}