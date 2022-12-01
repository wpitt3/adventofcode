package y2018


lines = new File("../../resources/25.txt").readLines()
List stars = lines.collect {line ->
	line.split(",").collect{Integer.parseInt(it.trim())}
}

println stars[0]
//List<Set<List<Integer>>> constellations = new ArrayList()


Map<Integer, List<Set>> visible = [:]

(0..(stars.size()-1)).forEach{
	visible[it] = [].toSet()
}

(0..(stars.size()-2)).forEach{ a ->
	visible[a] = visible[a].plus(((a+1)..(stars.size()-1)).findAll { b ->
		(0..3).sum{Math.abs(stars[a][it]-stars[b][it])} <= 3
	})
	visible[a].forEach{
		visible[it].add(a)
	}
}


Map<Integer, Set<Integer>> constellations = [:]
Set<Integer> seen = [].toSet()

visible.forEach { k, v ->
	if (!seen.contains(k)) {
		List<Integer> current = [k]
		constellations[k] = [].toSet()
		int i = 0
		while (i < current.size()) {
			if (!seen.contains(current[i])) {
				constellations[k] = constellations[k].plus(visible[current[i]])
				current.addAll(visible[current[i]])
				seen.add(current[i])
			}
			i += 1
		}
	}
}

println constellations.size()

//println ""
//visible.forEach{ k, v ->
//	println k +" " + v
//}
//
//println ""
//constellations.forEach{ k, v ->
//	println k +" " + v
//}
