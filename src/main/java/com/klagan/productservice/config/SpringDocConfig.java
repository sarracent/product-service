package com.klagan.productservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = "${springdoc.config}", ignoreResourceNotFound = true)
public class SpringDocConfig {

    @Value("${springdoc.info.nameContact}")
    private String nameContact;
    @Value("${springdoc.info.mailContact}")
    private String mailContact;
    @Value("${springdoc.info.urlContact}")
    private String urlContact;
    @Value("${springdoc.info.title}")
    private String title;
    @Value("${springdoc.info.description}")
    private String description;
    @Value("${springdoc.info.version}")
    private String version;
    @Value("${springdoc.info.urlGithub}")
    private String urlGithub;
    @Value("${springdoc.enabledGlobalHeaders}")
    private String enabledGlobalHeaders;
    @Value("${springdoc.enabledServerHttps}")
    private String enabledServerHttps;

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        if (isEnabled(this.enabledGlobalHeaders))
            return openApi -> {
                openApi.getPaths().values().stream()
                        .flatMap(pathItem -> pathItem.readOperations().stream());
                openApi.getServers().forEach(x -> {
                    if (isEnabled(this.enabledServerHttps))
                        x.setUrl(x.getUrl().replace("http", "https"));
                    x.setDescription(null);
                });
            };
        return openApi -> {
        };
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(new Contact()
                                .name(nameContact)
                                .email(mailContact)
                                .url(urlContact)
                        ))
                .externalDocs(externalDocumentation());
    }

    private boolean isEnabled(String value) {
        return (value != null && value.length() != 0 && value.compareTo("0") != 0);
    }

    private ExternalDocumentation externalDocumentation() {
        if (isEnabled(this.urlGithub))
            return new ExternalDocumentation().description("Confluence Documentation").url(urlGithub);
        return null;
    }
}
