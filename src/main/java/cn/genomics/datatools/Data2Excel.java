package cn.genomics.datatools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data2Excel {
	
	public static Parameter parameter;

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		
		parameter = new Parameter(args);

		 int in_excel = -2;
    	 String outfile = null, sheet_name = null;
    	 String[] infiles = new String[20];
		 
		if(infiles[0].endsWith(".xlsx") || infiles[0].endsWith(".xlsm"))
		{
			ReadXLSX excelfile = new ReadXLSX();
			excelfile.readExcel(new FileInputStream(new File(infiles[0])),parameter.getSheetIndexToPrint());
			System.exit(0);			
		}else if(infiles[0].endsWith(".xls")){
			ReadXLS xlsfile = new ReadXLS();
			xlsfile.readExcel(new FileInputStream(new File(infiles[0])),parameter.getSheetIndexToPrint());
			System.exit(0);
		}
		
		XSSFWorkbook wb = new XSSFWorkbook();		

		XSSFFont headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		
		CellStyle textStyle = wb.createCellStyle();
		textStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		textStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFont(headerFont);
		
		//set sheet name
		String[] sheetNameArr = new String[20];
		if(parameter.getSheetName() != null)
		{
			sheetNameArr = parameter.getSheetName().split(",");
		}
		
		for(int n = 0;infiles[n]!=null;n++)
		{
			System.out.println(infiles[n]+"\t"+"writing...");
			XSSFSheet sheet;
			if(sheet_name != null && sheetNameArr[n] != null)
			{
				sheet = wb.createSheet(sheetNameArr[n]);		
			}else
			{
				sheet = wb.createSheet();
			}
//			sheet.setDefaultRowHeight((short)(1.2*256));
			if(in_excel == -2)
				writeToSheet(sheet,infiles[n],headerStyle, textStyle);
			else if(-1 == writeToSheet(sheet,infiles[n],headerStyle, textStyle,in_excel))
				writeToSheet(sheet,infiles[n],headerStyle, textStyle);
			
		}
		FileOutputStream fileOut = new FileOutputStream(outfile);
		wb.write(fileOut);
		wb.close();
		fileOut.close();
	}
	
	public static int checkInExcel(String[] strArray)
	{
		for (int j = 0; j < strArray.length; j++) {
			strArray[j].trim();
			if(strArray[j].compareTo("InExcel") == 0)
				return j;
		}
		return -1;
	}

	public static void outLine2Row(String[] strArr, XSSFRow row) {
		for (int j = 0; j < strArr.length; j++) {

			XSSFCell cell = row.createCell(j);
			cell.setCellValue(strArr[j].trim());
//			col_width[j] = col_width[j] >= strArr[j].length() ? col_width[j] : strArr[j].length();
		}
		
	}
	public static void outLine2Row(String[] strArr, XSSFRow row, CellStyle style) {
		for (int j = 0; j < strArr.length; j++) {
			
			XSSFCell cell = row.createCell(j);
			cell.setCellValue(strArr[j].trim());
			cell.setCellStyle(style);
//			col_width[j] = col_width[j] >= strArr[j].length() ? col_width[j] : strArr[j].length();
		}
	}
	
	public static void deleteArrElement(String[] strArray,String[] strArr, int index) {
		int len = strArray.length,n=0;
		for (int i = 0; i < len; i++) {
			if(i == index) continue;
			strArr[n++] = strArray[i];
		}
	}

	public static void setColumnFormat(XSSFSheet sheet, String[] header, ReadText myData)
	{
		Map <String,int[]> format = new HashMap <String,int[]>(); 
		if(parameter.getFormatFile() != null) format = myData.readFormatSet(parameter.getFormatFile());
		short[] groupRegionList = new short[100];
		short[] groupRegion = new short[]{-1,-1};
		short[] groupRegionLevel = new short[]{-1,-1};
		int regionIndex = 0;
		for(int j=0;j<header.length;j++)
		{
			if(!format.containsKey('"'+header[j]+'"')) 
			{
				continue;				
			}
			
			sheet.setColumnWidth(j,format.get('"'+header[j]+'"')[0]*256);
			if(format.get('"'+header[j]+'"')[1] == 1 )
			{
				if(groupRegion[1]==-1){
					if(groupRegion[0] == -1) groupRegion[0] = (short)j; 
					else groupRegion[1] = (short)j;
				}
				else if((j-groupRegion[1]) == 1){
					groupRegion[1] = (short)j;
				}
				else{
					groupRegionList[regionIndex++] = groupRegion[0];
					groupRegionList[regionIndex++] = groupRegion[1];
					groupRegion[0] = (short)j;
					groupRegion[1] = (short)j;
				}
			}
			
			//level 
			if(format.get('"'+header[j]+'"')[2] == 2 )
			{
				if(groupRegionLevel[1]==-1){
					if(groupRegionLevel[0] == -1) groupRegionLevel[0] = (short)j; 
					else groupRegionLevel[1] = (short)j;
				}
				else if((j-groupRegionLevel[1]) == 1){
					groupRegionLevel[1] = (short)j;
				}
				else{
					groupRegionList[regionIndex++] = groupRegionLevel[0];
					groupRegionList[regionIndex++] = groupRegionLevel[1];
					groupRegionLevel[0] = (short)j;
					groupRegionLevel[1] = (short)j;
				}
			}
			
		}
		if(groupRegion[1] != -1){
			groupRegionList[regionIndex++] = groupRegion[0];
			groupRegionList[regionIndex++] = groupRegion[1];
		}
		if(groupRegionLevel[1] != -1){
			groupRegionList[regionIndex++] = groupRegionLevel[0];
			groupRegionList[regionIndex++] = groupRegionLevel[1];
		}
		
		for(int k=0;k<regionIndex;k+=2)
		{
//			System.out.println(groupRegionList[k]+"\t"+groupRegionList[k+1]);
			sheet.groupColumn(groupRegionList[k], groupRegionList[k+1]);
//			sheet.setColumnGroupCollapsed((short)0, false);
		}
	}
	
	public static void writeToSheet(XSSFSheet sheet, String infile, CellStyle headerStyle, CellStyle textStyle){
		int n = 0, col_num = 0;
		ReadText myData = new ReadText();		
		Iterator<String[]> i = myData.readDataToLineArray(infile);
//		Map <String,int[]> format = new HashMap <String,int[]>();
//		if(formatfile != null) format = myData.readFormatSet(formatfile);
		String[] header = new String[myData.max_col];
		boolean hasHeader = false;

		while (i.hasNext()) {
			
			String[] lineArr = i.next();
			if (lineArr[0].startsWith("##")) continue;			
			col_num = lineArr.length;
			XSSFRow row = null;
			
			if( col_num == 1)
			{
				CellRangeAddress r_merge = new CellRangeAddress(n, n, 0, myData.max_col-1);
				sheet.addMergedRegion(r_merge);
			}

			if (lineArr[0].startsWith("#")) {
				lineArr[0] = lineArr[0].substring(1);
				header = lineArr;
				hasHeader = true;
				row = sheet.createRow(n++);
				outLine2Row(lineArr, row, headerStyle);
				CellRangeAddress range = new CellRangeAddress(0, n - 1, 0, col_num - 1);
				sheet.setAutoFilter(range);
				sheet.createFreezePane(0,1);
				continue;
			}
			
			row = sheet.createRow(n++);
			if (parameter.isNoColor() || n % 2 != 0)
				outLine2Row(lineArr, row);
			else
				outLine2Row(lineArr, row, textStyle);
		}
		if(hasHeader && parameter.getFormatFile() != null)		
			setColumnFormat(sheet,header,myData);
		
	}
	
	public static int writeToSheet(XSSFSheet sheet, String infile, CellStyle headerStyle, CellStyle textStyle, int in_excel){
		int n = 0;
		ReadText myData = new ReadText();		
		Iterator<String[]> i = myData.readDataToLineArray(infile);
		String[] header = new String[myData.max_col];
		boolean hasHeader = false;
		
		while (i.hasNext()) {
			String[] lineArr = i.next();
			if (lineArr[0].startsWith("##")) continue;			
			String[] strArr = new String[lineArr.length-1];
			XSSFRow row = null;
			
			if (lineArr[0].startsWith("#")) {
				if(in_excel == -1 )
					in_excel = checkInExcel(lineArr);
				lineArr[0] = lineArr[0].substring(1);
				row = sheet.createRow(n++);
				deleteArrElement(lineArr,strArr,in_excel);
				outLine2Row(strArr, row, headerStyle);
				CellRangeAddress range = new CellRangeAddress(0, n - 1, 0, myData.max_col - 1);
				sheet.setAutoFilter(range);
				sheet.createFreezePane(0,1);
				
				header = strArr;
				hasHeader = true;
				continue;
			}
			
			if( in_excel == -1 ) return -1;
			int bool = Integer.parseInt(lineArr[in_excel]);
			if(bool == 1){
				deleteArrElement(lineArr,strArr,in_excel);
				row = sheet.createRow(n++);
				if (parameter.isNoColor() || n % 2 != 0)
					outLine2Row(strArr, row);
				else
					outLine2Row(strArr, row, textStyle);
			}
			else if( bool != 0 ){
				System.err.println("The inExcel column has an error!");
				System.exit(1);
			}
			
		}
		
		if(hasHeader && parameter.getFormatFile() != null) 		
			setColumnFormat(sheet,header,myData);
//		else{
//			for(int j=0;j<myData.max_col;j++)
//			{
//				sheet.setColumnWidth(j, col_width[j]*256);
//			}
//		}
		return 0;
		
	}
	
	

}
