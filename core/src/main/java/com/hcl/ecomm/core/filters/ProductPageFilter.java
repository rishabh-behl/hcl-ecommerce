/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.hcl.ecomm.core.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.hcl.ecomm.core.services.ProductService;

/**
 * Simple servlet filter component that logs incoming requests.
 */
@Component(service = Filter.class,
           property = {
                   EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
                 
           })
@ServiceDescription("Demo to filter incoming requests")
@ServiceRanking(1)
@ServiceVendor("Adobe")
public class ProductPageFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    ProductService productService;
    
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
        final SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) response;
        logger.debug("request for {}, with selector {}", slingRequest
                .getRequestPathInfo().getResourcePath(), slingRequest
                .getRequestPathInfo().getSelectorString());
        String path = slingRequest.getResource().getPath();
        
        if("displayProduct".equalsIgnoreCase(request.getParameter("action")) && request.getParameter("pid")!= null){
        	//String productDetails = productService.getProductDetails(slingRequest, slingResponse);
        	JsonArray jsonArray = new JsonArray();
        	 String responseStream = "[{\n" +
                     "\t\t\"name\": 1,\n" +
                     "\t\t\"price\": \"24-MB01\"\n" +
                     "\t} ]";
             JsonParser parser = new JsonParser();
             jsonArray   = parser.parse(responseStream).getAsJsonArray();
        	request.getRequestDispatcher("/content/hclecomm/us/en/product.html").forward(slingRequest, slingResponse);
        	
        	/*request.getRequestDispatcher("/content/hclecomm/us/en.html").forward(slingRequest, slingResponset);*/
        	//return;
        	//request.setAttribute("productJons", arg1);
        	
        	//slingResponset.sendRedirect("/content/hclecomm/us/en.html");
           
             // Stop processing the request chain
             return;
        }
       // List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        

       filterChain.doFilter(request, response);
    }

    public HashMap<String, JsonArray> getProductMapFromJson(JsonArray jsonArray)
    {
    	HashMap<String, JsonArray> map = new HashMap<>();
    	
    	return map;
    }  
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}