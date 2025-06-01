package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFileUtility
{
	public static int getCountOfLines(String filepath) throws Exception
	{
		//Access existing text file or CSV file, which consists of test data
		File f=new File(filepath); //connect to file in HDD
		FileReader fr=new FileReader(f); //take READ access to file
		BufferedReader br=new BufferedReader(fr); //move to RAM(cursor at first line by default)
		int count=0;
		while(br.readLine()!=null) //loop terminates after last line's reading
		{
			count++;
		}
		br.close();
		fr.close();
		return(count);
	}
	
	public static String[] getValuesOfLine(String filepath,int linenumber) throws Exception
	{
		//Access existing text file or CSV file, which consists of test data
		File f=new File(filepath); //connect to file in HDD
		FileReader fr=new FileReader(f); //take READ access to file
		BufferedReader br=new BufferedReader(fr); //move to RAM(cursor at first line by default)
		//move up to a line which is the before line for required line in that sequential text file
		for(int i=1;i<linenumber;i++)
		{
			br.readLine();
		}
		//Read required line text
		String temp=br.readLine();
		//split that line of text into words depending on comma
		String output[]=temp.split(",");
		br.close(); //save file back to HDD
		fr.close();
		return(output);
	}
	
	public static List<Object> getValuesOfLineMatchRegex(String filepath,int linenumber, String regex) throws Exception
	{
		//Open existing file in read mode
		File f=new File(filepath);
		FileReader fr=new FileReader(f);  
		//Load file into RAM as text file  
	    BufferedReader br=new BufferedReader(fr);
	    //move up to a line which is the before line for required line in that sequential text file
	  	for(int i=1;i<linenumber;i++)
	  	{
	  		br.readLine();
	  	}
	  	//Read required line text
	  	String line=br.readLine();
	  	//Get matched values in given line
	    Pattern p=Pattern.compile(regex); //one or more digits means a number
	    Matcher m=p.matcher(line);
	    List<Object> output=new ArrayList<Object>();
	    while(m.find())
	    {
	    	output.add(m.group());
	    }
	    //close file
	    br.close();
	    fr.close();
		return(output);
	}
	
	public static void removeDuplicatesAndEmptyLinesAndSave(String filepath) throws Exception
	{
		//Open existing file in read mode
		File f=new File(filepath);
		FileReader fr=new FileReader(f);  
	    BufferedReader br=new BufferedReader(fr);
	    //Take a new list
	    List<String> uniquelines=new ArrayList<String>(); 
	    //Goto each line in existing file
	    String currentline="";
	    while((currentline=br.readLine())!=null) 
	    {
	    	int flag=0;
	    	//compare current line with existing lines to skip duplicates
	    	for(int i=0;i<uniquelines.size();i++)
	    	{
	    		if(currentline.equalsIgnoreCase(uniquelines.get(i)) ||
	    			                                      currentline.equals(""))
	    		{
	    			flag=1; //duplicate
	    			break;
	    		}
	    	}
	    	if(flag==0) //not duplicate, add to unique-list
	    	{
	    		uniquelines.add(currentline);
	    	}
	    }
	    //close file in Read mode
	    br.close();
	    fr.close();
	    //Open same file in write mode(Existing data will be overwritten)
	    FileWriter fw=new FileWriter(f);  
        BufferedWriter bw=new BufferedWriter(fw);
        for(int i=0;i<uniquelines.size();i++)
        {
        	bw.write(uniquelines.get(i));
        	bw.newLine();
        }
        //close file in Read mode
        bw.close();
        fw.close();
	}
}











