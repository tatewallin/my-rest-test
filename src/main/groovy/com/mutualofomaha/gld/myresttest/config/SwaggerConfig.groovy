package com.mutualofomaha.gld.myresttest.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import static springfox.documentation.builders.PathSelectors.regex

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value('${swagger.api-info.title}')
    private String apiTitle

    @Value('${swagger.api-info.desc}')
    private String apiDesc

    @Value('${spring.application.version}')
    private String apiVersion

    @Value('${swagger.api-info.creator}')
    private String apiCreator

    @Value('${swagger.api-info.url-desc}')
    private String apiDocumenationTitle

    @Value('${swagger.api-info.url}')
    private String apiDocumentationUrl

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(regex('/v.*/.*/.*'))
                .build()
                .apiInfo( new ApiInfo(
                        apiTitle,               // Title displayed at top of page
                        apiDesc,                // Description of api under title
                        apiVersion,             // Version number found at bottom of page
                        null,                   // Not used
                        apiCreator,             // Area responsible for API
                        apiDocumenationTitle,   // Hyperlink text to display
                        apiDocumentationUrl)   // Hyperlink to further documentation of API
                )
    }
}
