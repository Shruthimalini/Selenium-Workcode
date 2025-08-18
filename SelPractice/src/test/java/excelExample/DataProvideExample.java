package excelExample;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProvideExample {
	@Test(dataProvider="driveTest")
		public void testCaseData(String greetings,String communication,int id) {
			System.out.println(greetings+" "+communication+" "+id);
			
		}
		@DataProvider(name="driveTest")
			public Object[][] getdata()
			{
				Object[][] data= {{"hello","test",1},{"bye","message",143}};
				return data;
			}
		
	}


