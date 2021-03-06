package com.mutualofomaha.gld.myresttest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.support.SpringBootServletInitializer
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ServletComponentScan
//@EnableJpaRepositories("com.mutualofomaha.gld.myresttest.dao.repo")
//@EntityScan("com.mutualofomaha.gld.myresttest.dao.entity")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args) // NOSONAR
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class)
    }
}

