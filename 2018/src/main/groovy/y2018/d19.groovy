package y2018

lines = new File("../../resources/19.txt").readLines()

List instructions = lines.drop(1).collect {line ->
	[line.substring(0, 4)] + line.substring(5).split(' ').collect { Integer.parseInt(it.trim()) }
}

Map functions = OpcodeRunner.generateFunctions()

ip = Integer.parseInt(lines[0].split(" ")[1])

List registers = [0, 0, 0, 0, 0, 0]

while (registers[ip] > -1 && registers[ip] < instructions.size()) {
	inst = instructions[registers[ip]]
	registers = functions[inst[0]](inst[1], inst[2], inst[3], registers)
	registers[ip] += 1
}
println registers[0]

registers = [1, 0, 0, 0, 0, 0]

while (registers[ip] > -1 && registers[ip] < instructions.size()) {
	inst = instructions[registers[ip]]
	registers = functions[inst[0]](inst[1], inst[2], inst[3], registers)
	registers[ip] += 1
}
println registers