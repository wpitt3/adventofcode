int serial = 3214

List grid = (0..300).collect{ x ->
	(0..300).collect{ y ->
		powerLevel(x, y, serial)
	}
}

List threeByThree = (1..298).collect{ x ->
	(1..298).collect{ y ->
		int value = (0..2).sum{ x2 ->
			(0..2).sum{ y2 ->
				grid[x+x2][y+y2]
			}
		}

		if( value == 77) {
			println x +" " + y
		}
	}
}

println threeByThree.collect{
	it.max()
}.max()
	


int powerLevel(int x, int y, int serial) {
	int rackId = x + 10
	int a = (rackId * y + serial) * rackId
	return ((a % 1000) - (a % 100))/100
}