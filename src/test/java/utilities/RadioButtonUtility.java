package utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RadioButtonUtility 
{
	public boolean isRadiobuttonDisplayed(RemoteWebDriver driver, WebElement e)
	{
		if(e.isDisplayed())
		{
			return(true);
		}
		else
		{
			return(false);
		}
	}
	public boolean isRadiobuttonEnabled(RemoteWebDriver driver, WebElement e)
	{
		if(e.isEnabled())
		{
			return(true);
		}
		else
		{
			return(false);
		}
	}
	public boolean isRadiobuttonSelected(RemoteWebDriver driver, WebElement e)
	{
		if(e.isSelected())
		{
			return(true);
		}
		else
		{
			return(false);
		}
	}
	public void selectRadiobutton(RemoteWebDriver driver, WebElement e)
	{
		e.click();
	}
	

}
