package com.bluegraybox.demo;

public class RunningAverage {
	private int count = 0;
	private double average = 0;

	void updateAverage(double value) {
		double old_total = average * count;
		average = (old_total + value) / (count + 1);
		count += 1;
	}

	public double getValue() {
		return average;
	}
}
