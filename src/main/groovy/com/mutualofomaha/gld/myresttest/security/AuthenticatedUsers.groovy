package com.mutualofomaha.gld.myresttest.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("rest.security")
class AuthenticatedUsers {
    List<AuthenticatedUser> users
}
