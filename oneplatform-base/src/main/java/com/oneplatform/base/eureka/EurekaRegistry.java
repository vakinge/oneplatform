package com.oneplatform.base.eureka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.common.util.NodeNameHolder;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.springweb.utils.IpUtils;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

public class EurekaRegistry {
	
	private static final int VERIFY_WAIT_MILLIS = 65*1000;

	private static Logger log = LoggerFactory.getLogger("com.aygframework.support");
	
	private static EurekaRegistry instance = new EurekaRegistry();
	
	private ApplicationInfoManager applicationInfoManager;
    private EurekaClient eurekaClient;
    private String vipAddress;
    private String instanceId;
	
	private EurekaRegistry() {}

	public static EurekaRegistry getInstance() {
		return instance;
	}

	public void start(Properties properties) {
		
		vipAddress = properties.getProperty("eureka.vipAddress");
		if(StringUtils.isBlank(vipAddress)){
			log.warn("config[eureka.vipAddress] not found ");
			return;
		}
		
		if(!properties.containsKey("eureka.port")){
			log.warn("config[eureka.port] not found ");
			return;
		}
		
		log.info("Service Register to Eureka begin,\n -ipAddr:{} \n-properties:{}",IpUtils.getLocalIpAddr(),properties);
		
		try {			
			initEurekaClient(properties);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("init eurekaClient error["+e.getMessage()+"]");
		}
    	
       log.info("Registering service to eureka with STARTING status");
       applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

       log.info("Simulating service initialization by sleeping for 2 seconds...");
       try {Thread.sleep(2000);} catch (InterruptedException e) {}

       log.info("Done sleeping, now changing status to UP");
       applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
       waitForRegistrationWithEureka();
       log.info("Service Register to Eureka finished,instanceId:{}",instanceId);
       
//       try {
//           int myServingPort = applicationInfoManager.getInfo().getPort();  // read from my registered info
//           ServerSocket serverSocket = new ServerSocket(myServingPort);
//           final Socket s = serverSocket.accept();
//           log.info("Client got connected... processing request from the client");
//           processRequest(s);
//
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//
//       log.info("Simulating service doing work by sleeping for " + 5 + " seconds...");
//       try {
//           Thread.sleep(5 * 1000);
//       } catch (InterruptedException e) {
//           // Nothing
//       }
   }

	public void shutdown() {
        if (eurekaClient != null) {
            log.info("Shutting down server. Demo over.");
            eurekaClient.shutdown();
        }
    }
	
	
	private void initEurekaClient(Properties properties) throws Exception{
		
		properties.setProperty("eureka.metadataMap.nodeId", NodeNameHolder.getNodeId());
		ConfigurationManager.loadProperties(properties);
		//ConfigurationManager.loadPropertiesFromResources("eureka.properties");
		
		//DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
		
		MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig(){
			@Override
			public String getHostName(boolean refresh) {
				String hostName = super.getHostName(refresh);
				if(ResourceUtils.getBoolean("eureka.preferIpAddress")){
					hostName = IpUtils.getLocalIpAddr();
				}
				return hostName;
			}

			@Override
			public String getIpAddress() {
				return IpUtils.getLocalIpAddr();
			}
			
			
		};
		InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        
        DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig();

        eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        
        instanceId = instanceInfo.getInstanceId();
	}
	
	public String getRealServerHost(String serviceId){
		InstanceInfo serverInfo = eurekaClient.getNextServerFromEureka(serviceId, false);
		String realServerName = serverInfo.getIPAddr() + ":" + serverInfo.getPort();
		return realServerName;
	}
	
	private void waitForRegistrationWithEureka() {
        long startTime = System.currentTimeMillis();
        new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
		        	if(System.currentTimeMillis() - startTime > VERIFY_WAIT_MILLIS){
		        		log.warn(" >>>> service registration status not verify,please check it!!!!");
		        		return;
		        	}
		            try {
		            	List<InstanceInfo> serverInfos = eurekaClient.getInstancesByVipAddress(vipAddress, false);
		            	for (InstanceInfo nextServerInfo : serverInfos) {
		            		if(nextServerInfo.getIPAddr().equals(IpUtils.LOCAL_BACK_IP) || 
		                    		nextServerInfo.getIPAddr().equals(IpUtils.getLocalIpAddr())){
		            			String instanceInfoJson = JsonUtils.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(nextServerInfo);
								log.info("verifying service registration with eureka finished,instance:\n{}",instanceInfoJson);
		            			return;
		            		}
						}
		            } catch (Throwable e) {}
		            try {Thread.sleep(5000);} catch (Exception e1) {}
		        	log.info("Waiting 5s... verifying service registration with eureka ...");
		        }
			}
		}).start();
    }

    private void processRequest(final Socket s) {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = rd.readLine();
            if (line != null) {
                log.info("Received a request from the example client: " + line);
            }
            String response = "BAR " + new Date();
            log.info("Sending the response to the client: " + response);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(response);

        } catch (Throwable e) {
            System.err.println("Error processing requests");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
