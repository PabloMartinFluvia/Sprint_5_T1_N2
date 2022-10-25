/*
 *CLASSE INÚTIL:
 *
 * Usada per a validacions i comprovacions en els 2 moments en què m'he estancat.
 * La deixo perquè no influeix en la funcionalitat i em serveix com apunts i recordatori
 * de què he après al remenar.
 *
 * Sé que lo intentat a continuació hauria sigut millor fer-ho via testos, però hauria implicat començar
 * ja a estudiar mocks i RestClient.
 */

package com.martinfluviapablo.s5t1n2.apunts;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;

import java.util.List;

@Configuration
public class ApuntsApiRest {

     /*
    APUNTS validació:

	Per a validar paràmetres i paths variables -> anotar @Validated la classe +
	tenir alguna implementació JSR 303 de Java Validation API, per exemple
	hibernate-validator, + cal un bean MethodValidationPostProcessor:

	EN TEORIA SPRING BOOT L'AUTOCONFIGURA al tenir en el class path hibernate-validator.
	En la versió 6.0.10.Final l'autoconfigura i NO cal declarar el bean,
	però usant l'última versió hibernate-validator NO es configura (WTF???)
	ni deixa crear el bean (no trova el classpath).

	-> SOLUCIÓ:
	No posar la versió del hibernate-validator en el pom.xml -> SpringBoot autoconfigura el bean correctament.
	Opcionalment afegir
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>

    //for use Java Validation API an implementation is needed (ex: hibernate.validator dependency)
    //in theory SpringBoot autoconfigures this bean
    @Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
	 */

    //---------------------------------------------------------------------------------------

    /*
    APUNTS jackson mapper i conflicte sorgit amb NoHandlerFoundException:

    Per a que es llanci NoHandlerFoundException i poder gestionar-la cal especificar-ho en propietats
    -> la millor solució
    https://stackoverflow.com/questions/51048707/spring-boot-handling-nohandlerfoundexception/
    no està ok, ja que la segona propietat està obsoleta (veure últim comentari de la solució)
    -> una alternativa és @EnableMvc (usada mentre no em vaig adonar que la propietat era errònia)
    Mentre no vaig arrivar al validation del PUT no em va donar problemes:
    jackson per defecte trunca floats a integers
	-> per a deshabilitar-ho cal especificar-ho en properties ->
	no s'aplicava (a causa de @EnableMvc, em va costar trovar que aquesta era la causa)
	Vaig perdre MOLT de temps comprovant (i remenant) que la feature estava desactivada,
	i no entenia per què no s'aplicava, si tot indicava que estava desactivada.
	Em va costar força assumir que Jackson estava OK i detectar on estava el problema realment.

	Solució: (obviament) treure @EnableMvc i per a que el NoHandlerFoundException funcioni posar
	la propietat actualitzada segons l'última versió de Spring.

	Però m'ha permès remenar força Spring:
     */

    //Tot lo següent són comprovacions de Jackson, no era el problema

    @Bean
    public CommandLineRunner checkJackson (ObjectMapper mapper, Jackson2ObjectMapperBuilder builder,
                                    List<HttpMessageConverter<?>> converters){
        return args -> {

            boolean activat;
            //bean ObjectMapper té la configuració segons arxiu de propietats
            activat= mapper.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
            Assert.isTrue(!activat,"Object mapper mal configurat.");

            //redundant, però comprovar que la DeserializationConfig està ben configurada
            activat = mapper.getDeserializationConfig().isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
            Assert.isTrue(!activat,"Configuració errònia.");

            /*
	        ObjectReader es crea a partir de la mateixa configuració que el mapper
	        JsonParser features no influeixen en el problema
	        */

            //Comprovar que les instàncies obtingudes mitjançant el default builder estan ben configurades
            activat = builder.build().getDeserializationConfig().isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
            Assert.isTrue(!activat,"Builder del mapper mal configurat.");

            //DeserializationConfig ok -> DeserializationContext, DeserializerFactory, JsonDeserializer també

            //El default builder ok -> MappingJackson2HttpMessageConverter i "família" HttpMessageConverter ok

            converters.forEach(converter -> {
				/*
				System.out.println(converter.getClass().getSimpleName());

				Result: 2 beans converters in context
				 	MappingJackson2HttpMessageConverter
				 	StringHttpMessageConverter
				 */

                if(converter instanceof MappingJackson2HttpMessageConverter){
                    //ok: constructors necessiten el ObjectMapper or Jackson2ObjectMapperBuilder (ja revisats)
                } else if (converter instanceof StringHttpMessageConverter) {
                    /*
                    converter.getSupportedMediaTypes().forEach(System.out::println);

                    supports all types -> potser és el problema?
                     */
                }else {
                    System.out.println("ERROR");
                }
            });
            // converters.remove(stringConverter); //no influeix -> no és el problema
        };
    }

	/*
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder(){
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.featuresToDisable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
		return builder;
	}
	*/

	/*
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
	}
	*/
}
