package cn.genomics.bgitools.exceltrans;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

public class ReadXLS {
	
    private HSSFWorkbook wb;
	
	public void readExcel(InputStream is, int sheetIndexToPrint) {
        try {
            wb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheet_tmp;
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
	
	public void printRow(HSSFSheet sheet){
		for (Row row :sheet)
    	{
    		for (Cell cell :row)
    		{
    	        switch (cell.getCellType()) {                                                       
    	            case Cell.CELL_TYPE_STRING:                                                     
    	                System.out.print(cell.getRichStringCellValue().getString().trim()+"\t");              
    	                break;                                                                      
    	            case Cell.CELL_TYPE_NUMERIC:                                                    
    	                if (DateUtil.isCellDateFormatted(cell)) {                                   
    	                    System.out.print(cell.getDateCellValue()+"\t");                            
    	                } else {                                                                    
    	                    System.out.print(cell.getNumericCellValue()+"\t");                         
    	                }                                                                           
    	                break;                                                                      
    	            case Cell.CELL_TYPE_BOOLEAN:                                                    
    	                System.out.print(cell.getBooleanCellValue()+"\t");                             
    	                break;                                                                      
    	            case Cell.CELL_TYPE_FORMULA:                                                    
    	                System.out.print(cell.getCellFormula()+"\t");                                  
    	                break;                                                                      
    	            default:                                                                        
    	                System.out.print("null");                                                       
    	        }                                                                                   
    			
    		}
    		System.out.println();
    	}
	}

}
