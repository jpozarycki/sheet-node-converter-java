package com.jpozarycki.sheetNodeConverter.validators;

import com.jpozarycki.sheetNodeConverter.util.InvalidWorkbookException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class SheetValidatorTest {
    private static final String CORRECT_TEMPLATE = "simple_test.xlsx";
    private static final String NO_ID_COLUMN_TEST = "no_id_column_test.xlsx";
    private static final String INVALID_LAYER_TEST = "invalid_layer_test.xlsx";
    private static final String INVALID_ID_TEST = "invalid_id_test.xlsx";
    private SheetValidator sheetValidator;

    @Before
    public void setUp() {
        sheetValidator = new SheetValidator();
    }

    @Test(expected = Test.None.class)
    public void noExceptionOnCorrectTemplate() {
        XSSFSheet sheet = getSheet(CORRECT_TEMPLATE);
        sheetValidator.validateSheet(sheet);
    }

    @Test(expected = InvalidWorkbookException.class)
    public void exceptionIsThrownWhenIdColumnIsMissing() {
        XSSFSheet sheet = getSheet(NO_ID_COLUMN_TEST);
        sheetValidator.validateSheet(sheet);
    }

    @Test(expected = InvalidWorkbookException.class)
    public void exceptionIsThrownWhenInvalidValueInLayersIsProvided() {
        XSSFSheet sheet = getSheet(INVALID_LAYER_TEST);
        sheetValidator.validateSheet(sheet);
    }

    @Test(expected = InvalidWorkbookException.class)
    public void exceptionIsThrownWhenInvalidIdIsProvided() {
        XSSFSheet sheet = getSheet(INVALID_ID_TEST);
        sheetValidator.validateSheet(sheet);
    }

    private XSSFSheet getSheet(String sheetName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(sheetName)) {
            return new XSSFWorkbook(is).getSheetAt(0);
        } catch (IOException | NullPointerException e) {
            throw new InvalidWorkbookException(e);
        }    }
}
