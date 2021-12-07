List lines = new File("../resources/5.txt").readLines()

String input = lines[0]

List x = (0..25).collect {
	((char)(it+65)).toString() + ((char)(it+65+32)).toString()
}.collect{
	[it, it.split('').reverse().join('')]
}.flatten()

int lineLength = input.length()
int newLineLength = 0

while (lineLength != newLineLength) {
	lineLength = input.length()
	x.each{
		input = input.replaceAll(it, '')
	}
	newLineLength = input.length()
}
println lineLength


