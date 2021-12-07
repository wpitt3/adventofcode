List lines = new File("../resources/10.txt").readLines()

lines = lines.collect{ line ->
	List split = line.split('>')
	List coords = split[0].split('<')[1].split(',').collect{Integer.parseInt(it.trim())}
	List velocity = split[1].split('<')[1].split(',').collect{Integer.parseInt(it.trim())}
	return [coords:coords, velocity: velocity]
}


int counter = 10000

lines = step(lines, counter)

int minScore = Integer.MAX_VALUE

boolean notFound = true

while(notFound) {
	List newLines = step(lines)

	int score = calcScore(newLines)

	if( score < minScore) {
		minScore = score
		lines = newLines
		counter ++;
	} else {
		printIt(lines)
		notFound = false
	}
}

println counter

int calcScore(lines) {
	return Math.abs(lines.min{ it.coords[0]}.coords[0]-lines.max{ it.coords[0]}.coords[0]) + Math.abs(lines.min{ it.coords[1]}.coords[1] - lines.max{ it.coords[1]}.coords[1])
}

void printIt(lines) {
	int minx = lines.min{ it.coords[0]}.coords[0]
	int maxx = lines.max{ it.coords[0]}.coords[0]
	int miny = lines.min{ it.coords[1]}.coords[1]
	int maxy = lines.max{ it.coords[1]}.coords[1]

	List grid = (miny..maxy).collect{(minx..maxx).collect{'.'}}
	lines.each{ line ->
		grid[line.coords[1]-miny][line.coords[0]-minx] = '#'
	}

	grid.each {
		println it.join('')
	}
}

List step(List lines, int steps = 1) {
	lines.collect{ line ->
		Map newLine = [coords: line.coords.clone(), velocity: line.velocity]
		newLine.coords[0] = newLine.coords[0] + (line.velocity[0] * steps)
		newLine.coords[1] = newLine.coords[1] + (line.velocity[1] * steps)
		return newLine
	}
}
