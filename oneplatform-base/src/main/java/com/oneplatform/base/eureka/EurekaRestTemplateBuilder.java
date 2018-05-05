package com.oneplatform.base.eureka;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.jeesuite.springweb.client.CustomResponseErrorHandler;
import com.jeesuite.springweb.client.RestTemplateAutoHeaderInterceptor;
import com.jeesuite.springweb.interceptor.LoggingRequestInterceptor;

public class EurekaRestTemplateBuilder {
	
	private static Map<String, RestTemplate> restTemplates = new HashMap<>();
	
	public static RestTemplate build(ClientHttpRequestInterceptor ...interceptors ){
		return build("default", interceptors);
	}
	
	public static RestTemplate build(String name,ClientHttpRequestInterceptor ...interceptors ){
		return build(name, 15000, interceptors);
	}
	
	
	public static synchronized RestTemplate build(String name,int readTimeout,ClientHttpRequestInterceptor ...interceptors ){
		
		if(restTemplates.containsKey(name))return restTemplates.get(name);
		
		SimpleClientHttpRequestFactory factory = new EurekaClientHttpRequestFactory();  
        factory.setReadTimeout(readTimeout);//ms  
        factory.setConnectTimeout(5000);//ms 
        
        RestTemplate restTemplate = new RestTemplate(factory);
        List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new RestTemplateAutoHeaderInterceptor());
        interceptorList.add(new LoggingRequestInterceptor());
        if(interceptors != null && interceptors.length > 0){
        	for (ClientHttpRequestInterceptor interceptor : interceptors) {
        		interceptorList.add(interceptor);
			}
        }
        restTemplate.setInterceptors(interceptorList);
        //
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        //
        restTemplates.put(name, restTemplate);
        
		return restTemplate;
	}
	
	private static class EurekaClientHttpRequestFactory extends SimpleClientHttpRequestFactory{

		@Override
		public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
			uri = convertToRealUri(uri);
			return super.createRequest(uri, httpMethod);
		}

		@Override
		public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
			uri = convertToRealUri(uri);
			return super.createAsyncRequest(uri, httpMethod);
		}
		
		private URI convertToRealUri(URI uri){
			String serviceId = uri.getHost();
			try {				
				String realHost = EurekaRegistry.getInstance().getRealServerHost(serviceId);
				uri = new URI(uri.toString().replace(serviceId, realHost));
				return uri;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}
}
