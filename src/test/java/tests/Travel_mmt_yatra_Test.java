package tests;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;
import testData.dataProviderClass_TestData;
import utils.BaseClass;

public class Travel_mmt_yatra_Test extends BaseClass {
	
	String priceOfFirstFlightInMMT;
	String priceOfFirstFlightInYatra;
	
	//Get today's date in "day", "month" and "year"	
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int year  = localDate.getYear();
	int monthvalue= localDate.getMonthValue() ;
	Month month = Month.of(monthvalue);
	int day   = localDate.getDayOfMonth();


  @Test(dataProviderClass = dataProviderClass_TestData.class , dataProvider ="mmtAndYatraTest", priority=1, enabled=true)
  public void getPriceOfTheFirstFlight_mmt(String testID, String url1, String url2,String fromPlace1, String fromPlace2, String toPlace, String departureFromTime, String arrivalAtTime) throws InterruptedException {
	  testLogger=extent.createTest("getPriceOfTheFirstFlight_mmt" , "This test is to get the price of the First Flight from Make my trip website.");
	  testLogger.info("Launch the url and navigate to MMT website.");
	  launchurl(url1);
	  mmt_homePg.ClickOnMenuFlights();
	  mmt_homePg.clickOnOneTime();
	  testLogger.info("Select From and To Place");
	  mmt_homePg.setFromCity(fromPlace1);
	  mmt_homePg.setToCity(toPlace);
	  testLogger.info("Select Depature Date.");
	  mmt_homePg.selectDepartureDate(28, Month.APRIL, year);
	  testLogger.info("Click On Button Search.");
	  mmt_homePg.clickOnButtonSearch();
	  mmt_homePg.clickOnNonStop();
	  mmt_homePg.selectDepartureFromTime(departureFromTime);
	  mmt_homePg.selectArrivalAtTime(arrivalAtTime);
	  priceOfFirstFlightInMMT=mmt_homePg.getPriceOfFirstFlight();
	  if(!priceOfFirstFlightInMMT.isEmpty()) {
		  System.out.println("Price Of First Flight In MMT is-"+priceOfFirstFlightInMMT);
		  testLogger.info("Price Of First Flight In MMT is-"+priceOfFirstFlightInMMT);
	  }else {
		  Assert.fail("Price of the flight is not returned from MMT- Test Failed");
	  }
	  
  } 
  
  
  @Test(dataProviderClass = dataProviderClass_TestData.class , dataProvider ="mmtAndYatraTest", priority=2, enabled=true)
  public void getPriceOfTheFirstFlight_yatra(String testID, String url1, String url2,String fromPlace1, String fromPlace2, String toPlace, String departureFromTime, String arrivalAtTime) throws InterruptedException {
	  testLogger=extent.createTest("getPriceOfTheFirstFlight_yatra" , "This test is to get the price of the First Flight from Yatra website.");
	  testLogger.info("Launch the url and navigate to Yatra website.");
	  launchurl(url2);
	  yathra_homePg.clickOnMenuFlights();
	  yathra_homePg.clickOnOneTime();
	  testLogger.info("Select From and To Place");
	  yathra_homePg.setFromCity(fromPlace2);
	  yathra_homePg.setToCity(toPlace);
	  testLogger.info("Select Depature Date.");
	  yathra_homePg.selectDepartureDate(28, Month.APRIL, year);
	  yathra_homePg.clickOnNonStop();
	  testLogger.info("Click On Button Search Flights.");
	  yathra_homePg.clickOnButtonSearchFlights();
	  yathra_homePg.selectDepartTime();
	  yathra_homePg.ClickOnButtonApplyFiletrs();
	  priceOfFirstFlightInYatra=yathra_homePg.getPriceOfFirstFlight();
	  if(!priceOfFirstFlightInYatra.isEmpty()) {
		  System.out.println("Price Of First Flight In MMT is-"+priceOfFirstFlightInYatra);
		  testLogger.info("Price Of First Flight In MMT is-"+priceOfFirstFlightInYatra);
	  }else {
		  Assert.fail("Price of the flight is not returned from Yatra- Test Failed");
	  }
  }
  
  @Test(priority=3, enabled=true, dependsOnMethods = {"getPriceOfTheFirstFlight_mmt","getPriceOfTheFirstFlight_yatra"})
  public void compareThePriceOfFlight_mmt_yatra() throws InterruptedException {
	  testLogger=extent.createTest("compareThePriceOfFlight_mmt_yatra" , "This test is to compare the price of the Flights in  yatra and Make my trip website.");
	  testLogger.info("Launch the url and navigate to MMT website.");
	  int priceOfFirstFlightInMakeMyTrip=Integer.parseInt(priceOfFirstFlightInMMT.replace(",", ""));
	  int priceOfFirstFlightInYatraCOM=Integer.parseInt(priceOfFirstFlightInYatra.replace(",", ""));  
	  if(priceOfFirstFlightInMakeMyTrip<priceOfFirstFlightInYatraCOM) {
		System.out.println("Price of the flight in Make My Trip is lower than Yatra.com");  
	  }
	  if(priceOfFirstFlightInMakeMyTrip>priceOfFirstFlightInYatraCOM) {
			System.out.println("Price of the flight in Yatra is lower than Make My Trip.");
			 testLogger.info("Price of the flight in Yatra is lower than Make My Trip.");
	  }
	  else {
			System.out.println("Price of the flight is same in both Make My Trip and Yatra.com");
	  }
  }
}
