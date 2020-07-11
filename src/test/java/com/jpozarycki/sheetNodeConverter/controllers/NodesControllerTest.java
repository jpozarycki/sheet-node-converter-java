package com.jpozarycki.sheetNodeConverter.controllers;

import com.jpozarycki.sheetNodeConverter.mappers.JsonMapper;
import com.jpozarycki.sheetNodeConverter.model.Node;
import com.jpozarycki.sheetNodeConverter.services.NodesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.OK;

public class NodesControllerTest {
    @Mock
    private NodesService nodesService;
    @Mock
    private JsonMapper jsonMapper;
    private NodesController nodesController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setupHttpContext();
        setupMocks();
        nodesController = new NodesController(nodesService, jsonMapper);
    }

    @Test
    public void getNodesReturnsOk() {
        Result result = nodesController.getNodes();

        assertThat(result.status(), is(OK));
    }

    private void setupMocks() {
        List<Node> nodes = List.of(new Node(1, "some node", Collections.emptyList()));
        when(nodesService.getDefaultNodes()).thenReturn(nodes);
        when(jsonMapper.toJson(any(List.class))).thenReturn(Json.toJson(nodes));
    }

    private void setupHttpContext() {
        Http.Context context = mock(Http.Context.class);
        when(context.response()).thenReturn(new Http.Response());
        Http.Context.current.set(context);
    }


}
