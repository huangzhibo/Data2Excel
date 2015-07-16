package cn.genomics.newborn.exceltrans;

public class GlobleDefined {
	private static final int MAX_COLUMN = 200;
	private static String SPLIT_CHAR = "\t";
	
	public static void setSplitChar(String split_char)
	{
		System.out.println(split_char);
		SPLIT_CHAR = split_char;
	}
	
	public static String getSplitChar()
	{
		return SPLIT_CHAR;
	}
	
	public static int getMaxColumn()
	{
		return MAX_COLUMN;
	}
}
