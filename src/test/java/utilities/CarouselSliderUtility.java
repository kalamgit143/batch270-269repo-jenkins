package utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CarouselSliderUtility 
{
	public int getCountOfSlides(WebElement slider)
	{
		int count=0;
		if(slider.getTagName().equalsIgnoreCase("ul") || 
				                  slider.getTagName().equalsIgnoreCase("ol"))
		{
			count=slider.findElements(By.xpath("child::li")).size();
		}
		else if(slider.getTagName().equalsIgnoreCase("div"))
		{
			List<WebElement> temp1=slider.findElements(By.xpath("child::div"));
			//here some are visible and remaining are hidden
			List<WebElement> temp2=new ArrayList<WebElement>(); //empty list
			for(WebElement e:temp1)
			{
				try
				{
					if(!e.getAttribute("class").contains("clone"))
				    {
						if(e.getAttribute("data-clone").equals("false"))
						{
							temp2.add(e); //data-clone is false to an element(visible)
						}
				    }
				}
				catch(Exception ex)
				{
					temp2.add(e); //no data-clone attribute to an element, means visible
				}
			}
			count=temp2.size();
		}
		else
		{
			System.out.println("Unknown tag");
			System.exit(0); //stop execution forcibly
		}
		return(count);
	}
	public String getTypeOfSlider(WebElement slider)
	{
		//"transform" and "transform-style" are CSS properties
		String value=slider.getCssValue("transform");
		if(value.equals("none"))
		{
			//take transform-style property value
			value=slider.getCssValue("transform-style");
		}
		//verification
		if(value.contains("3d"))
		{
			return("3D slider");
		}
		else
		{
			return("2D slider");
		}
	}
	public boolean areSlidesMovingAutomaticallyInSlider(WebElement slider) throws Exception
	{
		String oldvalue=slider.getCssValue("transform");
		if(oldvalue.equals("none"))
		{
			//take transform-style property value
			oldvalue=slider.getCssValue("transform-style");
		}
		//verification
		if(oldvalue.contains("3d"))
		{
			//wait for sometime
			Thread.sleep(5000);
			//get values again
			String newvalue=slider.getCssValue("transform");
			if(newvalue.equals("none"))
			{
				//take transform-style property value
				newvalue=slider.getCssValue("transform-style");
			}
			//compare
			if(oldvalue.equals(newvalue))
			{
				return(false); //not rotating
			}
			else
			{
				return(true); //rotating
			}
		}
		else //2D
		{
			String[] y=oldvalue.split(",");
			y[4]=y[4].trim();
			y[5]=y[5].trim();
			y[5]=y[5].replace(")","");  //remove ")"
			float tx=Float.parseFloat(y[4]);
			float ty=Float.parseFloat(y[5]);
			if(tx==0 && ty==0)
			{
			   return(false); //not rotating
			}
			else
			{
				return(true); //rotating
			}	
		}
	}
	public String getMovingDirectionOfSlidesInSlider(WebElement slider)
	{
		String value=slider.getCssValue("transform");
		if(value.contains("3d"))
		{
			String s=slider.getAttribute("style");
			if (s.contains("rotateZ(-")) 
			{
				return("Front to Back rotation");
			} 
			else if (s.contains("rotateZ(")) 
			{
				return("Back to Front rotation");
			} 
			else if (s.contains("rotateY(-")) 
			{
				return("Right to Left rotation");
			} 
			else if (s.contains("rotateY(")) 
			{
				return("Left to Right rotation");
			} 
			else if (s.contains("rotateX(-"))
			{
				return("Top to Bottom rotation");
			} 
			else if (s.contains("rotateX(")) 
			{
				return("Bottom to Top rotation");
			}
			else
			{
				return("Not matching any specific rotation condition");
			}
		}
		else //2D
		{
			String[] p=value.split(","); //comma(,) is separator
			p[4]=p[4].trim(); //remove unwanted spaces
			p[5]=p[5].trim(); //remove unwanted spaces
			p[5]=p[5].replace(")",""); //remove ")" symbol
			float tx=Float.parseFloat(p[4]);
			float ty=Float.parseFloat(p[5]);
			if(tx<0 && ty==0)
			{
				return("Slides are moving from Right to left");
			}
			else if(tx>0 && ty==0)
			{
				return("Slides are moving from Left to Right");
			}
			else if(tx==0 && ty<0)
			{
				return("Slides are moving from Bottom to top");
			}
			else if(tx==0 && ty>0) 
			{
				return("Slides are moving from Top to bottom");
			}
			else //tx and ty both are zero
			{
				return("Not moving");
			}
		}
	}
    public String getSlideMoveDuration(WebElement slider)
    {
        //"transition" property value is in same format for both 2D and 3D sliders in CSS
        String x=slider.getCssValue("transition");
        String[] y=x.split(" "); //split value depends on space
        y[1]=y[1].trim(); //remove leading and trailing spaces in 2nd piece
        if(y[1].contains("ms"))
        {
             String z=y[1];
             z=z.replace("ms",""); //remove "ms" if exists
             float duration=Float.parseFloat(z); //type conversion from string to float
             if(duration<=1) return(duration+" millisecond");
             else return(duration+" milliseconds"); 
        }
        else
        {
             String z=y[1];
             z=z.replace("s",""); //remove "s" if exists
             float duration=Float.parseFloat(z); //type conversion from string to float
             if(duration<=1) return(duration+" second");
             else return(duration+" seconds"); 
        }
    }
    public String getslideMovingStyle(WebElement slider)
    {
        String x=slider.getCssValue("transition");
        String[] y=x.split(" "); //space as seperator
        y[2]=y[2].trim(); //remove leading and trailing spaces
        if(y[2].equalsIgnoreCase("ease-in"))  return("Slow start");
        else if(y[2].equalsIgnoreCase("ease-out")) return("Slow end");
        else return("Slow start and slow end");
    }
    public String getDelayBetweenSlidesMove(WebElement slider)
    {
        String x=slider.getCssValue("transition");
        String[] y=x.split(" "); //Separator as space
        if(y[3].contains("ms"))
        {
             String z=y[3];
             z=z.replaceAll("[m][s][,]?",""); //remove "ms," or "ms" if exists
             float duration=Float.parseFloat(z); //type conversion from string to float
             if(duration<=1) return(duration+" millisecond");
             else return(duration+" milliseconds"); 
        }
        else
        {
             String z=y[3];
             z=z.replaceAll("[s][,]?",""); //remove "s," or "s" if exists
             float duration=Float.parseFloat(z); //type conversion from string to float
             if(duration<=1) return(duration+" second");
             else return(duration+" seconds"); 
        }
    }
}
