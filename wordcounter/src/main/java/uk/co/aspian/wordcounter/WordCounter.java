package uk.co.aspian.wordcounter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordCounter
{
	private static final Logger logger = LogManager.getLogger();
	private static final String WORD_REGEX = "\\s+";
	
	private File file; 
	
	public WordCounter(File file)
	{
		this.file = file;
	}
	
	/**
	 * Returns the count of the number of non-distinct words found within the file
	 * 
	 * @return the number of non-distinct words found within the file.
	 * @throws WordCounterException
	 */
	
	public long getWordCount() throws WordCounterException
	{
		if(validFile())
		{
			try
			{
				if(logger.isDebugEnabled()) logger.debug(String.format("Counting words in file \'%s\'", file.getAbsolutePath()));
				
				// --------------------------------------------------------------------------------
				// Count the words in the file using Java 8 Stream logic
				// --------------------------------------------------------------------------------
				
				return Files.lines(file.toPath())
						.filter(line -> line != null && line.length() > 0)
						.flatMap(line -> Stream.of(line.split(WORD_REGEX)))
						.count();						
			}
			catch(IOException e)
			{
				throw new WordCounterException("IO Exception", e);
			}
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * Returns a list of all the distinct words found within the file
	 * 
	 * @return a list of Strings representing the words
	 * @throws WordCounterException
	 */
	
	public List<String> getWords() throws WordCounterException
	{
		if(validFile())
		{
			try
			{
				if(logger.isDebugEnabled()) logger.debug(String.format("Getting words in file \'%s\'", file.getAbsolutePath()));
				
				// --------------------------------------------------------------------------------
				// Split to words and reduce to the distinct set of words using Java 8 Stream logic
				// --------------------------------------------------------------------------------
				
				return Files.lines(file.toPath())
						.filter(line -> line != null && line.length() > 0)
						.flatMap(line -> Stream.of(line.split(WORD_REGEX)))
						.map(String::toLowerCase)
						.distinct()
						.sorted()
						.collect(Collectors.toList());
			}
			catch(IOException e)
			{
				throw new WordCounterException("IO Exception", e);
			}
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns a Map containing the list of distinct words found in the file and the
	 * number of occurrences
	 * 
	 * @return a Map containing the words and their counts
	 * @throws WordCounterException
	 */
	
	public Map<String, Integer> getWordCounts() throws WordCounterException
	{
		if(validFile())
		{
			try
			{
				if(logger.isDebugEnabled()) logger.debug(String.format("Getting word counts in file \'%s\'", file.getAbsolutePath()));

				// --------------------------------------------------------------------------------
				// Split to Words and aggregate the count of each word using Java 8 Stream logic
				// --------------------------------------------------------------------------------
				
				return Files.lines(file.toPath())
						.filter(line -> line != null && line.length() > 0)
						.flatMap(line -> Stream.of(line.split(WORD_REGEX)))
						.map(String::toLowerCase)
						.collect(Collectors.toMap(word -> word, word -> 1, Integer::sum))
						.entrySet()
						.stream()
						.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			}
			catch(IOException e)
			{
				throw new WordCounterException("IO Exception", e);
			}
		}
		else
		{
			// --------------------------------------------------------------------------------
			// We don't have a file we can process
			// --------------------------------------------------------------------------------
			
			return null;
		}
	}
	
	/**
	 * Checks that the File object we've been given isn't null and exists
	 * @return true we have a valid file we can process, false if not
	 * @throws WordCounterException
	 */
	
	private boolean validFile() throws WordCounterException
	{
		if(file == null) throw new WordCounterException("No file specified");
		if(!file.exists()) throw new WordCounterException("The file does not exist"); 
		
		return true;
	}
}
