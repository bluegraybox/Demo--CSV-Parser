#!/usr/bin/ruby

# Process a CSV file. Entries are client id, date, and one or more numbers, like so:
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
        client_id, date, *data = line.chomp.split ','
        if data.length > 0 then
            data.map! {|x| x.to_f}                 # convert strings to floats
            value = data.reduce(:+) / data.length  # reduce(:+) sums the array
            count = averages[client_id]['count']
            old_total = averages[client_id]['average'] * count
            averages[client_id]['average'] = (old_total + value) / (count + 1)
            averages[client_id]['count'] += 1
        end
    end

    def average client_id
        averages[client_id]['average']
    end

end

