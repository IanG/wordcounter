# wordcounter
A simple Java project that shows how to use the Java 8 stream API to process data

This project demonstrates how to use the Java Stream API to process data to perform map, reduce and aggregate 
operations.

The uk.co.aspian.wordcounter.WordCounter utility class allows you to perform the following operations on a File object you provide it.

1.  Count the number of words in a given file.
2.  Obtain a List<String> of the distinct words in a given file.
3.  Obtain a Map<String, Integer> of all the distinct words and the number of occurrences in a given file.

The JUnit test uk.co.aspian.wordcounter.WordCounterTest.java carries out various tests on the WordCounter utility class
making use of file resources EmptyFile.txt and ValidFile.txt to ensure the class gives predictable results.

To run the tests run:

mvn test

To build the .jar file run:

mvn package
