package cn.genomics.newborn.exceltrans;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data2Excel {

	static boolean no_color = false;
	
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		LongOpt[] longopts = new LongOpt[10];
		longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
		longopts[1] = new LongOpt("infile", LongOpt.REQUIRED_ARGUMENT, null,'i');
		longopts[2] = new LongOpt("outfile", LongOpt.REQUIRED_ARGUMENT, null,'o');
		longopts[3] = new LongOpt("in_excel_col", LongOpt.OPTIONAL_ARGUMENT, null, 'e');
		longopts[4] = new LongOpt("no_color", LongOpt.NO_ARGUMENT, null, 'c');
		longopts[5] = new LongOpt("sheet_name", LongOpt.REQUIRED_ARGUMENT, null, 's');
		

		 int c , in_excel = -2 ,infile_num = 0;
    	 String arg, outfile = null, sheet_name = null;
    	 String[] infiles = new String[10];
		 Getopt g = new Getopt("Data2Excel", args, "-:i:o:s:c:e:h:t::", longopts);
		 if(args.length==0)	usage();
		 
		 while ((c = g.getopt()) != -1)
		 {
			 switch(c)
			 {
				 case 'i': 
					 infiles[infile_num++] = g.getOptarg();break;
				 case 'o':
					 outfile = g.getOptarg();break;
				 case 's':
					 sheet_name = g.getOptarg();break;
				 case 'e':
					 if(g.getOptarg() == null) in_excel = -1;
					 else in_excel = Integer.parseInt(g.getOptarg());break;
				 case 't':
					 arg = g.getOptarg();
					 System.out.print("You picked " + (char)c + " with an argument of "+((arg != null) ? arg : "null") + "\n");
					 break;
				 case 'c':
					 no_color = true;break;
				 case 'h':
					 usage();
			 }			 
		 }
		 
		if(infiles.length == 0) usage();
		if(outfile == null)  outfile = infiles[0]+".xlsx";
		else if(!outfile.endsWith(".xlsx"))	 outfile = outfile+".xlsx";
		
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
		String[] sheetNameArr = new String[10];
		if(sheet_name != null)
		{
			sheetNameArr = sheet_name.split(",");
		}
		
		for(int n = 0;infiles[n]!=null;n++)
		{
			XSSFSheet sheet;
			if(sheet_name != null && sheetNameArr[n] != null)
			{
				sheet = wb.createSheet(sheetNameArr[n]);		
			}else
			{
				sheet = wb.createSheet();
			}
			System.out.println(infiles[n]);
			if(in_excel == -2)
				writeToSheet(sheet,infiles[n],headerStyle, textStyle);
			else
				writeToSheetInExcel(sheet,infiles[n],headerStyle, textStyle,in_excel);
			
		}
		FileOutputStream fileOut = new FileOutputStream(outfile);
		wb.write(fileOut);
		wb.close();
		fileOut.close();
}
	private static void usage() {
		System.out.println();
		System.out.println("Data2Excel version 0.2");
		System.out.println("Author: huangzhibo@genomics.cn");
		System.out.println("Note  : Tools for transform plaintext file into Excelfile(.xlsx)");
		System.out.println("		Please ensure your java(JDK) version is later than 1.6.0.18");
		System.out.println();
		System.out.println("Usage : Data2Excel <options...>");
		System.out.println("\t-i, --infile\t\tInput plaintext file. [required]");
		System.out.println("\t-o, --outfile\t\tOutput Excel file. [infile.xlsx]");
		System.out.println("\t-s, --sheet_name\t\tThe sheet name. [no]");
		System.out.println("\t-e, --in_excel_col\tIn_Excel column index (0-base),default:check header for 'In_Excel'. [auto]");
		System.out.println("\t-h, --help\t\tPrint this help.");
		System.out.println();
		System.exit(0);
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
		}

	}
	
	public static void deleteArrElement(String[] strArray,String[] strArr, int index) {
		int len = strArray.length,n=0;
		for (int i = 0; i < len; i++) {
			if(i == index) continue;
			strArr[n++] = strArray[i];
		}
	}

	public static void outLine2Row(String[] strArr, XSSFRow row, CellStyle style) {
		for (int j = 0; j < strArr.length; j++) {

			XSSFCell cell = row.createCell(j);
			cell.setCellValue(strArr[j].trim());
			cell.setCellStyle(style);
		}
	}
	
	public static void writeToSheet(XSSFSheet sheet, String infile, CellStyle headerStyle, CellStyle textStyle){
		int n = 0, col_num = 0;
		ReadText myData = new ReadText();		
		Iterator<String[]> i = myData.readDataToLineArray(infile);

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
				row = sheet.createRow(n++);
				outLine2Row(lineArr, row, headerStyle);
				CellRangeAddress range = new CellRangeAddress(0, n - 1, 0, col_num - 1);
				sheet.setAutoFilter(range);
				sheet.createFreezePane(0,1);
				continue;
			}
			
			row = sheet.createRow(n++);
			if (no_color || n % 2 != 0)
				outLine2Row(lineArr, row);
			else
				outLine2Row(lineArr, row, textStyle);
		}
		
		
		
	}
	
	public static void writeToSheetInExcel(XSSFSheet sheet, String infile, CellStyle headerStyle, CellStyle textStyle, int in_excel){
		int n = 0;
		ReadText myData = new ReadText();		
		Iterator<String[]> i = myData.readDataToLineArray(infile);
		
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
				continue;
			}
			
			int bool = Integer.parseInt(lineArr[in_excel]);
			if(bool == 1){
				deleteArrElement(lineArr,strArr,in_excel);
				row = sheet.createRow(n++);
				if (no_color || n % 2 != 0)
					outLine2Row(strArr, row);
				else
					outLine2Row(strArr, row, textStyle);
			}
			else if( bool != 0 ){
				System.err.println("The inExcel column has an error!");
				System.exit(1);
			}
			
		}
	}
	
	

}
