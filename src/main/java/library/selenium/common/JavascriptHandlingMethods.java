package library.selenium.common;

import library.selenium.BasePO;
import org.openqa.selenium.WebDriver;

public class JavascriptHandlingMethods extends BasePO implements CommonMethods {
	protected WebDriver driver = getDriver();

	public void handleAlert(String decision)
	{
		if(decision.equals("accept"))
			driver.switchTo().alert().accept();
		else
			driver.switchTo().alert().dismiss();
	}
}
