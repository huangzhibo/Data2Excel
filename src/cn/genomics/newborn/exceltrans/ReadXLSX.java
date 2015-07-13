package cn.genomics.newborn.exceltrans;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXLSX {
	
    private XSSFWorkbook wb;
    private XSSFSheet sheet;

	
	public void readExcel(InputStream is) {
        try {
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i <wb.getNumberOfSheets(); i++)
//        {
        	sheet = wb.getSheetAt(0);
        	for (Row row :sheet)
        	{
        		for (Cell cell :row)
        		{
//        			CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());  
//        	        System.out.print(cellRef.formatAsString());                                         
//        	        System.out.print(" - ");                                                            
        	                                                                                            
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
        		
        		
        		
//        		int colNum = row.getPhysicalNumberOfCells();
//        		for (int j = 0; j < colNum; j++) {
//        			
//        		}
        		
//        	}
        }
	}

}
