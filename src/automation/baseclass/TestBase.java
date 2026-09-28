package automation.baseclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import automation.excelread.ExcelUtils;
import automation.pageobjectclass.DashboardPage;
import automation.pageobjectclass.InterviewPages;
import automation.pageobjectclass.JobmaLandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Properties;
import java.io.File;

public class TestBase {
	protected static final String projectPath = System.getProperty("user.dir");
	public static WebDriver driver;
	public static ExtentSparkReporter sparkReporter;

	public static ExtentReports extent;
	public static ExtentTest test;
	public static Properties prop = new Properties();
	public static int time = 50;
	public static ExcelUtils data;
	public static JobmaLandingPage jlp;
	public static DashboardPage dp;
	public static InterviewPages ip;

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeClass
	public static void setExtent() {
		String reportPath = projectPath + "/ExtentReports/Report_" + getCurrentTime() + ".html";
		sparkReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("ProjectName", "Ascendliv");
		extent.setSystemInfo("Env", "Production");
		extent.setSystemInfo("QA", "Vishal Korde");
	}

	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE_yyyy_MM_dd_HH_mm");
		Date date = new Date();
		return formatter.format(date);
	}

	@AfterClass
	public static void endExtent() {
		extent.flush();
	}

	public void loadData() throws IOException {
		FileInputStream fis = new FileInputStream(projectPath + "/src/automation/config/config.properties");
		prop.load(fis);
	}

	public void writeDate() throws IOException {
		FileOutputStream fos = new FileOutputStream(projectPath + "/src/automation/config/config.properties");
		prop.store(fos, null);
	}

	public void selectBrowser(String browser) {
	    switch (browser.toLowerCase()) {
	    case "chrome":
	        // Modern selenium automatically resolves drivers, setup() is optional
	        WebDriverManager.chromedriver().setup();
	        ChromeOptions chromeOptions = new ChromeOptions();
	        chromeOptions.addArguments("--incognito");
	        driver = new ChromeDriver(chromeOptions);
	        break;

	    case "edge":
	        // Removed WebDriverManager.edgedriver().setup() to fix msedgedriver.azureedge.net error
	        EdgeOptions edgeOptions = new EdgeOptions();
	        edgeOptions.addArguments("-inprivate"); // Edge uses '-inprivate' instead of '--incognito'
	        driver = new EdgeDriver(edgeOptions);
	        break;

	    default:
	        throw new IllegalArgumentException("Unsupported browser: " + browser);
	    }

	    if (driver != null) {
	        driver.manage().deleteAllCookies();
	        driver.manage().window().maximize();
	    }
	}
	public void getURL(String url) {
		driver.get(url);
	}

	@Parameters("browser")
	@BeforeMethod()
	public void init(String browserFromXML) throws IOException {
		loadData();
		String browser;
		if (browserFromXML != null && !browserFromXML.trim().isEmpty()) {
			browser = browserFromXML;
		} else {
			browser = prop.getProperty("browser");
		}

		if (browser != null && !browser.trim().isEmpty()) {
			selectBrowser(browser);
		} else {
			System.out.println("Browser parameter missing in both testng.xml and properties file");
			return;
		}

		String url = prop.getProperty("Jobma");
		getURL(url);

		data = new ExcelUtils();
		jlp = new JobmaLandingPage();
		dp = new DashboardPage();
		ip = new InterviewPages();

	}

	public static String getScreenShotForExtent(String ScreenshotName) throws IOException {

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destionation = (projectPath + "/Screenshot/Screenshot_" + ScreenshotName + "_" + getCurrentTime()
				+ ".jpg");
		FileUtils.copyFile(srcFile, new File(destionation));
		return destionation;
	}

	public static void waitForElement(WebDriver driver, Duration timeOutInSeconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElements(WebDriver driver, Duration timeOutInSeconds, List<WebElement> element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
	}

	public void waitForElementToBeClickable(WebDriver driver, Duration timeOutInSeconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static WebElement ShadowElement(SearchContext shadowRoot, By locator, Duration timeout) {
		return new FluentWait<>(shadowRoot).withTimeout(timeout).pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class).until(ctx -> ctx.findElement(locator));
	}

	public static void scriptExecutorClick(WebDriver driver, WebElement element) throws InterruptedException {
		// Creating JavaScriptExecuter Interface
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Execute javascript
		js.executeScript("arguments[0]. click();", element);
	}

	public static void moveToElement(WebDriver driver, WebElement element) throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}

	public static void doubleClickOnElement(WebDriver driver, WebElement element) throws InterruptedException {

		Actions actions = new Actions(driver);
		// actions.moveToElement(element).build().perform();
		actions.doubleClick(element).build().perform();
	}

	public static void ScrollToElement(WebDriver driver, WebElement element) throws InterruptedException {

		Point point = element.getLocation();
		int x_coordinate = point.getX();
		int y_coordinate = point.getY();
		JavascriptExecutor javScriptExecutor = (JavascriptExecutor) driver;
		javScriptExecutor.executeScript("window.scrollBy(" + x_coordinate + ", " + y_coordinate + ");");
	}

	public static void ScrollDownToElement(WebDriver driver, int cordinate) throws InterruptedException {

		JavascriptExecutor javScriptExecutor = (JavascriptExecutor) driver;
		javScriptExecutor.executeScript("window.scrollBy(" + 0 + ", " + cordinate + ");");
	}

	public static void ScrollToRight(WebDriver driver, int cordinate) throws InterruptedException {

		JavascriptExecutor javScriptExecutor = (JavascriptExecutor) driver;
		javScriptExecutor.executeScript("window.scrollBy(" + cordinate + ", " + 0 + ");");
	}

	public static void OpenNewTabInSameBrowser(WebDriver driver, String URLToLaunch) throws InterruptedException {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String jsOpenNewWindow = "window.open('" + URLToLaunch + "');";
		jsExecutor.executeScript(jsOpenNewWindow);
		Thread.sleep(1000);
	}

	public static void SwitchToNewlyOpenedWindow(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public static void ScrollDownToPerticularElement(WebDriver driver, WebElement element) throws InterruptedException {

		JavascriptExecutor javScriptExecutor = (JavascriptExecutor) driver;
		javScriptExecutor.executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void SwitchToWindow() throws InterruptedException {

		Thread.sleep(2000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

	}

	public static void ScrollUpToElement(WebDriver driver, int cordinate) throws InterruptedException {

		JavascriptExecutor javScriptExecutor = (JavascriptExecutor) driver;
		javScriptExecutor.executeScript("window.scrollTo(" + cordinate + ",document.body.scrollHeight)");

	}

	public Iterator<String> getAllWindows() {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		return itr;
	}

	public WebElement waitForElement(WebDriver driver, WebElement element, Duration timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

}
