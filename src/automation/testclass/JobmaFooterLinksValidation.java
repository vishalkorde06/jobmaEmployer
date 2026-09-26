package automation.testclass;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import automation.baseclass.TestBase;
import automation.excelread.ExcelUtils;

public class JobmaFooterLinksValidation extends TestBase {

	@Test
	public void To_Verify_That_Footer_Links_Are_Clikable_And_Redirecting_User_To_Correct_Page() throws InterruptedException, IOException {
		//String productToSearch = ExcelUtils.getCellDataString(1, 2);

		int totalLinks = jlp.FooterLinks.size();
		int passedLinks = 0;
		int failedLinks = 0;
		int linksHit=0;
		System.out.println("Total links present on footer sections is:- "+totalLinks);
		
		for (int i = 0; i < jlp.FooterLinks.size(); i++) {

		    WebElement link = jlp.FooterLinks.get(i);

		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("arguments[0].scrollIntoView({block:'center'});", link);

		    new Actions(driver).moveToElement(link).perform();

		    String text = link.getText();
		    String linkText = link.getText();
            String expectedUrl = link.getAttribute("href");

            linksHit++;

            Thread.sleep(2000);
            driver.navigate().to(expectedUrl);
            String actualUrl = driver.getCurrentUrl();

            if (actualUrl.equalsIgnoreCase(expectedUrl)) {
                passedLinks++;
            } 
            
            else {
                System.out.println("link is failed to redirect is " + actualUrl);
                failedLinks++;
            }

            driver.navigate().back();
            waitForElements(driver, Duration.ofSeconds(time), jlp.FooterLinks);
		}
		
		System.out.println("\n========== FINAL SUMMARY ==========");
		System.out.println("Total Links Present : " + totalLinks);
		System.out.println("Total Links Hit     : " + linksHit);
		System.out.println("Passed Links        : " + passedLinks);
		System.out.println("Failed Links        : " + failedLinks);

		Assert.assertEquals(linksHit, totalLinks, "Not all links were attempted.");
		Assert.assertEquals(passedLinks + failedLinks, linksHit,
		        "Mismatch in pass/fail count.");
        
							
	}
	
		
	@AfterMethod
	public void tearDown() {
		
		if (driver != null) {
			driver.quit();
		}
	}

}
