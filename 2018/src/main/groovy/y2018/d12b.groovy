List lines = new File("../resources/12.txt").readLines()

String initial = lines[0].split(':')[1].trim()

Map changes = lines.drop(2).collectEntries{
	def split = it.split(' => ')
	return [(split[0]): split[1]]
}

List start = ((0..10).collect{'.'} + initial.split('') + (0..200).collect{'.'}).flatten()

int prev = 0
int step = 0
int counter = 0;
boolean found = false

while(!found) {
	counter++
	List newStart = ((0..1).collect{'.'} + (0..<(start.size()-5)).collect { index ->
		String x = start[(index..(index+4))].join('')
		changes[x] ?: '.'
	} + (0..2).collect{'.'}).flatten()
	start = newStart

	int z = calcScore(start)

	if( step == z - prev) {
		found = true
	}
	step = z - prev
	prev = z
}

BigInteger fiftyBil = new BigInteger("50000000000")

println fiftyBil.subtract(counter).multiply(step).add(calcScore(start))

int calcScore(grid) {
	return ((0..<(grid.size())).findAll {
		grid[it] == '#'
	}.collect{it - 11}.sum())
}