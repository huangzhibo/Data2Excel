package cn.genomics.datatools;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;


public abstract class Parameter {
	protected Options options = new Options();
	protected CommandLine cmdLine;
	protected CommandLineParser parser = new DefaultParser();
	protected HelpFormatter helpInfo = new HelpFormatter();


	/**
	 * help info must use it before parse.
	 */
	public void FormatHelpInfo(String softwareName, String version) {
		StringBuilder sb = new StringBuilder();
		if (softwareName != null) {
			sb.append("Software name: ");
			sb.append(softwareName);
		}
		sb.append("\nVersion: ");
		sb.append(version);
		sb.append("\nLast update: 2015.02.19\n");
		sb.append("Developed by: Bioinformatics core technology laboratory | Science and Technology Division | BGI-shenzhen\n");
		sb.append("Authors: Li ShengKang & Zhang Yong\n");
		sb.append("E-mail: zhangyong2@genomics.org.cn or lishengkang@genomics.cn\n");
		sb.append("Copyright(c) 2015: BGI. All Rights Reserved.\n\n");
		helpInfo.setNewLine("\n");
		if(softwareName == null)
			softwareName = "tools_name";
		helpInfo.setSyntaxPrefix("hadoop jar " + "gaea.jar " + softwareName
				+ " [options]\n" + sb.toString() + "\n");
		helpInfo.setWidth(2 * HelpFormatter.DEFAULT_WIDTH);
	}

	protected void printHelpInfotmation(String softwareName) {
		helpInfo.printHelp(softwareName + " options list:", options);
	}
	
	public Parameter(String[] args) {
		parse(args);
	}
	
	
	private static void usage() {
		System.out.println();
		System.out.println("Data2Excel version 0.3");
		System.out.println("Author: huangzhibo@genomics.cn");
		System.out.println("Date  : 2015-7-16");
		System.out.println("Note  : Tools for transform plain text file into Excelfile(.xlsx)");
		System.out.println();
		System.out.println("Usage : java -jar Data2Excel_v0.3.jar <options...>");
		System.out.println("\t-i, --infile      \t<File>  \tInput plain text files. Support multiple files input(example：\"-i file1 -i file2\"). [required]");
		System.out.println("\t-o, --outfile     \t<File>  \tOutput Excel file, multi input will be writed into different sheets in the same workbook. [file1.xlsx]");
		System.out.println("\t-f, --format      \t<File>  \tThe format file to set sheet column style. [not using]");
		System.out.println("\t-s, --sheet_name  \t<String>\tTo set sheet name. When have more than one files, you need use it as \"-s name1,name2\". [not using]");
		System.out.println("\t-F, --split       \t<String>\tSplit char. (example: ' -F \"\\t\" ') [\\t]");
		System.out.println("\t-c, --no_color    \t        \tTo close the color display in the output file. [not using]");
		System.out.println("\t-e, --in_excel_col\t<int>   \tIn_Excel column index (0-base). Use it without argument will check 'In_Excel' in header line. [not using]");
		System.out.println("\t-p, --print_sheet \t<int>   \tThe index(0-base) of Sheet to print. Be effective when the input is excel file. [print sheet name]");
		System.out.println("\t-h, --help        \t        \tPrint this help.");
		System.out.println();
		System.exit(0);
	}
	

	
	public void parse(String[] args) {
		
		addOption("i", "input",      true,  "Input plain text files. Support multiple files input(example：\"-i file1 -i file2\"", true);
		addOption("o", "output",     true,  "Output Excel file, multi input will be writed into different sheets in the same workbook. [file1.xlsx]", true);
		addOption("s", "sheet_name", true,  "To set sheet name. When have more than one files, you need use it as \"-s name1,name2\". [not using]", true);
		addOption("c", "no_color",   true,  "config file.", true);
		addOption("F", "field_split",  true,  "The field split char in one line [\t]", true);
		addOption("f", "format", 	 true,  "The format file to set sheet column style.");
		addOption("p", "print_sheet", 	 true,  "The index(0-base) of Sheet to print. Be effective when the input is excel file. [print sheet name]");
		addOption(null,"verbose",    false, "display verbose information.");
		addOption(null,"debug",      false, "for debug.");
		addOption("h", "help",       false, "help information.");
		FormatHelpInfo("GaeaAnnotator", "0.0.1-SNAPSHOT");
		
		try {
			cmdLine = parser.parse(options, args);
			if(cmdLine.hasOption("h")) { 
				helpInfo.printHelp("test", options);
				System.exit(0);
			}
		} catch (ParseException e) {
			helpInfo.printHelp("Options:", options, true);
			System.exit(0);
		}

	}
	
	/**
	 * add normal option.
	 * 
	 * @param opt
	 * @param longOpt
	 * @param hasArg
	 * @param description
	 */
	protected void addOption(String opt, String longOpt, boolean hasArg,
			String description) {
		addOption(opt, longOpt, hasArg, description, false);
	}
	
	/**
	 * add required option
	 */
	protected void addOption(String opt, String longOpt, boolean hasArg,
			String description, boolean required) {
		Option option = new Option(opt, longOpt, hasArg, description);
		option.setRequired(required);
		options.addOption(option);
	}

}
