/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indexing.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.PageOrientation;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author ehab
 */
public class ExcelUtil {

    public WritableWorkbook createSchemaFile(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (! file.exists()){
            file.getParentFile().mkdirs();
        }
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ar", "AR"));
        WritableWorkbook workbook =
                Workbook.createWorkbook(file, ws);
        return workbook;
    }

    public WritableWorkbook readSchemaFile(File file)
            throws IOException, BiffException {
        Workbook workbook =
                Workbook.getWorkbook(file);
        return Workbook.createWorkbook(file, workbook);

    }

    public List<List<String>> readXLSFile(File file)
            throws IOException, BiffException {
        Workbook workbook =
                Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(0);
        List<List<String>> dataSheet = new ArrayList<List<String>>();
        for (int i = 1; i < sheet.getRows(); i++) {
            List<String> rowData = new ArrayList<String>();
            Cell[] cels = sheet.getRow(i);
            for (Cell cell : cels) {
                rowData.add(cell.getContents());
            }
            dataSheet.add(rowData);
        }

        return dataSheet;

    }

    public void addColumnsLables(WritableWorkbook wwb, List<String> labels )
            throws WriteException, IOException{
        WritableSheet ws = wwb.createSheet("Data", 0);
        Label label = null;
        for (int i = 0; i < labels.size(); i++) {
            String lbl = labels.get(i);
            label = new Label(i, 0, lbl);
            ws.addCell(label);
        }
        wwb.write();
        wwb.close();
    }
    public void addValues(WritableWorkbook wwb, List<String> values)
            throws WriteException, IOException{
        WritableSheet ws = wwb.getSheet(0);
        for (int i = 0; i < values.size(); i++) {
            Label label = new Label(i, new Integer(values.get(0)), values.get(i));
            ws.addCell(label);
        }
        wwb.write();
        wwb.close();
    }

    public static void main(String[] args) throws Exception {
        ExcelUtil excel = new ExcelUtil();
        WritableWorkbook wwb = excel.readSchemaFile(new File("c://input.xls"));
        WritableSheet ws = wwb.getSheet(0);
        ws.setPageSetup(PageOrientation.PORTRAIT);
//        WritableWorkbook wwb = excel.createSchemaFile("c://input.xls");
//        WritableSheet ws = wwb.createSheet("data", 0);
        Label label = new Label(2, 0, "d03");
        Label label02 = new Label(3, 0, "d04");
        ws.addCell(label);
        ws.addCell(label02);

        wwb.write();
        wwb.close();




    }
}
