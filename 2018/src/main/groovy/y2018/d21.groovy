package y2018

lines = new File("../../resources/21.txt").readLines()
List instructions = lines.drop(1).collect {line ->
	[line.substring(0, 4)] + line.substring(5).split(' ').collect { Integer.parseInt(it.trim()) }
}
Map functions = OpcodeRunner.generateLongFunctions()
ip = Integer.parseInt(lines[0].split(" ")[1])


step = 0
List registers = [0L, 0L, 0L, 0L, 0L, 0L]
while (registers[ip] > -1 && registers[ip] < instructions.size()) {
	inst = instructions[registers[ip]]
	if (registers[ip] == 28 || registers[ip] == 12) {
		println(registers[5])
//		println(registers[ip] + " " + inst)
	}
	println(registers[ip] + " " + inst)
	registers = functions[inst[0]](inst[1], inst[2], inst[3], registers)
	println("                                " + registers.collect{it.toString().padLeft(15, ' ')})
	registers[ip] += 1
	step++
}

println (step)