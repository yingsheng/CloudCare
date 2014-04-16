package wiki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.FailedLoginException;


public class wikiSearch  {
	public static String returnWiki(String input) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Wiki wiki = null;
		File f = new File("wiki.dat");
		if (f.exists()) // we already have a copy on disk
		{
		   ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
		   wiki = (Wiki)in.readObject();
		  
		}
		else
		{
		   try
		   {
		       wiki = new Wiki("en.wikipedia.org"); // create a new wiki connection to en.wikipedia.org
		       wiki.setThrottle(5000); // set the edit throttle to 0.2 Hz
		       wiki.login("EltshanCMU", "6325041a"); // log in as user ExampleBot, with the specified password
		       
		       
		   }
		   catch (FailedLoginException ex)
		   {
		       // deal with failed login attempt
		   }
		}
		   ///System.out.println(wiki.getPageText("Gastritis"));
		String tmp = wiki.getPageText(input);
		//System.out.println(tmp);
        String[] aa = tmp.split("\n");
        for(int i = 0; i < aa.length; i++)
        {
        	System.out.println("line = " + aa[i].toLowerCase());
        	System.out.println("input.low = " + aa[i].toLowerCase());
            
        	if(aa[i].toLowerCase().contains( input.toLowerCase() ) && aa[i].contains("\'\'\'"))
        	{
        		String s = wiki.parse(aa[i]);
        		StringWriter sw = new StringWriter();
        		int paracount = 0;
        		char[] ss = s.split("\n")[0].toCharArray();
        		for(int j = 0; j < ss.length; ++j){
        			if(ss[j] == '<'){
        				++paracount;
        			}
        			if((paracount == 0)){
        				sw.append(ss[j]);
        			}
        			if(ss[j] == '>'){
        				--paracount;
        			}
        		}
        		return sw.toString();
        	}
        }
		//return wiki.getPageText(input);
		return null;
		
	}
	
	
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		// System.out.println(wikiSearch.returnWiki("Diarrhea"));
	}

    }
