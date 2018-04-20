package com.oneplatform.platform.conf;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.jeesuite.common.util.ResourceUtils;
import com.oneplatform.base.GlobalContants;
import com.oneplatform.platform.shiro.CustomFormAuthenticationFilter;
import com.oneplatform.platform.shiro.CustomShiroRealm;
import com.oneplatform.platform.shiro.CustomerCredentialsMatcher;
import com.oneplatform.platform.shiro.RedisCacheManager;
import com.oneplatform.platform.shiro.RedisSessionDAO;


@Configuration
public class ShiroConfig {

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    @Bean
    @Primary
    public CustomFormAuthenticationFilter formAuthenticationFilter(){
    	return new CustomFormAuthenticationFilter();
    }
    
    @Bean
    public CookieRememberMeManager rememberMeManager(){
    	return new CookieRememberMeManager();
    }
    
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean("shiroFilter")
    @DependsOn("securityManager")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/redirctLogin");
        shiroFilterFactoryBean.setSuccessUrl("/redirctIndex");
        shiroFilterFactoryBean.setUnauthorizedUrl("/redirctNoLogin");
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //filterChainDefinitionMap.put("/logout", "logout");
        String[] anonUris = StringUtils.split(ResourceUtils.getProperty("anonymous.uris"), ";");
        for (String uri : anonUris) {
        	filterChainDefinitionMap.put(uri,"anon");
		}
        //filterChainDefinitionMap.put("/account/list", "perms[/account/list]");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean(name="securityManager")
    @DependsOn("shiroRealm")
    public SecurityManager securityManager(@Value("${session.storage.type:ehcache}")String cacheType,CustomShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
       securityManager.setCacheManager(cacheManager(cacheType));
        securityManager.setSessionManager(sessionManager(securityManager.getCacheManager(),cacheType));
        return securityManager;
    }

    @Bean("shiroRealm")
    public CustomShiroRealm myShiroRealm(){
        CustomShiroRealm myShiroRealm = new CustomShiroRealm();
        myShiroRealm.setCredentialsMatcher(new CustomerCredentialsMatcher());
        return myShiroRealm;
    }

    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);

        return hashedCredentialsMatcher;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    //@Bean
    //@ConditionalOnProperty(name = "session.storage.type", havingValue = "ehcache")
    public SessionManager sessionManager(CacheManager cacheManager,String cacheType) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheManager);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        if("ehcache".equals(cacheType)){
        	sessionManager.setSessionValidationInterval(30 * 1000);
            sessionManager.setGlobalSessionTimeout(GlobalContants.SESSION_EXPIRE_SECONDS * 1000);
            sessionManager.setDeleteInvalidSessions(true);
            sessionManager.setSessionValidationSchedulerEnabled(true);
        }else{
        	sessionManager.setSessionDAO(new RedisSessionDAO());
        }
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }


    //@Bean
    //@ConditionalOnProperty(name = "session.storage.type", havingValue = "ehcache")
    public CacheManager cacheManager(String cacheType) {
    	if("ehcache".equals(cacheType)){
    		EhCacheManager ehCacheManager = new EhCacheManager();
            ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
            return ehCacheManager;
    	}
        
    	RedisCacheManager redisCacheManager = new RedisCacheManager();
        return redisCacheManager;
    }

}
