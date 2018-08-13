package com.mutualofomaha.gld.myresttest.pss.config;


import net.bull.javamelody.MonitoringFilter
import net.bull.javamelody.Parameter
import net.bull.javamelody.SessionListener
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource

import javax.servlet.ServletContext
import javax.servlet.ServletException

/**
 * Created by req77961 on 02/28/2016.
 */
@Configuration
@ImportResource("classpath:net/bull/javamelody/monitoring-spring.xml")
class MelodyConfig implements ServletContextInitializer {

    @Bean
    public FilterRegistrationBean javaMelody() {

        FilterRegistrationBean javaMelody = new FilterRegistrationBean();
        javaMelody.setFilter(new MonitoringFilter());
        javaMelody.setName("com.mutualofomaha.config.javamelody");
        javaMelody.addInitParameter(Parameter.LOG.getCode(), Boolean.toString(true));
        javaMelody.addInitParameter(Parameter.MONITORING_PATH.getCode(), "/admin/monitoring");
        javaMelody.addUrlPatterns("/*");
        javaMelody.addInitParameter(Parameter.URL_EXCLUDE_PATTERN.getCode(), "/swagger/*");

        return javaMelody;
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        servletContext.addListener(new SessionListener());
    }
}
