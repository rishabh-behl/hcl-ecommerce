package com.hcl.ecomm.core.services.impl;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.ecomm.core.config.LoginServiceConfig;
import com.hcl.ecomm.core.services.LoginService;


@Component(
		enabled=true,
		immediate=true,
		name="Login Service Configuration structure",
		service = LoginService.class,
		configurationPolicy = ConfigurationPolicy.REQUIRE
		)
@Designate(ocd = LoginServiceConfig.class)
public class LoginServiceImpl implements LoginService{

	private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);	
		
	private LoginServiceConfig config;
	
	@Activate
	protected void activate(LoginServiceConfig config) {
		this.config=config;
	}
	@Override
	public String getDomainName() {		
		return config.loginservice_domainName_string();
	}
	@Override
	public String getServicePath() {
		return config.loginservice_servicePath_string();
	}
	@Override
	public String getUsername() {
		return config.loginservice_username_string();
	}
	@Override
	public String getPassword() {
		return config.loginservice_password_string();
	}
}
