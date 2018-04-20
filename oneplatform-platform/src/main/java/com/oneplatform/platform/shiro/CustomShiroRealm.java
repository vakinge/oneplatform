package com.oneplatform.platform.shiro;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.context.ApplicationContext;

import com.jeesuite.spring.ApplicationStartedListener;
import com.jeesuite.spring.InstanceFactory;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dao.entity.ResourceEntity;
import com.oneplatform.system.dao.entity.RoleEntity;
import com.oneplatform.system.service.AccountService;
import com.oneplatform.system.service.ResourcesService;

public class CustomShiroRealm extends AuthorizingRealm implements ApplicationStartedListener {

    private AccountService accountService;
    private ResourcesService resourcesService;
    private RedisSessionDAO redisSessionDAO;
    

	//授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    	LoginUserInfo user= (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
       
        List<RoleEntity> roles = resourcesService.findRoleWithResourceByUserId(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        List<ResourceEntity> resources;
        for (RoleEntity role : roles) {
        	info.addRole(String.valueOf(role.getId()));
        	resources = role.getResources();
        	if(resources != null){
        		for (ResourceEntity resource : resources) {
        			info.addStringPermission(resource.getCode());
				}
        	}
		}
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        AccountEntity user = accountService.findByLoginAccount(username);
        if(user==null) throw new UnknownAccountException();
        if (!user.getEnabled()) {
            throw new LockedAccountException(); // 帐号锁定
        }
        
        LoginUserInfo loginUserInfo = new LoginUserInfo(user.getId(), user.getUsername());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		loginUserInfo, //用户
                user.getPassword(), //密码
                Passwordhelper.getPasswordSalt(user.getCreatedAt()),
                getName()  //realm name
        );
        
        
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("loginUserInfo", loginUserInfo);
        return authenticationInfo;
    }


    public void clearActiveUserAuthorizationInfo(){
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session:sessions){
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null != obj && obj instanceof SimplePrincipalCollection){
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                obj = spc.getPrimaryPrincipal();
                if(null != obj && obj instanceof LoginUserInfo){
                	list.add(spc);
                }
            }
        }
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        CustomShiroRealm realm = (CustomShiroRealm)securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }

	@Override
	public void onApplicationStarted(ApplicationContext applicationContext) {
		accountService = InstanceFactory.getInstance(AccountService.class);
		resourcesService = InstanceFactory.getInstance(ResourcesService.class);
		redisSessionDAO = InstanceFactory.getInstance(RedisSessionDAO.class);
	}
}
