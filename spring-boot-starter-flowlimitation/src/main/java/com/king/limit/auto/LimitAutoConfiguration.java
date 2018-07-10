package com.king.limit.auto;

import com.king.limit.filter.LimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;

@Configuration
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties(LimitProperties.class)
public class LimitAutoConfiguration {

    @Autowired
    private LimitProperties limitProperties;

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(limitFilter());
        String url = limitProperties.getUrl();
        if (!StringUtils.isEmpty(url)) {
            String[] param = url.split(",");
            registrationBean.addUrlPatterns(param);
        }
        registrationBean.setName("limitFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    @Bean
    public Filter limitFilter() {
        return new LimitFilter();
    }
}
