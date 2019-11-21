package com.hcl.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.Gson;

public class Accounts extends WCMUsePojo {

	private List<AccountsModel> modelData;
	
	private String[] accounts;
	
	private static Logger log = LoggerFactory.getLogger(Accounts.class);
	@Override
	public void activate() throws Exception {
		log.info("inside activate method");
		accounts=  getProperties().get("accounts", String[].class);
		
		if(accounts!=null){
			log.info("value of accounts fron jcr is not null. value is: " + accounts);
			modelData = values();
		}
	}
	
	private List<AccountsModel> values(){
		log.info("inside values method");
		ArrayList<AccountsModel> accountList = new ArrayList<AccountsModel>();
		Gson gson = new Gson();
		log.info("outside for each loop, accounts size : " + accounts.length);
		for(String account : accounts){
			log.info("inside for each loop,accounts size : " + accounts.length);
			AccountsModel accountmodel = gson.fromJson(account, AccountsModel.class);
			log.info("accountmodel : "+accountmodel);
			accountList.add(accountmodel);
		}
		
		return accountList;
	}

	public List<AccountsModel> getModelData() {
		return modelData;
	}
	

	

}
