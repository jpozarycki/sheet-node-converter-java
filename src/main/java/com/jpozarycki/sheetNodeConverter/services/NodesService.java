package com.jpozarycki.sheetNodeConverter.services;

import com.jpozarycki.sheetNodeConverter.model.Node;
import com.jpozarycki.sheetNodeConverter.util.InvalidWorkbookException;
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
    private static final String DEFAULT_SHEET = "test.xlsx";

    private final SheetToNodesConverter converter;

    public List<Node> getDefaultNodes() {
        XSSFWorkbook wb = getWorkbookFromResources(DEFAULT_SHEET);
        XSSFSheet sheet = wb.getSheetAt(0);
        return converter.getNodesFromSheet(sheet);
    }

    private XSSFWorkbook getWorkbookFromResources(String sheetName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(sheetName)) {
            return new XSSFWorkbook(is);
        } catch (IOException | NullPointerException e) {
            throw new InvalidWorkbookException(e);
        }
    }
}
