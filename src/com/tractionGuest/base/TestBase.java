package com.tractionGuest.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.testng.Assert;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.FixedCutProvider;
import com.applitools.eyes.NewTestException;
import com.applitools.eyes.ProxySettings;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestFailedException;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.google.common.base.Function;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import com.tractionGuest.util.APIClient;
import com.tractionGuest.util.ErrorUtil;
import com.tractionGuest.util.Xls_Reader;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import io.appium.java_client.ios.IOSDriver;

public class TestBase {
	public static Properties CONFIG = null;
	public static Properties OR = null;
	public static Xls_Reader suiteXls = null;
	public static Xls_Reader LocationsuiteXls = null;
	public static boolean isInitalized = false;
	public static boolean isBrowserOpened = false;
	public static boolean fail = false;
	public static boolean skip = false;
	public static boolean isTestPass = true;
	public static WebDriver driver = null;
	public static Eyes eyes = null;
	public static XWPFDocument document = null;
	public static FileOutputStream fos = null;
	public static Logger Log = Logger.getLogger("-->");
	public static String fileName = null;
	public static BatchInfo batchinfo;
	public static TestResults stepsResults;
    public static StringBuilder a;
    public static String line;
    public static Map<String,String> jsonHashMap=null;
    public static int TotalAvailableDays;
    public ReportiumClient reportiumClient;
	// initializing the Tests
	public static void initialize() throws Exception {
		// logs
		if (!isInitalized) {

			DOMConfigurator.configure("log4j.xml");
			// config
			Log.info("Loading Property files");
			CONFIG = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//tractionGuest//config/config.properties");
			CONFIG.load(ip);

			OR = new Properties();
			ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//tractionGuest//config/OR.properties");
			OR.load(ip);
			Log.info("Loaded Property files successfully");
			Log.info("Loading XLS Files");

			// xls file
			LocationsuiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//tractionGuest//xls//location suite.xlsm");
			suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//tractionGuest//xls//Suite.xlsm");
			Log.info("Loaded XLS Files successfully");
			isInitalized = true;
		}
	}

	// ================================================Generic Functions==========================================================\\
	public static void setAuthorInfoForReports() {
		   ATUReports.setAuthorInfo("tractionGuest Automation Tester", Utils.getCurrentTime(),"V1.0");
		}


	public static void openBrowser(String browserType) throws Exception {
		try {
			if (browserType.equalsIgnoreCase("Firefox")) {
				Log.info("Opening Firefox Browser");
				FirefoxProfile profile = new FirefoxProfile();
				// This will set the true value
				profile.setAcceptUntrustedCertificates(true);
				// This will open firefox browser using above created profile
				driver = new FirefoxDriver();
				ATUReports.setWebDriver(driver);
				

				/*
				 * Dimension d =new Dimension(360,640);
				 * driver.manage().window().setSize(d);
				 */
				driver.manage().window().maximize();
				Log.info("Firefox browser started");
				ATUReports.add("Opening Firefox Browser" , LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));


			} else if (browserType.equalsIgnoreCase("IE")) {
				Log.info("Opening IE Browser");
				File file = new File(System.getProperty("user.dir")+"\\jars\\IEDriverServer.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				capabilities.setCapability("requireWindowFocus", true);
				System.setProperty("webdriver.ie.driver",file.getAbsolutePath());
				driver = new InternetExplorerDriver(capabilities);
				ATUReports.setWebDriver(driver);
				driver.manage().window().maximize();
				Log.info("IE browser started");
				ATUReports.add("Opening IE Browser" , LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));


			}else if (browserType.equalsIgnoreCase("Edge")) {
				Log.info("Opening Edge Browser");
				File file = new File(System.getProperty("user.dir")+"\\jars\\MicrosoftWebDriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.edge();
				capabilities.setCapability("requireWindowFocus", true);
				System.setProperty("webdriver.edge.driver",file.getAbsolutePath());
				driver =new EdgeDriver(capabilities);
				ATUReports.setWebDriver(driver);
				driver.manage().window().maximize();
				Log.info("Edge browser started");

			}
			else if (browserType.equalsIgnoreCase("Chrome")) {
				Log.info("Opening Chrome Browser");
				File file = new File(System.getProperty("user.dir")
						+ "\\jars\\chromedriver.exe");
				DesiredCapabilities cap = DesiredCapabilities.chrome();

				// Set ACCEPT_SSL_CERTS variable to true
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				// Set the driver path
				System.setProperty("webdriver.chrome.driver",
						file.getAbsolutePath());
				// Open browser with capability
				driver = new ChromeDriver(cap);
				ATUReports.setWebDriver(driver);
				driver.manage().window().maximize();
				Log.info("Chrome browser started");
				ATUReports.add("Chrome Browser started" , LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
			else if (browserType.equalsIgnoreCase("HTMLUnitDriver")) {
				Log.info("Opening HTMLUnitDriver");

				driver = new HtmlUnitDriver();
				ATUReports.setWebDriver(driver);
				Log.info("HTMLUnitDriver started");
				ATUReports.add("HTMLUnitDriver started" , LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
		} catch (Throwable t) {
			// report error
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to start the browser: -- " + t.getMessage());
			ATUReports.add("Not able to start browser" , LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			throw new Exception(" Stoping the script....!!!!");
		}
	}

	public static void navigate(String url) throws Exception {
		try {
			Log.info("Navigating to URL--:" + url);
			driver.get(url);
			ATUReports.add("Navigating to URL--:" + url, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			//closeBrowser();
			Log.error("Not able to open the url -- " + t.getMessage());
			ATUReports.add("Not able to Open Url-- " , LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			throw new Exception(" Stoping the script....!!!!");
		}
	}
	
	public static void refresh() throws Exception {
		try {
			Log.info("Refreshing browser--:");
			driver.navigate().refresh();
			ATUReports.add("Refreshing browser--:", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			//closeBrowser();
			Log.error("Not able to open the url -- " + t.getMessage());
			ATUReports.add("Not able to Open Url-- " , LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw new Exception(" Stoping the script....!!!!");
		}
	}

public static void click(String identifier) throws Exception {

		WebElement element = null;
		try {
			if (identifier.endsWith("_xpath")) {
			customWait(identifier);
			Log.info("Clicking on:-- " + identifier);
		    ATUReports.add("Clicking on  "  + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			element=driver.findElement(By.xpath(OR.getProperty(identifier)));
			element.click();
			} else if (identifier.endsWith("_id")) {
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on  "  + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.id(OR.getProperty(identifier)));
				element.click();
			} else if (identifier.endsWith("_name")) {
				Log.info("Clicking on:-- " + identifier);
			    ATUReports.add("Clicking on  "  + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.name(OR.getProperty(identifier)));
				element.click();
			} else if (identifier.endsWith("_css")) {
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on  "  + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.cssSelector(OR.getProperty(identifier)));
				element.click();
			} else if (identifier.endsWith("_linkText")) {
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on  "  + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.linkText(OR.getProperty(identifier)));
				element.click();
			}
			else{
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on  "  + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.xpath(identifier));
			}
			
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to click on -- " + t.getMessage());
			ATUReports.add("Not able to click on  " + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		    getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}
	}

	public static void pressTab(String identifier) throws Exception {
		try {

			if (identifier.endsWith("_xpath")) {
				Log.info("Tabbing on:-- " + identifier);
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(Keys.TAB);
			} else if (identifier.endsWith("_id")) {
				Log.info("Tabbing on:-- " + identifier);
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(Keys.TAB);
			} else if (identifier.endsWith("_name")) {
				Log.info("Tabbing on:-- " + identifier);
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(Keys.TAB);
			} else if (identifier.endsWith("_css")) {
				Log.info("Tabbing on:-- " + identifier);
				driver.findElement(By.cssSelector(OR.getProperty(identifier))).sendKeys(Keys.TAB);
			} else if (identifier.endsWith("_linkText")) {
				Log.info("Tabbing on:-- " + identifier);
				driver.findElement(By.linkText(OR.getProperty(identifier))).sendKeys(Keys.TAB);
			}
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to Tab on -- " + t.getMessage());
			ATUReports.add("Not able to tab on " + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
	}
	public static void pressEnterKey(String identifier) throws Exception {
		try {

			if (identifier.endsWith("_xpath")) {
				Log.info("pressing EnterKey on:-- " + identifier);
				ATUReports.add("Pressing enter key on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(Keys.ENTER);
			} else if (identifier.endsWith("_id")) {
				Log.info("pressing EnterKey on:-- " + identifier);
				ATUReports.add("Pressing enter key on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(Keys.ENTER);
			} else if (identifier.endsWith("_name")) {
				Log.info("pressing EnterKey on:-- " + identifier);
				ATUReports.add("Pressing enter key on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(Keys.ENTER);
			} else if (identifier.endsWith("_css")) {
				Log.info("pressing EnterKey on:-- " + identifier);
				ATUReports.add("Pressing enter key on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				driver.findElement(By.cssSelector(OR.getProperty(identifier))).sendKeys(Keys.ENTER);
			} else if (identifier.endsWith("_linkText")) {
				Log.info("pressing EnterKey on:-- " + identifier);
				ATUReports.add("Pressing enter key on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				driver.findElement(By.linkText(OR.getProperty(identifier))).sendKeys(Keys.ENTER);						
			}
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to press EnterKey on -- " + t.getMessage());
			ATUReports.add("Not able to press enterkey on:" + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
	}
public static void clear(String identifier) throws Exception {
		
		WebElement element = null;
		try {
			if (identifier.endsWith("_xpath")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.xpath(OR.getProperty(identifier)));
						
			} else if (identifier.endsWith("_id")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.id(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_name")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.name(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_css")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element=driver.findElement(By.cssSelector(OR.getProperty(identifier)));			
			}
			element.clear();
		} catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to clear the input field -- " + t.getMessage());
			ATUReports.add("Not able to clear the input field:" + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception("Stoping the script....!!!!");

		}
	}


public static void input(String identifier, String data) throws Exception {
		
		WebElement element = null;
		try {
			if (identifier.endsWith("_xpath")) {
				customWait(identifier);
				Log.info("Entering the value in:-- " + identifier);
				//ATUReports.add("Entering the value'"+data+"' in:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 

				element=driver.findElement(By.xpath(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_id")) {
				Log.info("Entering the value in:-- " + identifier);

				element=driver.findElement(By.id(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_name")) {
				Log.info("Entering the value in:-- " + identifier);
				element=driver.findElement(By.name(OR.getProperty(identifier)));
						
			} else if (identifier.endsWith("_css")) {
				Log.info("Entering the value in:-- " + identifier);
				element=driver.findElement(By.cssSelector(OR.getProperty(identifier)));			
			}
			 ATUReports.add("Entering the value in: "+ identifier,data, true);
			//ATUReports.add("Entering the value'"+data+"' in:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    		element.clear();
			element.sendKeys(data);
		} catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to enter the value:-- " + t.getMessage());
			 ATUReports.add("Not able to Entering the value in: "+ identifier,data, true);

			//ATUReports.add("Not able to enter the value '"+data+ "'in'" + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
		  // closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}
	}



	public static String getText(String identifier) throws Exception {
		String text = null;
		try {
			if (identifier.endsWith("_xpath")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(By.xpath(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_id")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(By.id(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_name")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(By.name(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_css")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from:" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(
						By.cssSelector(OR.getProperty(identifier))).getText();
			}

		} catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to get the text:-- " + t.getMessage());
			ATUReports.add("Not able to get the text:" + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
		return text;
	}


	public static void selectDropDownByVisibleTextAndHandleException(String identifier, String value)
			throws Exception {

		try {
			if (identifier.endsWith("_xpath")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("Selecting: '" + value + "' from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				new Select(driver.findElement(By.xpath(OR
						.getProperty(identifier)))).selectByVisibleText(value);
			} else if (identifier.endsWith("_id")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("Selecting: '" + value + "' from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				new Select(
						driver.findElement(By.id(OR.getProperty(identifier))))
						.selectByVisibleText(value);
			} else if (identifier.endsWith("_name")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("Selecting: '" + value + "' from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				new Select(driver.findElement(By.name(OR
						.getProperty(identifier)))).selectByVisibleText(value);
			} else if (identifier.endsWith("_css")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("Selecting: '" + value + "' from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				new Select(driver.findElement(By.cssSelector(OR
						.getProperty(identifier)))).selectByVisibleText(value);
			}

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			//fail = true;
			Log.error("Not able to select the drop down List -- "+ t.getMessage());
			ATUReports.add("Not able to select the dropdown List "+value, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			//getScreen(document, fos, "Fail");
			//closeBrowser();
			//throw new Exception(" Stoping the script....!!!!");

		}

	}

	public static void captureScreenshot(String filename) throws IOException {
		try {
			Log.info("Taking ScreenShot");
			DateFormat dateFormat = new SimpleDateFormat("MM-yyyy-dd HH.mm.ss");
			Calendar cal = Calendar.getInstance();
			String dateFormate = dateFormat.format(cal.getTime());
			// System.out.println(dateFormat.format(cal.getTime()));
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
					+ "\\Screenshots\\" + filename + " ," + dateFormate
					+ ".jpg"));
			Log.info("ScreenShot is captured, to view image please go to "
					+ System.getProperty("user.dir") + "\\Screenshots\\"
					+ filename + "" + dateFormate + ".jpg");
					
		} catch (Throwable t) {
			// ErrorUtil.addVerificationFailure(t);
			// fail=true;
			Log.error("Not able to take screenshot :" + t.getMessage());

		}
	}
	public static void waitForPageLoad(int time) throws Exception {
		try {
			Log.info("Waiting for page load");
			Thread.sleep(time);
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to wait :" + t.getMessage());
			throw new Exception(" Stoping the script....!!!!");

		}
	}

	// close browser
	public static void closeBrowser() {
		Log.info("Closing the browser now");
		ATUReports.add("Closing the browser", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
		driver.quit();
	}


	public static String getTitle() throws Exception {
		String title;
		try {
			 title=driver.getTitle();
			 Log.info("Current Title"+ title);
		    // ATUReports.add("Current Title" + title, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to get Title");
			//ATUReports.add("Not able to get title" , LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
			//closeBrowser();
			throw new Exception("Not able to get Title....!!!!");
		
	}
		return title;
		
	}

	public static void verifyTitle(String ExpectedTitle) throws Exception {
		try {
			Assert.assertEquals(getTitle(), ExpectedTitle);
			Log.info(ExpectedTitle +"--Title is verified");
			ATUReports.add("Verifying the title of the Current Page",ExpectedTitle, getTitle(), false);

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to get Title");
			ATUReports.add("Verifying the title of the Current Page",ExpectedTitle, getTitle(), false);
			//closeBrowser();
			throw new Exception("Not able to get Title....!!!!");
		
	}
		
		
	}
	public boolean compareNumbers(int expectedVal, int actualValue) {
		try {
			Assert.assertEquals(actualValue, expectedVal);
			Log.info("Value matches with URL");
			ATUReports.add("Value matches with URL", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Log.error("Values do not match");
			ATUReports.add("Values do not match with URL", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
		}
		return true;
	}

	public boolean checkElementPresence(String xpathKey) {
		int count = driver.findElements(By.xpath(OR.getProperty(xpathKey)))
				.size();

		try {
			Assert.assertTrue(count > 0, "Element present");
			ATUReports.add("Element Present", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Log.error("No element present");
			ATUReports.add("Element not Present", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
		}
		return true;
	}

	public static void waitForWebElement(final String identifier, int timeout,
			int polling) throws IOException {
		try {
			Log.info("Waiting for page load");
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(timeout, TimeUnit.SECONDS)// change here
					.pollingEvery(polling, TimeUnit.SECONDS) // change here
					.ignoring(NoSuchElementException.class);
			WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					if (identifier.endsWith("_xpath")) {

						return driver.findElement(By.xpath(OR
								.getProperty(identifier)));
					} else if (identifier.endsWith("_id")) {

						return driver.findElement(By.id(OR
								.getProperty(identifier)));
					} else if (identifier.endsWith("_name")) {

						return driver.findElement(By.name(OR
								.getProperty(identifier)));
					} else if (identifier.endsWith("_css")) {

						return driver.findElement(By.cssSelector(OR
								.getProperty(identifier)));
					}
					return null;

				}
			});
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			ATUReports.add(identifier+ " is not present ",LogAs.INFO,null);
			Log.error(t.getMessage());
			
		}
	}


	@SuppressWarnings("deprecation")
	public static void waitForTextToBeDisplayed(String identifier, String text, int timeOut)throws Exception {

		WebElement element = null;
		try {
			Log.info("Waiting for the text to display: "+text);
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.withTimeout(timeOut, TimeUnit.SECONDS);
			wait.pollingEvery(1, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class);
			if (identifier.endsWith("_xpath")) {
				wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(OR.getProperty(identifier)), text));
				ATUReports.add("Waiting for the text "+text+" to display:", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Log.info("Waiting done for text: "+text);

			} else if (identifier.endsWith("_id")) {
				wait.until(ExpectedConditions.textToBePresentInElement(By.id(OR.getProperty(identifier)), text));
				ATUReports.add("Waiting for the text "+text+" to display:", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Log.info("Waiting done for text: "+text);
			} else if (identifier.endsWith("_css")) {
				wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector(OR.getProperty(identifier)), text));
				ATUReports.add("Waiting for the text "+text+" to display:", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Log.info("Waiting done for text: "+text);
			} else if (identifier.endsWith("_name")) {
				wait.until(ExpectedConditions.textToBePresentInElement(By.name(OR.getProperty(identifier)), text));
				ATUReports.add("Waiting for the text "+text+" to display:", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Log.info("Waiting done for text: "+text);
			}
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to click on -- " + t.getMessage());
			ATUReports.add("Not able to wait  " + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		    getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}

	}
	public static void waitForElementToBeClickable(String xpath, int time) throws Exception {
        
		try{
			Log.info("Waiting for element to Clickable: "+xpath );
			WebDriverWait explicitwait = new WebDriverWait(driver, time);
			explicitwait.withTimeout(time, TimeUnit.SECONDS);
			explicitwait.pollingEvery(2, TimeUnit.SECONDS);
			explicitwait.ignoreAll(Arrays.asList(StaleElementReferenceException.class, InvalidElementStateException.class, WebDriverException.class, NoSuchElementException.class, ElementNotVisibleException.class, TimeoutException.class));
			explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			Log.info("Element is Clickable now: "+xpath);
		}catch(Throwable t){
			Log.info("Not able to wait");
			Log.error(t);
		}
	}

	public static void verifyTextData(String identifier, String data)throws Exception {
		String strng = null;
		Log.info("verifying the presence of " + data);
		//ATUReports.add("verifying the presence of" + data, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		try {
			if (identifier.endsWith("_xpath")) {
				strng = driver.findElement(By.xpath(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_id")) {
				strng = driver.findElement(By.id(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_name")) {
				strng = driver.findElement(By.name(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_css")) {
				strng = driver.findElement(By.cssSelector(OR.getProperty(identifier))).getText();
			}

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Element Not Present ---  " + t.getMessage());
			ATUReports.add("Check whether the text is available ' "+identifier+" '",data,strng,true);
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
		try {
			Assert.assertEquals(strng, data);
			Log.info(data + " is verified successfully");
			//ATUReports.add(data + " is verified successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			ATUReports.add("Check whether the text is available' "+identifier+" '",data,strng,true);

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to verify " + t.getMessage());
			ATUReports.add("Not able to verify the text "+ data, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
	}




	public static void wait1() throws Exception {
        
		try{
			Log.info("Waiting for page load");
			Thread.sleep(2000);
			WebDriverWait explicitwait = new WebDriverWait(driver, 120);
			explicitwait.withTimeout(120, TimeUnit.SECONDS);
			explicitwait.pollingEvery(1, TimeUnit.SECONDS);
			explicitwait.ignoring(WebDriverException.class, NoSuchElementException.class);
			explicitwait.ignoring(StaleElementReferenceException.class, InvalidElementStateException.class);
			explicitwait.ignoring(ElementNotVisibleException.class, TimeoutException.class);
			explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-panel")));
			// explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[contains(.,'SEARCHING FOR BEST OFFERS')]")));
			// explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@alt='Charter Spectrum Loading Timer']")));
			Thread.sleep(5000);
			Log.info("Waiting Done");
		}catch(Throwable t){
          Log.error(t);
		}

	}

	public static void wait2() throws Exception {
		
		try{
		Log.info("Waiting for page load");
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.withTimeout(120, TimeUnit.SECONDS);
		wait.pollingEvery(4, TimeUnit.SECONDS);
		wait.ignoring(WebDriverException.class, NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class, InvalidElementStateException.class);
		wait.ignoring(ElementNotVisibleException.class, TimeoutException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("img.timer.center-block")));
		Thread.sleep(6000);
		// css=div.timer
		// css=p.text
		// css=img[alt="loading"]
		
		}catch(Throwable t){
	          Log.error(t);
			}
	}

	public static void wait3() throws Exception {
		try{
		Log.info("Waiting for page load");
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.withTimeout(120, TimeUnit.SECONDS);
		wait.pollingEvery(4, TimeUnit.SECONDS);
		wait.ignoring(WebDriverException.class, NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class, InvalidElementStateException.class);
		wait.ignoring(ElementNotVisibleException.class, TimeoutException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.timer")));
		Thread.sleep(6000);
		// css=div.timer
		// css=p.text
		// css=img[alt="loading"]
		}catch(Throwable t){
	          Log.error(t);
			}
	}

	public static void getScreen(XWPFDocument document, FileOutputStream fos,String filename) throws InvalidFormatException, IOException {

		DateFormat dateFormat = new SimpleDateFormat("MM-yyyy-dd HH.mm.ss");
		Calendar cal = Calendar.getInstance();
		String dateFormate = dateFormat.format(cal.getTime());
		// System.out.println(dateFormat.format(cal.getTime()));
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ "\\Screenshots\\" + filename + " ," + dateFormate + ".jpg"));
		Log.info("ScreenShot is captured and stored at "
				+ System.getProperty("user.dir") + "\\Screenshots\\" + filename
				+ "" + dateFormate + ".jpg");
		String filepath = FindLatestFile();
		String blipId = document.addPictureData(new FileInputStream(new File(
				filepath)), Document.PICTURE_TYPE_JPEG);
		// System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));
		// System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));
		createPicture(document, blipId,
				document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG), 620,380);

	}

	private static String FindLatestFile() {
		File file = new File(System.getProperty("user.dir") + "\\Screenshots");
		File[] listofFile = file.listFiles();
		long data = 0, data1;
		File latest = null;
		for (File file2 : listofFile) {
			data1 = file2.lastModified();
			if (data1 > data) {
				long temp = data;
				data = data1;
				data1 = temp;
				latest = file2;
			}
		}
		return latest.getAbsolutePath();
	}

	public static void createPicture(XWPFDocument document, String blipId,
			int id, int width, int height) {

		final int EMU = 9525;
		width *= EMU;
		height *= EMU;
		// String blipId =
		// getAllPictures().get(id).getPackageRelationship().getId();

		CTInline inline = document.createParagraph().createRun().getCTR()
				.addNewDrawing().addNewInline();

		String picXml = ""
				+ "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
				+ id
				+ "\" name=\"Generated\"/>"
				+ "            <pic:cNvPicPr/>"
				+ "         </pic:nvPicPr>"
				+ "         <pic:blipFill>"
				+ "            <a:blip r:embed=\""
				+ blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>"
				+ "               <a:fillRect/>"
				+ "            </a:stretch>"
				+ "         </pic:blipFill>"
				+ "         <pic:spPr>"
				+ "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>"
				+ "               <a:ext cx=\""
				+ width
				+ "\" cy=\""
				+ height
				+ "\"/>"
				+ "            </a:xfrm>"
				+ "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>"
				+ "            </a:prstGeom>"
				+ "         </pic:spPr>"
				+ "      </pic:pic>"
				+ "   </a:graphicData>" + "</a:graphic>";

		// CTGraphicalObjectData graphicData =
		// inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);

		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);

		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("Picture " + id);
		docPr.setDescr("Generated");
	}

	public static void createDoc(String streetAddress, String zipCode)throws Exception {
		XWPFDocument document = new XWPFDocument();
		FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Screenshots\\"+ streetAddress + " ," + zipCode + ".docx"));

	}

	public static void scrollPage() throws Exception {

		try {
			Log.info("Scrolling Page");
			ATUReports.add("Scrolling Page", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			//jse.executeScript("window.scrollBy(0,250)", "");
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to Scroll Page -- " + t.getMessage());
			ATUReports.add("Not able to Scroll Page", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Not able to Scroll Page....!!!!");
		}
	}
	public static void scrollPageNotFull() throws Exception {

		try {
			Log.info("Scrolling Page");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,800)", "");
			//jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to Scroll Page -- " + t.getMessage());
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Not able to Scroll Page....!!!!");
		}
	}
	public static void switchToFrame(String identifier) throws Exception {

		try {
			if (identifier.endsWith("_id")) {
				Log.info("Switching to:-- " + identifier);
				ATUReports.add("Switching to" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				int id = Integer.parseInt(OR.getProperty(identifier));
				System.out.println(id);
				
				driver.switchTo().frame(Integer.parseInt(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_name")) {
				Log.info("Switching to:-- " + identifier);
				ATUReports.add("Switching to" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				driver.switchTo().frame(OR.getProperty(identifier));
			} else if (identifier.endsWith("_xpath")) {
				Log.info("Switching to:-- " + identifier);
				ATUReports.add("Switching to" + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				driver.switchTo().frame(OR.getProperty(identifier));
						
			} 
			
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to switch Frame:-- " + t.getMessage());
			ATUReports.add("Clicking on: ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
			getScreen(document, fos, "Fail");
		   // closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}
	}

	// ================================================Broken Link Function=========================================
	// http://download.java.net/jdk7/archive/b123/docs/api/java/net/HttpURLConnection.html
	public static List findAllLinks(WebDriver driver) {
		List<WebElement> elementList = new ArrayList();
		elementList = driver.findElements(By.tagName("a"));
		elementList.addAll(driver.findElements(By.tagName("img")));
		List finalList = new ArrayList();
		for (WebElement element : elementList) {
			if (element.getAttribute("href") != null) {
				finalList.add(element);
			}
		}
		return finalList;
	}

	public static String isLinkBroken(URL url) throws Exception {
		String response = "";
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.connect();
			response = connection.getResponseMessage();
			connection.disconnect();
			return response;
		} catch (Exception exp) {
			return exp.getMessage();
		}
	}

	public static void checkForBrokenLinks() {

		Log.info("Checking for Broken Link");
		List<WebElement> allImages = findAllLinks(driver);
		Log.info("Total number of Links found " + allImages.size());
		for (WebElement element : allImages) {
			try {
				Log.info("URL: " + element.getAttribute("href") + " returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))));
				ATUReports.add("URL: " + element.getAttribute("href") + " returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))), LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				Log.info("URL: " + element.getAttribute("outerhtml")
						+ " returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))));
				ATUReports.add("URL: " + element.getAttribute("outerhtml")
						+ " returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))), LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			} catch (Exception exp) {
				Log.info("At " + element.getAttribute("innerHTML")
						+ " Exception occured at; " + exp.getMessage());
				ATUReports.add("At " + element.getAttribute("innerHTML")
						+ " Exception occured at; " + exp.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
			}
		}
	}

	public static String getCurrentDateTime() {

		DateFormat dateFormat = new SimpleDateFormat("MM-yyyy-dd HH.mm.ss");
		Calendar cal = Calendar.getInstance();
		String dateFormate = dateFormat.format(cal.getTime());
		return dateFormate;
	}
	public static void handleCustomizationResi(String TV, String Internet,String PhoneNewNumber, String PhonePrivate, String PhoneAlarm, String PhoneBatteryBackup) throws Exception {

		try {
			if (!TV.isEmpty()) {
				Log.info("Clicking on:-- " + TV);
				ATUReports.add("Clicking on: "  + TV, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				//driver.findElement(By.id(TV)).click();
				WebElement element =driver.findElement(By.id(TV));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", element);
			}
			if (!Internet.isEmpty()) {
				Thread.sleep(5000);
				Log.info("Clicking on:-- " + Internet);
				ATUReports.add("Clicking on: "  + Internet, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				WebElement element =driver.findElement(By.id(Internet));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", element);
				Thread.sleep(10000);
			}
			if (!PhoneNewNumber.isEmpty()) {
				Thread.sleep(5000);
				Log.info("Clicking on:-- " + PhoneNewNumber);
				ATUReports.add("Clicking on: "  + PhoneNewNumber, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				WebElement element =driver.findElement(By.id(PhoneNewNumber));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", element);
				Thread.sleep(3000);
				
			}
			if (!PhonePrivate.isEmpty()) {
				Thread.sleep(5000);
				Log.info("Clicking on:--" +PhonePrivate);
				ATUReports.add("Clicking on: "  + PhonePrivate, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				WebElement element =driver.findElement(By.id(PhonePrivate));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", element);			
			}
			if (!PhoneAlarm.isEmpty()) {
				Thread.sleep(5000);
				Log.info("Clicking on:--" +PhoneAlarm);
				ATUReports.add("Clicking on: "  + PhoneAlarm, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				WebElement element =driver.findElement(By.id(PhoneAlarm));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", element);				
			}
			if (!PhoneBatteryBackup.isEmpty()) {
				Thread.sleep(5000);
				Log.info("Clicking on:--" +PhoneBatteryBackup);
				ATUReports.add("Clicking on: "  + PhoneBatteryBackup, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				WebElement element =driver.findElement(By.id(PhoneBatteryBackup));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", element);				
			}
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error(" -- " + t.getMessage());
			ATUReports.add( t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
	}

	public static String getSourceCode(String Url) throws IOException,
			Exception {
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line = null;
		boolean flag = false;

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}

		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/* End of the fix */

		url = new URL(Url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int i = conn.getResponseCode();
		String str = Integer.toString(i);
		Log.info(conn.getResponseCode());
		//ATUReports.add("Responsecode : "+conn.getResponseMessage(),LogAs.INFO,null);
		if (conn.getResponseCode() != 404) {
			is = url.openStream(); // throws an IOException
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

		} else {
			
		}

		return line;
	}
	
	
	public static void waitForBrowserLoad() {
		String state = null;
		String oldstate = null;
		try {
			System.out.print("Waiting for browser loading to complete");
			int i = 0;
			while (i < 5) {
				Thread.sleep(1000);
				state = ((JavascriptExecutor) driver).executeScript(
						"return document.readyState;").toString();
				System.out.print("." + Character.toUpperCase(state.charAt(0))
						+ ".");
				if (state.equals("interactive") || state.equals("loading"))
					break;
				/*
				 * If browser in 'complete' state since last X seconds. Return.
				 */

				if (i == 1 && state.equals("complete")) {
					System.out.println();
					return;
				}
				i++;
			}
			i = 0;
			oldstate = null;
			Thread.sleep(2000);

			/*
			 * Now wait for state to become complete
			 */
			while (true) {
				state = ((JavascriptExecutor) driver).executeScript(
						"return document.readyState;").toString();
				System.out.print("." + state.charAt(0) + ".");
				if (state.equals("complete"))
					break;

				if (state.equals(oldstate))
					i++;
				else
					i = 0;
				/*
				 * If browser state is same (loading/interactive) since last 60
				 * secs. Refresh the page.
				 */
				if (i == 15 && state.equals("loading")) {
					System.out
							.println("\nBrowser in "
									+ state
									+ " state since last 60 secs. So refreshing browser.");
					driver.navigate().refresh();
					System.out.print("Waiting for browser loading to complete");
					i = 0;
				} else if (i == 6 && state.equals("interactive")) {
					System.out
							.println("\nBrowser in "
									+ state
									+ " state since last 30 secs. So starting with execution.");
					return;
				}

				Thread.sleep(4000);
				oldstate = state;

			}
			System.out.println();

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	
	
	public static void validateCurrentUlr() throws Exception {

		String CurrentUrl = null;
		try {
			Log.info("Getting the current url");
			CurrentUrl= driver.getCurrentUrl();
			Log.info(CurrentUrl);
			if(CurrentUrl.contains(CONFIG.getProperty("Confirmation-Nonauto_com"))){
				Log.info("Non-schedule-order");
			}
			else if(CurrentUrl.contains(CONFIG.getProperty("Confirmation-auto_com"))){
				
				Log.info("Schedule-order");
			}else{
				fail = true;
				Log.error("not able to Place Order -- ");
				//closeBrowser();
				throw new Exception(" not able to get current url....!!!!");	
			}
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("not able to get current url -- " + t.getMessage());
			//closeBrowser();
			throw new Exception(" not able to get current url....!!!!");
		}
	
	}
	
public static void javaScriptClick(String identifier) throws Exception {
		
		WebElement element = null;
		try {

			if (identifier.endsWith("_xpath")) {
				customWait(identifier);
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				element =driver.findElement(By.xpath(OR.getProperty(identifier)));
				
			} else if (identifier.endsWith("_id")) {
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				element =driver.findElement(By.id(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_name")) {
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				element =driver.findElement(By.name(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_css")) {
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				element =driver.findElement(By.cssSelector(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_linkText")) {
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				element =driver.findElement(By.linkText(OR.getProperty(identifier)));
			}else{
				Log.info("Clicking on:-- " + identifier);
				ATUReports.add("Clicking on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				element =driver.findElement(By.xpath(identifier));
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			
		} catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to click on -- " + t.getMessage());
			ATUReports.add("Not able to click on " + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
		    getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}
	}

public static void javaScriptClickAndHandleException(String identifier) throws Exception {
		
		WebElement element = null;
		try {

			if (identifier.endsWith("_xpath")) {
				Log.info("Clicking on:-- " + identifier+":-- and handling exception ");
				element =driver.findElement(By.xpath(OR.getProperty(identifier)));
				
			} else if (identifier.endsWith("_id")) {
				Log.info("Clicking on:-- " + identifier+":-- and handling exception ");
				element =driver.findElement(By.id(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_name")) {
				Log.info("Clicking on:-- " + identifier+":-- and handling exception ");
				element =driver.findElement(By.name(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_css")) {
				Log.info("Clicking on:-- " + identifier+":-- and handling exception ");
				element =driver.findElement(By.cssSelector(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_linkText")) {
				Log.info("Clicking on:-- " + identifier+":-- and handling exception ");
				element =driver.findElement(By.linkText(OR.getProperty(identifier)));
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			ATUReports.add("Clicking on - " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
			
		} catch (Throwable t) {

			Log.error("Not able to click but exception is handled");
			ATUReports.add("Not able to click - "+identifier, LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 

		}
	}
public static void javaScriptClear(String identifier) throws Exception {
		
		WebElement element = null;
		try {

			if (identifier.endsWith("_xpath")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
				element =driver.findElement(By.xpath(OR.getProperty(identifier)));
				
			} else if (identifier.endsWith("_id")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element =driver.findElement(By.id(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_name")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element =driver.findElement(By.name(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_css")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element =driver.findElement(By.cssSelector(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_linkText")) {
				Log.info("Clearing on:-- " + identifier);
				ATUReports.add("Clearing on " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				element =driver.findElement(By.linkText(OR.getProperty(identifier)));
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].value ='';", element);
			
		} catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to click on -- " + t.getMessage());
			ATUReports.add("Not able to click on "+ t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		    getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}
	}
public static void javaScriptInput(String identifier, String data) throws Exception {
	
	WebElement element = null;
	try {

		if (identifier.endsWith("_xpath")) {
			Log.info("Entering the value in:-- " + identifier);
			ATUReports.add("Entering the value in " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			element =driver.findElement(By.xpath(OR.getProperty(identifier)));
			
		} else if (identifier.endsWith("_id")) {
			Log.info("Entering the value in:-- " + identifier);
			ATUReports.add("Entering the value in " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			element =driver.findElement(By.id(OR.getProperty(identifier)));
		} else if (identifier.endsWith("_name")) {
			Log.info("Entering the value in:-- " + identifier);
			ATUReports.add("Entering the value in " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			element =driver.findElement(By.name(OR.getProperty(identifier)));
		} else if (identifier.endsWith("_css")) {
			Log.info("Entering the value in:-- " + identifier);
			ATUReports.add("Entering the value in " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			element =driver.findElement(By.cssSelector(OR.getProperty(identifier)));
		} else if (identifier.endsWith("_linkText")) {
			Log.info("Entering the value in:-- " + identifier);
			ATUReports.add("Entering the value in " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			element =driver.findElement(By.linkText(OR.getProperty(identifier)));
		}
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		String s1 ="arguments[0].value='";
		String s2=data+"';";
		String s=s1+s2;
		System.out.println(s);
		executor.executeScript("s", element);
		//executor.executeScript("arguments[0].value ='';", element);
	} catch (Throwable t) {

		ErrorUtil.addVerificationFailure(t);
		fail = true;
		Log.error("Not able to enter in -- " + t.getMessage());
		ATUReports.add("Not able to enter in " + t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    getScreen(document, fos, "Fail");
		//closeBrowser();
		throw new Exception(" Stoping the script....!!!!");

	}
}
	public static void verifyImageSoure(String identifier, String ExpectedImageSource) throws Exception {
		String src = null;
		Log.info("verifying the Image ");
		ATUReports.add("verifying the Image", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 

		try {
			if (identifier.endsWith("_xpath")) {
				src = driver.findElement(By.xpath(OR.getProperty(identifier))).getAttribute("src").toString();;
			} else if (identifier.endsWith("_id")) {
				src = driver.findElement(By.id(OR.getProperty(identifier))).getAttribute("src").toString();;
			
			} else if (identifier.endsWith("_name")) {
				src = driver.findElement(By.name(OR.getProperty(identifier))).getAttribute("src").toString();;
			} else if (identifier.endsWith("_css")) {
				src = driver.findElement(By.cssSelector(OR.getProperty(identifier))).getAttribute("src").toString();
			}
			Log.info("Actual image source--> " + src);
			Log.info("Expected  image source-->" +ExpectedImageSource);
			Assert.assertEquals(src, ExpectedImageSource);
			Log.info("verified successfully");
			ATUReports.add("verified successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to verify " + t.getMessage());
			ATUReports.add("Not able to verify"+ t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
	}

	
	public static String sendGETRequest(String url, String location, String ApiKey) {
        try {

              String endPoint="http://www.webpagetest.org/runtest.php?url="+url+"&location="+location+"&runs=1&f=json&k="+ApiKey;
              Log.info(endPoint);
              URL uri = new URL(endPoint);

              HttpURLConnection con = (HttpURLConnection) uri.openConnection();

              // optional default is GET
              con.setRequestMethod("GET");

              // add request header
              con.setRequestProperty("Content-Type", "application/json");
              Log.info(con.getResponseCode());
              if (con.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                          response.append(inputLine);
                    }
                    // System.out.println(response.toString());
                    in.close();

                    // print result
                    return response.toString();
              } else {
                    System.out.println("error caught ----");
              }
        } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
        }

        return null;

  }

  public static HashMap<String, String> readJsonValue(String completeson, String rootkey)

  {
      JSONObject jObject  = new JSONObject(completeson);
      JSONObject  menu = jObject.getJSONObject(rootkey);

      jsonHashMap = new HashMap<String,String>();
      Iterator iter = menu.keys();
      while(iter.hasNext()){
          String key = (String)iter.next();
          String value = menu.getString(key);
          jsonHashMap.put(key,value);
      }
        return (HashMap<String, String>) jsonHashMap;
  }

  public static void verifyJsonResponse(String response, String ExpectedJsonResponse)throws Exception {
    
	  Log.info("verifying the presence of " + ExpectedJsonResponse+ " in JSON ");
	  ATUReports.add("verifying the presence of " + ExpectedJsonResponse+ " in JSON ", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 

    try {
          Assert.assertEquals(true, response.contains(ExpectedJsonResponse));
          Log.info("JSON response "+ ExpectedJsonResponse + " is verified successfully");
          ATUReports.add("JSON response "+ ExpectedJsonResponse + " is verified successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
    } catch (Throwable t) {
          ErrorUtil.addVerificationFailure(t);
          fail = true;
          Log.error("Not able to verify " + t.getMessage());
          ATUReports.add("Not able to verify " + t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); 
          throw new Exception(" Stoping the script....!!!!");
    }
  }
  public static void verifyErrorMessageInResponse(String response, String ErrorMessage)throws Exception {
    Log.info("verifying the presence of " + ErrorMessage+ " in JSON ");
    ATUReports.add("verifying the presence of " + ErrorMessage+ " in JSON ", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

    try {
          Assert.assertEquals(false, response.contains(ErrorMessage));
          Log.info("JSON Error message "+ ErrorMessage + " is not exist in response ");
          ATUReports.add("JSON Error message "+ ErrorMessage + " is not exist in response ", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    } catch (Throwable t) {
          ErrorUtil.addVerificationFailure(t);
          fail = true;
          Log.error("Not able to verify " + t.getMessage());
          ATUReports.add("Not able to verify " + t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
          throw new Exception(" Stoping the script....!!!!");
    }
}
  public static void selectDropDownByIndex(String identifier, int index)
			throws Exception {

		try {
			if (identifier.endsWith("_xpath")) {
				Log.info("selecting " + index + " from drop Down List");
				ATUReports.add("selecting " + index + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.xpath(OR
						.getProperty(identifier)))).selectByIndex(index);
			} else if (identifier.endsWith("_id")) {
				Log.info("selecting " + index + " from drop Down List");
				ATUReports.add("selecting " + index + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.id(OR
						.getProperty(identifier)))).selectByIndex(index);
			} else if (identifier.endsWith("_name")) {
				Log.info("selecting " + index + " from drop Down List");
				ATUReports.add("selecting " + index + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.name(OR
						.getProperty(identifier)))).selectByIndex(index);
			} else if (identifier.endsWith("_css")) {
				Log.info("selecting " + index + " from drop Down List");
				ATUReports.add("selecting " + index + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.cssSelector(OR
						.getProperty(identifier)))).selectByIndex(index);
			}

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to select the drop down List -- "
					+ t.getMessage());
			ATUReports.add("Not able to select the drop down List -- "
					+ t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}

	}

	public static void selectDropDownByValue(String identifier, String value)throws Exception {

		try {
			if (identifier.endsWith("_xpath")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("selecting " + value + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.xpath(OR
						.getProperty(identifier)))).selectByValue(value);
			} else if (identifier.endsWith("_id")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("selecting " + value + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.id(OR
						.getProperty(identifier)))).selectByValue(value);
			} else if (identifier.endsWith("_name")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("selecting " + value + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.name(OR
						.getProperty(identifier)))).selectByValue(value);
			} else if (identifier.endsWith("_css")) {
				Log.info("selecting " + value + " from drop Down List");
				ATUReports.add("selecting " + value + " from drop Down List", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				new Select(driver.findElement(By.cssSelector(OR
						.getProperty(identifier)))).selectByValue(value);
			}

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to select the drop down List -- "
					+ t.getMessage());
			ATUReports.add("Not able to select the drop down List -- "
					+ t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}

	}
	public static void postText(String identifier) throws Exception {
		String text = null;
		try {
			if (identifier.endsWith("_xpath")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(By.xpath(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_id")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(By.id(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_name")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(By.name(OR.getProperty(identifier)))
						.getText();
			} else if (identifier.endsWith("_css")) {
				Log.info("Getting the text from:--" + identifier);
				ATUReports.add("Getting the text from " + identifier, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				text = driver.findElement(
						By.cssSelector(OR.getProperty(identifier))).getText();
			}
			Log.info("posting Confirmation Number in the excel:--" + text);
			
		} catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to get the text:-- " + t.getMessage());
			ATUReports.add("Not able to get the text:-- " + t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
	}
	
	public static WebElement getWebElement(String orElements[]) {
		WebElement element = null;
		boolean status = true;
		while (status) {
			for (String selector : orElements) {
				try {
					Thread.sleep(5000);
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
					
					element = driver.findElement(By.xpath(selector));
				} catch (Exception e) {
					
					continue;
				}
				if (element != null) {
					System.out.println("###################   Found Element #############################");
					status = false;
					break;
				}

			}
			status = false;
		}
		
		return element;
	}
	
	
	public static String createRuntimeXpath(String identifierFirstPart,String identifierSecondPart, String variableData) throws Exception {
		String xpath = null;
		try {
			xpath=OR.getProperty(identifierFirstPart)+variableData+OR.getProperty(identifierSecondPart);
			Log.info("Generated xpath is: "+xpath);

			
		} catch (Throwable t) {

		}
		return xpath;
	}
	
	public static void mouseOver(String identifier) throws Exception, IOException {
		try {
			Log.info("mouseOver to:--" +identifier);
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath(identifier))).build().perform();
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to mouseOver:-- " + t.getMessage());
			ATUReports.add("Not able to mouseOver:-- " + t.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
			//closeBrowser();
			throw new Exception(" Stoping the script....!!!!");
		}
	}
	
	public static void clickInsideClick() {
		String s1="(//i[@class='icon icon--s icon--grey icon--flat mdi mdi-delete'])[";
		String s2="]";
		String identifier;
		try {
			identifier=s1+1+s2;
            driver.findElement(By.xpath(identifier)).click();
            Log.info("clicking on:--" +identifier);
		    } catch (Throwable t2) {
			    try{
				     identifier=s1+2+s2;
	                 driver.findElement(By.xpath(identifier)).click();
	                 Log.info("clicking on:--" +identifier);
			        }catch (Throwable t3) {
				      try{
					      identifier=s1+3+s2;
		                  driver.findElement(By.xpath(identifier)).click();
		                  Log.info("clicking on:--" +identifier);
				           }catch (Throwable t4) {
					           try{
						         identifier=s1+4+s2;
			                     driver.findElement(By.xpath(identifier)).click();
			                     Log.info("clicking on:--" +identifier);
					             }catch (Throwable t5) {
					            	 try{
								         identifier=s1+5+s2;
					                     driver.findElement(By.xpath(identifier)).click();
					                     Log.info("clicking on:--" +identifier);
	                              }catch (Throwable t6) {
	                            	  Log.info("Not able to clicked on:--");
	                              }
				               }
				      }
			    }
		}
	}
	
	public static void customWait(String identifier) throws Exception {
		try{
			Log.info("Waiting for WebElement: "+identifier);
			Thread.sleep(1000);
			WebDriverWait explicitwait = new WebDriverWait(driver, 30);
			explicitwait.withTimeout(30, TimeUnit.SECONDS);
			explicitwait.pollingEvery(3, TimeUnit.SECONDS);
			explicitwait.ignoreAll(Arrays.asList(StaleElementReferenceException.class, InvalidElementStateException.class, WebDriverException.class, NoSuchElementException.class, ElementNotVisibleException.class, TimeoutException.class));
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(identifier))));
			Thread.sleep(1000);
			Log.info("WebElement is available: "+identifier);
		}catch(Throwable t){
			Log.error(t);
		}
	}
	
public static void uploadFile(String identifier, String filePath) throws Exception {
		
		WebElement element = null;
		try {
			if (identifier.endsWith("_xpath")) {
				Log.info("Entering the value in:-- " + identifier);
				element=driver.findElement(By.xpath(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_id")) {
				Log.info("Entering the value in:-- " + identifier);

				element=driver.findElement(By.id(OR.getProperty(identifier)));
			} else if (identifier.endsWith("_name")) {
				Log.info("Entering the value in:-- " + identifier);
				element=driver.findElement(By.name(OR.getProperty(identifier)));
						
			} else if (identifier.endsWith("_css")) {
				Log.info("Entering the value in:-- " + identifier);
				element=driver.findElement(By.cssSelector(OR.getProperty(identifier)));			
			}
			 ATUReports.add("Entering the value in: "+ identifier,filePath, true);
			element.sendKeys(filePath);
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			fail = true;
			Log.error("Not able to enter the value:-- " + t.getMessage());
			 ATUReports.add("Not able to Entering the value in: "+ identifier,filePath, true);
			//ATUReports.add("Not able to enter the value '"+data+ "'in'" + identifier, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			getScreen(document, fos, "Fail");
		  // closeBrowser();
			throw new Exception(" Stoping the script....!!!!");

		}
	}
}