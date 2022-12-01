List lines = new File("../resources/13.txt").readLines()

String NORTH = 'north'
String SOUTH = 'south'
String WEST = 'west'
String EAST = 'east'

Map turns = [
    (NORTH):[WEST, EAST],
    (SOUTH):[EAST, WEST],
    (WEST):[SOUTH, NORTH],
    (EAST):[NORTH, SOUTH]
]

Map directions = [
    (NORTH):[WEST, NORTH, EAST],
    (SOUTH):[EAST, SOUTH, WEST],
    (WEST):[SOUTH, WEST, NORTH],
    (EAST):[NORTH, EAST, SOUTH]
]

lines = lines.collect{ it.split('') }

int xSize = lines.collect{ it.size() }.max()
int ySize = lines.size()

lines = lines.collect{
  return (it + (1..xSize).collect{' '}).flatten().take(xSize)
}

List<List<Node>> nodes = (0..<ySize).collect { y ->
  (0..<xSize).collect{ x ->
    return (lines[y][x] != ' ') ? new Node(x, y) : null
  }
}

List<Car> cars = []

(0..<ySize).each { y ->
  (0..<xSize).each{ x ->
    if (lines[y][x] != ' ') {
      int type = getType(lines[y][x])
      if (type == 1) {
        int west = getType(lines, x-1, xSize, y, ySize)
        if( west == 2 || west == 4) {
          nodes[y][x].nodes.west = nodes[y][x-1]
        }
        int east = getType(lines, x+1, xSize, y, ySize)
        if( east == 2 || east == 4) {
          nodes[y][x].nodes.east = nodes[y][x+1]
        }
        int north = getType(lines, x, xSize, y-1, ySize)
        if( north == 3 || north == 4) {
          nodes[y][x].nodes.north = nodes[y-1][x]
        }
        int south = getType(lines, x, xSize, y+1, ySize)
        if( south == 3 || south == 4) {
          nodes[y][x].nodes.south = nodes[y+1][x]
        }
      }
      if (type == 2 || type == 4) {
        nodes[y][x].nodes.west = nodes[y][x-1]
        nodes[y][x].nodes.east = nodes[y][x+1]
      }
      if (type == 3 || type == 4) {
        nodes[y][x].nodes.north = nodes[y-1][x]
        nodes[y][x].nodes.south = nodes[y+1][x]
      }
      if (type == 4) {
        nodes[y][x].isJunction = true
      }
      if (lines[y][x] == '<') {
        cars.add(new Car(nodes[y][x], WEST))
      }
      if (lines[y][x] == '>') {
        cars.add(new Car(nodes[y][x], EAST))
      }
      if (lines[y][x] == '^') {
        cars.add(new Car(nodes[y][x], NORTH))
      }
      if (lines[y][x] == 'v') {
        cars.add(new Car(nodes[y][x], SOUTH))
      }
    }
  }
}

int getType(List lines, int x, int xSize, int y, int ySize) {
  String z = getVal(lines, x, xSize, y, ySize)
  return getType(z)
}

String getVal(List lines, int x, int xSize, int y, int ySize) {
  return (x >= 0 && x < xSize && y >= 0 && y < ySize) ? lines[y][x] : null
}

int getType(String node) {
  if (node == '/' || node == '\\') {
    return 1
  } else if (node == '-' || node == '<' || node == '>') {
    return 2
  } else if (node == '|' || node == '^' || node == 'v') {
    return 3
  } else if (node == '+') {
    return 4
  }
  return 0
}

//boolean found = false;?
//println cars
while(cars.size() > 1) {
  cars = cars.sort{ car -> car.node.y * 200 + car.node.x}
//  println ''
  cars.each { car ->
//    println car.node.x + " " + car.node.y
    if (car.node.isJunction) {
      car.direction = directions[car.direction][car.turnIndex]
      car.turnIndex = (car.turnIndex + 1) % 3
    }
    if (car.node.nodes[car.direction]) {
      car.node = car.node.nodes[car.direction]
    } else {
      String direction = turns[car.direction].find { car.node.nodes[it] }
      car.direction = direction
      car.node = car.node.nodes[car.direction]
    }
    if(cars.collect{it.node.x+","+it.node.y}.unique().size() != cars.size()) {
      Car otherCar = cars.find{ car != it && it.node.x == car.node.x && it.node.y == car.node.y}
      if (otherCar) {
        car.crashed = true
        otherCar.crashed = true
        println cars.collect{it.node.x+","+it.node.y}
      }
    }
  }
  cars = cars.findAll{ !it.crashed}
}
println cars.collect{it.node.x+","+it.node.y}

//while() {
//
//}


class Car {
  int turnIndex = 0
  String direction
  Road node
  boolean crashed = false

  Car(Road node, String direction) {
    this.node = node
    this.direction = direction
  }

}

 class Road {
  Map <String, Road> nodes = [:]
  boolean isJunction = false
  int x
  int y

  Road(int x, int y) {
    this.x = x
    this.y = y
  }
}
