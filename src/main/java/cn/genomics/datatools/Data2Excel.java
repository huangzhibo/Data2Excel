package cn.genomics.datatools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data2Excel {
	
	public static Parameter parameter;

	public static void main(String[] args) throws IOException {
		parameter = new Parameter(args);

		if(parameter.getFirstInfile().endsWith(".xlsx") || parameter.getInfiles()[0].endsWith(".xlsm") || parameter.getFirstInfile().endsWith(".xls"))
		{
			ExcelReader xlsfile = new ExcelReader();
			xlsfile.readExcel(new FileInputStream(new File(parameter.getFirstInfile())),parameter.getSheetIndexToPrint());
		}else {
			XSSFWorkbook wb = new XSSFWorkbook();
			WriteToSheet wts;
			if(parameter.getFormatFile() != null){
				MySheetFormat mySheetFormat = new MySheetFormat(parameter.getFormatFile());
				wts = new WriteToSheet(wb, mySheetFormat);
			}else
				wts = new WriteToSheet(wb);
			wts.setRowColor(parameter.isRowColor());

			for(int i = 0; i < parameter.getInfiles().length; i++)
			{
				System.out.println(parameter.getInfiles()[i]+"\t"+"writing...");
				TextReader textReader = new TextReader(parameter.getInfiles()[i]);
				if(parameter.getSheetNames() != null && parameter.getSheetNames()[i] != null)
				{
					wts.writeToSheet(textReader.getDataArray(), textReader.max_col, parameter.getSheetNames()[i]);
				}else
				{
					wts.writeToSheet(textReader.getDataArray(), textReader.max_col);
				}
			}
			FileOutputStream fileOut = new FileOutputStream(parameter.getOutfile());
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		}
	}
}
