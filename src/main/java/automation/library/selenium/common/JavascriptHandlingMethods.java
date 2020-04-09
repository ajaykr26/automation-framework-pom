package automation.library.selenium.common;

import automation.library.selenium.BasePage;
import org.openqa.selenium.WebDriver;

public class JavascriptHandlingMethods extends BasePage implements MethodObjects {
	protected WebDriver driver = getDriver();

	public void handleAlert(String decision)
	{
		if(decision.equals("accept"))
			driver.switchTo().alert().accept();
		else
			driver.switchTo().alert().dismiss();
	}
}
