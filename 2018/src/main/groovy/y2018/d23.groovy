package y2018


lines = new File("../../resources/23.txt").readLines()
List nanobots = lines.collect {line ->
	split = line.split(", r=")
	split[0].replaceAll("[^0-9-,]", "").split(",").collect{Long.parseLong(it.trim())} + [Long.parseLong(split[1])]
}

biggestRange = nanobots.max{it[3]}
println nanobots.findAll{
	(Math.abs(it[0]-biggestRange[0]) + Math.abs(it[1]-biggestRange[1]) + Math.abs(it[2]-biggestRange[2])) <= biggestRange[3]
}.size()


offset = findpointWithMostNanobots(nanobots)
println offset
int minNanobotCount = findAllInRangeOf(offset, nanobots).size()
println minNanobotCount

int[] visibleNanobotCount = new int[1000]
(0..999).each{ visibleNanobotCount[it] = 0}
(0..998).each{ i ->
	((i+1)..999).each{ j ->
		if ((0..2).sum{Math.abs(nanobots[i][it] - nanobots[j][it])} <= nanobots[i][3] + nanobots[j][3]) {
			visibleNanobotCount[i] += 1;
			visibleNanobotCount[j] += 1;
		}
	}
}

ignoredNanobots = (0..999).findAll{visibleNanobotCount[it] < minNanobotCount}.toSet()
nanobots = nanobots.indexed().findAll {!ignoredNanobots.contains(it.key)}.collect{it.value}
offset = findCentreOfNanobots(nanobots)
println offset
println findAllInRangeOf(offset, nanobots).size()

offset = findpointWithMostNanobots(nanobots, offset, 1000)
println offset
nanobots = findAllInRangeOf(offset, nanobots)

int min = 0;
for (int i = -50; i < 10; i++) {
	for (int j = -50; j < 10; j++) {
		for (int k = -50; k < 10; k++) {
			if (min > i + j + k && nanobots.every{ (Math.abs(it[0]-(offset[0]+i)) + Math.abs(it[1]-(offset[1]+j)) + Math.abs(it[2]-(offset[2]+k))) <= it[3]}){
				min = i + j + k
				println(i + " " + j + " " + k +" " +(i+j+k))
			}
		}
	}
}


static List<Long> findCentreOfNanobots(nanobots) {
	List<Long> offset = [0L, 0L, 0L]
	(0..100).forEach {
		def newOffset = [0L, 0L, 0L]
		int count = 0
		nanobots.forEach { nanobot ->
			def error = (0..2).collect { Math.abs(nanobot[it] - offset[it]) }
			if (error.sum() > nanobot[3]) {
				(0..2).findAll {
					newOffset[it] += (nanobot[it] > offset[it] ? 1 : -1) * ((error.sum() - nanobot[3]) / error.sum() * error[it])
				}
				count += 1
			}
		}

		count = Math.max(count, 1)
		(0..2).forEach{
			offset[it] += Math.floor(newOffset[it] / count)
		}
	}
	return offset.collect{it.toLong()}
}
//println ()

//  912           13972632, 45661226, 28290272
//                  13972632, 45661226, 28290272
//  912 87924130 [13972632, 45661226, 28290272]
//      795 89915524
//      89915526
// 912            13972650, 45661219, 28290271
a = 13972632
b = 45661226
c = 28290272

a = 15972003
b = 44657553
c = 29285970
// 15972003,44657553,29285970
//

//
//
//outOfRange = findAllNotInRangeOf(a, b, c, instructions)
//println inRange.size()
//println inRange.collect{it[3] - (Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c))}.sort()
//println inRange.sort{it[3] - (Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c))}.collect{[it[0]-a, it[1]-b, it[2]-c]}
//println outOfRange.collect{ (Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c)) - it[3]}.sort()
//println outOfRange.sort{it[3] - (Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c))}.collect{[it[0]-a, it[1]-b, it[2]-c]}

//println (inRange.collect{it[3] - (Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c))}.sort())
//println (outOfRange.collect{(Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c)) - it[3]}.sort())
//





static List<Long> findpointWithMostNanobots(nanobots, offset = [0L, 0L, 0L], step = 100_000_000L) {
	while(step > 0) {
		Map results = [:]
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				for (int k = -2; k < 3; k++) {
					results[[(long)i, (long)j, (long)k]] = findAllInRangeOf(i * step + offset[0], j * step + offset[1], k * step + offset[2], nanobots).size()
				}
			}
		}
		def max = results.max { it.value }
		if (max.key.every { it == 0 }) {
			step = (long)(step * 0.5)
		} else {
			for (int i = 0; i < 3; i++) {
				offset[i] = offset[i] + step * max.key[i]
			}
		}
//		println  step + " " + max.value + " " + offset
	}
	return offset
}
//
//println  step + " " + max.value + " " + offset
//step = 100L

//for (int x = 0; x < 10; x++) {
//	results = [:]
//	for (int i = -1; i < 1; i++) {
//		for (int j = -1; j < 1; j++) {
//			for (int k = -1; k < 1; k++) {
//				results[[(long)i, (long)j, (long)k]] = findAllInRangeOf(i * step + offset[0], j * step + offset[1], k * step + offset[2], instructions).size()
//			}
//		}
//	}
//	if (results.size() == 1) {
//		step = (long)(step * 0.5)
//	} else {
//		newOffets = results.max{ - (it.key[0] + it.key[1] + it.key[2])}
//		for (int i = 0; i < 3; i++) {
//			offset[i] = offset[i] + step * newOffets.key[i]
//		}
//	}
//}
//println  step + " " + max.value + " " + offset.sum() + " " + offset



static List<List<Long>> findAllInRangeOf(Long a, Long b, Long c, List<List<Long>> nanobots) {
	return nanobots.findAll{
		(Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c)) <= it[3]
	}
}

static List<List<Long>> findAllInRangeOf(List<Long> offset, List<List<Long>> nanobots) {
	return nanobots.findAll{ nanobot ->
		(0..2).sum{Math.abs(nanobot[it]-offset[it])} <= nanobot[3]
	}
}

static List<List<Long>> findAllNotInRangeOf(Long a, Long b, Long c, List<List<Long>> nanobots) {
	return nanobots.findAll{
		(Math.abs(it[0]-a) + Math.abs(it[1]-b) + Math.abs(it[2]-c)) > it[3]
	}
}
