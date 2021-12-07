def lines = new File("../resources/4.txt").readLines()

lines = lines.sort()

lines.each{
	println it
}

// 9:31