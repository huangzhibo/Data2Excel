package cn.genomics.datatools.fileiterator;

import cn.genomics.datatools.utils.Gpr;


public class XLSFileIterator extends FileIterator<String> {
	
	public static boolean debug = false;

	public XLSFileIterator(String in, boolean gzip) {
		super(in);
		line = null;
		lineNum = 0;
		reader = null;
		reader = Gpr.reader(in, gzip);
	}

	

	@Override
	protected String readNext() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
