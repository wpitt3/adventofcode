package main

import (
	"fmt"
	"sort"
	"strconv"
	"strings"
)


func day9() {
	lines, err := readFile("9")
	if err != nil { panic(err) }

	values := parseLines9(lines)
	fmt.Println("Day 9")
	x := day9a(values)
	day9b(values, x)
}

func day9a(input []int64) int64 {
	index := 25
	found := true
	for found == true {
		found = false
		section := input[index-25:index]
		for i, _ := range section {
			if section[i] < input[index] {
				for j, _ := range section {
					if j > i && input[index] == section[i] + section[j] {
						found = true
					}
				}
			}
		}
		index++
	}

	fmt.Println(input[index-1])
	return input[index-1]
}

func day9b(input []int64, target int64) {
	from, to := findIndicesOfTargetConSet(input, target)
	var newValues []string
	for _, val := range input[from : to+1] {
		strVal := strconv.FormatInt(val, 10)
		newValues = append(newValues, strings.Repeat("0", 20 - len(strVal)) + strVal)
	}
	sort.Strings(newValues)

	a, _ := strconv.ParseInt(newValues[0], 10, 64)
	b, _ := strconv.ParseInt(newValues[len(newValues)-1], 10, 64)

	fmt.Println(a + b)
}

func findIndicesOfTargetConSet(input []int64, target int64) (int, int){
	for i, _ := range input {
		setTotal := input[i]
		for j, _ := range input {
			if j > i {
				setTotal += input[j]
				if target == setTotal {
					return i, j
				}
			}
		}
	}
	return -1, -1
}


func parseLines9(lines []string) []int64 {
	var values []int64
	for _, line := range lines {
		value, err := strconv.ParseInt(line, 10, 64)
		if err != nil { panic(err) }
		values = append(values, value)
	}
	return values
}
