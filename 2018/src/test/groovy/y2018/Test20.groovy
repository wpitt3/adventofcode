//import spock.lang.Specification
import y2018.D20a

class Test20 {//extends Specification {

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

  void "Double node or"() {
    when:
      def result = D20a.createMap("E(S|N(N|E))")

    then:
      result.east.south.north.west == result
      result.east.south.west == null
      result.east.south.east == null
      result.east.south.south == null
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
  }
}