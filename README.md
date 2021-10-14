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
usage: java -jar Data2Excel.jar [-c] [-F <String>] [-f <FILE>] [-h] -i <FILE> [-o <FILE>] [-p <INT>] [-s <String>]


    -c,--color                  To open the color display in the output file. [not using]
    -F,--field_split <String>   The field split char in one line [\t]
    -f,--format <FILE>          The format file to set sheet column style. [not using]
    -h,--help                   Print this help.
    -i,--input <FILE>           Input plain text files. Support multiple files input(example："-i file1 -i file2")
    -o,--output <FILE>          Output Excel file, multi input will be writed into different sheets in the same workbook. [file1.xlsx]
    -p,--print_sheet <INT>      The index(0-base) of Sheet to print. Be effective when the input is excel file. [print sheet name]
    -s,--sheet_name <String>    To set sheet name. When have more than one files, you need use it as "-s name1,name2". [not using]


## format file (.tsv)
Must be tab split file.

```
#DefaultRowHeight = 400
#fieldName      type    ColumnWidth     InExcel
row_name1     STRING  6000    true
row_name2     INT     4000    true
row_name3     INT     4000    true
```
