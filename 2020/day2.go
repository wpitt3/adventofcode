package main

import (
	"fmt"
	"strings"
)


func day2() {
	lines, err := readFile("2a")
	if err != nil { panic(err) }
	fmt.Println("Day 2")
	day2a(lines)
	day2b(lines)
}

func day2a(lines []string) {
	total := 0

	for _, line := range lines {
		splitLine := split(line, " ", ":")
		minMax := splitToInts(splitLine[0], "-", "")

		count := len(strings.Split(splitLine[2], splitLine[1])) - 1

		if count >= minMax[0] && count <= minMax[1] {
			total += 1
		}
	}

	fmt.Println(total)
}

func day2b(lines []string) {
	total := 0

	for _, line := range lines {
		splitLine := split(line, " ", ":")
		minMax := splitToInts(splitLine[0], "-", "")

		if xor(inputIndexMatchesValue(splitLine[2], minMax[0]-1, splitLine[1]), inputIndexMatchesValue(splitLine[2], minMax[1]-1, splitLine[1])) {
			total += 1
		}
	}

	fmt.Println(total)
}

func inputIndexMatchesValue(word string, index int, value string) bool {
	return string(word[index]) == value
}

func xor(x bool, y bool) bool {
	return (x || y) && !(x && y)
}