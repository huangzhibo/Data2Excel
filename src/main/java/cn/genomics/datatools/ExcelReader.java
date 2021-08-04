package cn.genomics.datatools;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.*;

public class ExcelReader {
	
    private Workbook wb;
	
	public void readExcel(InputStream is, int sheetIndexToPrint) {
        try {
        	wb = WorkbookFactory.create(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet_tmp;
        System.err.println();
        System.err.println("Use parameter -p,--print_sheet to print contents of one sheet. Sheet Name of this workbook:");
        for (int i = 0; i <wb.getNumberOfSheets(); i++)
        {
        	sheet_tmp = wb.getSheetAt(i);
        	System.err.print(i+". "+sheet_tmp.getSheetName()+"\t");
        }
        System.err.println();
        if(sheetIndexToPrint!=-1){
        	printRow(wb.getSheetAt(sheetIndexToPrint));
        }
    	
	}


	public void printRow(Sheet sheet){
		for (Row row :sheet)
		{
			for (Cell cell :row)
			{
				switch (cell.getCellType()) {
					case STRING:
						System.out.print(cell.getRichStringCellValue().getString().trim()+"\t");
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							System.out.print(cell.getDateCellValue()+"\t");
						} else {
							System.out.print(cell.getNumericCellValue()+"\t");
						}
						break;
					case BLANK:
						System.out.print("BLANK");
						break;
					case BOOLEAN:
						System.out.print(cell.getBooleanCellValue()+"\t");
						break;
					case FORMULA:
						System.out.print(cell.getCellFormula()+"\t");
						break;
					case ERROR:
						System.out.print("ERROR");
						break;
					default:
						System.out.print("null");
				}

			}
			System.out.println();
		}
	}

}
