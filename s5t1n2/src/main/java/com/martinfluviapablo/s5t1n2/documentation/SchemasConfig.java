package com.martinfluviapablo.s5t1n2.documentation;

import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SchemasConfig {

    private final String ID = "pk_FlorID";

    private final String NOM = "nomFLor";

    private final String PAIS = "paisFlor";

    /*
    Podria seguir el mateix procediment que ExamplesConfig, xo com que només són 2
    i les entries NO cal definir-les com a beans xk no cal llegir un resource
     -> ho faig de la manera "tradicional."
     */
    @Bean
    public Map<String,Schema> initSchemas(){
        return Map.ofEntries(
                getFlorPostSchema(),
                getFlorPutSchema()
        );
    }

    private Map.Entry<String, Schema> getFlorPostSchema(){
        Schema florPostSchema = new ObjectSchema() //type object, format null
                .required(List.of(NOM,PAIS))
                .addProperty(NOM,new StringSchema()
                        .minLength(1)
                        .maxLength(255)
                        .example("Rosa"))
                .addProperty(PAIS,new StringSchema()
                        .minLength(1)
                        .maxLength(255)
                        .example("Alemanya"));
        return new AbstractMap.SimpleEntry<>("FlorPostSchema", florPostSchema);
    }

    private Map.Entry<String,Schema> getFlorPutSchema(){
        Schema florPutSchema = new ObjectSchema() //type object, format null
                .required(List.of(ID,NOM,PAIS))
                .addProperty(ID, new IntegerSchema()
                        .minimum(BigDecimal.valueOf(1))
                        .example(2))
                .addProperty(NOM,new StringSchema()
                        .minLength(1)
                        .maxLength(255)
                        .example("RosaV2"))
                .addProperty(PAIS,new StringSchema()
                        .minLength(1)
                        .maxLength(255)
                        .example("AlemanyaV2"));
        return new AbstractMap.SimpleEntry<>("FlorPutSchema", florPutSchema);
    }
}
