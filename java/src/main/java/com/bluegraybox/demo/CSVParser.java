package com.bluegraybox.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class CSVParser {

	private HashMap<String, RunningAverage> runningAverages = new HashMap<String, RunningAverage>();

	/**
	 * @param args
	 * @throws IOException If unable to read from the input file.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 1)
			throw new IllegalArgumentException("Missing filename parameter.");
		String filename = args[0];
		CSVParser parser = new CSVParser();
		parser.processFile(filename);
		Set<Entry<String, RunningAverage>> averagesSet = parser.getAverages();
		List<Entry<String, RunningAverage>> averages = new ArrayList<Entry<String, RunningAverage>>(averagesSet);
		Collections.sort(averages, new Comparator<Entry<String, RunningAverage>>(){
			public int compare(Entry<String, RunningAverage> entry1,
					Entry<String, RunningAverage> entry2) {
				return entry1.getKey().compareTo(entry2.getKey());
			}});
		for (Entry<String, RunningAverage> entry : averages) {
			System.out.println( entry.getKey() + "\t" + entry.getValue().getValue() );
		}
	}

	/**
	 * Process a file and print out the averages for each ID.
	 * @param filename
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void processFile(String filename) throws NumberFormatException, IOException {
		Reader fileReader = new FileReader(filename);
		processReader(fileReader);
	}

	public void processReader(Reader fileReader) throws IOException {
		BufferedReader reader = new BufferedReader(fileReader);
		String line = null;
		while ((line = reader.readLine()) != null) {
			processLine(line);
		}
	}

	public void processLine(String line) {
		String[] fields = line.split(",");
		if (fields.length < 3)
			System.err.println("Missing fields in line: [" + line + "]");
		else {
			String id = fields[0];
			String date = fields[1];
			String[] data = Arrays.copyOfRange(fields, 2, fields.length);
			double value = calcValue(data);

			if (! runningAverages.containsKey(id))
				runningAverages.put(id, new RunningAverage());
			RunningAverage rAvg = runningAverages.get(id);
			rAvg.updateAverage(value);
		}
	}

	protected double calcValue(String[] data) {
		double total = 0;
		for (int i = 0; i < data.length; i++) {
			total += Double.valueOf(data[i]);
		}
		double value = total / data.length;  // calculate the average of the fields, not their total.
		// double value = total;
		return value;
	}

	public Set<Entry<String,RunningAverage>> getAverages() {
		return runningAverages.entrySet();
	}
	
	public double getAverage(String id) {
		double value = 0.0;
		RunningAverage average = runningAverages.get(id);
		if (average != null)
			value = average.getValue();
		return value;
	}
	
}