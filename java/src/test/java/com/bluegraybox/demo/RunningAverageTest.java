package com.bluegraybox.demo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RunningAverageTest {
	
	private RunningAverage average;

	@Before
	public void setUp() throws Exception {
		average = new RunningAverage();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test the initial state.
	 */
	@Test
	public void testInitial() {
		assertEquals(0.0, average.getValue(), 0.0);
	}

	/**
	 * Test a single value.
	 */
	@Test
	public void testSingle() {
		average.updateAverage(3.3);
		assertEquals(3.3, average.getValue(), 3.3);
	}

	/**
	 * Test multiple values.
	 */
	@Test
	public void testMultiple() {
		average.updateAverage(1.1);
		average.updateAverage(1.2);
		average.updateAverage(1.3);
		average.updateAverage(1.4);
		average.updateAverage(1.5);
		assertEquals(1.3, average.getValue(), 0.0);
	}

}
