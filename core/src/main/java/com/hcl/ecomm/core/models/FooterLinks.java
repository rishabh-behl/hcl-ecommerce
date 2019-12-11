package com.hcl.ecomm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class FooterLinks {

	@Inject
	public String links;
}
