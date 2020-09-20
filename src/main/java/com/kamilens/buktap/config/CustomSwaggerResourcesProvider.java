package com.kamilens.buktap.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class CustomSwaggerResourcesProvider implements SwaggerResourcesProvider {

    private final String defaultResourceName = "REST API";
    private final String defaultResourceUrl = "/v2/api-docs";

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        resources.add(swaggerResource(defaultResourceName, defaultResourceUrl));
        // resources.add(swaggerResource("TEST 123", route.getFullPath().replace("**", "v2/api-docs")));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}