package pages;

import java.time.Month;
import java.util.List;

import javax.swing.text.Document;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Yatra_HomePage {
	WebDriver driver;
	WebDriverWait wait;

	public Yatra_HomePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 5);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "booking_engine_flights")
	WebElement menu_flights;

	@FindBy(xpath = "//a[@title='One Way']")
	WebElement oneTime;

	@FindBy(id = "BE_flight_origin_city")
	WebElement txtFrom;

	@FindBy(id = "BE_flight_arrival_city")
	WebElement txtTo;

	@FindBy(id = "BE_flight_origin_date")
	WebElement departureDate;

	@FindBy(xpath = "//div[@class='month-title']")
	List<WebElement> txtMonth;;

	@FindBy(xpath = "//a[@for='BE_flight_non_stop']/i")
	WebElement ChkBoxnonStop;

	@FindBy(css = "input#BE_flight_flsearch_btn.js_ripple.search-btn")
	WebElement btnSearchFlights;

	@FindBy(xpath = "//div/span[text()='Depart Time ']")
	WebElement departTime;

	@FindBy(xpath = "//p[text()='06 - 12']")
	WebElement timeOfDeparture;

	@FindBy(xpath = "//input[@value='Apply Filters']")
	WebElement btnApplyFilters;

	@FindBy(xpath = "(//label[contains(@id,'fare-')]//p)[1]")
	WebElement txtPriceOfFirstFlight;

	@FindBy(xpath = "//div[@class='tablecell button-wrap right']/button[text()='View More >>']")
	WebElement btnViewMore;

	@FindBy(xpath = "//div/i[@class='we_forward']")
	WebElement we_forward;

	public void clickOnMenuFlights() {
		wait.until(ExpectedConditions.visibilityOf(menu_flights));
		menu_flights.click();
	}

	public void clickOnOneTime() {
		oneTime.click();
	}

	public void setFromCity(String fromPlace) throws InterruptedException {
		txtFrom.click();
		Thread.sleep(1000);
		txtFrom.clear();
		txtFrom.sendKeys(fromPlace);
		Thread.sleep(1000);
		txtFrom.sendKeys(Keys.ENTER);
	}

	public void setToCity(String toPlace) throws InterruptedException {
		txtTo.clear();
		txtTo.sendKeys(toPlace);
		Thread.sleep(1000);
		txtTo.sendKeys(Keys.ENTER);
	}

	public void selectDepartureDate(int day, Month month, int year) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfAllElements(departureDate));
		departureDate.click();
		Thread.sleep(1000);
		int monthsize = txtMonth.size();
		for (int i = 0; i <= monthsize - 1; i++) {
			String actMonth = txtMonth.get(i).getText();
			String[] getMonth = actMonth.split("' ");
			String monthValue = new StringBuffer().append(getMonth[0]).toString();
			String yearValue = new StringBuffer().append(getMonth[1]).toString();
			if ((month + " " + year).equalsIgnoreCase(monthValue + " 20" + yearValue)) {
				WebElement date = driver.findElement(By.xpath("(//div[@class='month-title'])[" + i + 1
						+ "]/following-sibling::div/table/tbody/tr/td[contains(@title,'" + day + " " + monthValue + " "
						+ year + "')]"));
				date.click();
				break;
			}
		}
	}

	public void clickOnNonStop() {
		wait.until(ExpectedConditions.visibilityOf(ChkBoxnonStop));
		ChkBoxnonStop.click();
	}

	public void clickOnButtonSearchFlights() throws InterruptedException {
		driver.switchTo().frame("webklipper-publisher-widget-container-notification-frame");
		we_forward.click();
		driver.switchTo().defaultContent();
		btnSearchFlights.click();
	}

	public void selectDepartTime() {
		wait.until(ExpectedConditions.elementToBeClickable(departTime));
		departTime.click();
		wait.until(ExpectedConditions.elementToBeClickable(timeOfDeparture));
		timeOfDeparture.click();
	}

	public void ClickOnButtonApplyFiletrs() {
		wait.until(ExpectedConditions.elementToBeClickable(btnApplyFilters));
		btnApplyFilters.click();
	}

	public String getPriceOfFirstFlight() {
		String price = txtPriceOfFirstFlight.getText();
		return price;
	}
}
