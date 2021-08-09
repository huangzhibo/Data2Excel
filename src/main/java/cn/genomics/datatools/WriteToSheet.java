package cn.genomics.datatools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

public class WriteToSheet {
    private boolean rowColor = false;
    private Workbook wb;
    private CellStyle textStyle;
    private CellStyle colorTextStyle;
    private CellStyle headerStyle;
    MySheetFormat mySheetFormat;

    public WriteToSheet(Workbook wb) {
        this.wb = wb;
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBold(true);
        headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);

        textStyle = wb.createCellStyle();

        colorTextStyle = wb.createCellStyle();
        colorTextStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        colorTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        mySheetFormat = new MySheetFormat();
    }

    public WriteToSheet(Workbook wb, MySheetFormat mySheetFormat) {
        this.wb = wb;
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBold(true);
        headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);

        textStyle = wb.createCellStyle();

        colorTextStyle = wb.createCellStyle();
        colorTextStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        colorTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        setMySheetFormat(mySheetFormat);
    }

    public void setRowColor(boolean rowColor) {
        this.rowColor = rowColor;
    }

    public void setMySheetFormat(MySheetFormat mySheetFormat) {
        this.mySheetFormat = mySheetFormat;
    }

    public void writeToSheet(List<String[]> inData, int max_col, String sheetName){
        Sheet sheet = wb.createSheet(sheetName);
        toSheet(sheet, inData, max_col);
    }

    public void writeToSheet(List<String[]> inData, int max_col){
        Sheet sheet = wb.createSheet();
        toSheet(sheet, inData, max_col);
    }

    public void toSheet(Sheet sheet, List<String[]> inData, int max_col){
        sheet.setDefaultRowHeight(mySheetFormat.getDefaultRowHeight());

        int n = 0, col_num;
        String[] header;
        List<MyColumnFormat> myColumnFormats = new ArrayList<>();

        for (String[] fields : inData) {
            col_num = fields.length;
            Row row;

            if( col_num == 1)
            {
                CellRangeAddress r_merge = new CellRangeAddress(n, n, 0, max_col-1);
                sheet.addMergedRegion(r_merge);
            }

            if (fields[0].startsWith("#")) {
                fields[0] = fields[0].substring(1);
                header = fields;
                row = sheet.createRow(n++);
                CellRangeAddress range = new CellRangeAddress(0, n - 1, 0, col_num - 1);
                sheet.setAutoFilter(range);
                sheet.createFreezePane(0,1);
                setColumnFormat(sheet,header,myColumnFormats);
                outLine2HeadRow(fields, row, headerStyle);
                continue;
            }

            row = sheet.createRow(n++);
            if (rowColor || n % 2 == 0){
                outLine2Row(fields, myColumnFormats, row, colorTextStyle);
            }else {
                outLine2Row(fields, myColumnFormats, row, textStyle);
            }
        }
    }

    public static void outLine2HeadRow(String[] strArr, Row row, CellStyle style) {
        for (int j = 0; j < strArr.length; j++) {
            Cell cell = row.createCell(j);
            cell.setCellStyle(style);
            cell.setCellValue(strArr[j].trim());
        }
    }

    public static void outLine2Row(String[] strArr, List<MyColumnFormat> myColumnFormats, Row row, CellStyle style) {
        for (int j = 0; j < strArr.length; j++) {
            Cell cell = row.createCell(j);
            cell.setCellStyle(style);
            if(!myColumnFormats.isEmpty() && myColumnFormats.get(j) != null){
                String valueType = myColumnFormats.get(j).getValueType();
                if(valueType.equals("INT") || valueType.equals("LONG")){
                    cell.setCellValue(Long.parseLong(strArr[j].trim()));
                }else if(valueType.equals("DOUBLE")){
                    cell.setCellValue(Double.parseDouble(strArr[j].trim()));
                }else {
                    cell.setCellValue(strArr[j].trim());
                }
            }else {
                cell.setCellValue(strArr[j].trim());
            }
        }
    }

    public void setColumnFormat(Sheet sheet, String[] header, List<MyColumnFormat> myColumnFormats)
    {
        for(int j=0;j<header.length;j++)
        {
            if(!mySheetFormat.format.containsKey(header[j])) {
                myColumnFormats.add(null);
                continue;
            }
            MyColumnFormat myColumnFormat = mySheetFormat.format.get(header[j]);
            myColumnFormats.add(myColumnFormat);
            sheet.setColumnHidden(j, !myColumnFormat.inExcel);
            sheet.setColumnWidth(j, myColumnFormat.getColumnWidth());
        }
    }


    public CellStyle getTextStyle() {
        return textStyle;
    }

    public CellStyle getHeaderStyle() {
        return headerStyle;
    }
}
