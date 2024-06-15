package com.ivy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import org.junit.jupiter.api.Test;

public class JsonschemaTest {

    @Test
    public void testJsonSchema() throws JsonProcessingException {
        JacksonModule jacksonModule = new JacksonModule();
        SchemaGeneratorConfigBuilder configBuilder =
                new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
                        .with(jacksonModule);

        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonNode = generator.generateSchema(BeanOutputConverterTest.User.class);

        ObjectWriter objectWriter = new ObjectMapper().writer(
                new DefaultPrettyPrinter()
                        .withObjectIndenter((new DefaultIndenter())
                                .withLinefeed(System.lineSeparator())));

        String jsonSchema = objectWriter.writeValueAsString(jsonNode);

        System.out.println(jsonSchema);
    }
}
