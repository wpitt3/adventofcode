import math

from FileReader import FileReader


class MapNode:
    def __init__(self):
        self.parent = None
        self.isLeftNode = False
        self.isRightNode = False
        self.leftIsValue = False
        self.rightIsValue = False
        self.left = None
        self.right = None

    def setLeft(self, left):
        self.leftIsValue = isinstance(left, int)
        self.left = left
        if not self.leftIsValue:
            left.parent = self
            left.isLeftNode = True

    def setRight(self, right):
        self.rightIsValue = isinstance(right, int)
        self.right = right
        if not self.rightIsValue:
            right.parent = self
            right.isRightNode = True

    def __repr__(self):
        return '[' + str(self.left) + ',' + str(self.right) + ']'


def stringToNode(input):
    rootNode = MapNode()
    currentNode = rootNode
    for i in range(1, len(input)-1):
        if input[i] == '[':
            newNode = MapNode()
            if currentNode.left == None:
                currentNode.setLeft(newNode)
            else:
                currentNode.setRight(newNode)
            currentNode = newNode
        elif input[i] == ']':
            currentNode = currentNode.parent
        elif not input[i] ==',':
            if currentNode.left == None:
                currentNode.setLeft(int(input[i]))
            else:
                currentNode.setRight(int(input[i]))
    return rootNode


def reduceTree(rootNode):
    done = False

    while not done:
        exploded = False
        toExplode = findNumbersAboveMaxDepth(rootNode, [], 0)
        for node in toExplode:
            explode(node)
            exploded = True
        split = False
        if not exploded:
            toSplit = findNumberToSplit(rootNode)
            if not toSplit == None:
                splitIt(toSplit)
                split = True
            # while
            #     newExplode = split(toSplit)
            #     if not newExplode == None:
            #         explode(newExplode)
            #     toSplit = findNumberToSplit(rootNode)
        done = not (exploded or split)

def explode(node):
    firstLeftNode = node
    while firstLeftNode.isLeftNode:
        firstLeftNode = firstLeftNode.parent
    if not firstLeftNode.parent == None:
        firstLeftNode = firstLeftNode.parent
        if firstLeftNode.leftIsValue:
            firstLeftNode.left += node.left
        else:
            firstLeftNode = firstLeftNode.left
            while not firstLeftNode.rightIsValue:
                firstLeftNode = firstLeftNode.right
            firstLeftNode.right += node.left

    firstRightNode = node
    while firstRightNode.isRightNode:
        firstRightNode = firstRightNode.parent
    if not firstRightNode.parent == None:
        firstRightNode = firstRightNode.parent
        if firstRightNode.rightIsValue:
            firstRightNode.right += node.right
        else:
            firstRightNode = firstRightNode.right
            while not firstRightNode.leftIsValue:
                firstRightNode = firstRightNode.left
            firstRightNode.left += node.right
    if node.isLeftNode:
        node.parent.setLeft(0)
    else:
        node.parent.setRight(0)


def splitIt(node):
    if node.leftIsValue and node.left > 9:
        node.setLeft(splitNode(node.left))
        if calculateDepth(node.left) > 3:
            return node.left
    if node.rightIsValue and node.right > 9:
        node.setRight(splitNode(node.right))
        if calculateDepth(node.right) > 3:
            return node.right
    return None


def splitNode(node):
    left = math.floor(node / 2)
    right = math.ceil(node / 2)
    newNode = MapNode()
    newNode.setLeft(left)
    newNode.setRight(right)
    return newNode


def calculateDepth(node):
    cNode = node
    depth = 0
    while not cNode.parent == None:
        cNode = cNode.parent
        depth += 1
    return depth


def findNumbersAboveMaxDepth(node, queue, depth):
    if depth == 4:
        queue.append(node)
    if depth > 4:
        print("SHITE")
    if not node.leftIsValue:
        findNumbersAboveMaxDepth(node.left, queue, depth + 1)
    if not node.rightIsValue:
        findNumbersAboveMaxDepth(node.right, queue, depth + 1)
    return queue


def findNumberToSplit(node):
    if not node.leftIsValue:
        x = findNumberToSplit(node.left)
        if not x == None:
            return x
    elif node.left > 9:
        return node
    if not node.rightIsValue:
        x = findNumberToSplit(node.right)
        if not x == None:
            return x
    elif node.right > 9:
            return node
    return None


def magnitude(node):
    if not node.leftIsValue:
        left = magnitude(node.left)
    else:
        left = node.left
    if not node.rightIsValue:
        right = magnitude(node.right)
    else:
        right = node.right

    return left * 3 + right * 2


if __name__ == "__main__":

    # 3972 >

    lines = FileReader().readLines('18')

    root = stringToNode(lines[0])
    reduceTree(root)

    for lineIndex in range(1, len(lines)):
        newNode = MapNode()
        newNode.setLeft(root)
        newNode.setRight(stringToNode(lines[lineIndex]))
        reduceTree(newNode)
        root = newNode

    print(root)
    print(magnitude(root))
    print(3488)
