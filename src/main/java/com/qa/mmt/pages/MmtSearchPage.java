package com.qa.mmt.pages;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.mmt.base.Base;
import com.qa.mmt.utils.MmtUtils;
/**
 * This class is written to display Search Result, auto populate dates, from city, to city
 * 
 * @author Nalinakshee
 * @class MmtSearchPage  
 */
public class MmtSearchPage extends Base{	
	
	@FindBy(xpath="//a[@class='active makeFlex hrtlCenter column']")
	WebElement flightLink;
	
	@FindBy(xpath="//ul[@class='fswTabs latoBlack greyText']//li[2]//span")
	WebElement roundTrip;
	
	@FindBy(xpath="//input[@id='fromCity']")
	WebElement fromCity;
	
	@FindBy(xpath="//input[@id='toCity']")
	WebElement toCity;
	
	@FindBy(xpath="//div[@class='fsw_inputBox dates inactiveWidget ']")
	WebElement deptCal;
	
	@FindBy(xpath="//a[contains(text(),'Search')]")
	WebElement searchBtn;
	/**
	 * Default constructor to initialize page elements
	 */
	public MmtSearchPage(){
		PageFactory.initElements(driver, this);
	}
	/**
	 * Method to navigate to search page
	 */
	public void gotoSearchPage() {		
		try {		
		roundTrip.click(); // Round trip
		
		fromCity.sendKeys(prop.getProperty("fromCity"));
		toCity.sendKeys(prop.getProperty("toCity"));			
		
		deptCal.click(); // departure calender
		
		
		//Get the current date
	    LocalDate today = LocalDate.now();
	    System.out.println("Current date using Local Date: " + today);
			
	    //add 1 week to the current date
	    LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
	    System.out.println("Next week using Local Date: " + nextWeek);
	      
		/*Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		System.out.println("Current Date "+ currentDate);
		 cal.add(Calendar.DATE, 7);
		Date afterSevenDays = cal.getTime();;
		System.out.println("Next week: " + afterSevenDays);*/
		
		/*Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		 cal.add(Calendar.DATE, 7);
		Date afterSevenDays = cal.getTime();
		*/
		
		String row = "";
		String col = "";
		
		String cordinate = MmtUtils.getCoordinate(today);
		row = cordinate.split("-")[0];
		col = cordinate.split("-")[1];
		String dayXpthFirst = "//div[@class='DayPicker-Months']/div[1]//div[@class='DayPicker-Body']/div["+row+"]/div["+col+"]";
		
		
		if (driver.findElement(By.xpath(dayXpthFirst)).isDisplayed()) {
			MmtUtils.clickElementByJS(driver.findElement(By.xpath(dayXpthFirst)), driver);
			
		}
		
		cordinate = MmtUtils.getCoordinate(nextWeek);
		row = cordinate.split("-")[0];
		col = cordinate.split("-")[1];
		String dayXpthSecond = "//div[@class='DayPicker-Months']/div[1]//div[@class='DayPicker-Body']/div[" + row + "]/div["
				+ col + "]";
		// Below Logic will check if month is present in same year or in case if it is
		// present in December and January i.e. in case of different year
		if ((nextWeek.getMonth().getValue() > today.getMonth().getValue())
				|| (nextWeek.getYear() > today.getYear())) {
			dayXpthSecond = "//div[@class='DayPicker-Months']/div[2]//div[@class='DayPicker-Body']/div[" + row + "]/div["
					+ col + "]";
		}	
			
		if (driver.findElement(By.xpath(dayXpthSecond)).isDisplayed()) {
			MmtUtils.clickElementByJS(driver.findElement(By.xpath(dayXpthSecond)), driver);	
		}
		
		MmtUtils.clickElementByJS(searchBtn, driver);
		driver.manage().deleteAllCookies();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method to return title of the page
	 * @return String
	 */
	public String getTitle() {
		return driver.getTitle();
	}

}
