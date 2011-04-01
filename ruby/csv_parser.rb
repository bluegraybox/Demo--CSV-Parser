#!/usr/bin/ruby

filename = ARGV.shift()
runningAverages = Hash.new {|hash, key| hash[key] = { 'count' => 0, 'average' => 0 } }
File.open(filename, 'r') { |io|
    io.each_line() { |line|
        id, date, *data = line.chomp.split ','
        data.map! {|x| x.to_f }                # convert strings to floats
        value = data.reduce(:+) / data.length  # reduce(:+) sums the array
        count = runningAverages[id]['count']
        old_total = (runningAverages[id]['average'] * count)
        runningAverages[id]['average'] = (old_total + value) / (count + 1)
        runningAverages[id]['count'] += 1
    }
}

runningAverages.keys.sort.each() { |key|
    print "%s\t%.15f\n" % [key, runningAverages[key]['average']]
}

