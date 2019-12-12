package com.hcl.ecomm.core.services.impl;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.ecomm.core.config.LoginServiceConfig;
import com.hcl.ecomm.core.services.LoginService;




@Component(service = {LoginService.class})
@Designate(ocd = LoginServiceConfig.class)

@ServiceDescription("Login service")
@ServiceRanking(1001)
@ServiceVendor("Shubham Mittal")

public class LoginServiceImpl implements LoginService{

	private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);	
	
	 @Activate
	 private LoginServiceConfig config;

	@Override
	public String getDomainName() {		
		return this.config.loginservice_domainName_string();
	}
	@Override
	public String getServicePath() {
		return this.config.loginservice_servicePath_string();
	}
	@Override
	public String getUsername() {
		return this.config.loginservice_username_string();
	}
	@Override
	public String getPassword() {
		return this.config.loginservice_password_string();
	}
}
