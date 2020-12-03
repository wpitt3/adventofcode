package main

import (
	"fmt"
	"sort"
)


func day1() {
	lines, err := readFile("1a")
	if err != nil { panic(err) }

	values := toIntArray(lines)
	sort.Ints(values)
	fmt.Println("Day 1")
	day1a(values)
	day1b(values)
}

func day1a(sortedValues []int) {
	a, b := findSum(2020, sortedValues)
	fmt.Println(a * b)
}

func day1b(sortedValues []int) {
	for _, target := range sortedValues {
		a, b := findSum(2020 - target, sortedValues)
		if a != b && a != target && b != target {
			fmt.Println(a * b * target)
			return
		}
	}
}

func findSum(target int, sortedValues []int) (int, int) {
	i := 0
	j := len(sortedValues) - 1

	for i < j && sortedValues[i] + sortedValues[j] != target {
		if sortedValues[i] + sortedValues[j] < target {
			i += 1
		} else {
			j -= 1
		}
	}
	return sortedValues[i], sortedValues[j]
}