package main

import (
	"fmt"
	"strconv"
)

// 3756

func y2020d18() {

	lines, _ := readFile("y2021d18")

	start := stringToNode(lines[0], nil)
	reduce(start)

	for _, line := range lines[1:] {
		start = add(start, stringToNode(line, nil))
		reduce(start)
		//fmt.Println(printNode(start))
	}
	fmt.Println(magnitude(start))


	max := 0
	for i := 0; i < len(lines)-1; i++ {
		for j := i+1; j < len(lines); j++ {
			x := add(stringToNode(lines[i], nil), stringToNode(lines[j], nil))
			reduce(x)
			score := magnitude(x)
			if score > max {
				max = score
			}
			x = add(stringToNode(lines[j], nil), stringToNode(lines[i], nil))
			reduce(x)
			score = magnitude(x)
			if score > max {
				max = score
			}
		}
	}
	fmt.Println(max)

}

func add(a *D18Node, b *D18Node) *D18Node {
	newNode := &D18Node{0, 0, a, b, nil}
	a.parent = newNode
	b.parent = newNode
	return newNode
}

func reduce(node *D18Node) {
	for _, x := range findUnExploded(node, 0) {
		explode(x)
	}
	for splitFirst(node) {
		for _, x := range findUnExploded(node, 0) {
			explode(x)
		}
	}
}

func splitFirst(node *D18Node) bool {
	toSplit := findUnSplit(node)
	if toSplit == nil {
		return false
	}

	if toSplit.ln == nil && toSplit.lv > 9 {
		toSplit.ln = &D18Node{toSplit.lv / 2, (toSplit.lv + 1)/ 2,  nil, nil, toSplit}
		toSplit.lv = 0
		return true
	}
	toSplit.rn = &D18Node{toSplit.rv / 2, (toSplit.rv + 1)/ 2,  nil, nil, toSplit}
	toSplit.rv = 0

	return true
}

func findUnSplit(node *D18Node) *D18Node {
	if node.ln == nil {
		if node.lv > 9 {
			return node
		}
	} else {
		x := findUnSplit(node.ln)
		if x != nil {
			return x
		}
	}

	if node.rn == nil {
		if node.rv > 9 {
			return node
		}
	} else {
		x := findUnSplit(node.rn)
		if x != nil {
			return x
		}
	}
	return nil
}

func findUnExploded(node *D18Node, depth int) []*D18Node {
	unexploded := make([]*D18Node, 0)
	if depth == 4 {
		return append(unexploded, node)
	}
	if node.ln != nil {
		unexploded = append(unexploded, findUnExploded(node.ln, depth+1)...)
	}
	if node.rn != nil {
		unexploded = append(unexploded, findUnExploded(node.rn, depth+1)...)
	}
	return unexploded
}

func explode(node *D18Node) {
	nextLeft := node
	for nextLeft.parent != nil && nextLeft == nextLeft.parent.ln {
		nextLeft = nextLeft.parent
	}
	nextLeft = nextLeft.parent
	if nextLeft != nil {
		if nextLeft.ln != nil {
			nextLeft = nextLeft.ln
			for nextLeft.rn != nil {
				nextLeft = nextLeft.rn
			}
			nextLeft.rv += node.lv
		} else {
			nextLeft.lv += node.lv
		}
	}

	nextRight := node
	for nextRight.parent != nil && nextRight == nextRight.parent.rn {
		nextRight = nextRight.parent
	}
	nextRight = nextRight.parent

	if nextRight != nil {
		if nextRight.rn != nil {
			nextRight = nextRight.rn
			for nextRight.ln != nil {
				nextRight = nextRight.ln
			}
			nextRight.lv += node.rv
		} else {
			nextRight.rv += node.rv
		}
	}



	if node == node.parent.ln {
		node.parent.ln = nil
	} else {
		node.parent.rn = nil
	}
}

func magnitude(node *D18Node) int {
	score := 0
	if node.ln != nil {
		score += 3 * magnitude(node.ln)
	} else {
		score += 3 * node.lv
	}
	if node.rn != nil {
		score += 2 * magnitude(node.rn)
	} else {
		score += 2 * node.rv
	}
	return score
}

func stringToNode(input string, parent *D18Node) *D18Node {
	input = input[1:len(input)-1]
	i := 0
	depth := 0
	for depth > 0 || input[i] != ',' {
		if input[i] == '[' {
			depth += 1
		} else if input[i] == ']' {
			depth -= 1
		}
		i++
	}
	lv, rv := 0, 0
	var ln, rn *D18Node
	current := &D18Node{lv, rv, ln, rn, parent}
	if input[:i][0] == '[' {
		current.ln = stringToNode(input[:i], current)
	} else {
		lv, _ = strconv.Atoi(input[:i])
		current.lv = lv
	}
	if input[i+1:][0] == '[' {
		current.rn = stringToNode(input[i+1:], current)
	} else {
		rv, _ = strconv.Atoi(input[i+1:])
		current.rv = rv
	}
	return current
}

func printNode(node *D18Node) string {
	var str = "["
	if node.ln != nil {
		str += printNode(node.ln)
	} else {
		str += strconv.Itoa(node.lv)
	}
	str += ","
	if node.rn != nil {
		str += printNode(node.rn)
	} else {
		str += strconv.Itoa(node.rv)
	}
	return str + "]"
}


type D18Node struct {
	lv int
	rv int
	ln *D18Node
	rn *D18Node
	parent *D18Node
}