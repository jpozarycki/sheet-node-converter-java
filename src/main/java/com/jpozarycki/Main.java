package com.jpozarycki;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
        System.out.println(nodes.toString());
        saveNodesAsJSON(nodes);
    }

    private static XSSFWorkbook getWorkbookFromResources(String fileName) {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName)) {
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
        for (int i = 1; i <= lastRowIdx; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(layer);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                int lastCellIdx = row.getLastCellNum() - 1;
                int nodeId = (int) row.getCell(lastCellIdx).getNumericCellValue();
                Node newNode = new Node(nodeId, cell.getStringCellValue());
                parentNodes.add(newNode);
            }
        }
        for (Node childNode : childNodes) {
            for (Node parentNode : parentNodes) {
                if (childNode.getName().startsWith(parentNode.getName())) {
                    List<Node> parentNodeNodes = parentNode.getNodes();
                    parentNodeNodes.add(childNode);
                    parentNode.setNodes(parentNodeNodes);
                }
            }
        }

        return createNodes(sheet, parentNodes, layer - 1);
    }

    private static void saveNodesAsJSON(List<Node> nodes) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        try {
            objectWriter.writeValue(Paths.get("src/main/resources/nodes.json").toFile(), nodes);
        } catch (IOException e) {
            throw new RuntimeException("Error on saving Nodes to JSON", e);
        }
    }
}
