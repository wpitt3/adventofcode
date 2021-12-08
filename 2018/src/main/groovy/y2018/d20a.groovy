package y2018


class D20a {
  static List permutationsOfAllFour = ['N','S','W','E'].permutations().collect{it.join()}

  static String findFurthest(String directions) {
    directions = directions.replaceAll('\\^|\\$', '')

    return directions.length()
  }

  static MapNode createMap(String directions) {
    MapNode start = new MapNode(0, 0)
    MapNode current = start
    List nodeStack = []
    Map existingNodes = [:]
    existingNodes[createLocationKey(current)] = current
    List newDirectionsStack = []

    while (newDirectionsStack != [] || directions != "") {
      if (newDirectionsStack.size() > 0) {
        Map x = newDirectionsStack.pop()
        directions = x.directions
        current = x.current
        nodeStack = x.nodeStack
      }

      while (directions != "") {
        String direction = directions[0]
        directions = directions.substring(1)
        MapNode nextNode = lookForNodes(current, direction, existingNodes)
        switch (direction) {
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
          case '(':
            nodeStack.push(current)
            break
          case ')':
            break
          case '|':
            //without last nodeStack, and directions from )
            List currentStack = nodeStack.clone().take(nodeStack.size()-1)
            String currentDirections = directions.replaceAll("^[^\\)]*\\)", "")
            newDirectionsStack.push([nodeStack: currentStack, current: current, directions: currentDirections])
            current = nodeStack.pop()
            break
        }
        existingNodes[createLocationKey(current)] = current
      }
    }

    return start
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

  private static class MapNode {
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
  }
}


