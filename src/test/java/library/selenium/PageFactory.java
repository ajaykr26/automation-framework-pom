package library.selenium;

import pageobjects.Launch_ApplicationPage;
import pageobjects.Facebook_LoginPage;
import pageobjects.Facebook_HomePage;
import pageobjects.Facebook_SearchPage;

public class PageFactory {

    public static Launch_ApplicationPage getLaunch_ApplicationPage() {
        return new Launch_ApplicationPage();
    }

    public static Facebook_LoginPage getFacebookLoginPage() {
        return new Facebook_LoginPage();
    }

    public static Facebook_HomePage getFacebookHomePage() {
        return new Facebook_HomePage();
    }

    public static Facebook_SearchPage getFacebookSearchPage() {
        return new Facebook_SearchPage();
    }

}
