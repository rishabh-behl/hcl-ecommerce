package com.hcl.ecomm.core.services.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.ecomm.core.config.ProductServiceConfig;
import com.hcl.ecomm.core.services.ProductService;
import com.hcl.ecomm.core.servlets.ProductServlet;

@Component(immediate = true, enabled = true, service = ProductService.class)
@Designate(ocd = ProductServiceConfig.class)
public class ProductServiceImpl implements ProductService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

	private ProductServiceConfig config;

	@Activate
	protected void activate(ProductServiceConfig config) {
		this.config = config;
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

	@Override
	public String getProductDetails(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		LOG.info("inside getProductDetails method");

		String scheme = "http";
		String domainName;
		String servicePath;
		String searchCriteriaField;
		String searchCriteriaValue;
		String responseStream = null;

		String token = (String) request.getAttribute("token");
		try {
			if (token == null) {
				RequestDispatcher rd = request.getRequestDispatcher("/bin/hclecomm/login");
				rd.forward(request, response);
			} else {
				LOG.info("value of token : " + token);

				domainName = getDomainName();
				servicePath = getServicePath();
				searchCriteriaField = getSearchCriteriaField();
				searchCriteriaValue = getSearchCriteriaValue();

				String productUrl = scheme + "://" + domainName + servicePath
						+ "?searchCriteria[filterGroups][0][filters][0][field]=" + searchCriteriaField
						+ "&searchCriteria[filterGroups][0][filters][0][value]=" + searchCriteriaValue;

				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(productUrl);

				httpGet.setHeader("Content-Type", "application/json");
				String bearerToken = "Bearer " + token;
				String finalToken = bearerToken.replaceAll("\"", "");
				LOG.info("Final Token Value is : " + finalToken);
				httpGet.setHeader("Authorization", finalToken);

				CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					responseStream = EntityUtils.toString(httpResponse.getEntity());
					LOG.info("Product Response Json : " + responseStream);
				} else {
					responseStream = "Failed to fetch products from the store";
					LOG.error("Failed to fetch products from the store");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStream;
	}
}
