package utilities;

import java.util.Random;

public class RandomUtility
{
	    public static String generateRandomEmail() 
	    {
	        String[] domains={"gmail.com","yahoo.com","hotmail.com","example.com","batch267268.co.in"};
	        Random rand=new Random();
	        String username=generateRandomString(8); // You can specify the username length
	        String domain=domains[rand.nextInt(domains.length)];
	        return(username+"@"+domain);
	    }

	    public static String generateRandomString(int length) 
	    {
	        String characters="abcdefghijklmnopqrstuvwxyz0123456789_-.@#$"; //You can add other chars if needed
	        StringBuilder username=new StringBuilder();
	        Random rand=new Random();
	        for(int i = 0; i<length; i++) 
	        {
	            int index=rand.nextInt(characters.length());
	            username.append(characters.charAt(index));
	        }
	        return(username.toString());
	    }
	    
	    public static String generateRandomMobileNumber() 
	    {
	    	//Random number but starts with 6 or 7 or 8 or 9
	    	Random random = new Random();
	        //First digit must be 6, 7, 8, or 9
	        int firstDigit=6+random.nextInt(4); // Generates a random number between 6 and 9
	        //Generate the remaining 9 digits
	        StringBuilder number = new StringBuilder();
	        number.append(firstDigit);
	        for (int i=0; i<9; i++) {
	            number.append(random.nextInt(10)); // Appends digits between 0 and 9
	        }
	        return number.toString();
	    }
}


