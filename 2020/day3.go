package main

import (
	"fmt"
)


func day3() {
	lines, err := readFile("3")
	if err != nil { panic(err) }

	trees := convertToTrees(lines)
	fmt.Println("Day 3")
	day3a(trees)
	day3b(trees)
}

func day3a(trees [][]bool) {
	total := traverseTrees(trees, 3, 1)

	fmt.Println(total)
}

func day3b(trees [][]bool) {
	total := traverseTrees(trees, 3, 1)
	total =  total * traverseTrees(trees, 1, 1)
	total =  total * traverseTrees(trees, 5, 1)
	total =  total * traverseTrees(trees, 7, 1)
	total =  total * traverseTrees(trees, 1, 2)

	fmt.Println(total)
}

func convertToTrees(lines []string) [][]bool {
	rowSize := len(lines[0])
	trees := make([][]bool, len(lines))
	for i := range lines {
		trees[i] = make([]bool, rowSize)
	}

	for i, line := range lines {
		for j := 0; j < rowSize; j++ {
			trees[i][j] = line[j] == '#'
		}
	}

	return trees
}

func traverseTrees(trees [][]bool, xStep int, yStep int) int {
	total := 0
	x := 0
	rowSize := len(trees[0])
	for y := 0; y < len(trees); y += yStep {
		if trees[y][x] {
			total++
		}
		x = (x + xStep) % rowSize
	}
	return total
}