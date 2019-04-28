package com.qa.mmt.testcases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.mmt.base.Base;
import com.qa.mmt.pages.MmtSearchPage;
import com.qa.mmt.pages.MmtSearchResult;
import com.qa.mmt.utils.MmtUtils;
/**
 * This class is written to execute TestNG testcases
 * 
 * @author Nalinakshee
 * @class MmtTest  
 */
public class MmtTest extends Base{
	MmtSearchPage mmtSearchPage;
	MmtSearchResult mmtSearchResult;
	String prices;
	boolean result;
	/**
	 * Default constructor to call the base class and load properties file 
	 */
	MmtTest()
	{
		super();
	}
	/**
	 * Method to initialize the driver and create object reference for pages
	 */
	@BeforeMethod()
	public void setup(){
		initialization();
		mmtSearchPage = new MmtSearchPage();
		mmtSearchResult = new MmtSearchResult();
	}	
	/**
	 * Method to test the testcases
	 */
	@Test(priority=1)
	public void searchTest() {
		//For step 1 to 5
		mmtSearchPage.gotoSearchPage();
		assertEquals(mmtSearchPage.getTitle(), "Makemytrip", "Title is not matching");
		//For step 6
		MmtUtils.scrollPageDown(driver, MmtUtils.SCROLL_COUNTER);
		mmtSearchResult.countFlights("Page Load");
		//For step 7 
		mmtSearchResult.countForNonStopFlights();
		MmtUtils.scrollPageDown(driver, MmtUtils.SCROLL_COUNTER);
		mmtSearchResult.countForOneStopFlights();
		MmtUtils.scrollPageDown(driver, MmtUtils.SCROLL_COUNTER);
		//For step 8, 9 and 10
		prices = mmtSearchResult.selectRadioButtonsAndVerifyPrice(3, 4);
		assertEquals(prices.split("-")[0], prices.split("-")[2], "Departure Price between selected and footer is not matching");
		assertEquals(prices.split("-")[1], prices.split("-")[3], "Return Price between selected and footer is not matching");
		
		result = mmtSearchResult.checkSumOfPriceWithTotal();
		assertEquals(result, true, "Sum of Departure and Return Price is not matching with Total Price");
		
		prices = mmtSearchResult.selectRadioButtonsAndVerifyPrice(1, 4);
		assertEquals(prices.split("-")[0], prices.split("-")[2], "Departure Price between selected and footer is not matching");
		assertEquals(prices.split("-")[1], prices.split("-")[3], "Return Price between selected and footer is not matching");
		
		result = mmtSearchResult.checkSumOfPriceWithTotal();
		assertEquals(result, true, "Sum of Departure and Return Price is not matching with Total Price");
		
		prices = mmtSearchResult.selectRadioButtonsAndVerifyPrice(2, 1);
		assertEquals(prices.split("-")[0], prices.split("-")[2], "Departure Price between selected and footer is not matching");
		assertEquals(prices.split("-")[1], prices.split("-")[3], "Return Price between selected and footer is not matching");
		
		result = mmtSearchResult.checkSumOfPriceWithTotal();
		assertEquals(result, true, "Sum of Departure and Return Price is not matching with Total Price");
	}
	/**
	 * Method to close the browser
	 */
	@AfterMethod()
	public void teardown() {
		driver.quit();
	}
}
