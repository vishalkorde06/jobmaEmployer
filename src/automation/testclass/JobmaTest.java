package automation.testclass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import automation.baseclass.TestBase;
import automation.excelread.ExcelUtils;

public class JobmaTest extends TestBase {

	@Test
	public void To_Verify_That_User_Is_Able_To_Login_JobmaEmployer() throws InterruptedException, IOException {
		//String productToSearch = ExcelUtils.getCellDataString(1, 2);
        test.info("Shopify form fill method started");
        jlp.JobmaLogin();
        //dp.Pre_RecordedInterviewFlow_TrackPage_CandidateLink();
        Thread.sleep(20000);
		       
							
	}
	
	
	
	
	
	@AfterMethod
	public void tearDown() {
		
		if (driver != null) {
			driver.quit();
		}
	}

}
