import spock.lang.Specification
import y2018.D20a

class Test20 extends Specification {

  def setup() {

  }

  void "example"() {
    when:
      def result = D20a.findFurthest("ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))")

    then:
      result == 23
  }

  void "example 2"() {
    when:
      def result = D20a.findFurthest("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$")

    then:
      result == 31
  }

  void "Basic node east"() {
    when:
      def result = D20a.createMap("E")

    then:
      result.east.west == result
  }

  void "Basic node loop"() {
    when:
      def result = D20a.createMap("ESWN")

    then:
      result.east.south.west.north == result
  }

  void "Basic node or"() {
    when:
      def result = D20a.createMap("E(S|N)")

    then:
      result.east.south.north.west == result
      result.east.north.south.west == result
  }

  void "Nested or"() {
    when:
      def result = D20a.createMap("E(S|N(N|E))")

    then:
      result.east.south.north.west == result
      result.east.south.west == null
      result.east.south.east == null
      result.east.south.south == null
      result.east.north.north != null
      result.east.north.east != null
      result.east.north.south.west == result
      result.east.north.west == null
      result.east.north.north != null
      result.east.north.east != null
  }

  void "Basic node or with after"() {
    when:
      def result = D20a.createMap("E(S|N)E")

    then:
      result.east.south.east.west.north.west == result
      result.east.north.east.west.south.west == result
      result.north == null
      result.south == null
      result.west == null
      result.east.east == null
      result.east.south.west == null
      result.east.north.west == null
  }

  void "Basic empty or with after"() {
    when:
      def result = D20a.createMap("E(S|)E")

    then:
      result.east.south.east.west.north.west == result
      result.east.east.west.west == result
      result.north == null
      result.south == null
      result.west == null
  }

  void "Double node or with after"() {
    when:
      def result = D20a.createMap("E(S|N|E)E")

    then:
      result.east.south.east.west.north.west == result
      result.east.north.east.west.south.west == result
      result.north == null
      result.south == null
      result.west == null
      result.east.east.east.west.west.west == result
      result.east.south.west == null
      result.east.north.west == null
  }

  void "Nested or with trailing"() {
    when:
      def result = D20a.createMap("E(S|N(N|E))E")

    then:
      result.east.south.north.west == result
      result.east.south.west == null
      result.east.south.east != null
      result.east.south.south == null
      result.east.north.north != null
      result.east.north.east != null
      result.east.north.south.west == result
      result.east.north.west == null
      result.east.north.north != null
      result.east.north.east != null
  }

//  void "splitLayer basic"() {
//    when:
//      def result = D20a.splitLayer("S|N(N|E)|E)")
//
//    then:
//      result.split[0] == "S"
//      result.split[1] == "N(N|E)"
//      result.split[2] == "E"
//  }
//
//  void "splitLayer basic with trailing"() {
//    when:
//      def result = D20a.splitLayer("S|N(N|E)|E)E")
//
//    then:
//      result.split[0] == "S"
//      result.split[1] == "N(N|E)"
//      result.split[2] == "E"
//      result.remaining == "E"
//  }
//
//  void "splitLayer trailing has or"() {
//    when:
//      def result = D20a.splitLayer("S|N(N|E)|E)E(E|W)")
//
//    then:
//      result.split[0] == "S"
//      result.split[1] == "N(N|E)"
//      result.split[2] == "E"
//      result.remaining == "E(E|W)"
//  }
//
//  void "splitLayer example 1"() {
//    when:
//      def result = D20a.splitLayer("E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))")
//
//    then:
//      result.split[0] == "E"
//      result.split[1] == "NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE))"
//  }

  void "recursively splitLayer trailing has or"() {
    when:
      def result = D20a.splitDirections("E(S|N(N|E)|E)E(E|W)")

    then:
      result == ["E", [or:["S", ["N", [or:["N", "E"]]], "E"]], "E", [or:["E", "W"]]]
  }
}