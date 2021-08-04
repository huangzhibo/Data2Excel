package cn.genomics.datatools;

/**
 * 共4列
 * 字段名  数据类型  列宽  是否写入到Excel
 * fieldName type ColumnWidth InExcel
 */
public class MyColumnFormat {
    String name;
    String valueType;
    int columnWidth;
    boolean inExcel;

    public MyColumnFormat(String name, String valueType, String columnWidth, String inExcel) {
        this.name = name;
        this.valueType = valueType;
        setColumnWidth(columnWidth);
        setInExcel(inExcel);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(String columnWidth) {
        this.columnWidth = Integer.parseInt(columnWidth);;
    }

    public boolean getInExcel() {
        return inExcel;
    }

    public void setInExcel(String inExcel) {
        this.inExcel = Boolean.parseBoolean(inExcel);;
    }
}