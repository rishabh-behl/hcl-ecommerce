package com.hcl.ecomm.core.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
		service=Servlet.class,
		property={
				"sling.servlet.methods=" +HttpConstants.METHOD_GET,
				"sling.servlet.paths=/bin/hclecomm/order",
				"sling.servlet.extensions=json"
		}
	)
public class OrderServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(OrderServlet.class);

	String token;
	String url;
	
	CloseableHttpClient httpClient = HttpClients.createDefault();
	
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException{
		
		token= (String)request.getAttribute("token");
		
		if(token == null){
			RequestDispatcher rd = request.getRequestDispatcher("/bin/hclecomm/login");
			rd.forward(request,response);
		}
		else{
			String action = request.getParameter("action");
			String cartId = request.getParameter("cartId");	
			LOG.info("Cart ID is : "+cartId);
			
			String bearerToken = "Bearer " + token;
			String finalToken = bearerToken.replaceAll("\"","");
			LOG.info("Final Token Value is : " +finalToken );
			
			if(cartId==null || cartId.isEmpty() || cartId==""){
				response.getWriter().append("No cart found.");
			}
			else{
				if(action==null || action.isEmpty() || action==""){
				response.getWriter().append("No action to perform on cart.");
				}
				else{
					if("shipping".equalsIgnoreCase(action)){
						LOG.info("inside shipping action");
						url="http://localhost:8081/magento/rest/V1/guest-carts/"+cartId+"/shipping-information";			
						String payload = request.getParameter("payload");
						
						/*Sample Payload
						payload_shipping
						{ "addressInformation": { "shippingAddress": { "country_id": "US","street": 
						[ "HCL Technologies Pvt. Ltd.","Plot-3A, Sector 126","Noida, U.P."],"company": "HCL",
						"telephone": "87654321092","postcode": "201301","region_id": 23,"city": "Noida",
						"firstname": "Shubham","lastname": "Mittal","email": "shubham@gmail.com","sameAsBilling": 1 },
						"billingAddress": { "country_id": "US","street": [ "HCL Technologies Pvt. Ltd.","Plot-3A, Sector 126",
						"Noida, U.P." ],"company": "HCL","telephone": "87654321092","postcode": "201301","region_id": 23,
						"city": "Noida","firstname": "Shubham","lastname": "Mittal","email": "shubham@gmail.com" }, 
						"shipping_method_code": "flatrate", "shipping_carrier_code": "flatrate" } }
						*/
						
						if(payload==null || payload.isEmpty() || payload==""){
							response.getWriter().append("No shipping details found for the cart "+cartId);
						}
						else{
							StringEntity input = new StringEntity(payload);
							
							HttpPost httpPost = new HttpPost(url);	
							httpPost.setEntity(input);
							httpPost.setHeader("Content-Type","application/json");				
							httpPost.setHeader("Authorization", finalToken);
							
							CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
							if (httpResponse.getStatusLine().getStatusCode()==200){
								String responseStream = EntityUtils.toString(httpResponse.getEntity());
								LOG.info("Shipping information added to the cart "+cartId+" with payment information as : "+responseStream);
								response.getWriter().append("Payment methods available are : "+responseStream);
							}
							else{
								response.getWriter().append("Error adding Shipping information to cart "+cartId);
								LOG.error("Error adding Shipping information to cart "+cartId);
							}
						}
						
					}
					else if("payment".equalsIgnoreCase(action)){
						LOG.info("inside payment action");
						url="http://localhost:8081/magento/rest/V1/guest-carts/"+cartId+"/order";			
						String payload = request.getParameter("payload");
						
						/*Sample Payload
						payload_payment
						{ "paymentMethod": { "method": "checkmo" } }
						*/
						
						if(payload==null || payload.isEmpty() || payload==""){
							response.getWriter().append("No payment details found for the cart "+cartId);
						}
						else{
							StringEntity input = new StringEntity(payload);
							
							HttpPut httpPut = new HttpPut(url);	
							httpPut.setEntity(input);
							httpPut.setHeader("Content-Type","application/json");				
							httpPut.setHeader("Authorization", finalToken);
							
							CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
							if (httpResponse.getStatusLine().getStatusCode()==200){
								String responseStream = EntityUtils.toString(httpResponse.getEntity());
								LOG.info("Payment information added to the cart "+cartId+" with with order ID : "+responseStream);
								response.getWriter().append("Order has been created with order ID : "+responseStream);
							}
							else{
								response.getWriter().append("Error adding Payment information to cart "+cartId);
								LOG.error("Error adding Payment information to cart "+cartId);
							}
						}						
					}
				}
			}			
		}
	}
}
