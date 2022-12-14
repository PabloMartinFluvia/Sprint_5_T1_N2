package com.martinfluviapablo.s5t1n2.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;


@Configuration
public class DocsConfig {

    @Bean
    public OpenAPI initDocumentation(Server server, Info info, Components components){
        return new OpenAPI()
                .info(info)
                .components(components)
                .addServersItem(server); // if not specified it's autodetected (ok),  but with a default server's description.
                //* .openapi : default specification version 3.0
                // .paths -> autodetected and customized with annotations
    }

    @Bean
    public Info initInfo(
            @Value("${application.version}") String appVersion,
            @Value("${application.description}") String appDescription){
        return new Info()
                .title("FLORS CRUD API ")
                .version(appVersion)
                .description(appDescription)
                .contact(new Contact()
                        .name("Pablo Martín Fluivà")
                        .email("paumf00@gmail.com"));
                 //no provided: license nor terms of service nor summary
    }

    @Bean
    public Server initServer(@Value("${server.port}") String serverPort){
        return new Server()
                .url("http://localhost:"+serverPort)
                .description("Application's server.");
    }

    @Bean
    public Components initComponents(Map<String,Example> examples, Map<String,Schema> schemas){
        return new Components()
                .schemas(schemas)
                .examples(examples);
    }
}
