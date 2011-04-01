#!/usr/bin/ruby

require File.join(File.dirname(__FILE__), 'csv_parser')

filename = ARGV.shift()
parser = CSVParser.new
parser.process_file filename

parser.averages.keys.sort.each() { |key|
    print "%s\t%.15f\n" % [key, parser.averages[key]['average']]
}

