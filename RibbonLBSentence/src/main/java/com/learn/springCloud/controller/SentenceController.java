package com.learn.springCloud.controller;

import java.net.URI;
import java.util.List;

import org.assertj.core.api.UriAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author rajes
 * date    Apr 7, 20171:27:33 AM
 *
 */
@RestController
public class SentenceController {
	private static final Logger log = LoggerFactory.getLogger(SentenceController.class);
	

	
	
	//private DiscoveryClient client;
	@Autowired
	private LoadBalancerClient lbClient;

	 @RequestMapping("/sentence")
	  public @ResponseBody String getSentence() {
		return getWordsFromAllClients("eurekaClient1Subject") + " " +getWordsFromAllClients("eurekaClient2Verb") 
		+" "+  getWordsFromAllClients("eurekaClient3Adjective");
		
	    
	  }
	 
	 //Ribbon gets the info about the clients deom the eureka
	 public String getWordsFromAllClients(String appName)
	 {
		 ServiceInstance app=lbClient.choose(appName);
		 System.out.println(app);
	    
	    	 
	    		 RestTemplate rest=new RestTemplate();
	    		return rest.getForObject(app.getUri(),String.class);
	     
		
	 }
}
