List lines = new File("../resources/15.txt").readLines()

List map = lines.collect{ line ->
  line.split('').toList()
}

List<Unit> units = []
(0..<(map.size)).each { y ->
  (0..<(map[0].size)).each { x ->
    if (map[y][x] == 'E' || map[y][x] == 'G') {
      Unit unit = new Unit(map[y][x] == 'E', x, y)
      map[y][x] = unit
      units.add(unit)
    }
  }
}

//printMap(map)
int round = -1
while(units.any{ it.elf } && units.any{ !it.elf }) {
  println ''
  println units.findAll{it.elf}.collect{it.health}
  println units.findAll{!it.elf}.collect{it.health}
  printMap(map)

  units.each { Unit unit ->
    if (unit.health > 0) {
      List enemyNeighbours = getEnemyNeighbours(unit, map)
      if (!enemyNeighbours) {
        List move = findMove(unit, map)
        if (move) {
          map[unit.y][unit.x] = '.'
          unit.y = move[0]
          unit.x = move[1]
          map[move[0]][move[1]] = unit
        }
        enemyNeighbours = getEnemyNeighbours(unit, map)
      }
      if (enemyNeighbours) {
        Unit enemy = enemyNeighbours.sort { it.health * 1000 + it.y * 50 + it.x}[0]

        enemy.health -= enemy.elf ? 3 : 29
        if(enemy.health <= 0) {
          map[enemy.y][enemy.x] = '.'
        }
      }
    }
  }
  units = units.findAll { it.health > 0 }.sort{it.y * 100 + it.x}
  round += 1
}

println ''
println units.findAll{it.elf}.sum{it.health}
println units.findAll{!it.elf}.sum{it.health}
println round
println units.sum{it.health} * round
printMap(map)

List findMove(Unit unit, List map) {
  List movesGrid = getMoves(map, unit.elf)
  boolean found = false
  List moves = getNeighbours([unit.y, unit.x]).findAll { movesGrid[it[0]][it[1]] }.collect {
    [coords: it, history: []]
  }.sort { it.coords[0] * 100 + it.coords[1] }
  while (!found && moves.size() > 0) {
    List newMoves = moves.inject([], { result, i ->
      List neighbours = getNeighbours(i.coords)
      neighbours.each { n ->
        if (movesGrid[n[0]][n[1]] && !result.any { it.coords == n }) {
          result.add(coords: n, history: (i.history + [i.coords]))
        }
      }
      return result
    }).sort { it.coords[0] * 100 + it.coords[1] }.sort { it.history[0][0] * 100 + it.history[0][1] }

    List enemies = newMoves.findAll { isEnemy(unit, it.coords, map) }.sort {
      it.history.last()[0] * 100 + it.history.last()[1]
    }
//    println ''
//    newMoves.each {
//      if (it.history[0][0] > 10 && it.history[0][0] < 14 && it.history[0][1] > 7 && it.history[0][1] < 11) {
//        println it
//      }
//    }
    if (enemies) {
//      println '##### enemies'  + enemies
      return enemies[0].history[0]
      found = true
    } else {
//      println newMoves
      moves = newMoves
      newMoves.each {
        movesGrid[it.coords[0]][it.coords[1]] = false
      }
    }
  }
  return null
}

List getEnemyNeighbours(Unit unit, List map) {
  return getNeighbours([unit.y, unit.x]).findAll { isEnemy(unit, it, map) }.collect{map[it[0]][it[1]]}
}

boolean isEnemy(Unit unit, List coords, List map) {
  def square = map[coords[0]][coords[1]]
  return square instanceof Unit && square.elf != unit.elf
}

List getNeighbours(List z) {
  return [[z[0]-1,z[1]], [z[0],z[1]-1], [z[0],z[1]+1], [z[0]+1,z[1]]]
}

List getMoves(List maps, boolean elf) {
  (0..<(maps.size())).collect{ x -> (0..<(maps[0].size())).collect{ y ->
    maps[x][y] == '.' || (maps[x][y] instanceof Unit && maps[x][y].elf != elf)
  }}
}

void printMap(List map) {
  map.each { x ->
    println x.collect{ it instanceof Unit ? (it.elf ?'E':'G') : it}.join('')
  }
}

class Unit {
  int health
  int x
  int y
  boolean moved
  boolean elf

  Unit(boolean elf, x, y, health = 200, moved = false) {
    this.elf = elf
    this.x = x
    this.y = y
    this.moved = moved
    this.health = health
  }

  Unit clone() {
    return new Unit(elf, x, y, health, moved)
  }
}