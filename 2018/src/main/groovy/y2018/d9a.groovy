int players = 428
int lastMarble = 7082500

Node first = null
Node second = new Node(null, null, 1)
first = new Node(second, second, 0)
second.next = first
second.previous = first

List<BigInteger> scores = (0..<players).collect{BigInteger.ZERO}

Node node = second
long x = System.currentTimeMillis()
(2..lastMarble).each{ int current ->
  if(current % 23 == 0) {
    node = node.previous.previous.previous.previous.previous.previous.previous
    scores[current%players] = scores[current%players].add(node.value).add(new BigInteger(current.toString()))
    node = node.remove()
  } else {
    node = node.next.addAfter(current)
  }
}
println System.currentTimeMillis() - x
println scores.max()

class Node {
  Node previous
  Node next
  BigInteger value

  Node(Node previous, Node next, int value) {
    this.previous = previous
    this.next = next
    this.value = new BigInteger(value.toString())
  }

  Node remove() {
    Node next = this.next
    this.previous.next = next
    this.next.previous = previous
    return this.next
  }

  Node addAfter(int value) {
    Node newNode = new Node(this, next, value)
    this.next.previous = newNode
    this.next = newNode
    return newNode
  }
}