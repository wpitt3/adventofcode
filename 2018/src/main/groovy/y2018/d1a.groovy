
def x = new File("../resources/1.txt")

println x.readLines().collect{ Integer.parseInt(it.replace('+', ''))}.sum();

