package com.bluegraybox.demo


/**
 * Utility to process a data file.
 * The data file contains one entry per row. Each entry has a user id, date, and one or more floating-point numbers, all comma-separated like so:
 * <pre>
 * foo,2011-04-01,37.5,56.8,73.9
 * </pre>
 * Invoke as:
 * <pre>
 * java -cp ../java/target/csv_parser-1.0-SNAPSHOT.jar com.bluegraybox.demo.CSVParser data.csv > data.out
 * </pre>
 */
class CSVParser {
	def averages = [:]

	/**
	* Parse an input file, perform a calculation, and print out the results.
	* @param args command-line arguments; should just be filename
	*/
	static main(args) {
		if (args.length < 1)
			throw new IllegalArgumentException("Missing filename parameter.")
		String filename = args[0]
		def parser = new CSVParser()
		parser.processFile(filename)
		parser.averages.keySet().sort().each { key ->
			println( String.format("%s\t%.8f", key, parser.averages[key]['average']))
		}
	}

	protected processFile( filename ) {
		new File(filename).eachLine{ line ->
			processLine( line )
		}
	}

	protected processReader( reader ) {
		reader.eachLine{ line ->
			processLine( line )
		}
	}

	protected processLine( line ) {
		def data = line.trim().split( ',' )
		def clientId = data[0]
		def date = data[1]
		try {
			if (data.length > 2) {
				data = data[2..data.size()-1]
				data = data.collect {x -> x.toFloat() }                 // convert strings to floats
				def value = data.inject(0) { total, item -> total += item }
				value /= data.size
				if (! averages[clientId])
					averages[clientId] = ["count": 0, "average": 0]
				def count = averages[clientId]['count']
				def old_total = averages[clientId]['average'] * count
				averages[clientId]['average'] = (old_total + value) / (count + 1)
				averages[clientId]['count'] += 1
			}
		} catch (Exception e) {
			// Skip this line
		}
	}

	protected getAverage( clientId ) {
		averages[clientId] ? averages[clientId]['average'] : 0
	}

}
