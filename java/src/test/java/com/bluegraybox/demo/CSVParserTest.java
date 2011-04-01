package com.bluegraybox.demo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CSVParserTest {

	private CSVParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new CSVParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test that nothing breaks if there's no data.
	 */
	@Test
	public void testMissingData() {
		parser.processLine("aaa,2011-03-30");
		assertEquals(0.0, parser.getAverage("aaa"), 0.0);
	}

	/**
	 * Test a single line with one item.
	 */
	@Test
	public void testSingleData() {
		parser.processLine("aaa,2011-03-30,1.1");
		assertEquals(1.1, parser.getAverage("aaa"), 0.00000001);
	}

	/**
	 * Test a single line with multiple items.
	 */
	@Test
	public void testMultiData() {
		parser.processLine("aaa,2011-03-30,1.1,1.3,1.5,1.7");
		assertEquals(1.4, parser.getAverage("aaa"), 0.00000001);
	}

	/**
	 * Test multiple lines, each with one item.
	 */
	@Test
	public void testMultiLineSingleData() {
		parser.processLine("aaa,2011-03-30,1.1");
		parser.processLine("aaa,2011-03-30,1.3");
		parser.processLine("aaa,2011-03-30,1.5");
		parser.processLine("aaa,2011-03-30,1.7");
		assertEquals(1.4, parser.getAverage("aaa"), 0.00000001);
	}

	/**
	 * Test multiple lines, each with one item.
	 */
	@Test
	public void testMultiLineMultiData() {
		parser.processLine("aaa,2011-03-27,1.1,2.1,3.1,4.1");
		parser.processLine("aaa,2011-03-28,1.3,2.3,3.3,4.3");
		parser.processLine("aaa,2011-03-29,1.5,2.5,3.5,4.5");
		parser.processLine("aaa,2011-03-30,1.7,2.7,3.7,4.7");
		assertEquals(2.9, parser.getAverage("aaa"), 0.00000001);
	}
	
	/**
	 * Test the Reader interface.
	 * @throws IOException If it can't read a StringReader (not likely)
	 */
	@Test
	public void testReader() throws IOException {
		Reader reader = new StringReader("" +
			"aaa,2011-03-27,1.1,2.1,3.1,4.1\n" +
			"aaa,2011-03-28,1.3,2.3,3.3,4.3\n" +
			"aaa,2011-03-29,1.5,2.5,3.5,4.5\n" +
			"aaa,2011-03-30,1.7,2.7,3.7,4.7");
		parser.processReader(reader);
		assertEquals(2.9, parser.getAverage("aaa"), 0.00000001);
	}

}
