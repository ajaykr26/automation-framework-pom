package pageobjects;

import com.aventstack.extentreports.Status;
import library.reporting.ExtentReporter;
import library.selenium.BasePO;
import library.selenium.common.InputMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Plurals_Membership extends BasePO {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    private final By mobile = By.id("ContentPlaceHolder1_Phone");
    private final By fullname = By.id("ContentPlaceHolder1_full_name");
    private final By gender = By.id("ContentPlaceHolder1_Gender");
    private final By father_name = By.id("ContentPlaceHolder1_father_name");
    private final By dateofbirth = By.id("ContentPlaceHolder1_date");
    private final By email = By.id("ContentPlaceHolder1_Email");
    private final By address_box = By.id("ContentPlaceHolder1_address_box");
    private final By pincode = By.id("ContentPlaceHolder1_pincode");
    private final By state_list = By.id("ContentPlaceHolder1_state_list");
    private final By district_list = By.id("ContentPlaceHolder1_district_list");
    private final By vidhan_sabha_list = By.id("ContentPlaceHolder1_vidhan_sabha_list");
    private final By qualification_list = By.id("ContentPlaceHolder1_qualification_list");
    private final By occupation_list = By.id("ContentPlaceHolder1_occupation_list");
    private final By watsapp_no = By.id("ContentPlaceHolder1_watsapp_no");
    private final By facebook_id = By.id("ContentPlaceHolder1_facebook_id");
    private final By twitter_id = By.id("ContentPlaceHolder1_Twitter_id");
    private final By fileUpload1 = By.id("ContentPlaceHolder1_FileUpload1");
    private final By reference_no = By.id("ContentPlaceHolder1_reference_no");

    public void launchApplication(String applicationName) {
        launchApplication(applicationName);
    }

    public void fillupMembershipForm() {
        getElement(mobile).sendKeys(parseText("mobile"));
        getElement(fullname).sendKeys(parseText("fullname"));
        InputMethods.inputMethodsObj.selectOptionFromDropdown("value", parseText("gender"), gender);
        getElement(father_name).sendKeys(parseText("father_name"));

    }


}
