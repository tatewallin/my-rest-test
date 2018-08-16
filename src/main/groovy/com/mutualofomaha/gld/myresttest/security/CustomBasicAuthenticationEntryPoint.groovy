package com.mutualofomaha.gld.myresttest.security

import com.mutualofomaha.gld.myresttest.util.R

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;


class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "")

        PrintWriter writer = response.getWriter()
        writer.println("HTTP Status 401 : " + authException.getMessage())
    }

    @Override
    void afterPropertiesSet() throws Exception {
        setRealmName(R.Security.REALM)
        super.afterPropertiesSet()
    }
}