List lines = new File("../resources/6.txt").readLines()

lines = lines.collect{ line ->
	line.split(',').collect{Integer.parseInt(it.trim())}
}

int size = 354;

List grid = (0..size).collect{(0..size).collect{0}}


(0..<(lines.size())).each{ lineIndex ->
	List line = lines[lineIndex]
	grid[line[1]][line[0]] = lineIndex + 1
}

List newGrid = cloneGrid(grid);

int changes = 1;
while(changes > 0) {
	changes = 0;
	(1..<size).each{ x ->
		(1..<size).each{ y -> 
			if (grid[x][y] == 0) {
				List z = [ grid[x+1][y], grid[x-1][y], grid[x][y+1], grid[x][y-1]].findAll{it != 0}.flatten().unique()
				if( z.size() != 0) {
					newGrid[x][y] = z.size() > 1 ? z : z[0]
					changes +=1;
				}
			}
		}
	}
	grid = cloneGrid(newGrid)
	println changes
	// grid.each{ line ->
	// 	println line.collect{ it instanceof List ? '.' : it }
	// }
	// println ''
}

List edges = grid[1] + grid[size-1] + grid.collect{it[1]} + grid.collect{it[size-1]}
edges = edges.findAll{! (it instanceof List)}.unique()

List containedNodes = ( (0..(lines.size())) - edges )

freq = (0..(lines.size())).collect{0}

(1..<size).each{ x ->
	(1..<size).each{ y -> 
		if (!(grid[x][y] instanceof List)) {
			freq[grid[x][y]] += 1
		}
	}
}

println containedNodes.collect{freq[it]}.max()

List cloneGrid(grid) {
	return grid.collect{
		it.clone();
	}
}