package com.jpozarycki.sheetNodeConverter.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.jpozarycki.sheetNodeConverter.mappers.JsonMapper;
import com.jpozarycki.sheetNodeConverter.model.Node;
import com.jpozarycki.sheetNodeConverter.services.NodesService;
import lombok.RequiredArgsConstructor;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NodesController extends Controller {

    private final NodesService nodesService;
    private final JsonMapper jsonMapper;

    public Result getNodes() {
        List<Node> nodes = nodesService.getDefaultNodes();
        JsonNode json = jsonMapper.toJson(nodes);
        response().setHeader("Access-Control-Allow-Origin", "*"); // temporary solution since I can't configure CORS in play
        return ok(json);
    }
}
