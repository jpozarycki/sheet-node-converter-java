package com.jpozarycki.sheetNodeConverter.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.jpozarycki.sheetNodeConverter.mappers.JsonMapper;
import com.jpozarycki.sheetNodeConverter.model.Node;
import com.jpozarycki.sheetNodeConverter.services.NodesService;
import lombok.RequiredArgsConstructor;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;

import static play.mvc.Results.ok;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NodesController {

    private final NodesService nodesService;
    private final JsonMapper jsonMapper;

    public Result getNodes() {
        List<Node> nodes = nodesService.getDefaultNodes();
        JsonNode json = jsonMapper.toJson(nodes);
        return ok(json);
    }
}
