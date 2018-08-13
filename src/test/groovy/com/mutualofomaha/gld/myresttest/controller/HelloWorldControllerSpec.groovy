package com.mutualofomaha.gld.myresttest.controller

import com.mutualofomaha.gld.myresttest.Application

import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title

//@ActiveProfiles("Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Title("Test controller for response")
class HelloWorldControllerSpec extends Specification {

    @Value('${local.server.port}')  // use this to avoid Address Already In Use errors
    int PORT

    @Value('${security.user.name}')  // get user name for basic auth
    String USER

    @Value('${security.user.password}') // get password for basic auth
    String PASSWORD

    @Shared  // Share rest template
    RestTemplate restTemplate

    int SYNC_TIMEOUT = 15000  // set time out in milliseconds

    // Setup for RestTemplate for functional test
    def setup() {

        // Set time out in milliseconds for test calls
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SYNC_TIMEOUT)
                .setConnectTimeout(SYNC_TIMEOUT)
                .setConnectionRequestTimeout(SYNC_TIMEOUT)
                .build();

        // Set credentials for test
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(AuthScope.ANY_HOST,
                AuthScope.ANY_PORT,
                AuthScope.ANY_REALM);
        UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(USER, PASSWORD);
        credentialsProvider.setCredentials(authScope,
                usernamePasswordCredentials);

        // Build Http Client that the rest template will be based on
        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build()
        // Return test template
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
    }

    // Execute test against endpoint
    def "Test calling the example endpoint"() {
        given: "new http headers for the /Example end point"
        HttpHeaders httpHeaders = new HttpHeaders()
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON.toString())
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString())
        httpHeaders.add("correlationId",UUID.randomUUID().toString())
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders)

        String url = "http://localhost:${PORT}/MyRestTest/v1.0/"

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(url)

        when: "the endpoint is called"
        println "calling $url"
        String response = restTemplate.getForObject(uriBuilder.build().encode().toUri(), String.class)

        then: "the default response is returned from the hello world controller"
        response == 'Greetings from Spring Boot!'
    }
}