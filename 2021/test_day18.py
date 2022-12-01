from day18 import stringToNode, explode, split, magnitude


def test_stringToNode():
    assert "[[1,2],[3,[4,5]]]" == str(stringToNode("[[1,2],[3,[4,5]]]"))
    assert "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]" == str(stringToNode("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"))


def test_explode_to_values():
    root = stringToNode("[[0,[1,2]],0]")
    explode(root.left.right)
    assert str(root) == str(stringToNode("[[1,0],2]"))


def test_explode_to_nothing_on_left():
    root = stringToNode("[[1,2],0]")
    explode(root.left)
    assert str(root) == str(stringToNode("[0,2]"))


def test_explode_to_nothing_on_left():
    root = stringToNode("[[1,[1,[1,1]]],[[[2,2],1],1]]")
    print(root.right.left.left)
    explode(root.right.left.left)
    assert str(root) == str(stringToNode("[[1,[1,[1,3]]],[[0,3],1]]"))

def test_magnitude():
    assert 143 == magnitude(stringToNode("[[1,2],[[3,4],5]]"))
    assert 1384 == magnitude(stringToNode("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"))
    assert 445 == magnitude(stringToNode("[[[[1,1],[2,2]],[3,3]],[4,4]]"))
    assert 791 == magnitude(stringToNode("[[[[3,0],[5,3]],[4,4]],[5,5]]"))
    assert 1137 == magnitude(stringToNode("[[[[5,0],[7,4]],[5,5]],[6,6]]"))
    assert 3488 == magnitude(stringToNode("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"))