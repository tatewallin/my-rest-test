package com.mutualofomaha.gld.myresttest.security

import com.mutualofomaha.gld.myresttest.util.R
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

//  https://www.baeldung.com/spring-security-expressions
//  https://www.baeldung.com/spring-security-expressions-basic
//  http://websystique.com/spring-security/secure-spring-rest-api-using-basic-authentication/
@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN")
        auth.inMemoryAuthentication().withUser("tom").password("abc123").roles("USER")
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //.antMatchers("/MyRestTest/v1.0/").hasRole("ADMIN")
                //.antMatchers("/MyRestTest/v1.0/testUSER").hasRole("USER")
                .antMatchers("/MyRestTest/v1.0/").permitAll()
                .antMatchers("/MyRestTest/v1.0/testUSER").hasAnyRole("USER", "ADMIN")
                .antMatchers("/MyRestTest/v1.0/testADMIN").hasRole("ADMIN")
                .and().httpBasic().realmName(R.Security.REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint()
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
    }
}