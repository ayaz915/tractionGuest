package com.tractionGuest.suiteLocation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.tractionGuest.util.TestUtil;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class CreateBulkInviteOnExistingLoc extends TestSuiteBase{
	{
        System.setProperty("atu.reporter.config", "./src/com/tractionGuest/config/atu.properties");
        }
	
	String runmodes[]=null;
	static int count=-1;

	
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip(){
		
		if(!TestUtil.isTestCaseRunnable(LocationsuiteXls,this.getClass().getSimpleName())){
			Log.info("Skipping Test Case "+this.getClass().getSimpleName()+" as runmode set to NO");//logs
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(LocationsuiteXls, this.getClass().getSimpleName());
	}
	
	  @Test(dataProvider="getTestData")
       public void createBulkInviteOnExistingLoc(
                String testCaseName,
	            String browserType,
	            String url,
	            String username,
				String password,
				String locationNameExisting,
				String first_name,
				String last_name,
				String email,
				String company,
				String start_date,
				String end_date			

							) throws Exception{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
		String testName="CreateBulkInviteOnExistingLoc"+" ("+testCaseName+")";
	
      Log.info("================================ Executing "+testName+"======================================================");
      Log.info("==================================================================================================================================================");	  

      setAuthorInfoForReports();		
	     openBrowser(browserType);
	     navigate(url);
		 ATUReports.add("login Page",LogAs.INFO,null);
		 input("loginPage_txtbx_username_xpath", username);
		 input("loginPage_txtbx_password_xpath", password);
		 click("loginPage_btn_signIn_xpath");
		 waitForTextToBeDisplayed("guestBook_txt_fullView_xpath", "All | Full View", 30);
		 click("guestBook_btn_locations_xpath");
		 waitForTextToBeDisplayed("locations_txt_locations_xpath", "Locations", 30);
		 String xpath=createRuntimeXpath("locations_lnk_locationNamePart1_xpath", "locations_lnk_locationNamePart2_xpath", locationNameExisting);
		 waitForElementToBeClickable(xpath, 30);
		 javaScriptClick(xpath);
		 waitForWebElement("locations_txtbx_locationName_xpath", 30, 1);
		 click("locations_tab_invites_xpath");
		 waitForWebElement("locations_icon_uploadCSV_xpath", 30, 1);
		 click("locations_icon_uploadCSV_xpath");
		 customWait("locations_btn_upload_xpath");
		 uploadFile("locations_inputFile_browse_xpath", "C:\\Users\\T935219\\Desktop\\Traction Guest\\Invited Guests Template.csv");
		 click("locations_btn_upload_xpath");
		 waitForTextToBeDisplayed("commanPage_txt_notificationMsg_xpath", "Invited Guests Uploaded", 30);
		 refresh();
		 click("locations_tab_invites_xpath");
	}
	
	@AfterMethod
	public void reportDataSetResult() throws Exception{

		if(skip){
			TestUtil.reportDataSetResult(LocationsuiteXls, this.getClass().getSimpleName(), count+2, "SKIP");
		}
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(LocationsuiteXls, this.getClass().getSimpleName(), count+2, "FAIL");
			//closeBrowser();
		}
		else{
			TestUtil.reportDataSetResult(LocationsuiteXls, this.getClass().getSimpleName(), count+2, "PASS");
			//closeBrowser();
		}
		skip=false;
		fail=false;	
		
	}
	@AfterTest
	public void reportTestResult(){
		if(isTestPass)
			TestUtil.reportDataSetResult(LocationsuiteXls, "Test Cases", TestUtil.getRowNum(LocationsuiteXls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(LocationsuiteXls, "Test Cases", TestUtil.getRowNum(LocationsuiteXls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(LocationsuiteXls, this.getClass().getSimpleName()) ;
	}
}
