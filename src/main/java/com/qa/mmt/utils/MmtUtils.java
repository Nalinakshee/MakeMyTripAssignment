package com.qa.mmt.utils;

import java.time.LocalDate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class is written to define common variables and common utilities
 * function
 * 
 * @author Nalinakshee
 * @class MmtUtils
 */
public class MmtUtils {
	public static long PAGELOAD_TIMEOUT = 50;
	public static long IMPLICIT_TIMEOUT = 50;
	public static int SCROLL_COUNTER = 20;

	/**
	 * This method will take one date from calender's current date and will return
	 * the corresponding co-ordinate
	 * 
	 * @param objDate
	 * @return String
	 */
	public static String getCoordinate(LocalDate objDate) {
		String cordinate = "";
		// DateFormat dateFormat = new SimpleDateFormat("dd MMM yy");

		//String myDate = objDate.toString();
		int row = 0;
		int month = objDate.getMonthValue();
		int year = objDate.getYear();
		
		LocalDate firstdayoftheMonth = LocalDate.of(year, month, 1);
		
		int firstcol = firstdayoftheMonth.getDayOfWeek().getValue();
		
		if (firstcol == 7) {
			firstcol = 1;
		}else {
			firstcol += 1;
		}
		
		int offset = 0;
		if (firstcol==1) {
			offset = 7;
			row =1;
		}else {
			offset = (7-firstcol)+1;
			row=1;
		}
		
		int dayoftheMonth = objDate.getDayOfMonth();
		
		int restcells = dayoftheMonth-offset;
		
		if (restcells>0) {
			int addrow= 0;
			if (restcells%7 == 0) {
				addrow = (restcells/7);
			}else {
				addrow = (restcells/7)+1;
			}
			
			row =row+addrow;
		}
		
		int col = objDate.getDayOfWeek().getValue();
		
		if (col == 7) {
			col = 1;
		} else {
			col += 1;
		}
		
		/*
		String day = myDate.split("-")[2];
		int dayInt = Integer.parseInt(day);	

		row = dayInt / 7;

		if (dayInt % 7 != 0) {
			// If condition to check if date is present in first row
			if (dayInt < 7 && dayInt > col) {
				row = 1;
			}

			row += 1;

		} else { // Else condition to check if date is present in first coloumn and is divisible
					// by 7 (e.g. for dates 7,14,21,28)
			if (col == 1) {
				row += 1;
			}

		}*/
		cordinate = "" + row + "-" + col;
		return cordinate;
	}

	/**
	 * This method is used to click any Element by JavaScript
	 * 
	 * @param element
	 * @param driver
	 */
	public static void clickElementByJS(WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);
	}

	/**
	 * This method is used to scroll down the page till 20 times
	 * 
	 * @param driver
	 * @param scrollCounter
	 */
	public static void scrollPageDown(WebDriver driver, int scrollCounter) {
		int counter = 0;
		while (counter != scrollCounter) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter++;
		}
	}

	/**
	 * This method is used to draw border on specific element
	 * 
	 * @param element
	 * @param driver
	 */
	public static void drawBorder(WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.border = '3px solid yellow'", element);
	}

	/**
	 * Method is used to scroll until a particular element is visible
	 * 
	 * @param element
	 * @param driver
	 */
	public static void scrollIntoView(WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
}
