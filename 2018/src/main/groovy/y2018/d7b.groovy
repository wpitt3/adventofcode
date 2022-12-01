List lines = new File("../resources/7.txt").readLines()

lines = lines.collect{ [it[5], it[36]] }

workers = 5

int time = 0;
List pool = (0..<workers).collect{[job:'', time:0, free:true]}

while(lines.size() > 0) {
	// String x = nextJob(lines)
	// int time = time(x)
	while(nextJob(lines, pool) && pool.find{it.free}) {
		String job = nextJob(lines, pool)
		def thread = pool.find{it.free}
		thread.free = false
		thread.job = job
		thread.time = getTime(job) 
	}
	pool.collect{ thread -> 
		if( thread.time == 1 ) {
			if (lines.size() == 1) {
				time += getTime(lines[0][1])
			}
			lines = lines.findAll{ it[0] != thread.job }
			thread.job = ''
			thread.free = true
		}
		if( thread.time != 0 ) {
			thread.time -= 1
		}
		return thread
	}
	time += 1
}

println time

String nextJob(lines, pool) {
	return (lines.collect{it[0]}.unique() - lines.collect{it[1]} - pool.collect{it.job}.unique()).sort()[0]
}

int getTime(x) {
	return (int)x.charAt(0) - 64 + 60
}