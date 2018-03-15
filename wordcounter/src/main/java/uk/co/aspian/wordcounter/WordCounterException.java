package uk.co.aspian.wordcounter;

public class WordCounterException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public WordCounterException()
	{
		super();
	}
	
	public WordCounterException(String message)
	{
		super(message);
	}
	
	public WordCounterException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
