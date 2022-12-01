
def x = new File("../resources/1.txt")

List y = x.readLines().collect{ Integer.parseInt(it.replace('+', ''))};

Set<Integer> z = new HashSet<>();
int current = 0;

boolean found = false;

while(!found) {
	for ( int val : y ) {
		current += val
		if (z.contains(current)) {
			found = true;
			break;
		} else {
			z.add(current);
		}
	}
}

println current
