#!/usr/bin/ruby

(1..31).each do |day|
    date = "2011-03-%02d" % day
    ('a'..'z').each do |first|
        ('a'..'z').each do |second|
            id = first + second
            data = Array.new(24) {|i| rand(100) }
            print "%s,%s,%s\n" % [id, date, data.join(',')]
        end
    end
end

