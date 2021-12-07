def lines = new File("../resources/3.txt").readLines()

def canvas = (0..1000).collect{(0..1000).collect{0}}


lines = lines.collect{ line ->
	String afterAt = line.split('@')[1];
	List coords = afterAt.split(':')[0].split(',').toList().collect{ Integer.parseInt(it.trim())}
	List size = afterAt.split(':')[1].split('x').toList().collect{ Integer.parseInt(it.trim())}

	return [coords: coords, size: size]
}

lines.each { patch ->
	(0..<(patch.size[0])).each { x ->
		(0..<(patch.size[1])).each { y ->
			if (canvas[x + patch.coords[0]][y + patch.coords[1]] == 0) {
				canvas[x + patch.coords[0]][y + patch.coords[1]] = 1
			} else {
				canvas[x + patch.coords[0]][y + patch.coords[1]] = 2
			}

		}
	}

}

println canvas.sum{ line ->
	line.count{it == 2}
} 
