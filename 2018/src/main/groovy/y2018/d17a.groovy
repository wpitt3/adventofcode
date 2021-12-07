List lines = new File("../resources/17.txt").readLines()

lines = lines.collect { line ->
  line.split(', ').collectEntries{ split ->
    List area = split.substring(2).split('\\.\\.').collect{Integer.parseInt(it)}
    [(split.substring(0, 1)) : area + (area.size() == 1 ? area[0] : [])]
  }
}

List allX = lines.collect{it['x']}.flatten()
List allY = lines.collect{it['y']}.flatten()

int minX = allX.min()-1
int maxX = allX.max()+1
int minY = allY.min()
int maxY = allY.max()+1
println minY

String clay = '#'
String flowing = '|'
String level = '~'
String empty = '.'

List grid = (0..maxY).collect{(0..(maxX-minX)).collect{empty}}
grid[0][500-minX] = '+'

lines.each{ line ->
  ((line.x[0])..(line.x[1])).each { x ->
    ((line.y[0])..(line.y[1])).each { y ->
      grid[y][x-minX] = clay
    }
  }
}

List stack = [[1, 500-minX]]

grid[1][500-minX] = flowing

int turn = 0

while(stack) {
  List c = stack.last()
  if (c[0] >= (maxY )) {
    grid[c[0]][c[1]] = flowing
    stack.pop()
  } else if (grid[c[0]+1][c[1]] == empty) {
    grid[c[0]][c[1]] = flowing
    stack.push([c[0]+1,c[1]])
  } else if (grid[c[0]+1][c[1]] == flowing) {
    grid[c[0]][c[1]] = flowing
    stack.pop()
  } else {
    stack.pop()
    int from = c[1]
    int to = c[1]
    while(grid[c[0]][from-1] != clay && (grid[c[0]+1][from] == clay || grid[c[0]+1][from] == level)) {
      from-=1
    }
    while(grid[c[0]][to+1] != clay && (grid[c[0]+1][to] == clay || grid[c[0]+1][to] == level)) {
      to+=1
    }
    if (grid[c[0]][to+1] == clay && grid[c[0]][from-1] == clay) {
      (from..to).each {
        grid[c[0]][it] = level
      }
    } else {
      if(grid[c[0]][to+1] != clay) {
        stack.push(([c[0],to]))
      }
      if(grid[c[0]][from-1] != clay) {
        stack.push(([c[0],from]))
      }
      (from..to).each {
        grid[c[0]][it] = flowing
      }
    }
  }

  turn++
}

grid.pop()

println grid.drop(minY).sum{ line ->
  line.count{it == flowing || it == level}
}

println grid.drop(minY).sum{ line ->
  line.count{ it == level}
}
