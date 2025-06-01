package utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CheckBoxUtility 
{
	public boolean isCheckBoxDisplayed(RemoteWebDriver driver, WebElement e)
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
	public boolean isCheckBoxEnabled(RemoteWebDriver driver, WebElement e)
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
	public boolean isCheckBoxSelected(RemoteWebDriver driver, WebElement e)
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
	public void selectCheckbox(RemoteWebDriver driver, WebElement e)
	{
		if(!e.isSelected())
		{
			e.click();
		}
	}
	public void deselectCheckbox(RemoteWebDriver driver, WebElement e)
	{
		if(e.isSelected())
		{
			e.click();
		}
	}

}
