package com.mutualofomaha.gld.myresttest.security

import com.mutualofomaha.gld.myresttest.util.EncryptionDecryptionUtil
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

//https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/
// https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/
//  https://www.baeldung.com/spring-security-expressions
//  https://www.baeldung.com/spring-security-expressions-basic
//  http://websystique.com/spring-security/secure-spring-rest-api-using-basic-authentication/
@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    void configureGlobalSecurity(AuthenticationManagerBuilder auth, EncryptionDecryptionUtil cryptUtil, AuthenticatedUsers authenticatedUsers) throws Exception {

        authenticatedUsers.users.each { user ->
            auth.inMemoryAuthentication().withUser(user.userId).password(cryptUtil.decrypt(user.password, R.Security.CRYPT_KEY)).roles(user.roles)
            auth.inMemoryAuthentication().withUser("bob").password("abc123").roles("ADMIN")
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/MyRestTest/v1.0/").permitAll() //access to root, but still needs to be an authenticated user
                .antMatchers("/MyRestTest/v1.0/testUSER").hasAnyRole("USER", "ADMIN")
                .antMatchers("/MyRestTest/v1.0/testADMIN").hasRole("ADMIN")
                .and().httpBasic().realmName(R.Security.REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    // In case the Authentication fails [invalid/missing credentials], this entry point will get triggered. It is very important, because we don’t want [Spring Security default behavior] of redirecting to a login page on authentication failure [ We don't have a login page].
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