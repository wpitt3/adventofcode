package main

import (
	"fmt"
)


func day6() {
	lines, err := readFile("../resources/y2020d06.txt")
	if err != nil { panic(err) }

	fmt.Println("Day 6")
	day6a(lines)
	day6b(lines)
}

func day6a(lines []string) {
	total := 0
	m := make(map[int32]bool)
	for _, line := range lines {
		if line == "" {
			total += len(m)
			m = make(map[int32]bool)
		}
		for _, character := range line {
			m[character] = true
		}
	}
	total += len(m)
	fmt.Println(total)
}

func day6b(lines []string) {
	total, groupSize := 0, 0
	freqTable := make(map[int32]int)
	for _, line := range lines {
		if line == "" {
			total += countValueMatchingGroupSize(freqTable, groupSize)
			freqTable = make(map[int32]int)
			groupSize = 0
		} else {
			for key, _ := range getUniqueCharsFromString(line) {
				freqTable[key]++
			}
			groupSize++
		}
	}
	total += countValueMatchingGroupSize(freqTable, groupSize)
	fmt.Println(total)
}

func getUniqueCharsFromString(line string) map[int32]bool {
	m := make(map[int32]bool)
	for _, character := range line {
		m[character] = true
	}
	return m
}

func countValueMatchingGroupSize(freqTable map[int32]int, groupSize int) int {
	total := 0
	for _, val := range freqTable {
		if val == groupSize {total++}
	}
	return total
}

