package com.qa.mmt.testcases;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.mmt.base.Base;
import com.qa.mmt.pages.MmtSearchPage;
import com.qa.mmt.pages.MmtSearchResult;
import com.qa.mmt.utils.MmtUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This class is written to execute TestNG testcases
 * 
 * @author Nalinakshee
 * @class MmtTest
 */
public class MmtTest extends Base {
	MmtSearchPage mmtSearchPage;
	MmtSearchResult mmtSearchResult;
	String prices;
	boolean result;
	ExtentReports extent;
	ExtentTest extentTest;

	/**
	 * Default constructor to call the base class and load properties file
	 */
	MmtTest() {
		super();
	}

	/**
	 * Method to setup properties for extent report
	 */
	@BeforeTest
	public void extentSetUp() {
		if (prop.getProperty("os").equals("windows")) {
			extent = new ExtentReports(System.getProperty("user.dir") + File.separator + "test-output" + File.separator
					+ "ExtentReport.html", true);
		} else {
			extent = new ExtentReports(
					System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "ExtentReport",
					true);
		}
		extent.addSystemInfo("User Name", prop.getProperty("systemuser"));
		extent.addSystemInfo("Host Name", prop.getProperty("systemhost"));
	}

	/**
	 * Method to end extent report
	 */
	@AfterTest
	public void endExtent() {
		extent.flush();
		extent.close();
	}

	/**
	 * Method to initialize the driver and create object reference for pages
	 */
	@BeforeMethod()
	public void setup() {
		initialization();
		mmtSearchPage = new MmtSearchPage();
		mmtSearchResult = new MmtSearchResult();
	}

	/**
	 * Method to test the testcases
	 */
	@Test(priority = 1)
	public void searchTest() {
		extentTest = extent.startTest("searchTest");
		// For step 1 to 5
		mmtSearchPage.gotoSearchPage();
		assertEquals(mmtSearchPage.getTitle(), "Makemytrip", "Title is not matching");
		// For step 6
		MmtUtils.scrollPageDown(driver, MmtUtils.SCROLL_COUNTER);
		mmtSearchResult.countFlights("Page Load");
		// For step 7
		mmtSearchResult.countForNonStopFlights();
		MmtUtils.scrollPageDown(driver, MmtUtils.SCROLL_COUNTER);
		mmtSearchResult.countForOneStopFlights();
		MmtUtils.scrollPageDown(driver, MmtUtils.SCROLL_COUNTER);
		// For step 8, 9 and 10
		prices = mmtSearchResult.selectRadioButtonsAndVerifyPrice(3, 4);
		assertEquals(prices.split("-")[0], prices.split("-")[2],
				"Departure Price between selected and footer is not matching");
		assertEquals(prices.split("-")[1], prices.split("-")[3],
				"Return Price between selected and footer is not matching");

		result = mmtSearchResult.checkSumOfPriceWithTotal();
		assertEquals(result, true, "Sum of Departure and Return Price is not matching with Total Price");

		prices = mmtSearchResult.selectRadioButtonsAndVerifyPrice(1, 4);
		assertEquals(prices.split("-")[0], prices.split("-")[2],
				"Departure Price between selected and footer is not matching");
		assertEquals(prices.split("-")[1], prices.split("-")[3],
				"Return Price between selected and footer is not matching");

		result = mmtSearchResult.checkSumOfPriceWithTotal();
		assertEquals(result, true, "Sum of Departure and Return Price is not matching with Total Price");

		prices = mmtSearchResult.selectRadioButtonsAndVerifyPrice(2, 1);
		assertEquals(prices.split("-")[0], prices.split("-")[2],
				"Departure Price between selected and footer is not matching");
		assertEquals(prices.split("-")[1], prices.split("-")[3],
				"Return Price between selected and footer is not matching");

		result = mmtSearchResult.checkSumOfPriceWithTotal();
		assertEquals(result, true, "Sum of Departure and Return Price is not matching with Total Price");
	}

	/**
	 * Method to close the browser and generate screenshots for failed cases using
	 * extent report
	 * 
	 * @param result
	 * @throws IOException
	 */
	@AfterMethod()
	public void teardown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in
																							// extent report

			String screenshotPath = MmtTest.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // to add screenshot in extent
																							// report
			// extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath));
			// //to add screencast/video in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}

		extent.endTest(extentTest); // ending test and ends the current test and prepare to create html report
		driver.quit();
	}

	/**
	 * Method to generate screenshots
	 * 
	 * @param driver
	 * @param screenshotName
	 * @return String
	 * @throws IOException
	 */
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + File.separator + "FailedTestsScreenshots" + File.separator
				+ screenshotName + dateName + ".jpeg";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
}
