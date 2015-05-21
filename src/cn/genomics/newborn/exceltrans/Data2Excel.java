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

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		LongOpt[] longopts = new LongOpt[10];
		longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
		longopts[1] = new LongOpt("infile", LongOpt.REQUIRED_ARGUMENT, null,'i');
		longopts[2] = new LongOpt("outfile", LongOpt.REQUIRED_ARGUMENT, null,'o');
		longopts[3] = new LongOpt("in_excel_col", LongOpt.REQUIRED_ARGUMENT, null, 'e');
		longopts[4] = new LongOpt("no_color_line", LongOpt.NO_ARGUMENT, null, 'c');
		longopts[5] = new LongOpt("sheet_name", LongOpt.REQUIRED_ARGUMENT, null, 's');
		

		 int c , in_excel = -1 , no_color_line = 0;
    	 String arg,infile = null, outfile = null, sheet_name = null;
		 Getopt g = new Getopt("Data2Excel", args, "-:i:o:s:c:e:h:t::", longopts);
		 if(args.length==0)	usage();
		 
		 while ((c = g.getopt()) != -1)
		 {
			 switch(c)
			 {
				 case 'i': 
					 infile = g.getOptarg();break;
				 case 'o':
					 outfile = g.getOptarg();break;
				 case 's':
					 sheet_name = g.getOptarg();break;
				 case 'e':
					 in_excel = Integer.parseInt(g.getOptarg());break;
					 
				 case 't':
					 arg = g.getOptarg();
					 System.out.print("You picked " + (char)c + " with an argument of "+((arg != null) ? arg : "null") + "\n");
					 break;
				 case 'c':
					 no_color_line = 1;
				 case 'h':
					 usage();break;
			 }			 
		 }
		 
		if(infile == null) usage();
		if(outfile == null)  outfile = infile+".xlsx";
		else if(!outfile.endsWith(".xlsx"))	 outfile = outfile+".xlsx";
		
		ReadTXT myData = new ReadTXT();		
		Iterator<String> i = myData.readData(infile).iterator();
		int max_col = myData.max_col;
		XSSFWorkbook wb = new XSSFWorkbook();		
		XSSFSheet sheet = wb.createSheet();
		if(sheet_name != null)
		{
			wb.setSheetName(0, sheet_name);
		}
		XSSFFont headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);

		CellStyle textStyle = wb.createCellStyle();
		textStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		textStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFont(headerFont);

		int n = 0, col_num = 0;

		while (i.hasNext()) {
			String line = i.next();
			line = line.trim();
			if (line.startsWith("##"))
				continue;			
			

			String[] strArray = line.split("\t");
			String[] strArr = new String[strArray.length-1];
			col_num = strArray.length;
			XSSFRow row = null;
			
			if( col_num == 1)
			{
				CellRangeAddress r_merge = new CellRangeAddress(n, n, 0, max_col-1);
				sheet.addMergedRegion(r_merge);
			}

			if (line.startsWith("#")) {
				if(in_excel == -2 )
					in_excel = checkInExcel(strArray);
				strArray[0] = strArray[0].substring(1);
				row = sheet.createRow(n++);
				
				if(in_excel != -1){
					deleteArrElement(strArray,strArr,in_excel);
					outLine2Row(strArr, row, headerStyle);
					col_num = strArr.length;
				}
				else{
					outLine2Row(strArray, row, headerStyle);
				}

				CellRangeAddress range = new CellRangeAddress(0, n - 1, 0, col_num - 1);
				sheet.setAutoFilter(range);
				sheet.createFreezePane(0,1);
				continue;
			}
			
			if(in_excel != -1){
				int bool = Integer.parseInt(strArray[in_excel]);
				if( bool == 0 ){
					continue;
				}
				else if(bool == 1){
					deleteArrElement(strArray,strArr,in_excel);
					row = sheet.createRow(n++);
					if (no_color_line != 1 && n % 2 == 0)
						outLine2Row(strArr, row, textStyle);
					else {
						outLine2Row(strArr, row);
					}
					continue;
				}
				else {
					System.err.println("The inExcel column has an error!");
					System.exit(1);
				}
					
			}
			
			row = sheet.createRow(n++);
			if (no_color_line != 1 && n % 2 == 0)
				outLine2Row(strArray, row, textStyle);
			else {
				outLine2Row(strArray, row);
			}
			

		}

//		
//		set col width & hidden
//		ReadTXT format = new ReadTXT();
//		Iterator<String> tsv = format.readData("").iterator();
		
		do {
			sheet.autoSizeColumn(col_num-1);
		} while ((col_num--) == 0);
		
		FileOutputStream fileOut = new FileOutputStream(outfile);
		wb.write(fileOut);
		wb.close();
		fileOut.close();

	}

	private static void usage() {
		System.out.println();
		System.out.println("Data2Excel version 0.1");
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

}
