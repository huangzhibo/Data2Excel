package cn.genomics.newborn.exceltrans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class ReadText {
	
	int max_line = 0;
	int max_col = 0; 
	int[] col_width = new int[100];

	public Iterator<String> readDataToLineStr(String filename) {
		List<String> data = new ArrayList<String>();
		try {
			File file = new File(filename);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputReader = null;

			if (filename.endsWith(".gz")) {
				inputReader = new InputStreamReader(new GZIPInputStream(
						inputStream));
			} else {
				inputReader = new InputStreamReader(inputStream);
			}
			BufferedReader bufferReader = new BufferedReader(inputReader);

			String str;
			while ((str = bufferReader.readLine()) != null) {
				data.add(str);
				max_line++;
				
				String[] strArray = str.split("\t");
				for(int j=0;j<strArray.length;j++)
				{
					col_width[j] = strArray[j].length();
					
				}
				
				max_col = max_col < strArray.length ? strArray.length : max_col;
			}
			bufferReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Iterator<String> i = data.iterator();
		return i;
	}
	
	public Iterator<String[]> readDataToLineArray(String filename) {
		List<String[]> data = new ArrayList<String[]>();
		try {
			File file = new File(filename);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputReader = null;

			if (filename.endsWith(".gz")) {
				inputReader = new InputStreamReader(new GZIPInputStream(
						inputStream));
			} else {
				inputReader = new InputStreamReader(inputStream);
			}
			BufferedReader bufferReader = new BufferedReader(inputReader);

			String line;
			while ((line = bufferReader.readLine()) != null) {
				line.trim();
				String[] strArray = line.split("\t");
				data.add(strArray);
				for(int j=0;j<strArray.length;j++)
				{
					col_width[j] = strArray[j].length();
				}
				
				max_col = max_col < strArray.length ? strArray.length : max_col;
				max_line++;
			}
			bufferReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data.iterator();

	}
	
	public Map <String,String[]> readFormatSet(String filename) {
		Map <String,String[]> format = new HashMap <String,String[]>();
		try {
			File file = new File(filename);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputReader = null;

			if (filename.endsWith(".gz")) {
				inputReader = new InputStreamReader(new GZIPInputStream(
						inputStream));
			} else {
				inputReader = new InputStreamReader(inputStream);
			}
			BufferedReader bufferReader = new BufferedReader(inputReader);

			String str;
			while ((str = bufferReader.readLine()) != null) {
				if (str.startsWith("#"))
					continue;
				String[] strArray  = str.split("\t");
				format.put(strArray[0], strArray);
			}
			bufferReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return format;

	}
	
	

}
