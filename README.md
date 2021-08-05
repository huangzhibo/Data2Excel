[![Code Climate](https://codeclimate.com/github/huangzhibo/Data2Excel/badges/gpa.svg)](https://codeclimate.com/github/huangzhibo/Data2Excel)
[![Build Status](https://travis-ci.org/huangzhibo/Data2Excel.svg?branch=master)](https://travis-ci.org/huangzhibo/Data2Excel)
[![License](http://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/huangzhibo/Data2Excel)
[![Language](http://img.shields.io/badge/language-java-brightgreen.svg)](https://www.java.com/)
# Description
Data2Excel version 0.5  
Author: huangzhibo@genomics.cn  
Note  : transform plain text data to Excel (.xlsx)

# Building
- mvn assembly:assembly

# Usage
Usage : java -jar Data2Excel.jar \<options...\>  

	-c, --color    	        	        To close the color display in the output file. [not using]
	-i, --infile      	<File>  	Input plain text files. Support multiple files input(example："-i file1 -i file2"). [required]
	-o, --outfile     	<File>  	Output Excel file, multi input will be writed into different sheets in the same workbook. [file1.xlsx]
	-f, --format      	<File>  	The format file to set sheet column style. [not using]
	-s, --sheet_name  	<String>	To set sheet name. When have more than one files, you need use it as "-s name1,name2". [not using]
	-F, --split       	<String>	Split char. (example: ' -F "\t" ') [\t]
	-e, --in_excel_col	<int>   	In_Excel column index (0-base). Use it without argument will check 'In_Excel' in header line. [not using]
	-p, --print_sheet 	<int>   	Sheet index(0-base) to print. Effective when the input is excel file. [print sheet name]
	-h, --help        	        	Print this help.
