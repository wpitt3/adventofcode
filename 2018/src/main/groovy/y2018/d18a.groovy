List lines = new File("../resources/18.txt").readLines()

List visual = ['.','|','#']

int lineSize = lines.size()

lines = lines.collect{ line ->
  line.split('').collect{ it == '.' ? 0 : ( it == '|' ? 1 : 2)}
}

List seen = []
List scores = []
boolean found = false
int index = 1
while(!found) {
  clonedLines = cloneList(lines)

  (0..<lineSize).each { y ->
    (0..<lineSize).each { x ->
      List freq = countNeighbours(lines, x, y)
      if (lines[y][x] == 0 && freq[1] > 2) {
        clonedLines[y][x] = 1
      } else if (lines[y][x] == 1 && freq[2] > 2) {
        clonedLines[y][x] = 2
      } else if (lines[y][x] == 2 && (freq[1] < 1 || freq[2] < 1)) {
        clonedLines[y][x] = 0
      }
    }
  }

  lines = clonedLines
  int hash = hashCode(clonedLines)
  if(seen.contains(hash)) {
    found = true
  }
  seen.add(hash)
  scores.add(score(lines))
  index += 1
}
int startOfLoop = seen.indexOf(hashCode(lines)) + 1
int sizeOfLoop = seen.size() - startOfLoop
int x = ((new BigInteger(1000000000)).minus(startOfLoop).remainder(sizeOfLoop)).intValue()
println (scores[startOfLoop + x-1])



int score(List lines) {
  int lineSize = lines.size()
  return ((0..<lineSize).sum { y ->
    (0..<lineSize).count { x ->
      lines[y][x] == 2
    }
  } * (0..<lineSize).sum { y ->
    (0..<lineSize).count { x ->
      lines[y][x] == 1
    }
  })
}

int hashCode(List lines) {
  int lineSize = lines.size()
  int hashCode = 1
  (0..<lineSize).each { y ->
    (0..<lineSize).each { x ->
      hashCode = 31*hashCode + lines[y][x]
    }
  }
  return hashCode
}

List cloneList(List lines) {
  lines.collect{line-> line.clone()}
}

List countNeighbours(List lines, int x, int y) {
  int maxSize = lines.size()
  List freq = [0,0,0]
  (-1..1).each { xOff ->
    (-1..1).each { yOff ->
      if ((xOff != 0 || yOff != 0) && x+xOff >-1 && x+xOff < maxSize && y+yOff >-1 && y+yOff < maxSize) {
        freq[lines[y+yOff][x+xOff]] += 1
      }
    }
  }
  return freq
}