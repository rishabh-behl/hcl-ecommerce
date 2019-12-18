package com.hcl.ecomm.core.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hcl.ecomm.core.services.ProductService;
import com.hcl.ecomm.core.services.impl.ProductServiceImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class ProductContainerModel {
    private int totalProducts = 6;
    private int itemsPerSlide = 2;
    private static final Logger LOG = LoggerFactory.getLogger(ProductContainerModel.class);

    @Inject @Optional
    private JsonArray productJson;

    @Inject @Optional
    private int index;


    @OSGiService
    private ProductService productService;
    @PostConstruct
    protected void init() {
    }
    private JsonArray getJson() {
        JsonArray o = new JsonArray();
        try {
            String responseStream = "[{\n" +
                    "\t\t\"name\": 1,\n" +
                    "\t\t\"price\": \"24-MB01\"\n" +
                    "\t},\n" +
                    "\t{\n" +
                    "\t\t\"name\": 1,\n" +
                    "\t\t\"price\": \"24-MB02\"\n" +
                    "\t},\n" +
                    "\t{\n" +
                    "\t\t\"name\": 1,\n" +
                    "\t\t\"price\": \"24-MB03\"\n" +
                    "\t},\n" +
                    "\t{\n" +
                    "\t\t\"name\": 1,\n" +
                    "\t\t\"price\": \"24-MB04\"\n" +
                    "\t},\n" +
                    "\t{\n" +
                    "\t\t\"name\": 1,\n" +
                    "\t\t\"price\": \"24-MB05\"\n" +
                    "\t},\n" +
                    "\t{\n" +
                    "\t\t\"name\": 1,\n" +
                    "\t\t\"price\": \"24-MB06\"\n" +
                    "\t}\n" +
                    "]";
            JsonParser parser = new JsonParser();
            o   = parser.parse(responseStream).getAsJsonArray();
        }
        catch (Exception e){

        }
     return o;
    }

    public JsonArray getJsonArray() {
        return getJson();
    }

    public List<HashMap<String, String>> getProductListAccordingToIndex() {


        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            int elements [] = {(index*2)-1,index*2};
            for(int i : elements){
                JsonObject obj = productJson.get(i-1).getAsJsonObject();
                HashMap<String, String> map = new HashMap<>();
                map.put("name", obj.get("name").getAsString());
                map.put("price", obj.get("price").getAsString());
                list.add(map);
            }

            return list;
        }
        catch (Exception e){

        }
        return list;
    }
      /* private List<HashMap<String, String>> convertJsonToList(JsonArray json) {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            for (int i = 0; i < json.size(); i++) {
                JsonObject obj = json.get(i).getAsJsonObject();
                HashMap<String, String> map = new HashMap<>();
                map.put("name", obj.get("name").getAsString());
                map.put("price", obj.get("price").getAsString());
                list.add(map);
            }
            return list;
        }
         catch (Exception e){

       }
     return list;
    }
*/
 /*   public List<HashMap<String, String>> getProductList() {

        JsonArray array = getJson();
        return convertJsonToList(array);
    }*/
}