package com.jpozarycki;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpozarycki.model.Node;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private final static String FILE_NAME = "test.xlsx";

    public static void main(String[] args) {
        XSSFWorkbook wb = getWorkbookFromResources(FILE_NAME);
        XSSFSheet mainSheet = wb.getSheetAt(0);
        int lastLayer = mainSheet.getRow(0).getLastCellNum() - 2;
        List<Node> nodes = createNodes(mainSheet, Collections.emptyList(), lastLayer);
        saveAndPrintNodesAsJson(nodes);
    }

    private static XSSFWorkbook getWorkbookFromResources(String fileName) {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            assert is != null;
            return new XSSFWorkbook(is);
        } catch (IOException e) {
            throw new RuntimeException("Error on retrieving workbook", e);
        }
    }

    private static List<Node> createNodes(XSSFSheet sheet, List<Node> childNodes, Integer layer) {
        if (layer < 0) {
            return childNodes;
        }
        List<Node> parentNodes = new ArrayList<>();
        int lastRowIdx = sheet.getLastRowNum();
        // iterating over cells in a column
        for (int i = 1; i <= lastRowIdx; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(layer);
            // creating a node and assigning child nodes if values are present
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                int lastCellIdx = row.getLastCellNum() - 1;
                int nodeId = (int) row.getCell(lastCellIdx).getNumericCellValue();
                String nodeName = cell.getStringCellValue();
                List<Node> currentChildren = new ArrayList<>();
                for (Node childNode : childNodes) {
                    if (childNode.getName().startsWith(nodeName)) {
                        currentChildren.add(childNode);
                    }
                }
                Node newNode = new Node(nodeId, nodeName, currentChildren);
                parentNodes.add(newNode);
            }
        }

        return createNodes(sheet, parentNodes, layer - 1);
    }

    private static void saveAndPrintNodesAsJson(List<Node> nodes) {
        ObjectMapper objectMapper = new ObjectMapper();
        printJsonAsPrettyString(objectMapper.valueToTree(nodes));
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get("src/main/resources/nodes.json").toFile(), nodes);
        } catch (IOException e) {
            throw new RuntimeException("Error on saving Nodes to JSON", e);
        }
    }

    private static void printJsonAsPrettyString(JsonNode jsonNode) {
        System.out.println(jsonNode.toPrettyString());
    }
}
