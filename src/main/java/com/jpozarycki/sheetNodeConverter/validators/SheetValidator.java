package com.jpozarycki.sheetNodeConverter.validators;

import com.jpozarycki.sheetNodeConverter.util.InvalidWorkbookException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import javax.inject.Singleton;
import java.util.Iterator;

@Singleton
public class SheetValidator {
    private static final String ID_COLUMN_NAME = "ID";
    private static final String NO_ID_COLUMN_MESSAGE = "Sheet does not contain ID column or first row is missing";
    private static final String INVALID_ID_TYPE_MESSAGE = "Only numeric values are allowed in ID column";
    private static final String INVALID_LAYERS_TYPE_MESSAGE = "Only string anf blank values are allowed in layers";

    public void validateSheet(XSSFSheet sheet) {
        checkIfLastColumnIsId(sheet);
        checkIfIdColumnContainsOnlyNumbers(sheet);
        checkIfLayersContainOnlyBlankAndStringValues(sheet);
    }

    private void checkIfLastColumnIsId(XSSFSheet sheet) {
        int lastCellIdx = sheet.getRow(0).getLastCellNum() - 1;
        Cell lastCell = sheet.getRow(0).getCell(lastCellIdx);
        if (!lastCell.getStringCellValue().equals(ID_COLUMN_NAME)) {
            throw new InvalidWorkbookException(NO_ID_COLUMN_MESSAGE);
        }
    }

    private void checkIfIdColumnContainsOnlyNumbers(XSSFSheet sheet) {
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            int lastCellIdx = row.getLastCellNum() - 1;
            CellType cellType = row.getCell(lastCellIdx).getCellType();
            if (cellType != CellType.NUMERIC) {
                throw new InvalidWorkbookException(INVALID_ID_TYPE_MESSAGE);
            }
        }
    }

    private void checkIfLayersContainOnlyBlankAndStringValues(XSSFSheet sheet) {
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                CellType cellType = cell.getCellType();
                if (cellType != CellType.STRING || cellType != CellType.BLANK) {
                    throw new InvalidWorkbookException(INVALID_LAYERS_TYPE_MESSAGE);
                }
            }
        }
    }
}
