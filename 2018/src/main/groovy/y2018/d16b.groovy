List lines = new File("../resources/16.txt").readLines()

lines = lines.take(3188).collate(4).collect{ instruction ->
	instruction.take(3)
		.collect{ line -> line.replaceAll('.*\\[', '').replaceAll(']|,', '').split(' ').collect{Integer.parseInt(it.trim())}
	}
}

Map functions = getFunctions()
List fPossible = (0..15).collect{ [] }

lines.count { line ->
	List fs = functions.findAll { k, v ->
		line[2] == v(line[1][1], line[1][2], line[1][3], line[0])
	}.collect{ k, v -> k }
	fPossible[line[1][0]].add(fs)
}


fPossible = fPossible.collect{ fs ->
	functions.findAll { k, v ->
		fs.every{it.contains(k)}
	}.collect{ k, v -> k}
}

(0..11).each {
	List x = fPossible.findAll{ it.size() == 1}
	x.each { y ->
		fPossible = fPossible.collect{ it == y ? it : it - y[0] }
	}
}
fPossible = fPossible.collect{it[0]}
// println fPossible

List instructions =  new File("../resources/16.txt").readLines().drop(3190).collect{it.split(' ').collect{Integer.parseInt(it.trim())}}

List registers = [0, 0, 0, 0]

instructions.each {
	registers = functions[fPossible[it[0]]](it[1], it[2], it[3], registers)
}

println registers[0]

Map getFunctions() {
	Closure byR = { int rIndex, List registers -> registers[rIndex] }
	Closure byV = { int rIndex, List registers -> rIndex }

	Closure add = { int a, int b -> a + b }
	Closure multi = { int a, int b -> a * b }
	Closure and = { int a, int b -> a & b }
	Closure or = { int a, int b -> a | b }
	Closure assign = { int a, int b -> a }
	Closure gThan = { int a, int b -> a>b?1:0 }
	Closure equals = { int a, int b -> a==b?1:0 }

	Closure buildIt = { Closure method, Closure aBy, Closure bBy ->
		return {int a, int b, int c, List registers -> 
			List x = registers.clone()
			x[c] = method( aBy(a,registers), bBy(b,registers) )
			return x
		}
	}

	return [
		addr: buildIt(add, byR, byR),
		addi: buildIt(add, byR, byV),
		mulr: buildIt(multi, byR, byR),
		muli: buildIt(multi, byR, byV),
		banr: buildIt(and, byR, byR),
		bani: buildIt(and, byR, byV),
		borr: buildIt(or, byR, byR),
		bori: buildIt(or, byR, byV),
		setr: buildIt(assign, byR, byR),
		seti: buildIt(assign, byV, byR),
		gtir: buildIt(gThan, byV, byR),
		gtri: buildIt(gThan, byR, byV),
		gtrr: buildIt(gThan, byR, byR),
		eqir: buildIt(equals, byV, byR),
		eqri: buildIt(equals, byR, byV),
		eqrr: buildIt(equals, byR, byR),
	]
}