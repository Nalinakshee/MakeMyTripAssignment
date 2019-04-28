package com.qa.mmt.pages;

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
		
		
		Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		 cal.add(Calendar.DATE, 7);
		Date afterSevenDays = cal.getTime();;
		
		
		String row = "";
		String col = "";
		
		String cordinate = MmtUtils.getCoordinate(currentDate);
		row = cordinate.split("-")[0];
		col = cordinate.split("-")[1];
		String dayXpth = "//div[@class='DayPicker-Months']/div[1]//div[@class='DayPicker-Body']/div["+row+"]/div["+col+"]";
		
		if (driver.findElement(By.xpath(dayXpth)).isDisplayed()) {
			MmtUtils.clickElementByJS(driver.findElement(By.xpath(dayXpth)), driver);
			
		}
		
		cordinate = MmtUtils.getCoordinate(afterSevenDays);
		row = cordinate.split("-")[0];
		col = cordinate.split("-")[1];

		// Below Logic will check if month is present in same year or in case if it is
		// present in December and January i.e. in case of different year
		if ((afterSevenDays.getMonth() > currentDate.getMonth())
				|| (afterSevenDays.getYear() > currentDate.getYear())) {
			dayXpth = "//div[@class='DayPicker-Months']/div[2]//div[@class='DayPicker-Body']/div[" + row + "]/div["
					+ col + "]";
		}	
			
		if (driver.findElement(By.xpath(dayXpth)).isDisplayed()) {
			MmtUtils.clickElementByJS(driver.findElement(By.xpath(dayXpth)), driver);	
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
