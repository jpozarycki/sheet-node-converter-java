package com.jpozarycki;

import com.jpozarycki.model.Node;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        XSSFWorkbook wb = getWorkbookFromResources("test.xlsx");
        XSSFSheet mainSheet = wb.getSheetAt(0);
        int lastLayer = mainSheet.getRow(0).getLastCellNum() - 2;
        List<Node> nodes = createNodes(mainSheet, Collections.emptyList(), lastLayer);
        System.out.println(nodes.toString());
    }

    private static XSSFWorkbook getWorkbookFromResources(String fileName) {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            return new XSSFWorkbook(is);
        } catch (IOException e) {
            throw new RuntimeException("Error on retrieving workbook", e);
        }
    }

    private static List<Node> createNodes(XSSFSheet sheet, List<Node> nodes, Integer layer) {
        if (layer < 0) {
            return nodes;
        }
        List<Node> newNodes = new ArrayList<>();
        int lastRowIdx = sheet.getLastRowNum();
        for (int i = 1; i <= lastRowIdx; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(layer);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                int lastCellIdx = row.getLastCellNum() - 1;
                Node newNode = new Node(lastCellIdx, cell.getStringCellValue());
                newNodes.add(newNode);
            }
        }
        for (Node childNode : nodes) {
            for (Node parentNode : newNodes) {
                if (childNode.getName().startsWith(parentNode.getName())) {
                    List<Node> parentNodeNodes = parentNode.getNodes();
                    parentNodeNodes.add(childNode);
                    parentNode.setNodes(parentNodeNodes);
                }
            }
        }

        return createNodes(sheet, newNodes, layer - 1);
    }
}
