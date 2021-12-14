
List lines = new File("resources/y2018d20.txt").readLines()
long timeBefore = new Date().getTime();
new D20a().findFurthest(lines[0])


new D20a().findFurthest("ENWWW(NEEE|SSE(EE|N))") // 10
new D20a().findFurthest("ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN") //18
new D20a().findFurthest("ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))") //23
new D20a().findFurthest("WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))") //31

class D20a {

  static List permutationsOfAllFour = ['N','S','W','E'].permutations().collect{it.join()}

  static int findFurthest(String directions) {
    directions = directions.replaceAll('\\^|\\$', '')
    int size = 203
    int x = size/2
    int y = size/2
    boolean[][] grid = new boolean[size][size]
    grid[y][x] = true
//    println("directions")
//    println(directions)

    findPaths(directions, grid, [x*10000 + y].toSet())
//    grid.each{ row ->
//      println(row.collect{it ? ' ' : '#'}.join(''))
//    }

    x = size/2
    y = size/2

    Set visited = [x*10000 + y].toSet()
    Set previousEdges = [x*10000 + y].toSet()

    int countAt1000Doors = 0
    int steps = -1
    while(previousEdges.size() > 0) {
      Set newEdges = [].toSet()
      previousEdges.each{ edge ->
        newEdges.addAll(findValidPaths((int)Math.floor(edge/10000), edge%10000, grid))
      }
      newEdges = newEdges - visited
      previousEdges = newEdges
      visited.addAll(newEdges)
      steps += 1
      if (steps == 998) {
        countAt1000Doors = visited.size()
      }
    }
    println(steps)
    println(visited.size() - countAt1000Doors)
    return 1
  }

  static Set<Integer> findValidPaths(x, y, grid) {
    Set paths = [].toSet()
    if (grid[y][x + 1]) {
      paths.add((x+2)*10000 + y)
    }
    if (grid[y][x - 1]) {
      paths.add((x-2)*10000 + y)
    }
    if (grid[y+1][x]) {
      paths.add(x*10000 + y+2)
    }
    if (grid[y-1][x]) {
      paths.add(x*10000 + y-2)
    }
    return paths
  }

  static int indexOf(String search, String value, int min) {
    int x = search.indexOf(value, min+1)
    return x == -1 ? search.length() : x
  }

  static int followPath(String directions, boolean[][] grid, int a, int b) {
    int x = a
    int y = b
    directions.each{
      if (it == 'N') {
        y -= 1
        grid[y][x] = true
        y -= 1
        grid[y][x] = true
      }
      if (it == 'S') {
        y += 1
        grid[y][x] = true
        y += 1
        grid[y][x] = true
      }
      if (it == 'W') {
        x -= 1
        grid[y][x] = true
        x -= 1
        grid[y][x] = true
      }
      if (it == 'E') {
        x += 1
        grid[y][x] = true
        x += 1
        grid[y][x] = true
      }
    }
    return x*10000 + y
  }

  static Set<Integer> findPaths(String directions, boolean[][] grid, Set<Integer> positions) {
    int bracketStart = directions.indexOf('(')
    if (bracketStart == -1) {
      Set<Integer> newPositions = [].toSet()
      positions.each{
        int x = (int)Math.floor(it/10000)
        int y = it%10000
        newPositions.add(followPath(directions, grid, x, y))
      }
      return newPositions
    }

    int index = bracketStart
    int sectionStart = bracketStart
    int depth = 1
    ArrayList orSections = []
    while (depth > 0) {
      Map symbolToIndex = ['(', ')', '|'].collectEntries{ [(it): indexOf(directions, it, index)]}
      int firstMatching = symbolToIndex.min{it.value}.value

      if (firstMatching == symbolToIndex['(']) { depth ++ }

      if (firstMatching == symbolToIndex[')']) {
        depth --
        if (depth == 0) {
          orSections.add(directions.substring(sectionStart + 1, symbolToIndex[')']))
        }
      }
      if (firstMatching == symbolToIndex['|'] && depth == 1) {
        orSections.add(directions.substring(sectionStart + 1, symbolToIndex['|']))
        sectionStart = symbolToIndex['|']
      }
      index = firstMatching
    }
    Set<Integer> newPositions = [].toSet()
    positions.each{
      int x = (int)Math.floor(it/10000)
      int y = it%10000
      newPositions.add(followPath(directions.substring(0, bracketStart), grid, x, y))
    }

    Set<Integer> postOrPositions = [].toSet()
    orSections.each{
      postOrPositions.addAll(findPaths(it, grid, newPositions))
    }

    String remaining = directions.substring(index+1)
    return findPaths(remaining, grid, postOrPositions)
  }




















}


