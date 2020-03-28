package pages;

import java.time.Month;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MakeMyTrip_HomePage {
	WebDriver driver;
	WebDriverWait wait;

	public MakeMyTrip_HomePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 5);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[@class='chNavIcon appendBottom2 chSprite chFlights active']")
	WebElement menu_flights;

	@FindBy(xpath = "//li[@data-cy='oneWayTrip']")
	WebElement oneTime;

	@FindBy(id = "fromCity")
	WebElement txtFrom;

	@FindBy(xpath = "//input[@placeholder='From']")
	WebElement txtBoxFrom;

	@FindBy(xpath = "//div[@id='react-autowhatever-1']//li//p[1]")
	List<WebElement> listFrom;

	@FindBy(id = "toCity")
	WebElement txtTo;

	@FindBy(xpath = "//input[@placeholder='To']")
	WebElement txtBoxTo;

	@FindBy(xpath = "//div[@id='react-autowhatever-1']//li//p[1]")
	List<WebElement> listTo;

	@FindBy(xpath = "//label[@for='departure']/span")
	WebElement departure;

	@FindBy(xpath = "//*[@class='DayPicker-Month']/div[@class='DayPicker-Caption']/div")
	List<WebElement> DayPicker_Month;

	@FindBy(xpath = "//*[@class='DayPicker-Month']/div[@class='DayPicker-Body']//div[@class='dateInnerCell']/p")
	List<WebElement> datepicker;

	@FindBy(xpath = "//a[text()='Search']")
	WebElement btnsearch;

	@FindBy(xpath = "//div[@id='stop_group']//label/span[2][contains(text(),'Non Stop')]")
	WebElement ChkBoxnonStop;

	@FindBy(xpath = "//div[@id='stop_group']//label/span[2][contains(text(),'1 Stop')]")
	WebElement ChkBox1Stop;

	@FindBy(xpath = "(//div[contains(@id,'fli_list_item_')]//div[@class='fli-list-body-section']//div[@class='pull-left  make_relative price']//p)[1]")
	WebElement txtPriceOfFirstFlight;

	@FindBy(xpath = "//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")
	WebElement btnNextInCalendar;

	public void ClickOnMenuFlights() {
		wait.until(ExpectedConditions.visibilityOf(menu_flights));
		menu_flights.click();
	}

	public void clickOnOneTime() {
		oneTime.click();
	}

	private void selectFromCity(String city) {
		wait.until(ExpectedConditions.visibilityOfAllElements(listFrom));
		int size = listFrom.size();
		for (int i = 0; i <= size - 1; i++) {
			String place = listFrom.get(i).getText();
			if (place.contains(city)) {
				listFrom.get(i).click();
				break;
			}
		}
	}

	public void setFromCity(String fromPlace) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(txtFrom));
		txtFrom.click();
		txtBoxFrom.clear();
		Thread.sleep(1000);
		txtBoxFrom.sendKeys(fromPlace);
		Thread.sleep(1000);
		selectFromCity(fromPlace);
	}

	private void selectToCity(String city) {
		wait.until(ExpectedConditions.visibilityOfAllElements(listTo));
		int size = listTo.size();
		for (int i = 0; i <= size - 2; i++) {
			String place = listTo.get(i).getText();
			if (place.contains(city)) {
				listTo.get(i).click();
				break;
			}
		}
	}

	public void setToCity(String toPlace) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(txtTo));
		txtTo.click();
		txtBoxTo.clear();
		Thread.sleep(2000);
		txtBoxTo.sendKeys(toPlace);
		Thread.sleep(2000);
		selectToCity(toPlace);
	}

	public void selectDepartureDate(int day, Month month, int year) {
		wait.until(ExpectedConditions.visibilityOfAllElements(DayPicker_Month));
		int monthsize = DayPicker_Month.size();
		for (int i = 0; i <= monthsize - 1; i++) {
			String actMonth = DayPicker_Month.get(i).getText();
			if (actMonth.equalsIgnoreCase(month + " " + year)) {
				WebElement date = driver.findElement(By.xpath("//*[@class='DayPicker-Month']//div[text()='" + actMonth
						+ "']/../following-sibling::div[@class='DayPicker-Body']//div[@class='dateInnerCell']/p[text()='"
						+ day + "']"));
				date.click();
				break;
			}
			if (i == monthsize - 1) {
				System.out.println("Inside if 2 Loop");
				btnNextInCalendar.click();
				i = 0;
			}
		}
	}

	public void clickOnButtonSearch() {
		wait.until(ExpectedConditions.visibilityOf(btnsearch));
		btnsearch.click();
	}

	public void clickOnNonStop() {
		wait.until(ExpectedConditions.visibilityOf(ChkBoxnonStop));
		ChkBoxnonStop.click();
	}

	public void selectDepartureFromTime(String departureFromTime) throws InterruptedException {
		Thread.sleep(500);
		WebElement departureTime = driver
				.findElement(By.xpath("//div[@id='departure_group']//label/span[2][contains(text(),'"
						+ departureFromTime + "')]/preceding-sibling::span"));
		departureTime.click();
	}

	public void selectArrivalAtTime(String arrivalAtTime) {
		driver.findElement(
				By.xpath("//div[@id='arrival_group']//label/span[2][contains(text(),'" + arrivalAtTime + "')]"))
				.click();
	}

	public String getPriceOfFirstFlight() {
		String[] getprice = txtPriceOfFirstFlight.getText().split(" ");
		String price = new StringBuffer().append(getprice[1]).toString();
		return price;
	}

}
