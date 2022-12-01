package y2018

import groovy.json.JsonSlurper


class D20a {
//  static List permutationsOfAllFour = ['N','S','W','E'].permutations().collect{it.join()}

  static Integer findFurthest(String directions) {
    MapNode start = createMap(directions)
    start.visited == true
    Set<MapNode> currentStep = [start]
    Integer steps = -1
    while (!currentStep.isEmpty()) {
      Set<MapNode> nextStep = []
      for (MapNode node : currentStep) {
        node.visited = true
        List<MapNode> neighbours = node.neighbours().findAll { !it.visited }
        nextStep.addAll(neighbours)
      }
      steps += 1
      currentStep = nextStep
    }

    return steps
  }

  static MapNode createMap(String directionsString) {
    List directions = splitDirections(directionsString)
    println(directions)
    MapNode start = new MapNode(0, 0)
    MapNode current = start
    Map existingNodes = [:]
    existingNodes[createLocationKey(current)] = current

    recursive(current, directions, existingNodes)
    return start
  }

  static recursive(MapNode original, def directions, Map existingNodes) {
    MapNode current = original
    if (directions instanceof String) {
      current = followDirection(current, directions, existingNodes)
    } else {
      def direction = directions[0]
      directions = directions.drop(1)
      while(direction != null) {
        if (direction instanceof Map) {
          direction.or.each {
            recursive(current, (it instanceof List ? it : [it]) + directions.clone(), existingNodes)
          }
          directions = []
        } else {
          current = followDirection(current, direction, existingNodes)
        }
        direction = directions[0]
        directions = directions.drop(1)
      }
    }
  }

  static followDirection(MapNode current, String direction, Map existingNodes) {
    direction.each { letter ->
      MapNode nextNode = lookForNodes(current, letter, existingNodes)
      switch (letter) {
        case 'N':
          current = current.goNorth(nextNode)
          break
        case 'S':
          current = current.goSouth(nextNode)
          break
        case 'E':
          current = current.goEast(nextNode)
          break
        case 'W':
          current = current.goWest(nextNode)
          break
        case '':
//          current = current.goWest(nextNode)
          break
        default:
          throw new RuntimeException("")
      }
      existingNodes[createLocationKey(current)] = current
    }
    return current
  }

  static MapNode lookForNodes(MapNode current, String direction, Map existingNodes) {
    switch (direction) {
      case 'N':
        return existingNodes[createLocationKey(current, 0, -1)]
      case 'S':
        return existingNodes[createLocationKey(current, 0, 1)]
      case 'E':
        return existingNodes[createLocationKey(current, 1, 0)]
      case 'W':
        return existingNodes[createLocationKey(current, -1, 0)]
      default:
        return null
    }
  }

  static createLocationKey(MapNode node) {
    return createLocationKey(node, 0,0)
  }

  static createLocationKey(MapNode node, Integer x, Integer y) {
    return (node.location[0] + x) + "|" + (node.location[1] + y)
  }

  static String printMap(List<List<Boolean>> map) {
    String toPrint = ''
    for (int y = 0; y < map[0].size(); y++) {
      for (int x = 0; x < map.size(); x++) {
        toPrint += map[x][y] ? ' ' : '#'
      }
      toPrint+='\n'
    }
    return toPrint
  }

  static List splitDirections(String toSplit) {
    toSplit = toSplit+ '"]'
    toSplit = toSplit
        .replaceAll('\\^|\\$', '')
        .replaceAll("\\(", '",{"or":[["')
        .replaceAll("\\)", '"]]},"')
        .replaceAll("\\|", '"],["')
        .replaceAll(',""', '')
        .replaceAll("\\[(\"[NSWE]*\")\\]", '$1' )
    return new JsonSlurper().parseText('["' + toSplit)
  }

  static class MapNode {
    MapNode north
    MapNode south
    MapNode east
    MapNode west
    boolean visited
    List<Integer> location

    MapNode() {
      north = null
      south = null
      east = null
      west = null
      visited = false
    }

    MapNode(Integer x, Integer y) {
      north = null
      south = null
      east = null
      west = null
      visited = false
      location = [x, y]
    }

    MapNode goSouth(MapNode moveTo) {
      moveTo = moveTo ?: new MapNode()
      moveTo.north = this
      this.south = moveTo
      if (!moveTo.location) {
        moveTo.location = [this.location[0], this.location[1] + 1]
      }
      return moveTo
    }

    MapNode goNorth(MapNode moveTo) {
      moveTo = moveTo ?: new MapNode()
      moveTo.south = this
      this.north = moveTo
      if (!moveTo.location) {
        moveTo.location = [this.location[0], this.location[1] - 1]
      }
      return moveTo
    }

    MapNode goEast(MapNode moveTo) {
      moveTo = moveTo ?: new MapNode()
      moveTo.west = this
      this.east = moveTo
      if (!moveTo.location) {
        moveTo.location = [this.location[0] + 1, this.location[1]]
      }
      return moveTo
    }

    MapNode goWest(MapNode moveTo) {
      moveTo = moveTo ?: new MapNode()
      moveTo.east = this
      this.west = moveTo
      if (!moveTo.location) {
        moveTo.location = [this.location[0] - 1, this.location[1]]
      }
      return moveTo
    }

    List<MapNode> neighbours() {
      return [north, south, east, west].findAll{it}
    }
  }
}


