package pageobjects;

import library.selenium.BasePO;
import org.openqa.selenium.By;

public class Facebook_RegistraionPage extends BasePO {


    private final By userName = By.id("userName");
    private final By usrPwd = By.id("usrPwd");
    private final By cnfUsrPwd = By.id("cnfUsrPwd");
    private final By selectSecurity = By.xpath("//label[text()='Select Security Question']");
    private final By selectPetName = By.xpath("//span[text()='What is your pet name?']");
    private final By securityAnswer = By.xpath("//input[@placeholder='Security Answer']");
    private final By selectLanguage = By.xpath("//label[text()='Select Preferred Language']");
    private final By firstName = By.id("firstName");
    private final By lastname = By.id("lastname");
    private final By male = By.id("M");
    private final By calendar = By.xpath("//p-calendar[@formcontrolname='dob']");
    private final By selectOccupation = By.xpath("//label[text()='Select Occupation']");
    private final By selfEmployed = By.xpath("//span[text()='SELF EMPLOYED']");
    private final By email = By.id("email");
    private final By mobile = By.id("mobile");
    private final By resAddress1 = By.id("resAddress1");
    private final By resPinCode = By.name("resPinCode");
    private final By resState = By.id("resState");
    private final By resCity = By.xpath("//select[@formcontrolname='resCity']");
    private final By resPostOff = By.xpath("//select[@formcontrolname='resPostOff']");
    private final By resPhone = By.id("resPhone");
    private final By permanentAddress = By.id("//input[@value='Y' and @type='radio']");

    public void userResistration() {

    }

    public void manualyProceed() {
        logger.debug("proceed manually and resume execution");
    }

}
