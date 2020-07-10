package com.jpozarycki.sheetNodeConverter.services;

import com.jpozarycki.sheetNodeConverter.model.Node;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SheetToNodesConverter {

    public List<Node> getNodesFromSheet(XSSFSheet sheet) {
        int lastLayer = sheet.getRow(0).getLastCellNum() - 2;
        return getNodes(sheet, emptyList(), lastLayer);
    }

    private List<Node> getNodes(XSSFSheet sheet, List<Node> childNodes, int layer) {
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

        return getNodes(sheet, parentNodes, layer - 1);
    }
}
