package testscripts;

import library.selenium.BaseTest;
import library.selenium.PageObject;
import org.testng.annotations.Test;


public class PluralsMembership extends BaseTest implements PageObject {

    @Test(testName = "Submit Membership Form", description = "submit plurals membership form")
    public void SubmitMembershipForm() {
        PLURALS_MEMBERSHIP.launchApplication("plurals");
        PLURALS_MEMBERSHIP.fillupMembershipForm();
    }

}