package cn.genomics.datatools;

import org.junit.Assert;
import org.junit.Test;

public class Data2ExcelTest {

	@Test
	public void test() {
		String[] s= new String[2];
		s[0] = "s";
		s[1]= "d";
		 Assert.assertEquals(Data2Excel.checkInExcel(s),-1);
	}

}
