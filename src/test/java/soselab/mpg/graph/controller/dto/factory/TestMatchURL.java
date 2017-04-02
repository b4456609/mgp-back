package soselab.mpg.graph.controller.dto.factory;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class TestMatchURL {

    @Test
    public void isMatchUrl() throws Exception {
        Set<String> endpoints = new HashSet<>();
        endpoints.add("name endpoint /id/1234 post");
        endpoints.add("name endpoint /id/3533 post");
        boolean matchUrl = GraphVisualizationFromGraphFactory.isMatchUrl(endpoints, "name endpoint /id/{id} post");
        assert matchUrl == true;
    }

}