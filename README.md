# Data2Excel
Transfer plain text data to Excel (Java code) 

Data2Excel version 0.2  
Author: huangzhibo@genomics.cn  
Data  : 2015-7-2  
Note  : Tools for transform plain text file into Excelfile(.xlsx)  
&ensp;&ensp; Please ensure your java(JDK) version is 1.8.0 or later   

Usage : java -jar Data2Excel_v0.2.jar <options...>  

	-i, --infile      	<File>  	Input plain text files. Support multiple files input(example："-i file1 -i file2"). [required]
	-o, --outfile     	<File>  	Output Excel file, multi input will be writed into different sheets in the same workbook. [file1.xlsx]
	-f, --format      	<File>  	The format file to set sheet column style. [not using]
	-s, --sheet_name  	<String>	To set sheet name. When have more than one files, you need use it as "-s name1,name2". [not using]
	-c, --no_color    	        	To close the color display in the output file. [not using]
	-e, --in_excel_col	<int>   	In_Excel column index (0-base). Use it without argument will check 'In_Excel' in header line. [not using]
	-h, --help        	        	Print this help.
