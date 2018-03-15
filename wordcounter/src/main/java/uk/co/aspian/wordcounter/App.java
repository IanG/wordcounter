package uk.co.aspian.wordcounter;

import java.io.File;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LogManager.getLogger();
	
    public static void main( String[] args )
    {
        if(args != null && args.length == 1)
        {        		
    			try
			{
    				File file = new File(args[0]);
          
    				System.out.println(String.format("Obtaining words counts from file \'%s\'", file.getAbsolutePath()));
            		
    				WordCounter wc = new WordCounter(file);

    				for(Map.Entry<String, Integer> entry : wc.getWordCounts().entrySet())
    				{
    					System.out.println(String.format("%s appears %d times", entry.getKey(), entry.getValue()));
    				}				
			}
			catch (WordCounterException e)
			{
				if (logger.isErrorEnabled()) logger.error("Error counting words", e);
				System.out.println(String.format("Error counting words: %s.", e.getMessage()));
			}
    			finally
    			{
    				System.out.println("Exiting");
    			}
        }
        else
        {
        		System.out.println("Invalid Command Line Arguments.");
        		System.out.println("Usage WordCount <file>");
        }
    }
}
