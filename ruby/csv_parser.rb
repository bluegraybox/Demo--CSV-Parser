#!/usr/bin/ruby

# Process a CSV file. Entries are user id, date, and one or more numbers, like so:
#     foo,2011-04-01,37.5,56.8,73.9


class CSVParser
    attr_reader :averages

    def initialize
        @averages = Hash.new {|hash, key| hash[key] = { 'count' => 0, 'average' => 0 } }
    end

    def process_file filename
        IO.foreach(filename) { |line|
            process_line line
        }
    end

    def process_line line
        id, date, *data = line.chomp.split ','
        if data.length > 0 then
            data.map! {|x| x.to_f}                 # convert strings to floats
            value = data.reduce(:+) / data.length  # reduce(:+) sums the array
            count = averages[id]['count']
            old_total = averages[id]['average'] * count
            averages[id]['average'] = (old_total + value) / (count + 1)
            averages[id]['count'] += 1
        end
    end

    def average id
        averages[id]['average']
    end

end

