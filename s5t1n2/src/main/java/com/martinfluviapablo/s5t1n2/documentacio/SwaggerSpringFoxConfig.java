/*
TEORIA:
Intro / guia inicial:
https://swagger.io/docs/specification/about/

OpenAPI Specification (Swagger Specification): API format de descripció per a REST APIs
    Especificació completa:
    https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.0.3.md

Swagger: conjunt d'eines al voltant de OpenAPI Specification per ajudar a disenyar, construir, documentar
i consumir una API REST. Principals:
     Editor: editor web per escriure OpenAPI deficinicions
     UI: renderitzar OpenApi definicions en documentació interactiva
     Core: llibreries java per a crear, consumir, i treballar amb OpenApi deficinicions
     Parser: per parsing OpenApi defincions

     Core annotations
     https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X

Springfox: conjunt de llibreries java que implementen Swagger 2 per a poder treballar en projectes de Spring.
https://springfox.github.io/springfox/docs/current/
    Amb la dependència indicada en la documentació + usant Spring Boot:
        ja no cal l'anotació @EnableSwagger2
        swagger-ui habilitat automàticament.
            http://host/context-path/swagger-ui/index.html or http://host/context-path/swagger-ui/
            ->
            http://localhost:9001/swagger-ui/index.html
            http://localhost:9001/swagger-ui/
    Obs: hi ha la opció d'afegir dependencies extra (+ usant @Import en la classe de configuració)
        springfox-data-rest EN ESTAT D'INCUBACIÓ
        springfox-bean-validators

    Exemple d'us d'anotacions
    https://springfox.github.io/springfox/docs/current/#property-file-lookup

 */
package com.martinfluviapablo.s5t1n2.documentacio;


import com.fasterxml.classmate.TypeResolver;
import com.martinfluviapablo.s5t1n2.controllers.FlorController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

/*
APUNTS PRÀCTICS:
1) Afegir dependència de Springfox

2) Configuració de beans
    1) Docket: Interfaç principal de SpringFox per a la configuració de swagger
        Té estructura de Builder.
        Indicar el tipus de documentació
    2) ApiSelectorBuilder: builder d'un ApiSelector per al Docket
                    .build() : Docket amb docket.selector(el ApiSelector construit)
            ApiSelector: 2 Predicates de <RequestHandler> i <String>
                Permet indicar al ApiSelector del Docket com evaluar i seleccionar mètodes
                que gestionen peticions i paths (end-points)
    3+4) Afegir (mitjançant un AND), opcionalment, condicions al predicat de sel·lecció
        Veure documentació per veure possibles opcions.
    5) Adds a servlet path mapping, when the servlet has a path mapping.
        This prefixes paths with the provided path mapping
        ->  NO ACAVO D'ENTENDRE FUNCIONALITAT
    6) Indicar si s'ha d'utilitzar els http response codes per defecte o no
 */


@Configuration
//@EnableSwagger2 //With SpringBoot and spring fox starter dependency -> not needed
@ComponentScan(basePackageClasses = FlorController.class) // Instructs spring where to scan for API controllers
@Import({BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class}) // add bean validator plugin to this configuration
public class SwaggerSpringFoxConfig {

    @Bean
    public Docket florDocket(){
        return new Docket(DocumentationType.SWAGGER_2) // 1) Docket for swagger 2 specifications
                .select() // 2) retorna/accedir al ApiSelectorBuilder
                .apis(RequestHandlerSelectors.any()) // 3) default Predicate for RequestHandlerSelector
                .paths(PathSelectors.any()) // 4) default Predicate for PathSelector
                .build() //Docket configured with the specified ApiSelector
                .pathMapping("/") // 5) Add a servlet path mapping (Optional<String>). Default empty
                .useDefaultResponseMessages(false) // 6) Flag for NOT using default response codes
                /*
                API INFO:
                constructor requires: title, description, version, terms of service, Contact, license,
                licenseUrl, Collection<VendorExtension>
                ApiInfo apiInfo = new ApiInfo(XXXXXXXX);
                With so many arguments -> exist a builder
                in this example no terms of service, no license, no license url, no vendors extensions
                */
                .apiInfo(new ApiInfoBuilder()
                        .title("API Flors CRUD")
                        .description("API per a realitzar operacions CRUD de flors")
                        .version("1.0")
                        .contact(new Contact("Pablo Martín Fluvià", "","paumf00@gmail.com"))
                        .build());

                //generate rule builders for specific classes
                /* if I want to specify which class substitutes other when rendering model properties

                .directModelSubstitute(Locale.class,String.class) //uses TypeResolver.resolve
                 */

                /*
                 	Similar: Convenience rule builder that substitutes a generic type with one type parameter
                 	with the type parameter. In this example ResponseEntity<T> with T. alternateTypeRules allows
                 	custom rules that are a bit more involved. The example substitutes
                 	DeferredResult<ResponseEntity<T>> with T generically.

                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules( //Adds model substitution rules (AlternateTypeRules)
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(
                                        DeferredResult.class,
                                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class))
                        )
                */


                /*way to define all the available tags that services/operations can opt into.
                 Currently this only has name and description.

                .tags(new Tag("Pet Service", "All apis relating to pets"))
                */

                /*
                Are there models in the application that are not "reachable"? Not reachable is when
                we have models that we would like to be described but aren’t explicitly used in any
                operation. An example of this is an operation that returns a model serialized as a string.
                We do want to communicate the expectation of the schema for the string. This is a way to do
                    exactly that.

                .additionalModels(typeResolver.resolve(AdditionalModel.class));
                 */

                /*
                Allows globally overriding response messages for different http methods.
                 In this example we override the 500 error code for all GET requests
                and indicate that it will use the response model Error (which will be defined elsewhere)
                 ->  NO ACAVO D'ENTENDRE FUNCIONALITAT

                .globalResponses(HttpMethod.GET,
                        singletonList(new ResponseBuilder()
                                .code("500")
                                .description("500 message")
                                .representation(MediaType.APPLICATION_JSON)
                                .apply(r ->
                                        r.model(m ->
                                                m.referenceModel(ref ->
                                                        ref.key(k ->
                                                                k.qualifiedModelName(q ->
                                                                        q.namespace("some:namespace")
                                                                                .name("ERROR"))))))
                                .build()))
                */

                /*
                Allows globally configuration of default path-/request-/headerparameters which are
                common for every rest operation of the api, but aren`t needed in spring controller
                method signature (for example authenticaton information). Parameters added here will be
                part of every API Operation in the generated swagger specification. on how the security
                is setup the name of the header used may need to be different. Overriding this value is a
                way to override the default behavior.


                .globalRequestParameters(
                        singletonList(new springfox.documentation.builders.RequestParameterBuilder()
                                .name("someGlobalParameter")
                                .description("Description of someGlobalParameter")
                                .in(ParameterType.QUERY)
                                .required(true)
                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                                .build()))
                 */

                /*
                Temes de seguretat

                Sets up the security schemes used to protect the apis. S
                upported schemes are ApiKey, BasicAuth and OAuth

                .securitySchemes(singletonList(apiKey()))


                Provides a way to globally set up security contexts for operation.
                The idea here is that we provide a way to select operations to be protected
                 by one of the specified security schemes.

                .securityContexts(singletonList(securityContext()))
                */

                /*
                  Incubating * setting this flag signals to the processor that the paths generated should
                  try and use form style query expansion.

                 .enableUrlTemplating(true)
                */


    }






    @Autowired
    TypeResolver typeResolver;

    /*
    Temes de seguretat:

    Here we use ApiKey as the security schema that is identified by the name mykey
    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    Selector for the paths this security context applies to.
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    Here we use the same key defined in the security scheme mykey
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(
                new SecurityReference("mykey", authorizationScopes));
    }

    Optional swagger-ui security configuration for oauth and apiKey settings
    @Bean
  SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
        .clientId("test-app-client-id")
        .clientSecret("test-app-client-secret")
        .realm("test-app-realm")
        .appName("test-app")
        .scopeSeparator(",")
        .additionalQueryStringParams(null)
        .useBasicAuthenticationWithAccessCodeGrant(false)
        .enableCsrfSupport(false)
        .build();
  }

    Optional swagger-ui ui configuration currently only supports the validation url
  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .deepLinking(true)
        .displayOperationId(false)
        .defaultModelsExpandDepth(1)
        .defaultModelExpandDepth(1)
        .defaultModelRendering(ModelRendering.EXAMPLE)
        .displayRequestDuration(false)
        .docExpansion(DocExpansion.NONE)
        .filter(false)
        .maxDisplayedTags(null)
        .operationsSorter(OperationsSorter.ALPHA)
        .showExtensions(false)
        .showCommonExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
        .validatorUrl(null)
        .build();
  }
    */

}
