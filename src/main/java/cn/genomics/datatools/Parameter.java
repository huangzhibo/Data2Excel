package cn.genomics.datatools;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Parameter {
	
	final String SOFTWARE_NAME= "Data2Excel";
	final String SOFTWARE_VERSION_NUMBER = "0.4";
	final String LAST_UPDATE = "2016-10-11";
	
	private String filedSplitChar = "\t";
	private String[] infiles;
	private String outfile = null;
	private String sheetName = null;
	private int sheetIndexToPrint = -1;
	private boolean noColor = false;
	private String formatFile = null;
	
	Options options = new Options();
	CommandLine cmdLine;
	CommandLineParser parser = new DefaultParser();
	
	public Parameter(){}
	
	public Parameter(String[] args) {
		parse(args);
	}
	
	private String helpHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nVersion    : ");
		sb.append(SOFTWARE_VERSION_NUMBER);
		sb.append("\nAuthor     : huangzhibo@genomics.cn");
		sb.append("\nLast update: ");
		sb.append(LAST_UPDATE);
		sb.append("\nNote       : Read data from plain text file and write it into Excel\n");
		sb.append("\nOptions:\n");
		return sb.toString();
	}
	
	public void parse(String[] args) {
		String header = helpHeader();
		String footer = "\nPlease report issues at https://github.com/huangzhibo/Data2Excel/issues";
		
		options.addOption(Option.builder("i")
				.longOpt("input")
				.required(true)
				.hasArg()
				.argName("FILE")
				.desc("Input plain text files. Support multiple files input(example：\"-i file1 -i file2\"")
			    .build());
		options.addOption(Option.builder("o")
				.longOpt("output")
				.hasArg()
				.argName("FILE")
				.desc("Output Excel file, multi input will be writed into different sheets in the same workbook. [file1.xlsx]")
				.build());
		options.addOption(Option.builder("s")
				.longOpt("sheet_name")
				.hasArg()
				.argName("String")
				.desc("To set sheet name. When have more than one files, you need use it as \"-s name1,name2\". [not using]")
				.build());
		options.addOption(Option.builder("c")
				.longOpt("no_color")
				.desc("To close the color display in the output file. [not using]")
				.build());
		options.addOption(Option.builder("h")
				.longOpt("help")
				.desc("Print this help.")
				.build());
		options.addOption(Option.builder("F")
				.longOpt("field_split")
				.hasArg()
				.argName("String")
				.desc("The field split char in one line [\\t]")
				.build());
		options.addOption(Option.builder("f")
				.longOpt("format")
				.hasArg()
				.argName("FILE")
				.desc("The format file to set sheet column style.")
				.build());
		options.addOption(Option.builder("p")
				.longOpt("print_sheet")
				.hasArg()
				.argName("INT")
				.desc("The index(0-base) of Sheet to print. Be effective when the input is excel file. [print sheet name]")
				.build());
		
		
		HelpFormatter formatter = new HelpFormatter();
		formatter.setWidth(2 * HelpFormatter.DEFAULT_WIDTH);
		
		try {
			cmdLine = parser.parse(options, args);
			if(cmdLine.hasOption("h")) { 
				formatter.printHelp("java -jar Data2Excel.jar", header, options, footer, true);
				System.exit(0);
			}
		} catch (ParseException e) {
			formatter.printHelp("java -jar Data2Excel.jar", header, options, footer, true);
			System.exit(0);
		}
		
		infiles = cmdLine.getOptionValues("input");
		
		if(cmdLine.hasOption("output")){
			outfile = cmdLine.getOptionValue("output");
		}
		
		if(cmdLine.hasOption("sheet_name")){
			sheetName = cmdLine.getOptionValue("sheet_name");
		}
		
		if(cmdLine.hasOption("no_color")){
			noColor = true;
		}
		
		if(cmdLine.hasOption("field_split")){
			filedSplitChar = cmdLine.getOptionValue("field_split");
		}
		
		if(cmdLine.hasOption("format")){
			formatFile = cmdLine.getOptionValue("format");
		}
		
		if(cmdLine.hasOption("print_sheet")){
			sheetIndexToPrint = Integer.parseInt(cmdLine.getOptionValue("print_sheet"));
		}
	}

	public String[] getInfiles() {
		return infiles;
	}

	public void setInfiles(String[] infiles) {
		this.infiles = infiles;
	}

	public String getOutfile() {
		if(outfile == null)  outfile = infiles[0]+".xlsx";
		else if(!outfile.endsWith(".xlsx"))	 outfile = outfile+".xlsx";
		return outfile;
	}

	public void setOutfile(String outfile) {
		this.outfile = outfile;
	}

	public int getSheetIndexToPrint() {
		return sheetIndexToPrint;
	}

	public void setSheetIndexToPrint(int sheetIndexToPrint) {
		this.sheetIndexToPrint = sheetIndexToPrint;
	}

	public String getFiledSplitChar() {
		return filedSplitChar;
	}

	public void setFiledSplitChar(String filedSplitChar) {
		this.filedSplitChar = filedSplitChar;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public boolean isNoColor() {
		return noColor;
	}

	public void setNoColor(boolean noColor) {
		this.noColor = noColor;
	}

	public String getFormatFile() {
		return formatFile;
	}

	public void setFormatFile(String formatFile) {
		this.formatFile = formatFile;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String[] arg = {  "-i", "test.txt" , "-i", "test2.txt"};
		Parameter parameter = new Parameter();
		parameter.parse(arg);
		for (String file : parameter.getInfiles()) {
			System.out.println(file);
		}
	}
}
