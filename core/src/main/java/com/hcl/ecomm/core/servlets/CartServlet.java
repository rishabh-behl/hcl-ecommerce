package com.hcl.ecomm.core.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
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
		service = Servlet.class,
		property = {
				"sling.servlet.extensions=json",
				"sling.servlet.paths=/bin/hclecomm/cart",
				"sling.servlet.method=" + HttpConstants.METHOD_GET				
		}
	)
public class CartServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CartServlet.class);
	
	String token;
	String url;
	
	CloseableHttpClient httpClient = HttpClients.createDefault();
	HttpPost httpPost;
		
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
			//cartId=rMFSYyqeTXGOqj4s7A9QRs9AVaLAMC9T (example)
			LOG.info("Cart ID is : "+cartId);
			
			String bearerToken = "Bearer " + token;
			String finalToken = bearerToken.replaceAll("\"","");
			LOG.info("Final Token Value is : " +finalToken );
			
			if(cartId==null || cartId.isEmpty() || cartId==""){
				LOG.info("Generating new cart");
				cartId=generateCart();
				cartId=cartId.replaceAll("\"","");
			}
			
			if(action==null || action.isEmpty() || action==""){
				response.getWriter().append("No action to perform on cart.");
			}
			else{
				if("view".equalsIgnoreCase(action)){
					LOG.info("inside view action");
					url="http://localhost:8081/magento/rest/V1/guest-carts/"+cartId;
					HttpGet httpGet = new HttpGet(url);
					
					httpGet.setHeader("Content-Type","application/json");				
					httpGet.setHeader("Authorization", finalToken);
					
					CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode()==200){
						String responseStream = EntityUtils.toString(httpResponse.getEntity());
						LOG.info("Cart Details for cart "+cartId+" : "+responseStream);
						response.getWriter().append("Cart Details for cart "+cartId+" : "+responseStream);
					}
					else{
						response.getWriter().append("Error viewing cart");
						LOG.error("Error viewing cart");
					}
						
									
				}
				else if("add/update".equalsIgnoreCase(action)){
					LOG.info("inside add/update action");
					url="http://localhost:8081/magento/rest/V1/guest-carts/"+cartId+"/items";			
					String payload = request.getParameter("payload");			
					
					if(payload==null || payload.isEmpty() || payload==""){
						response.getWriter().append("No product to add/update in the cart "+cartId);
					}
					else{
						/*Sample Payload:
						payload_add
						{ "cartItem": { "quote_id": "VbfYhqDHXyDcx1MlTUg9jJzEC3bso2ug", "sku": "24-MB01", "qty": 14} }
						
						payload_update
						{ "cartItem": { "quote_id": "rMFSYyqeTXGOqj4s7A9QRs9AVaLAMC9T", "sku": "24-MB01", "qty": 10, "item_id":5} }				
						*/
						StringEntity input = new StringEntity(payload);
						
						httpPost = new HttpPost(url);	
						httpPost.setEntity(input);
						httpPost.setHeader("Content-Type","application/json");				
						httpPost.setHeader("Authorization", finalToken);
						
						CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
						if (httpResponse.getStatusLine().getStatusCode()==200){
							String responseStream = EntityUtils.toString(httpResponse.getEntity());
							LOG.info("Added items to the cart "+cartId+" with item ID as : "+responseStream);
							response.getWriter().append("Added items to the cart "+cartId+" with item ID as : "+responseStream);
						}
						else{
							response.getWriter().append("Error adding items to cart "+cartId);
							LOG.error("Error adding items to cart "+cartId);
						}
					}
					
									
				}			
				else if("delete".equalsIgnoreCase(action)){
					LOG.info("inside delete action");
					String itemId = request.getParameter("itemId");
					//itemId=14 , sample
					if(itemId==null || itemId.isEmpty() || itemId==""){
						response.getWriter().append("No product having item ID "+itemId+ " to delete from cart "+cartId);
					}
					else{
						url="http://localhost:8081/magento/rest/V1/guest-carts/"+cartId+"/items/"+itemId;
						HttpDelete httpDelete=new HttpDelete(url);
										
						httpDelete.setHeader("Content-Type","application/json");
						httpDelete.setHeader("Authorization", finalToken);
						
						CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);
						if (httpResponse.getStatusLine().getStatusCode()==200){
							String responseStream = EntityUtils.toString(httpResponse.getEntity());
							response.getWriter().append("Item "+itemId+" has been deleted from cart "+cartId+"with response as "+responseStream);
						}
						else{
							response.getWriter().append("Error deleting items from cart");
							LOG.error("Error deleting items from cart");
						}
					}
					
				}
				else{
					response.getWriter().append("No valid action found for the cart.");
				}
			}
			
			
			
			
			
		}
	}
	
	private String generateCart(){
		
		LOG.info("Inside generate cart method");
		String url="http://localhost:8081/magento/rest/V1/guest-carts";		
		String response=null;
		
		try {			
			httpPost = new HttpPost(url);
			
			httpPost.setHeader("Content-Type","application/json");
			String bearerToken = "Bearer " + token;
			String finalToken = bearerToken.replaceAll("\"","");
			LOG.info("Final Token Value is : " +finalToken );
			httpPost.setHeader("Authorization", finalToken);
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode()==200){
				response = EntityUtils.toString(httpResponse.getEntity());
				LOG.info("New cart created with id : "+response);
			}
			else{
				LOG.error("Error in creating new cart");
			}
			
		} catch (Exception e) {
			LOG.error("Invalid response : ");
			e.printStackTrace();
		} 
		return response;
	}
}
