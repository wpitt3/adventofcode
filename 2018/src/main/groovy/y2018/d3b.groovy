def lines = new File("../resources/3.txt").readLines()

def canvas = (0..1000).collect{(0..1000).collect{-1}}


lines = lines.collect{ line ->
	String afterAt = line.split('@')[1];
	List coords = afterAt.split(':')[0].split(',').toList().collect{ Integer.parseInt(it.trim())}
	List size = afterAt.split(':')[1].split('x').toList().collect{ Integer.parseInt(it.trim())}

	return [coords: coords, size: size]
}



(0..<(lines.size())).each { index ->
	patch = lines[index]
	(0..<(patch.size[0])).each { x ->
		(0..<(patch.size[1])).each { y ->
			if (canvas[x + patch.coords[0]][y + patch.coords[1]] == -1) {
				canvas[x + patch.coords[0]][y + patch.coords[1]] = index
			} else {
				canvas[x + patch.coords[0]][y + patch.coords[1]] = -2
			}

		}
	}

}


List freq = (0..(lines.size())).collect{0}

canvas.each{ line ->
	line.each{
		if (it >= 0) {
			freq[it]+=1
		}
	}
} 

// println freq


(0..<(lines.size())).each { index ->
	int size = lines[index].size[0] * lines[index].size[1]

	if (freq[index] == size) {
		println index + 1
	}
}


//105047