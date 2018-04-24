/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.base.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jeesuite.spring.InstanceFactory;
import com.jeesuite.springweb.client.SimpleRestTemplateBuilder;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月29日
 */
public class RestClientFactoty {

	private static final String DEFALUT = "restTemplate";
	private static final String EUREKA = "eurekaRestTemplate";
	private static Map<String, RestClientFactoty> clients = new HashMap<>();
	
	private RestTemplate restTemplate;
	
	private RestClientFactoty(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public static RestClientFactoty use(){
		return use(DEFALUT);
	}
	
	public static RestClientFactoty useEureka(){
		return use(EUREKA);
	}
	
	public static RestClientFactoty use(String name){
		if(clients.isEmpty()){
			initClients();
		}
		return clients.get(name);
	}
	
	private static synchronized void initClients(){
		if(!clients.isEmpty())return;
		try {
			Map<String, RestTemplate> interfaces = InstanceFactory.getInstanceProvider().getInterfaces(RestTemplate.class);
			for (String key : interfaces.keySet()) {
				clients.put(key, new RestClientFactoty(interfaces.get(key)));
			}
		} catch (Exception e) {}
		if(!clients.containsKey(DEFALUT)){
			clients.put(DEFALUT, new RestClientFactoty(new SimpleRestTemplateBuilder().build()));
		}
		
	}

	/**
	 * 
	 * @param template
	 * @param url
	 * @param param
	 * @param responseType
	 * @return
	 */
	public <T> T postJSON(String url,Object param, Class<T> responseType){
		return doRequest(url, HttpMethod.POST, MediaType.APPLICATION_JSON, param, responseType);
	}
	
	public <T> T postJSON(String url,Object param, ParameterizedTypeReference<T> responseType){
		return doRequest(url, HttpMethod.POST, MediaType.APPLICATION_JSON, param, responseType);
	}
	
	public <T> T getJSON(String url,Object param, Class<T> responseType){
		return doRequest(url, HttpMethod.GET, MediaType.APPLICATION_JSON, param, responseType);
	}
	
	public <T> T getJSON(String url,Object param, ParameterizedTypeReference<T> responseType){
		return doRequest(url, HttpMethod.GET, MediaType.APPLICATION_JSON, param, responseType);
	}
	
	private <T> T doRequest(String url,HttpMethod method,MediaType media,Object param, Class<T> responseType){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(media);
		HttpEntity<?> httpEntity = new HttpEntity<>(param, headers);
		ParameterizedTypeReference<T> wrapperResponseType = new ParameterizedTypeReference<T>() {};
		ResponseEntity<T> responseEntity = restTemplate.exchange(url,method, httpEntity, wrapperResponseType);
		return responseEntity.getBody();
	}
	
	private <T> T doRequest(String url,HttpMethod method,MediaType media,Object param, ParameterizedTypeReference<T> responseType){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(media);
		HttpEntity<?> httpEntity = new HttpEntity<>(param, headers);
		ResponseEntity<T> responseEntity = restTemplate.exchange(url,method, httpEntity, responseType);
		return responseEntity.getBody();
	}

}
