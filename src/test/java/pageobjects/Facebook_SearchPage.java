package pageobjects;

import library.selenium.BasePO;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

import static library.selenium.FactoryMethod.*;

public class Facebook_SearchPage extends BasePO {

    private final By origin = By.xpath("//input[@placeholder='From*']");
    private final By destination = By.xpath("//input[@placeholder='To*']");
    private final By calendar = By.xpath("//input[@placeholder='Journey Date(dd-mm-yyyy)*']");
    private final By bookingClassDropDown = By.xpath("//label[text()='All Classes']");
    private final By bookingClass = By.xpath("//ul[contains(@class,'ui-dropdown-items')]//span[text()='" + parseText("BOOKING CLASS") + "']");
    private final By findTrains = By.xpath("//button[text()='Find trains']");
    private final By general = By.xpath("(//label[text()='GENERAL'])[1]");
    private final By tatkal = By.xpath("(//span[text()='TATKAL'])[1]");
    private final By checkavailability = By.xpath("//button[@id='check-availability' and contains(@aria-label,'LOKMANYATILAK')]");
    private final By agreementPopup = By.xpath("//span[text()='I Agree']");
    private final By bookNow = By.xpath("//button[contains(@aria-label,'" + parseText("BOOKING DATE") + "')]");
    private final By addPassenger = By.xpath("//span[text()='+ Add Passenger']");
    private final By bookIfConfirm = By.xpath("//label[text()='Book only if confirm berths are allotted.']");
    private final By insurance = By.xpath("//label[text()='Yes and I accept the ']/.");
    private final By address1 = By.xpath("//input[@placeholder='Correspondence 1 *']");
    private final By pincode = By.xpath("//input[@placeholder='PIN *']");
    private final By city = By.id("address-City");
    private final By postOffice = By.id("address-postOffice");
    private final By upi = By.xpath("//label[text()=' Pay through BHIM/UPI']/input");
    private final By continueBtn = By.xpath("//button[text()='Continue ']");
    private final By availability = By.xpath("//app-availability-summary/div/span[@class='ng-star-inserted']");
    private final By makePaymentIrctcUPI = By.xpath("(//button[text()='Make Payment'])[1]");
    private final By irctcUPI = By.xpath("//input[contains(@aria-label,'Powered by IRCTC')]");
    private final By upiAddress = By.id("vpaCheck");
    private final By payBtn = By.id("upi-sbmt");

    public void searchTrain() {
        getElement(origin).sendKeys(parseText("ORIGIN"));
        getElement(destination).sendKeys(parseText("DESTINATION"));
        getElement(calendar).clear();
        getElement(calendar).sendKeys(parseText("JOURNEY DATE"));
        getElement(bookingClassDropDown).click();
        getElement(bookingClass).click();
        getElement(findTrains).click();
    }

    public void checkTatkalAvailibility() {
        getElement(general).click();
        getElement(tatkal).click();
    }

    public void proceedToBooking() {
        getWait().until(ExpectedConditions.presenceOfElementLocated(bookNow));
        getElement(bookNow).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(agreementPopup));
        getElement(agreementPopup).click();
    }

    public void addPassengers(String passengerNumber) {
        if (!passengerNumber.equals("1"))
            getElement(addPassenger).click();
        getElement(By.xpath("(//input[@formcontrolname='passengerName'])[" + passengerNumber + "]")).sendKeys(parseText("PASSENGER NAME " + passengerNumber));
        getElement(By.xpath("(//input[@formcontrolname='passengerAge'])[" + passengerNumber + "]")).sendKeys(parseText("PASSENGER AGE " + passengerNumber));
        getSelectMethods().selectOptionFromDropdown("text", parseText("PASSENGER GENDER " + passengerNumber), getObject("(//select[@formcontrolname='passengerGender'])[" + passengerNumber + "]"));
    }

    public void fillOtherDetails() {
        getElement(bookIfConfirm).click();
        getElement(insurance).click();
        getElement(address1).sendKeys(parseText("ADDRESS 1"));
        getElement(pincode).sendKeys(parseText("PINCODE"));
        waitForDropDownValueToLoad(city);
        getInputMethods().selectOptionFromDropdown("text", parseText("CITY"), city);
        getInputMethods().selectOptionFromDropdown("text", parseText("POST OFFICE"), postOffice);
        getElement(upi).click();
        getElement(continueBtn).click();
        if (isAvailable()) {
            logger.debug("seats still available");
        } else {
            getDriver().manage().window().maximize();
            logger.debug("seats are not available");
        }
    }

    public void ProceedToPay() {
        waitForPageToLoad();
        getElement(irctcUPI).click();
        getElement(makePaymentIrctcUPI).click();
        getElement(upiAddress).sendKeys(parseText("UPI ADDRESS"));
        getElement(payBtn).click();
    }

    public boolean isAvailable() {
        waitForPageToLoad();
        return getTextFromObject(availability).contains("AVAILABLE");
    }

    public void waitForDropDownValueToLoad(By by) {
        do {
            getElement(by).click();
            getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        } while (getNestedElementsLocatedBy(by, By.tagName("option")).size() == 1);
    }

}
