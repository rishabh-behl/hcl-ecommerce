package com.hcl.ecomm.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Login Service Configuration")
public @interface LoginServiceConfig {

	String servicePathDefaultValue = "/V1/integration/admin/token";
	String domainNameDefaultValue = "localhost:8081/magento/rest";
	String userNameDefaultValue = "admin";
	String passwordDefaultValue = "Admin123";
	
	@AttributeDefinition(
			name="DOMAIN_NAME",
			description="This is domain name",
			defaultValue=domainNameDefaultValue,
			type=AttributeType.STRING
			)	
	String loginservice_domainName_string() default domainNameDefaultValue;
	
	@AttributeDefinition(
			name="SERVICE_PATH",
			description="This is the API path",
			defaultValue=servicePathDefaultValue,
			type=AttributeType.STRING
			)
	String loginservice_servicePath_string() default servicePathDefaultValue;
	
	@AttributeDefinition(
			name="USERNAME",
			description="USERNAME",
			defaultValue=userNameDefaultValue,
			type=AttributeType.STRING
			)	
	String loginservice_username_string() default userNameDefaultValue;
	
	@AttributeDefinition(
			name="PASSWORD",
			description="PASSWORD",
			defaultValue=passwordDefaultValue,
			type=AttributeType.STRING
			)	
	String loginservice_password_string() default passwordDefaultValue;
}