package automation.pageobjectclass;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.baseclass.TestBase;

public class DashboardPage extends TestBase {
	@FindBy(xpath = "//*[@class=\"nav-txt\"][contains(text(),'Applicants')]")
	public WebElement Applicants;

	@FindBy(xpath = "//*[@class=\"nav-txt\"][contains(text(),'Track')]")
	public WebElement Track;

	@FindBy(xpath = "//*[@class=\"nav-txt\"][contains(text(),'Evaluate')]")
	public WebElement Evaluate;

	@FindBy(css = "[id='submitBtn']")
	public WebElement LoginButton;

	// Track Page

	@FindBy(xpath = "(//*[@class=\"list-main-info flex-grow-1\"]//span[contains(text(),'Invited')]/ancestor::div[@class=\"list-main-info flex-grow-1\"]//button[contains(@class,'view_invitation_details')])[1]")
	public WebElement ViewDetail_Invited;

	@FindBy(xpath = "(//*[@class=\"list-main-info flex-grow-1\"]//span[contains(text(),'In Progress')]/ancestor::div[@class=\"list-main-info flex-grow-1\"]//button[contains(@class,'view_invitation_details')])[1]")
	public WebElement ViewDetails_InProgerss;

	@FindBy(xpath = "(//*[@class=\"list-main-info flex-grow-1\"]//span[contains(text(),'Completed')]/ancestor::div[@class=\"list-main-info flex-grow-1\"]//button[contains(@class,'view_invitation_details')])[1]")
	public WebElement ViewDetails_Completed;

	@FindBy(xpath = "//*[@id=\"candidate_live_url_btn\"]")
	public WebElement CandidateLink;

	public DashboardPage() {
		PageFactory.initElements(driver, this);
	}

	public void Pre_RecordedInterviewFlow_TrackPage_CandidateLink() throws IOException, InterruptedException {
		jlp.JobmaLogin();

		waitForElement(driver, Duration.ofSeconds(time), dp.Applicants);
		dp.Applicants.click();

		waitForElement(driver, Duration.ofSeconds(time), dp.Track);
		dp.Track.click();

		waitForElement(driver, Duration.ofSeconds(time), dp.ViewDetails_InProgerss);
		dp.ViewDetails_InProgerss.click();

		waitForElement(driver, Duration.ofSeconds(time), dp.CandidateLink);
		String interviewLink = dp.CandidateLink.getAttribute("data-url");
		System.out.println("Interview link :-" + interviewLink);

		driver.navigate().to(interviewLink);

		try {
			waitForElement(driver, Duration.ofSeconds(time),
					ip.ToolTipOkButton_Click_here_to_choose_your_preferred_language);
			ip.ToolTipOkButton_Click_here_to_choose_your_preferred_language.click();
		}

		catch (Exception e) {
			waitForElement(driver, Duration.ofSeconds(time), ip.SwitchToThisDeviceButton_Interview_already_in_progress);

			ip.SwitchToThisDeviceButton_Interview_already_in_progress.click();

			waitForElement(driver, Duration.ofSeconds(time),
					ip.ToolTipOkButton_Click_here_to_choose_your_preferred_language);
			ip.ToolTipOkButton_Click_here_to_choose_your_preferred_language.click();

		}
		
	   waitForElement(driver, Duration.ofSeconds(time), ip.AcceptAll_Cookies);
		new Actions(driver).moveToElement(ip.AcceptAll_Cookies).click().perform();


		try {
			Thread.sleep(10000);
			waitForElement(driver, Duration.ofSeconds(time), ip.GetStartedButton);
			new Actions(driver).moveToElement(ip.GetStartedButton).click().perform();
		} 
		
		catch (Exception e) {
			waitForElement(driver, Duration.ofSeconds(time), ip.ContinueButtonOnInterviewLandingPgae);
			new Actions(driver).moveToElement(ip.ContinueButtonOnInterviewLandingPgae).click().perform();
			Thread.sleep(20000);

		}
		
		for (int i = 0; i < ip.Checkboxes.size(); i++) {
		    ip.Checkboxes.get(i).click();
		}

	}

}
