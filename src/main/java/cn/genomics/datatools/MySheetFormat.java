package cn.genomics.datatools;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 可在header之前设置行宽
 * ## DefaultRowHeight = 400
 * ##
 * 共4列
 * 字段名  数据类型  列宽  是否写入到Excel
 * fieldName type ColumnWidth InExcel
 */
public class MySheetFormat {

    short defaultRowHeight = 400;
    Map <String,MyColumnFormat> format = null;

    public MySheetFormat() {
        format = new HashMap<>();
    }

    public MySheetFormat(String formatFile) {
        init(formatFile);
    }

    public void init(String filename) {
        this.format = new HashMap<>();
        try {

            File file = new File(filename);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferReader.readLine()) != null) {
                if (line.startsWith("#")){
                    if(line.contains("DefaultRowHeight"))
                        setDefaultRowHeight(line);
                    continue;
                }
                String[] strArray  = line.trim().split("\t");
                MyColumnFormat myColumnFormat = new MyColumnFormat(strArray[0], strArray[1], strArray[2], strArray[3]);
                format.put(strArray[0], myColumnFormat);
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultRowHeight(String line) {
        String[] fields = line.replaceAll("#", "").trim().split("=");
        if(fields[0].trim().equalsIgnoreCase("DefaultRowHeight")){
            this.defaultRowHeight = Short.parseShort(fields[1].trim());
        }
    }

    public void setDefaultRowHeight(short defaultRowHeight) {
        this.defaultRowHeight = defaultRowHeight;
    }

    public short getDefaultRowHeight() {
        return defaultRowHeight;
    }
}
