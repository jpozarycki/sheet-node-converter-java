package com.jpozarycki.sheetNodeConverter.services;

import com.jpozarycki.Main;
import com.jpozarycki.sheetNodeConverter.model.Node;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NodesService {
    private static final String SHEET_NAME = "test.xlsx";

    private final SheetToNodesConverter converter;

    public List<Node> getDefaultNodes() {
        XSSFWorkbook wb = getWorkbookFromResources(SHEET_NAME);
        XSSFSheet sheet = wb.getSheetAt(0);
        return converter.getNodesFromSheet(sheet);
    }

    private XSSFWorkbook getWorkbookFromResources(String sheetName) {
        try (InputStream is = NodesService.class.getClassLoader().getResourceAsStream(sheetName)) {
            return new XSSFWorkbook(is);
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Error on retrieving workbook: %s", e);
        }
    }
}
