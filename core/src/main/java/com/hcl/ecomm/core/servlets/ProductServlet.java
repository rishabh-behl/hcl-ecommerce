package com.hcl.ecomm.core.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.ecomm.core.services.ProductService;

@Component(service = Servlet.class, property = { "sling.servlet.paths=/bin/hclecomm/products",
		"sling.servlet.method=" + HttpConstants.METHOD_GET, "sling.servlet.extensions=json" })
public class ProductServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 4016057296495129474L;
	private static final Logger LOG = LoggerFactory.getLogger(ProductServlet.class);

	@Reference
	ProductService productService;

	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		LOG.info("inside doGET method");

		String responseStream = productService.getProductDetails(request, response);
		response.getWriter().append(responseStream);

		/*
		 * URL url = new URL(productUrl); HttpURLConnection conn =
		 * (HttpURLConnection) url.openConnection();
		 * conn.setRequestMethod("GET"); String bearerToken = "Bearer " + token;
		 * conn.setRequestProperty("Authorization", bearerToken);
		 * conn.setRequestProperty("Content-Type","application/json");
		 * conn.connect(); int value=conn.getResponseCode();
		 * if(value==HttpURLConnection.HTTP_OK){ BufferedReader in = new
		 * BufferedReader(new InputStreamReader(conn.getInputStream())); String
		 * inputLine; StringBuffer responseStream = new StringBuffer(); while
		 * ((inputLine = in.readLine()) != null) {
		 * responseStream.append(inputLine); }
		 * 
		 * response.getWriter().append(responseStream); } else{
		 * LOG.error(conn.getResponseMessage()); }
		 */
	}
}
