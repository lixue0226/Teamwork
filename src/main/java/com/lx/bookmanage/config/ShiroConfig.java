package com.lx.bookmanage.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lx.bookmanage.auth.BookShiroRealm;
import com.lx.bookmanage.model.Book;


@Configuration
public class ShiroConfig {
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/manage/login");
		shiroFilterFactoryBean.setSuccessUrl("/manage/dashboard");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 静态资源
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/libs/**", "anon");
        filterChainDefinitionMap.put("/res/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        
        filterChainDefinitionMap.put("/api/login", "anon");
        filterChainDefinitionMap.put("/api/captcha.jpg", "anon");
        filterChainDefinitionMap.put("/upload/**", "anon");
        
        filterChainDefinitionMap.put("/notice/**", "anon");

        filterChainDefinitionMap.put("/manage/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
		return shiroFilterFactoryBean;
	}
	
	@Bean
	public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(bookShiroRealm());
        return securityManager;
    }
	
	@Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于md5(md5(""));
        
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        return hashedCredentialsMatcher;
    }
	
	@Bean
    public BookShiroRealm bookShiroRealm() {
BookShiroRealm bookShiroRealm = new BookShiroRealm();
        bookShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return bookShiroRealm;
    }

}
