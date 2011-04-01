Demo CSV Parser
---------------
Mostly, I created this project to show how Java and Ruby compare
for performing a simple common task: parsing a CSV file full of data.
It's pretty straightforward, and I've implemented it a number of times at
various jobs in various languages.  I've probably done this most often
in Perl, but I decided to use Ruby for the scripting language version,
just to get a bit of practice with it.  If this turns out to be useful
or instructive to anyone else, that's gravy.

The java directory contains the Java version. It's a Maven project. The
CSVParser class does most of the work, with RunningAverage as a
helper. There are test classes for each. 'mvn cobertura:cobertura'
will build the code, run the tests, and generate a coverage report. See
target/site/cobertura/index.html.

The ruby directory contains the Ruby version. It's one file.

The data directory contains a Ruby script to generate random input data.

