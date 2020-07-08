package com.jpozarycki;

import com.jpozarycki.model.Node;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        XSSFWorkbook wb = getWorkbook("test.xlsx");
        List<Node> nodes = getNodesFromWorkbook(wb);
    }

    private static XSSFWorkbook getWorkbook(String fileName) {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            return new XSSFWorkbook(is);
        } catch (IOException e) {
            throw new RuntimeException("Error on retrieving workbook", e);
        }
    }

    private static List<Node> getNodesFromWorkbook(XSSFWorkbook workbook) {
        XSSFSheet mainSheet = workbook.getSheetAt(0);
        Iterator<Row> rows = mainSheet.rowIterator();
        List<Node> nodes = new ArrayList<>();

        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                System.out.println(cells.next().toString());
            }
        }
        return nodes;
    }
}
