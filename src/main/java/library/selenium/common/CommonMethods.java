package library.selenium.common;

public interface CommonMethods {

	MiscMethods miscMethodsObj = new MiscMethods();
	NavigateMethods navigationMethodsObj = new NavigateMethods();
	AssertionMethods assertionMethodsObj = new AssertionMethods();
	ClickMethods clickMethodsObj = new ClickMethods();
	ConfigMethods configMethodsObj = new ConfigMethods();
	InputMethods inputMethodsObj = new InputMethods();
	ProgressMethods progressMethodsObj = new ProgressMethods();
	TableMethods tableMethodsObject = new TableMethods();
	JavascriptHandlingMethods javascriptMethodsObj = new JavascriptHandlingMethods();
	ScreenShotMethods screenshotMethodsObj = new ScreenShotMethods();

/*


	public void click(String locatorType, String locator);

	public void clickForcefully(String locatorType, String locator);

	public void doubleClick(String locatorType, String locator);


	public String getPageTitle();


	public void checkTitle(String title, boolean testCase) throws TestCaseFailed;

	public void checkPartialTitle(String partialTitle, boolean testCase) throws TestCaseFailed;

	public String getElementText(String locatorType, String locator);

	public void validateElementText(String matchType, String locatorType, String actualValue, String locator) throws TestCaseFailed;

	public void checkElementText(String locatorType, String actualValue, String locator, boolean testCase) throws TestCaseFailed;

	public void checkElementPartialText(String locatorType, String actualValue, String locator, boolean testCase) throws TestCaseFailed;

	public boolean isElementEnabled(String locatorType, String locator);

	public void checkElementEnable(String locatorType, String locator, boolean testCase) throws TestCaseFailed;

	public String getElementAttribute(String locatorType, String locator, String attributeName);

	public void checkElementAttribute(String locatorType, String attributeName, String attributeValue, String locator, boolean testCase) throws TestCaseFailed;

	public boolean isElementDisplayed(String locatorType, String locator);

	public void checkElementPresence(String locatorType, String locator, boolean testCase) throws TestCaseFailed;

	public void isCheckboxChecked(String locatorType, String locator, boolean shouldBeChecked) throws TestCaseFailed;

	public void isRadioButtonSelected(String locatorType, String locator, boolean shouldBeSelected) throws TestCaseFailed;

	public void isOptionFromRadioButtonGroupSelected(String locatorType, String by, String option, String locator, boolean shouldBeSelected) throws TestCaseFailed;

	public String getAlertText();

	public void checkAlertText(String text) throws TestCaseFailed;

	public void isOptionFromDropdownSelected(String locatorType, String by, String option, String locator, boolean shouldBeSelected) throws TestCaseFailed;
*/
}
