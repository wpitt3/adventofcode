List lines = new File("../resources/7.txt").readLines()

lines = lines.collect{ [it[5], it[36]] }

String output = ''

while(lines.size() > 0) {
	String x = (lines.collect{it[0]}.unique() - lines.collect{it[1]}.unique()).sort()[0]
	output += x;

	if (lines.size() == 1) {
		output += lines[0][1];
	}
	lines = lines.findAll{ it[0] != x }
}

println output