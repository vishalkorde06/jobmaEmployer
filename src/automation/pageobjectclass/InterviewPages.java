package automation.pageobjectclass;

import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automation.baseclass.TestBase;
import automation.excelread.ExcelUtils;

public final class InterviewPages extends TestBase {

	@FindBy(xpath="//button[contains(@id,'okay-button')]")
	public WebElement ToolTipOkButton_Click_here_to_choose_your_preferred_language;
	
	@FindBy(xpath = "//*[@class=\"modal-content\"]//button[contains(text(),'Switch to this device.')]")
	public WebElement SwitchToThisDeviceButton_Interview_already_in_progress;

	@FindBy(xpath = "//*[@id=\"get-started-button\"]")
	public WebElement GetStartedButton;
	
	@FindBy(xpath="//*[@id=\"continue-button\"]")
	public WebElement ContinueButtonOnInterviewLandingPgae;

	@FindBy(xpath = "//span[@role=\"button\"]")
	public WebElement CloseRegistrationPopUp;

	@FindBy(xpath = "//li[@class=\"header__search-link \"]")
	public WebElement SearchIcon;

	@FindBy(xpath = "//input[@id=\"search-drawer-input\"]")
	public WebElement SearchBar;

	@FindBy(xpath ="//div[@class=\"predictive-search__products\"]//div[@class=\"product-card__figure\"]//a")
	public WebElement SearchedProduct;

	@FindBy(xpath ="//*[@class=\"predictive-search__products\"]//*[@class=\"product-card__figure\"]")
	public WebElement ResultProduct;
	
	@FindBy(xpath="//*[@class=\"cookie-notification-body\"]//button[contains(text(),'Accept All')]")
	public WebElement AcceptAll_Cookies;
	
	@FindBy(xpath="//*[@id=\"interview-steps\"]")
	public WebElement InterviewSteps;
	
	@FindBy(xpath = "//*[@id='interview-steps']//input[contains(@id,'flexCheckDefault')]")
	public List<WebElement> Checkboxes;
	
	public InterviewPages() {
		PageFactory.initElements(driver, this);
	}



}
