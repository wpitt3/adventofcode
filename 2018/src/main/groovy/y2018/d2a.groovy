def x = new File("../resources/2.txt").readLines()

List allFreqs = x.collect{ code ->
	List freq = (0..200).collect{0}

	List chars = (0..<(code.size())).collect {(int)code.charAt(it)}

	chars.each{
		freq[it] += 1
	}

	return freq
}


println allFreqs.count{it.contains(2)} * allFreqs.count{it.contains(3)}
