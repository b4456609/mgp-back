package soselab.mpg.testreader.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class UATDTO {
    @Test
    public void deserialize() throws IOException {
        String content = this.getClass().getResource("/uat/cucumber.json").getFile();
        byte[] bytes = Files.readAllBytes(Paths.get(content));
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(JsonParser.Feature.IGNORE_UNDEFINED)
                .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<UATDTO> uatdto = objectMapper.readValue(bytes, new TypeReference<List<UATDTO>>() {
        });
        System.out.println(uatdto);
    }
}