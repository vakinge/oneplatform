package com.oneplatform.base.spring;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jeesuite.common.util.NodeNameHolder;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.spring.ApplicationStartedListener;
import com.jeesuite.spring.InstanceFactory;
import com.jeesuite.spring.SpringInstanceProvider;
import com.jeesuite.springweb.utils.IpUtils;
import com.oneplatform.base.eureka.EurekaRegistry;



public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {


	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		try {
			String bathPath = System.getProperty("catalina.home");
			if(StringUtils.isBlank(bathPath)){
				String realPath = event.getServletContext().getRealPath("/");
				if(realPath.contains("/webapps/")){
					bathPath = StringUtils.splitByWholeSeparator(realPath, "/webapps/")[0];
				}else if(realPath.contains("/src/")){
					bathPath = StringUtils.splitByWholeSeparator(realPath, "/src/main/")[0];
				}else{
					///Users/jiangwei/project/ayg/ayg-newpayment/WebRoot
					bathPath = realPath.substring(0,realPath.lastIndexOf("/"));
				}
				
				if(StringUtils.isNotBlank(bathPath)){					
					System.setProperty("catalina.home", bathPath);
				}
			}
		} catch (Exception e) {}
		
		//
		try {
			System.setProperty("spring.cloud.client.hostname", InetAddress.getLocalHost().getHostName());
		} catch (Exception e) {}
		System.setProperty("spring.cloud.client.ip-address", IpUtils.getLocalIpAddr());
		System.setProperty("client.nodeId", NodeNameHolder.getNodeId());
		
		super.contextInitialized(event);
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		SpringInstanceProvider provider = new SpringInstanceProvider(applicationContext);
		InstanceFactory.setInstanceProvider(provider);
		
		Map<String, ApplicationStartedListener> interfaces = applicationContext.getBeansOfType(ApplicationStartedListener.class);
		if(interfaces != null){
			for (ApplicationStartedListener listener : interfaces.values()) {
				System.out.println(">>>begin to execute listener:"+listener.getClass().getName());
				listener.onApplicationStarted(applicationContext);
				System.out.println("<<<<finish execute listener:"+listener.getClass().getName());
			}
		}
		
		if(ResourceUtils.getBoolean("eureka.registration.enabled")){	
			Properties properties = ResourceUtils.getAllProperties("eureka");
			if(!StringUtils.isNumeric(properties.getProperty("eureka.port"))){
				int serverPort = getServerPort();
				if(serverPort > 0){
					properties.setProperty("eureka.port", String.valueOf(serverPort));
				}else{
					properties.remove("eureka.port");
				}
			}
			EurekaRegistry.getInstance().start(properties);
		}
		
	}
	
	
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		EurekaRegistry.getInstance().shutdown();
	}
	
    private static  int getServerPort(){
        int port = 0;
        try {
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                    Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

            port = Integer.valueOf(objectNames.iterator().next().getKeyProperty("port"));
        }catch (Exception e){
        	if(StringUtils.isNotBlank(System.getProperty("jetty.port"))){
        		port = Integer.parseInt(System.getProperty("jetty.port"));
        	}
        }
        return port;
    }
}
