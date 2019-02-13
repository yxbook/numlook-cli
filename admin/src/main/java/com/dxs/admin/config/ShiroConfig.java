package com.dxs.admin.config;


import com.dxs.admin.shiro.AdminRealm;
import com.dxs.admin.shiro.ShiroAuthenticationFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Mr.Dxs on 2018/6/4.
 * Shiro 配置类
 */
@Configuration
public class ShiroConfig {


    /**
     * 安全管理器：SecurityManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置 自定义 realm
        securityManager.setRealm(managerRealm());
        return securityManager;
    }


    /**
     * 注入 自定义过滤器
     * @return
     */
    @Bean
    public ShiroAuthenticationFilter shiroAuthenticationFilter(){
        return new ShiroAuthenticationFilter();
    }


    /**
     * 注入 自定义 Realm
     * @return
     */
    @Bean
    public AdminRealm managerRealm(){
        AdminRealm adminRealm= new AdminRealm();
        // 告诉realm,使用credentialsMatcher加密算法类来验证密文
        adminRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return adminRealm;
    }


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        System.out.println("ShiroConfig.shiroFilterFactoryBean()———————— >路过");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加自己的过滤器并且取名为: saf
        Map filerMap = new HashMap();
        filerMap.put("saf",new ShiroAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filerMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 因为 管理员登录模块 和 用户管理模块 有命名冲突，规范 管理员登录 即：sys
        filterChainDefinitionMap.put("/sys/admin/login", "anon");
        filterChainDefinitionMap.put("/sys/admin/save", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

//        filterChainDefinitionMap.put("/sys/logout", "logout");

        // 自定义过滤器 对所有请求都进行处理
        filterChainDefinitionMap.put("/**","saf");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;

    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行 处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;）
     *  可以扩展凭证匹配器，实现 输入密码错误次数后锁定等功能，下一次
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法 这里使用 MD5算法
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-512");
        // 散列次数   散列两次 即：md5（md5（""））
        hashedCredentialsMatcher.setHashIterations(2);
        // storedCredentialsHexEncoded 默认是true，此时 密码加密用的是 Hex 编码，false 是用 Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * Shiro 生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 开启 Shiro 的注解 （如： @RequiresRoles、@RequiresPermissions）,需要借助 SpringAOP 扫描使用 Shiro 注解的类，并在必要时进行安全逻辑验证
     * 配置一下两个 Bean （DefaultAdvisorAutoProxyCreator（可选） 和 AuthorizationAttributeSourceAdvisor ）即可实现此功能
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}
