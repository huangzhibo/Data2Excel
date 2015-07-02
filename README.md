# Data2Excel
Transfer plain text data to Excel (Java code) 

Data2Excel version 0.2  
Author: huangzhibo@genomics.cn  
Data  : 2015-7-2  
Note  : Tools for transform plain text file into Excelfile(.xlsx)  
&ensp;&ensp; Please ensure your java(JDK) version is later than 1.6.0.18   

Usage : java -jar Data2Excel_v0.2.jar <options...>  

	-i, --infile      	<String>	Input plain text files. Support multiple files input(example："-i file1 -i file2"). [required]
	-o, --outfile     	<String>	Output Excel file, multi input will be writed into different sheets in the same workbook. [infile.xlsx]
	-s, --sheet_name  	<String>	To set sheet name. When have more than one files, you need use it as "-s name1,name2". [no]
	-f, --format      	<String>	The format file to set sheet column style. [no]
	-c, --no_color    	        	To close the color display in the output file. [no]
	-e, --in_excel_col	<int>   	In_Excel column index (0-base). Use '--in_excel_col' without argument will check 'In_Excel' in header line. [no]
	-h, --help        	        	Print this help.
