package com.qa.mmt.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.qa.mmt.utils.MmtUtils;

/**
 * This class is written to load properties and initialize driver
 * 
 * @author Nalinakshee
 * @class Base
 */
public class Base {
	public static WebDriver driver;
	public static Properties prop;

	/**
	 * Default Constructor
	 */
	public Base() {
		prop = new Properties();
		FileInputStream file;
		try {

			file = new FileInputStream(
					System.getProperty("user.dir") + File.separator + "Config" + File.separator + "config.properties");
			prop.load(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to initialize driver depends on different browsers
	 */
	public static void initialization() {
		String browserName = prop.getProperty("browser");
		String driverfolderpath = System.getProperty("user.dir") + File.separator + "Drivers" + File.separator;
		String executableString = ".exe";

		if (browserName.equals("chrome")) {
			if (prop.getProperty("os").equals("windows")) {
				System.setProperty("webdriver.chrome.driver", driverfolderpath + "chromedriver" + executableString);
			} else {
				System.setProperty("webdriver.chrome.driver", driverfolderpath + "chromedriver");
			}

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capabilities);
			// driver = new ChromeDriver();
		} else if (browserName.equals("ff")) {
			if (prop.getProperty("os").equals("windows")) {
				System.setProperty("webdriver.gecko.driver", driverfolderpath + "geckodriver" + executableString);
			} else {
				System.setProperty("webdriver.gecko.driver", driverfolderpath + "geckodriver");
			}

			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(MmtUtils.PAGELOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(MmtUtils.IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		driver.manage().deleteAllCookies();
	}

}
