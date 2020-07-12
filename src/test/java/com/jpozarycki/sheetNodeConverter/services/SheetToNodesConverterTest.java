package com.jpozarycki.sheetNodeConverter.services;

import com.jpozarycki.sheetNodeConverter.model.Node;
import com.jpozarycki.sheetNodeConverter.util.InvalidWorkbookException;
import com.jpozarycki.sheetNodeConverter.validators.SheetValidator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class SheetToNodesConverterTest {
    private static final String SIMPLE_SHEET = "simple_test.xlsx";

    @Mock
    private SheetValidator validator;
    private SheetToNodesConverter sheetToNodesConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sheetToNodesConverter = new SheetToNodesConverter(validator);
    }

    @Test
    public void handlesProperlyConfiguredSheets() {
        doNothing().when(validator).validateSheet(isA(XSSFSheet.class));
        List<Node> expectedNodes = simpleNodes();

        XSSFSheet sheet = getSheet(SIMPLE_SHEET);
        List<Node> nodes = sheetToNodesConverter.getNodesFromSheet(sheet);

        assertThat(nodes, is(expectedNodes));
    }

    @Test(expected = InvalidWorkbookException.class)
    public void passesOnExceptionOnInvalidSheet() {
        doThrow(new InvalidWorkbookException("Invalid Workbook")).when(validator).validateSheet(isA(XSSFSheet.class));

        XSSFSheet sheet = getSheet(SIMPLE_SHEET);
        sheetToNodesConverter.getNodesFromSheet(sheet);
    }

    private List<Node> simpleNodes() {
        List<Node> simpleNodes = List.of(
                new Node(1, "A", List.of(
                        new Node(2, "AA", List.of(
                                new Node(3, "AA1", Collections.emptyList()
                                ))))));
        return simpleNodes;
    }

    private XSSFSheet getSheet(String sheetName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(sheetName)) {
            return new XSSFWorkbook(is).getSheetAt(0);
        } catch (IOException | NullPointerException e) {
            throw new InvalidWorkbookException(e);
        }
    }

}
