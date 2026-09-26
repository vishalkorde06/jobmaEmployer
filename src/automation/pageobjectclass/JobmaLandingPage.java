package automation.pageobjectclass;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import automation.baseclass.TestBase;
import automation.excelread.ExcelUtils;

public class JobmaLandingPage extends TestBase {

	@FindBy(xpath = "//*[@class=\"nav-link\"][contains(text(),'Login')]")
	public WebElement Login;

	@FindBy(css = "[id='jobma_user_name']")
	public WebElement Email;

	@FindBy(css = "[id='password']")
	public WebElement Password;

	@FindBy(css = "[id='submitBtn']")
	public WebElement LoginButton;

	@FindBy(xpath = "//*[@class=\"footer-links txt-light smaller\"]/a")
	public List<WebElement> FooterLinks;

	@FindBy(xpath = "//*[@class=\"breadcrumbs-box\"]//span[contains(@class,'current')]")
	public WebElement PageNameOnBreadCumBox;

	@FindBy(xpath = "(//footer/div[@class=\"container\"])[1]")
	public WebElement Footer;

	public JobmaLandingPage() {
		PageFactory.initElements(driver, this);
	}

	public void JobmaLogin() throws IOException {
		String username = ExcelUtils.getCellDataString(1, 0);
		String password = ExcelUtils.getCellDataString(1, 1);

//		waitForElement(driver, Duration.ofSeconds(time), jlp.Login);
//		test.info("User is successfully landed on jobma home page");
//		test.addScreenCaptureFromPath(getScreenShotForExtent("Login"));
//
//		jlp.Login.click();

		waitForElement(driver, Duration.ofSeconds(time), jlp.Email);
		jlp.Email.click();
		jlp.Email.clear();
		jlp.Email.sendKeys(username);
		test.info("User is successfully entered email id inside the email field");

		waitForElement(driver, Duration.ofSeconds(time), jlp.Password);
		jlp.Password.click();
		jlp.Password.clear();
		jlp.Password.sendKeys(password);
		test.info("User is successfully entered password inside the password field");

		waitForElement(driver, Duration.ofSeconds(time), jlp.LoginButton);
		test.addScreenCaptureFromPath(getScreenShotForExtent("LoginButton"));
		jlp.LoginButton.click();

	}

}
