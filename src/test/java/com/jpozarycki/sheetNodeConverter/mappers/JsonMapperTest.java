package com.jpozarycki.sheetNodeConverter.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.jpozarycki.sheetNodeConverter.model.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonMapperTest {
    private static final String NODE_NAME = "Some name";
    private static final Integer NODE_ID = 1;
    private JsonMapper jsonMapper;

    @Before
    public void setUp() {
        jsonMapper = new JsonMapper();
    }

    @Test
    public void toJsonReturnsConvertedObject() {
        Node node = new Node(NODE_ID, NODE_NAME, Collections.emptyList());
        JsonNode expectedJsonNode = getExpectedJsonNode(node);
        JsonNode jsonNode = jsonMapper.toJson(node);

        assertThat(jsonNode, is(expectedJsonNode));
    }

    @Test
    public void toJsonReturnsNullNodeWhenNullPassed() {
        JsonNode jsonNode = jsonMapper.toJson(null);
        assertThat(jsonNode.getClass(), is(NullNode.class));
    }

    private JsonNode getExpectedJsonNode(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(object);
    }

}
