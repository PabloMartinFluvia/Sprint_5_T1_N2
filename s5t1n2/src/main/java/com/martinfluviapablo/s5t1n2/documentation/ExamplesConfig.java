package com.martinfluviapablo.s5t1n2.documentation;

import io.swagger.v3.oas.models.examples.Example;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Configuration
public class ExamplesConfig {

    @Bean
    public Map<String,Example> initExamples(Collection<Entry<String,Example>> examples){
        return examples.stream().collect(
                Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    /*
    Mètode anterior substitueix aquest, per evitar el potencial spaghetti code + tenir que passar el null
    @Bean
    public Map<String,Example> initExamples(){
        return Map.ofEntries(
                getAllFlors200Example(null),
                getOneFlorExample(null),
                getUpdatedFlorExample(null),
                getInvalidIdValue400Example(null),
                getInvalidIdFormat400Example(null),
                getFlorNotFound404Example(null),
                getFlorDuplicated409Example(null)
        );
    }
    */

    private String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            return FileCopyUtils.copyToString(reader);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Bean
    public Entry<String, Example> getAllFlors200Example(
            @Value("classpath:/examples/AllFlors.txt") Resource allFlors){
        Example example = new Example()
                .summary("Flor Collection Example")
                .value(asString(allFlors));
        return new AbstractMap.SimpleEntry<>("AllFlorsExample", example);
    }

    @Bean
    public Entry<String,Example> getOneFlorExample(
            @Value("classpath:/examples/OneFlor.txt") Resource oneFlor){
        Example example = new Example()
                .summary("One Flor Example")
                .value(asString(oneFlor));
        return new AbstractMap.SimpleEntry<> ("OneFlorExample", example);
    }

    @Bean
    public Entry<String,Example> getUpdatedFlorExample(
            @Value("classpath:/examples/UpdatedFlor.txt") Resource updatedFlor){
        Example example = new Example()
                .summary("Updated Flor Example")
                .value(asString(updatedFlor));
        return new AbstractMap.SimpleEntry<>("UpdatedFlorExample", example);
    }

    @Bean
    public Entry<String,Example> getFlorNotFound404Example(
            @Value("classpath:/examples/FlorNotFoundError.txt") Resource florNotFound){
        Example example = new Example()
                .summary("Flor Not Found Example")
                .value(asString(florNotFound))
                .description("Response Body example when none flor is found with the provided ID (ex: 23).");
        return new AbstractMap.SimpleEntry<>("FlorNotFoundErrorExample", example);
    }

    @Bean
    public Entry<String,Example> getInvalidIdValue400Example(
            @Value("classpath:/examples/InvalidIdValueError.txt") Resource invalidIdValue){
        Example example = new Example()
                .summary("Int ID <=0 Example")
                .value(asString(invalidIdValue))
                .description("Response Body example when ID value in path it's <= 0.");
        return new AbstractMap.SimpleEntry<> ("InvalidIdValueErrorExample", example);
    }

    @Bean
    public Entry<String,Example> getInvalidIdFormat400Example(
            @Value("classpath:/examples/InvalidIdFormatError.txt") Resource invalidIdFormat){
        Example example = new Example()
                .summary("NOT Int ID Example")
                .value(asString(invalidIdFormat))
                .description("Response Body example when ID value is not an integer.");
        return new AbstractMap.SimpleEntry<> ("InvalidIdFormatErrorExample", example);
    }

    @Bean
    public Entry<String,Example> getFlorDuplicated409Example(
            @Value("classpath:/examples/FlorDuplicatedError.txt") Resource florDuplicated){
        Example example = new Example()
                .summary("Duplicated Data Example")
                .value(asString(florDuplicated))
                .description("Response Body example when already exists another flor with same name and country values.");
        return new AbstractMap.SimpleEntry<>("FlorDuplicatedErrorExample", example);
    }

    @Bean
    public Entry<String,Example> getInvalidPost400Example(
            @Value("classpath:/examples/InvalidPostError.txt") Resource invalidPost){
        Example example = new Example()
                .summary("Invalid Post Body Example")
                .value(asString(invalidPost))
                .description("Response Body example when id / tipusFlor values are provided and required pais is not provided with a valid value.");
        return new AbstractMap.SimpleEntry<>("InvalidPostErrorExample", example);
    }

    @Bean
    public Entry<String,Example> getInvalidPut400Example(
            @Value("classpath:/examples/InvalidPutError.txt") Resource invalidPut){
        Example example = new Example()
                .summary("Invalid Put Body Example")
                .value(asString(invalidPut))
                .description("Response Body example when tipusFlor value is provided and required id and nom is not provided with a valid value.");
        return new AbstractMap.SimpleEntry<>("InvalidPutErrorExample", example);
    }
}
