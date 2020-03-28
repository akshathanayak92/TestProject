package testData;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import utils.BaseClass;

public class dataProviderClass_TestData extends BaseClass{

	@DataProvider (name="mmtAndYatraTest")
	public  static Object[][] mmtAndYatraTest() {
		Object[][] mmtAndYatraTest=new Object[1][8];
		//String testID, String url1, String url2,String fromPlace1, String fromPlace2, String toPlace, String departureFromTime, String arrivalAtTime
		mmtAndYatraTest[0][0]="Travel_mmt_yatra_Test";   			//testID
		mmtAndYatraTest[0][1]="https://www.makemytrip.com/";    	//url1
		mmtAndYatraTest[0][2]="https://www.yatra.com/";  			//url2      
		mmtAndYatraTest[0][3]="Bengaluru";							//fromPlace1    
		mmtAndYatraTest[0][4]="Bangalore";  						//fromPlace2
		mmtAndYatraTest[0][5]="Delhi";  						 	//toPlace
		mmtAndYatraTest[0][6]="6AM-12 Noon";  						//departureFromTime
		mmtAndYatraTest[0][7]="6AM-12 Noon";  		                //arrivalAtTime					
		
		return mmtAndYatraTest;
		
	}
}
