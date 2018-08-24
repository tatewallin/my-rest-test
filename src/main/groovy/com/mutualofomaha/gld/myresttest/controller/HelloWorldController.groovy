package com.mutualofomaha.gld.myresttest.controller

import com.mutualofomaha.gld.myresttest.HelloWorldService
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

// This annotation was causing an issue with endpoints and JPA
// This controllers endpoints would work with out JPA enabled
//@MonitoredWithSpring
@RestController
@RequestMapping('/${spring.application.name}/v1.0')
@Api(value = 'MyRestTest', description = 'Endpoint for Hello World Resources')
@Slf4j
class HelloWorldController {

    // Overkill, but an example of injecting a value from properties to a usable value
    @Value('${spring.profiles.active}')
    private final String environment

    @Autowired
    HelloWorldService helloWorldService

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="Returns a Hello message to confirm things are working")
    String helloTest(HttpServletRequest request) {
        return "Hello.  Things are working on the ${environment} Spring side.  Thanks for stopping by!!!"
    }

    @RequestMapping(value = "/testUSER", method = RequestMethod.GET)
    @ApiOperation(value="Used to test SecurityConfiguration")
    String helloTest3(HttpServletRequest request) {
        return "ADMIN and USER role users should have access.  Results of a service - ${helloWorldService.doSomeCoolBusinessShit()} - ${helloWorldService.doSomeMoreCoolBusinessShit()}"
    }

    @RequestMapping(value = "/testADMIN", method = RequestMethod.GET)
    @ApiOperation(value="Used to test SecurityConfiguration")
    String helloTest4(HttpServletRequest request) {
        return "Only users with the ADMIN roll should have access to this endpoint.  Results of a service - ${helloWorldService.doSomeCoolBusinessShit()}"
    }
}