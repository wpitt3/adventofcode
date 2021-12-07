List lines = new File("../resources/5.txt").readLines()

String input = lines[0]

List polymerPairs = (0..25).collect {
	((char)(it+65)).toString() + ((char)(it+65+32)).toString()
}.collect{
	[it, it.split('').reverse().join('')]
}.flatten()


input = minisePolymer(input, polymerPairs)

List polymers = (0..25).collect {
	[((char)(it+65)).toString(), ((char)(it+65+32)).toString()]
}

println polymers.collect{ polymerTypes ->
	String newInput = input
	polymerTypes.each{
		newInput = newInput.replaceAll(it, '')
	}

	return minisePolymer(newInput, polymerPairs).length()
}.min()

String minisePolymer(input, polymerPairs) {
	int lineLength = input.length()
	int newLineLength = 0

	while (lineLength != newLineLength) {
		lineLength = input.length()
		polymerPairs.each{
			input = input.replaceAll(it, '')
		}
		newLineLength = input.length()
	}
	return input
}