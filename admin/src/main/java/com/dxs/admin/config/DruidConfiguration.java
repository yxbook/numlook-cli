package com.dxs.admin.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Mr.Dxs on 2018/7/13.
 * 整合 Druid 实现 数据库监控
 */
@Configuration
public class DruidConfiguration {

    /**
     * 配置监控服务器
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){

        // 第二个参数 配置的就是监控界面的请求路径
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        /** 初始化参数配置，initParams**/
        // 添加 IP 白名单
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");

        // 添加 IP 黑名单，当白名单和黑名单重复时  黑名单优先级高
        servletRegistrationBean.addInitParameter("deny","192.168.1.111");

        // 添加控制台 管理用户
        servletRegistrationBean.addInitParameter("loginUsername","druid");
        // 添加控制台用户 密码
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");

        return servletRegistrationBean;

    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return filterRegistrationBean;
    }



}
