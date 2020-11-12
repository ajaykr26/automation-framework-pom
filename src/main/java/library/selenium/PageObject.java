package library.selenium;

import library.selenium.common.CommonMethods;
import pageobjects.Facebook_HomePage;
import pageobjects.Facebook_LoginPage;
import pageobjects.Facebook_SearchPage;
import pageobjects.Plurals_Membership;

public interface PageObject extends CommonMethods {

    Facebook_LoginPage FACEBOOK_LOGIN_PAGE = new Facebook_LoginPage();
    Facebook_HomePage FACEBOOK_HOME_PAGE = new Facebook_HomePage();
    Facebook_SearchPage FACEBOOK_SEARCH_PAGE = new Facebook_SearchPage();
    Plurals_Membership PLURALS_MEMBERSHIP = new Plurals_Membership();


}
