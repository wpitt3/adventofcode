import y2018.OpcodeRunner

lines = OpcodeRunner.readInstructions();
Map functions = OpcodeRunner.generateFunctions()

Integer linesWithMoreThanThreeFunctions = lines.count { line ->
	2 < functions.count { k, v ->
		line[2] == v(line[1][1], line[1][2], line[1][3], line[0])
	}
}

println linesWithMoreThanThreeFunctions

List fPossible = (0..15).collect{ functions.keySet() }

lines.forEach { line ->
	fPossible[line[1][0]] = fPossible[line[1][0]].intersect(functions.findAll { k, v ->
		line[2] == v(line[1][1], line[1][2], line[1][3], line[0])
	}.keySet())
}

(0..16).each {
	fPossible.findAll{ it.size() == 1}.each { y ->
		fPossible = fPossible.collect{ it == y ? it : it - y[0] }
	}
}
fPossible = fPossible.collect{it[0]}

List instructions = new File("../../resources/16.txt").readLines().drop(3190).collect{it.split(' ').collect{Integer.parseInt(it.trim())}}

List registers = [0, 0, 0, 0]

instructions.each {
	registers = functions[fPossible[it[0]]](it[1], it[2], it[3], registers)
}

println registers[0]