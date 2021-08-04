package cn.genomics.datatools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class TextReader {
	int max_line = 0;
	int max_col = 0;
	int min_col = Integer.MAX_VALUE;
	List<String[]> dataArray;

	public TextReader(String filename) {
		this.dataArray = new ArrayList<>();
		readDataToLineArray(filename);
	}

	public void readDataToLineArray(String filename) {
		try {
			File file = new File(filename);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputReader;

			if (filename.endsWith(".gz")) {
				inputReader = new InputStreamReader(new GZIPInputStream(inputStream));
			} else {
				inputReader = new InputStreamReader(inputStream);
			}
			BufferedReader bufferReader = new BufferedReader(inputReader);

			String line;
			while ((line = bufferReader.readLine()) != null) {
				String[] strArray = line.trim().split("\t");
				dataArray.add(strArray);
				max_col = max_col < strArray.length ? strArray.length : max_col;
				min_col = min_col > strArray.length ? strArray.length : max_col;
				max_line++;
			}
			bufferReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getMax_line() {
		return max_line;
	}

	public int getMax_col() {
		return max_col;
	}

	public int getMin_col() {
		return min_col;
	}

	public List<String[]> getDataArray() {
		return dataArray;
	}
}
