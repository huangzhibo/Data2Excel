package cn.genomics.newborn.exceltrans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class ReadTXT {
	
	int max_line = 0;
	int max_col = 0; 

	public List<String> readData(String filename) {
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
			}
			bufferReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<String> i = data.iterator();
		while(i.hasNext())
		{
			 String line = i.next();
			 String[] strArray = line.split("\t");
			 max_col = max_col < strArray.length ? strArray.length : max_col;
		// System.out.println(line);
		 }
		return data;

	}

}
