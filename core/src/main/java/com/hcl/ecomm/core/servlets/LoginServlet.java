package com.hcl.ecomm.core.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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
import com.hcl.ecomm.core.services.LoginService;

@Component(
		service = Servlet.class,
		property = {
				"sling.servlet.extensions=json",
				"sling.servlet.paths=/bin/hclecomm/login",
				"sling.servlet.method="	 + HttpConstants.METHOD_GET,			
		}
	)
public class LoginServlet extends SlingSafeMethodsServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);	
	
	@Reference
	private LoginService loginService;	
	
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOG.info("inside doGET method");
		
		LOG.info("loginService : " + loginService);
		
		String token = loginService.getToken();		
		response.getWriter().append("Token Value : "+token);
		
		/*String uri=request.getRequestURI();
		if(uri.equals("/bin/hclecomm/products.json")){
			RequestDispatcher rd = request.getRequestDispatcher("/bin/hclecomm/products");		
			rd.include(request,response);
		}
		else if(uri.equals("/bin/hclecomm/cart.json")){
			RequestDispatcher rd = request.getRequestDispatcher("/bin/hclecomm/cart");		
			rd.include(request,response);
		}
		else if(uri.equals("/bin/hclecomm/order.json")){
			RequestDispatcher rd = request.getRequestDispatcher("/bin/hclecomm/order");		
			rd.include(request,response);
		}
		else{
			response.getWriter().append("Token Value : "+token);
		}*/
	}
	
	
}
