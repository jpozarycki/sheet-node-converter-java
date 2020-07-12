package com.jpozarycki.sheetNodeConverter.services;

import com.jpozarycki.sheetNodeConverter.model.Node;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class NodesServiceTest {
    private static final Integer NODE_ID = 2;
    private static final String NODE_NAME = "Some node";
    private static final List<Node> NODE_CHILDREN = Collections.emptyList();

    @Mock
    private SheetToNodesConverter sheetToNodesConverter;
    private NodesService nodesService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nodesService = new NodesService(sheetToNodesConverter);
    }

    @Test
    public void getDefaultNodesReturnsListOfNodes() {
        setupConverterMock();

        List<Node> nodes = nodesService.getDefaultNodes();
        Node firstNode = nodes.get(0);

        assertEquals(NODE_ID, firstNode.getId());
        assertEquals(NODE_NAME, firstNode.getName());
        assertEquals(NODE_CHILDREN, firstNode.getNodes());
    }

    private void setupConverterMock() {
        List<Node> nodes = List.of(new Node(NODE_ID, NODE_NAME, NODE_CHILDREN));
        when(sheetToNodesConverter.getNodesFromSheet(any(XSSFSheet.class))).thenReturn(nodes);
    }
}
