#!/usr/bin/ruby

# Unit tests for csv_parser2

require 'test/unit'
require File.join(File.dirname(__FILE__), 'csv_parser')


class TestText < Test::Unit::TestCase

    # Test that nothing breaks if there's no data.
    def test_missing_data
        parser = CSVParser.new
        parser.process_line "aaa,2011-03-30"
        assert_in_delta(0.0, parser.average("aaa"), 0.000001)
    end

    # Test a single line with one item.
    def test_single_data
        parser = CSVParser.new
        parser.process_line "aaa,2011-03-30,1.1"
        assert_in_delta(1.1, parser.average("aaa"), 0.000001)
    end

    # Test a single line with multiple items.
    def test_multi_data
        parser = CSVParser.new
        parser.process_line "aaa,2011-03-30,1.1,1.3,1.5,1.7"
        assert_in_delta(1.4, parser.average("aaa"), 0.000001)
    end

    # Test multiple lines, each with one item.
    def test_multi_line_single_data
        parser = CSVParser.new
        parser.process_line "aaa,2011-03-30,1.1"
        parser.process_line "aaa,2011-03-30,1.3"
        parser.process_line "aaa,2011-03-30,1.5"
        parser.process_line "aaa,2011-03-30,1.7"
        assert_in_delta(1.4, parser.average("aaa"), 0.000001)
    end

    # Test multiple lines, each with one item.
    def test_multi_line_multi_data
        parser = CSVParser.new
        parser.process_line "aaa,2011-03-27,1.1,2.1,3.1,4.1"
        parser.process_line "aaa,2011-03-28,1.3,2.3,3.3,4.3"
        parser.process_line "aaa,2011-03-29,1.5,2.5,3.5,4.5"
        parser.process_line "aaa,2011-03-30,1.7,2.7,3.7,4.7"
        assert_in_delta(2.9, parser.average("aaa"), 0.000001)
    end

    # Test that Number_Format_Exceptions are handled correctly.
    def test_non_numeric
        parser = CSVParser.new
        parser.process_line "aaa,2011-03-26,1.1,2.1,3.1,4.1"
        parser.process_line "aaa,2011-03-27,1.3,2.3,3.3,4.3"
        parser.process_line "aaa,2011-03-28,9999.9,1.3,2.3,3.3.4.3"  # typo! period instead of comma
        parser.process_line "aaa,2011-03-29,1.5,2.5,3.5,4.5"
        parser.process_line "aaa,2011-03-30,1.7,2.7,3.7,4.7"
        assert_in_delta(2.9, parser.average("aaa"), 0.000001)
    end

end

