int serial = 3214

int gridSize = 300

List grid = (1..gridSize).collect{ x ->
	(1..gridSize).collect{ y ->
		powerLevel(x, y, serial)
	}
}

(1..<gridSize).each { z ->
	grid[0][z] = grid[0][z] + grid[0][z-1]
	grid[z][0] = grid[z][0] + grid[z-1][0]
}

(1..<gridSize).each { x ->
	(1..<gridSize).each { y ->
		grid[x][y] = grid[x][y] + grid[x-1][y] + grid[x][y-1] - grid[x-1][y-1]
	}
}

int max = 0
String maxCode = ""

(0..<gridSize).each { x ->
	(0..<gridSize).each { y ->
		int maxSize = gridSize - Math.max(x, y);
		maxSize = Math.min(maxSize, 20)
		(1..maxSize).each{ size ->
			int current = calcPower(x, y, size, grid)
			if( current > max ) {
				max = current
				maxCode =  (x+1) + "," + (y+1) + "," + size
			}
		}
	}
}

println maxCode



int calcPower(int x, int y, int sizeA, List grid) {
	int size = sizeA -1
	return getGrid(x-1, y-1, grid) + getGrid(x+size, y+size, grid) - getGrid(x+size, y-1, grid) - getGrid(x-1, y+size, grid)
}

int getGrid(int x, int y, List grid) {
	return (x < 0 || y < 0) ? 0 : grid[x][y]
}

int powerLevel(int x, int y, int serial) {
	int rackId = x + 10
	int a = (rackId * y + serial) * rackId
	return ((a % 1000) - (a % 100))/100 - 5
}