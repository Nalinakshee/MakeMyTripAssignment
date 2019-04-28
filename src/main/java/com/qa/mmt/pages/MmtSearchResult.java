package com.qa.mmt.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.mmt.base.Base;
import com.qa.mmt.utils.MmtUtils;
/**
 * This class is written to display Search Result, count total departure and return flights, verify Price
 * 
 * @author Nalinakshee
 * @class MmtSearchResult  
 */
public class MmtSearchResult extends Base {

	@FindBy(xpath = "//input[@id='filter_stop0']")
	WebElement nonStopChkBox;

	@FindBy(xpath = "//label[@for='filter_stop0']")
	WebElement nonStopLbl;

	@FindBy(xpath = "//input[@id='filter_stop1']")
	WebElement oneStopChkBox;

	@FindBy(xpath = "//label[@for='filter_stop1']")
	WebElement oneStopLbl;
	/**
	 * Default constructor to initialize page elements
	 */
	public MmtSearchResult() {
		PageFactory.initElements(driver, this);
	}
	/**
	 * Method to count the departure and return flights
	 * 
	 * @param state
	 */
	public void countFlights(String state) {
		WebElement departure = driver.findElement(By.xpath("//div[@id='ow_domrt-jrny']"));
		List<WebElement> departureList = departure.findElements(By.tagName("input"));
		System.out.println("Departure Flights Count for " + state + " " + departureList.size());

		WebElement returnflights = driver.findElement(By.xpath("//div[@id='rt-domrt-jrny']"));
		List<WebElement> returnflightsList = returnflights.findElements(By.tagName("input"));
		System.out.println("Return Flights Count for " + state + " " + returnflightsList.size());
	}
	/**
	 * Method to count the departure and return flights for Non Stop
	 * 
	 */
	public void countForNonStopFlights() {
		MmtUtils.clickElementByJS(nonStopChkBox, driver);
		MmtUtils.drawBorder(nonStopLbl, driver);
		countFlights("Non Stop");
	}
	/**
	 * Method to count the departure and return flights for One Stop
	 * 
	 */
	public void countForOneStopFlights() {
		//MmtUtils.clickElementByJS(nonStopChkBox, driver);
		MmtUtils.clickElementByJS(oneStopChkBox, driver);
		MmtUtils.drawBorder(oneStopLbl, driver);
		countFlights("One Stop");
	}
	/**
	 * Method to select Departure and Return Radio buttons and verify its Price
	 * 
	 * @param deptRow
	 * @param returnRow
	 * @return String
	 */
	public String selectRadioButtonsAndVerifyPrice(int deptRow, int returnRow) {
		String prices = "";
		try {

			String deptBeforeXpath = "//div[@id='ow_domrt-jrny']//div[@class='fli-list splitVw-listing'][";
			String retBeforeXpath = "//div[@id='rt-domrt-jrny']//div[@class='fli-list splitVw-listing'][";
			String afterXpath = "]//div[@class='fli-list-body-section clearfix']//div[@class='pull-right marL5 text-right split-price-sctn']/p";

			WebElement deptRadioInput = driver.findElement(By.xpath(deptBeforeXpath + deptRow + "]/input"));
			WebElement deptRadiolabel = driver.findElement(By.xpath(deptBeforeXpath + deptRow + "]/label"));

			MmtUtils.scrollIntoView(deptRadiolabel, driver);
			MmtUtils.drawBorder(deptRadiolabel, driver);
			MmtUtils.clickElementByJS(deptRadioInput, driver);

			WebElement retRadioInput = driver.findElement(By.xpath(retBeforeXpath + returnRow + "]/input"));
			WebElement retRadiolabel = driver.findElement(By.xpath(retBeforeXpath + returnRow + "]/label"));

			MmtUtils.scrollIntoView(retRadiolabel, driver);
			MmtUtils.drawBorder(retRadiolabel, driver);
			MmtUtils.clickElementByJS(retRadioInput, driver);

			String depPrice = driver.findElement(By.xpath((deptBeforeXpath + deptRow + afterXpath))).getText();
			System.out.println("Departure Price of element no. " + deptRow + " is : " + depPrice);

			String retPrice = driver.findElement(By.xpath((retBeforeXpath + returnRow + afterXpath))).getText();
			System.out.println("Return Price of element no. " + returnRow + " is : " + retPrice);

			String footerDepPrice = driver
					.findElement(By
							.xpath("//div[@class='splitVw-footer-left ']//div[@class='pull-right marL5 text-right']/p"))
					.getText();
			System.out.println("Footer Departure Price is : " + footerDepPrice);

			String footerRetPrice = driver
					.findElement(By.xpath(
							"//div[@class='splitVw-footer-right ']//div[@class='pull-right marL5 text-right']/p"))
					.getText();
			System.out.println("Footer Return Price is : " + footerRetPrice);

			prices = depPrice + "-" + retPrice + "-" + footerDepPrice + "-" + footerRetPrice;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prices;
	}
	/**
	 * Method to verify sum of Departure and Return Flights with Total
	 * 
	 * @return boolean
	 */
	public boolean checkSumOfPriceWithTotal() {
		try {
			String footerDepPrice = driver
					.findElement(By
							.xpath("//div[@class='splitVw-footer-left ']//div[@class='pull-right marL5 text-right']/p"))
					.getText();
			footerDepPrice = footerDepPrice.replace(",", "");
			footerDepPrice = footerDepPrice.replace("Rs ", "");
			//System.out.println("Footer Departure Price is : " + footerDepPrice);

			String footerRetPrice = driver
					.findElement(By.xpath(
							"//div[@class='splitVw-footer-right ']//div[@class='pull-right marL5 text-right']/p"))
					.getText();
			footerRetPrice = footerRetPrice.replace(",", "");
			footerRetPrice = footerRetPrice.replace("Rs ", "");
			//System.out.println("Footer Return Price is : " + footerRetPrice);

			String totalPrice = driver
					.findElement(By.xpath("//div[@class='footer-fare']//span[@class='splitVw-total-fare']")).getText();
			totalPrice = totalPrice.replace(",", "");
			totalPrice = totalPrice.replace("Rs ", "");
			//System.out.println("Footer Total Price is : " + totalPrice);

			if (Integer.parseInt(totalPrice) == (Integer.parseInt(footerDepPrice) + Integer.parseInt(footerRetPrice))) {
				return true;
			} else if (Integer
					.parseInt(totalPrice) < (Integer.parseInt(footerDepPrice) + Integer.parseInt(footerRetPrice))) {
				if (driver.findElement(By.xpath("//div[@class='footer-fare']//span[@class='slashed-price']"))
						.isDisplayed()) {
					String slashedPrice = driver
							.findElement(By.xpath("//div[@class='footer-fare']//span[@class='slashed-price']"))
							.getText();
					slashedPrice = slashedPrice.replace(",", "");
					slashedPrice = slashedPrice.replace("Rs ", "");
					//System.out.println("Footer Slashed Price is : " + slashedPrice);

					if (Integer.parseInt(
							slashedPrice) == (Integer.parseInt(footerDepPrice) + Integer.parseInt(footerRetPrice))) {
						return true;
					}
				}

			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
