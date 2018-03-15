package uk.co.aspian.wordcounter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.collection.IsMapContaining;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import uk.co.aspian.wordcounter.test.category.UnitTests;


/**
 * @author idg
 * 
 * Tests for the uk.co.aspian.wordcounter.WordCounter class
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Category(UnitTests.class)

public class WordCounterTest
{
	private static final Logger logger = LogManager.getLogger();
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Test we get a WordCounterException when we pass a null File object
	 * 
	 * @throws WordCounterException
	 */
	
	@Test
    public void TestGetWordCountsWithNullFile() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing null File");
		
		// --------------------------------------------------------------------------------
		// We expect a WordCounterException as we've not provided a File 
		// --------------------------------------------------------------------------------
		
    		thrown.expect(WordCounterException.class);
    		thrown.expectMessage("No file specified");
    		
    		// --------------------------------------------------------------------------------
		// Attempt to get the word counts to throw the WordCounterException
		// --------------------------------------------------------------------------------

    		WordCounter wc = new WordCounter(null);
    		wc.getWordCounts();				
    }
    
    /**
     * Test getWordCounts() and check that we get a WordCounterException when we pass a File object 
     * that points to a file that doesn't actually exist.
     * 
     * @throws WordCounterException
     */

	@Test
    public void TestGetWordCountsWithMissingFile() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing missing File");
		
		// --------------------------------------------------------------------------------
		// We expect a WordCounterException as we are providing a file that doesn't exist 
		// --------------------------------------------------------------------------------
				
    		thrown.expect(WordCounterException.class);
    		thrown.expectMessage("The file does not exist");

    		// --------------------------------------------------------------------------------
    		// Attempt to get the word counts to throw the WordCounterException 
    		// --------------------------------------------------------------------------------
    		
    		WordCounter wc = new WordCounter(new File("MissingFile.txt"));
    		wc.getWordCounts();
	}
	
    /**
     * Test getWordCounts() and check that we get an empty Map when the File object points 
     * to a file that contains no words.
     * 
     * @throws WordCounterException
     */

	@Test
	public void TestGetWordCountsEmptyFileMap() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing empty File");

		// --------------------------------------------------------------------------------
		// Attempt to get the word counts 
		// --------------------------------------------------------------------------------
	
    		WordCounter wc = new WordCounter(new File(getClass().getResource("EmptyFile.txt").getFile()));
		Map<String, Integer> wordcounts = wc.getWordCounts();
		
		// --------------------------------------------------------------------------------
		// Make sure that we got an empty Map back
		// --------------------------------------------------------------------------------
		
		assertThat("Map is empty", wordcounts.size(), is(0));
	}
    
    /**
     * Test getWordCounts() and check that we get a populated Map when the File object points 
     * to a known file that contains words we expect and that the word counts are correct
     * 
     * @throws WordCounterException
     */
	
	@Test
    public void TestGetWordCountsValidFileMap() throws WordCounterException
    {
		if (logger.isDebugEnabled()) logger.debug("Testing Valid File");

		// --------------------------------------------------------------------------------
		// Attempt to get the word counts 
		// --------------------------------------------------------------------------------

		WordCounter wc = new WordCounter(new File(getClass().getResource("ValidFile.txt").getFile()));
		Map<String, Integer> wordcounts = wc.getWordCounts();

		// --------------------------------------------------------------------------------
		// Make sure we got the Map we expected 
		// --------------------------------------------------------------------------------

		assertThat("Map Contains 4 items", wordcounts.size(), is(4));
		assertThat("Map Contains entry 'dog' with Count of 4", wordcounts, IsMapContaining.hasEntry("dog", 4));
		assertThat("Map Contains entry 'cat' with Count of 3", wordcounts, IsMapContaining.hasEntry("cat", 3));
		assertThat("Map Contains entry 'pig' with Count of 2", wordcounts, IsMapContaining.hasEntry("pig", 2));
		assertThat("Map Contains entry 'rat' with Count of 1", wordcounts, IsMapContaining.hasEntry("rat", 1));
		assertThat("Map Excludes entry 'donkey'", wordcounts, not(IsMapContaining.hasKey("donkey")));
	}
	
	/**
	 * Test getWords() and check we get a WordCounterException when we pass 
	 * a null File object
	 * 
	 * @throws WordCounterException
	 */
	
	@Test
    public void TestGetWordsWithNullFile() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing null File");
		
		// --------------------------------------------------------------------------------
		// We expect a WordCounterException as we've not provided a File 
		// --------------------------------------------------------------------------------
		
    		thrown.expect(WordCounterException.class);
    		thrown.expectMessage("No file specified");
    		
    		// --------------------------------------------------------------------------------
		// Attempt to get the word counts to throw the WordCounterException
		// --------------------------------------------------------------------------------

    		WordCounter wc = new WordCounter(null);
    		wc.getWords();
    }
	
	/**
     * Test getWords() and check that we get a WordCounterException when we pass a File object 
     * that points to a file that doesn't actually exist.
     * 
     * @throws WordCounterException
     */

	@Test
    public void TestGetWordsWithMissingFile() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing missing File");
		
		// --------------------------------------------------------------------------------
		// We expect a WordCounterException as we are providing a file that doesn't exist 
		// --------------------------------------------------------------------------------
				
    		thrown.expect(WordCounterException.class);
    		thrown.expectMessage("The file does not exist");

    		// --------------------------------------------------------------------------------
    		// Attempt to get the word counts to throw the WordCounterException 
    		// --------------------------------------------------------------------------------
    		
    		WordCounter wc = new WordCounter(new File("MissingFile.txt"));
    		wc.getWords();
	}
	

    /**
     * Test getWords() and check that we get an empty List<String> when the File object points 
     * to a file that contains no words.
     * 
     * @throws WordCounterException
     */

	@Test
	public void TestGetWordsEmptyFileList() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing empty File");

		// --------------------------------------------------------------------------------
		// Attempt to get the word counts 
		// --------------------------------------------------------------------------------
	
    		WordCounter wc = new WordCounter(new File(getClass().getResource("EmptyFile.txt").getFile()));
		List<String> words = wc.getWords();
		
		// --------------------------------------------------------------------------------
		// Make sure that we got an empty List back
		// --------------------------------------------------------------------------------
		
		assertThat("List is empty", words.size(), is(0));
	}
	
    /**
     * Test getWords() and check that we get a populated List<String> when the File object points 
     * to a known file that contains the words we expect
     * 
     * @throws WordCounterException
     */
	
	@Test
    public void TestGetWordsValidFileList() throws WordCounterException
    {
		if (logger.isDebugEnabled()) logger.debug("Testing Valid File");

		// --------------------------------------------------------------------------------
		// Attempt to get the word counts 
		// --------------------------------------------------------------------------------

		WordCounter wc = new WordCounter(new File(getClass().getResource("ValidFile.txt").getFile()));
		List<String> words = wc.getWords();

		// --------------------------------------------------------------------------------
		// Make sure we got the List we expected 
		// --------------------------------------------------------------------------------

		assertThat("List Contains 4 words", words.size(), is(4));
		assertThat("List Matches", words, containsInAnyOrder("dog","cat", "pig", "rat"));		
	}
	
	/**
	 * Test getWordCount() and check we get a WordCounterException when we pass 
	 * a null File object
	 * 
	 * @throws WordCounterException
	 */
	
	@Test
    public void TestGetWordCountWithNullFile() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing null File");
		
		// --------------------------------------------------------------------------------
		// We expect a WordCounterException as we've not provided a File 
		// --------------------------------------------------------------------------------
		
    		thrown.expect(WordCounterException.class);
    		thrown.expectMessage("No file specified");
    		
    		// --------------------------------------------------------------------------------
		// Attempt to get the word counts to throw the WordCounterException
		// --------------------------------------------------------------------------------

    		WordCounter wc = new WordCounter(null);
    		wc.getWordCount();
    }
	
	/**
     * Test getWordCount() and check that we get a WordCounterException when we pass a File object 
     * that points to a file that doesn't actually exist.
     * 
     * @throws WordCounterException
     */

	@Test
    public void TestGetWordCountWithMissingFile() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing missing File");
		
		// --------------------------------------------------------------------------------
		// We expect a WordCounterException as we are providing a file that doesn't exist 
		// --------------------------------------------------------------------------------
				
    		thrown.expect(WordCounterException.class);
    		thrown.expectMessage("The file does not exist");

    		// --------------------------------------------------------------------------------
    		// Attempt to get the word counts to throw the WordCounterException 
    		// --------------------------------------------------------------------------------
    		
    		WordCounter wc = new WordCounter(new File("MissingFile.txt"));
    		wc.getWordCount();
	}
	
    /**
     * Test getWordCount() and check that we get a 0 count when the File object points 
     * to a file that contains no words.
     * 
     * @throws WordCounterException
     */

	@Test
	public void TestGetWordCountEmptyFile() throws WordCounterException
	{
		if (logger.isDebugEnabled()) logger.debug("Testing empty File");

		// --------------------------------------------------------------------------------
		// Attempt to get the word counts 
		// --------------------------------------------------------------------------------
	
    		WordCounter wc = new WordCounter(new File(getClass().getResource("EmptyFile.txt").getFile()));
		long wordCount = wc.getWordCount();
		
		// --------------------------------------------------------------------------------
		// Make sure that we got an empty List back
		// --------------------------------------------------------------------------------
		
		assertThat("Word Count is 0", wordCount, is(0L));
	}
	
	/**
     * Test getWordCount() and check that we get the correct count when the File object points 
     * to a known file that contains the words we expect
     * 
     * @throws WordCounterException
     */
	
	@Test
    public void TestGetWordCountValidFile() throws WordCounterException
    {
		if (logger.isDebugEnabled()) logger.debug("Testing Valid File");

		// --------------------------------------------------------------------------------
		// Attempt to get the word counts 
		// --------------------------------------------------------------------------------

		WordCounter wc = new WordCounter(new File(getClass().getResource("ValidFile.txt").getFile()));
		long wordCount = wc.getWordCount();

		// --------------------------------------------------------------------------------
		// Make sure we got the List we expected 
		// --------------------------------------------------------------------------------

		assertThat("Word Count is 10", wordCount, is(10L));			
	}
}
