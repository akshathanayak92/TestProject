package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import pages.MakeMyTrip_HomePage;
import pages.Yatra_HomePage;

public class BaseClass {

	protected WebDriver driver;
	protected static String path = System.getProperty("user.dir");
	protected MakeMyTrip_HomePage mmt_homePg;
	protected Yatra_HomePage yathra_homePg;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest testLogger;

	void intialization() {
		mmt_homePg = new MakeMyTrip_HomePage(driver);
		yathra_homePg = new Yatra_HomePage(driver);

		htmlReporter = new ExtentHtmlReporter(path + "\\Reports\\extent-report.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	/**
	 * This Method open the browser and Launch the Url
	 * 
	 * @param browser
	 * @param url
	 */
	@BeforeClass
	@Parameters({ "browser" })
	public void launchBrowser(String browser) {
		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", path + "\\drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", path + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.ie.driver", path + "\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		intialization();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
	}

	public void launchurl(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	/**
	 * This method will close all the driver instances
	 */
	@AfterClass
	void tearDown() {
		// driver.quit();
	}

	/**
	 * Script to Capture Screenshot
	 * 
	 * @param imageDescription
	 * @return String : destinationPath
	 * @throws IOException
	 */
	public String takeScreenshot(WebDriver driver, String imageDescription) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		// Taking screenshot
		File Screenshot = ts.getScreenshotAs(OutputType.FILE);
		// Creating the path to save the file
		String destinationPath = path + "\\Reports\\Screenshots\\" + imageDescription + dateName + ".png";
		// creating destination file to save the screenshot
		File destinationFile = new File(destinationPath);
		// copying Screenshot to destination file
		FileUtils.copyFile(Screenshot, destinationFile);
		return destinationPath;
	}

	/**
	 * capturescreen shot after test Pass/Fail And Generate ExtentReport
	 * 
	 * @param result
	 * @throws IOException
	 */
	@AfterMethod
	public void capturescreenshotAndGenerateExtentReport(ITestResult result) throws IOException {
		String imageDescription = result.getMethod().getMethodName();
		if (result.getStatus() == ITestResult.FAILURE) {
			testLogger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case FAILED", ExtentColor.RED));
			testLogger.fail(result.getThrowable().getMessage());
			String screenShotLocation = takeScreenshot(driver, imageDescription);
			testLogger.fail("Test Case failed check screenshot below."
					+ testLogger.addScreenCaptureFromPath(screenShotLocation));
		} else if (result.getStatus() == ITestResult.SKIP) {
			testLogger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case SKIPPED", ExtentColor.YELLOW));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			testLogger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " - Test Case PASSED", ExtentColor.GREEN));
			String screenShotLocation = takeScreenshot(driver, imageDescription);
			testLogger.pass("Test Case passed check screenshot below."
					+ testLogger.addScreenCaptureFromPath(screenShotLocation));
		}
	}

	/**
	 * Flush Extent Report-Append the HTML file with all the ended tests.
	 */
	@AfterSuite
	public void cleanup() {
		driver.quit();
		extent.flush();
	}
}
