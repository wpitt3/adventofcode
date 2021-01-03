package main

import (
	"fmt"
	"math"
	"sort"
	"strconv"
)


func day10() {
	lines, err := readFile("10")
	if err != nil { panic(err) }

	values := parseLines10(lines)

	fmt.Println("Day 10")
	//day10a(values)
	day10b(values)
}

func day10a(input []int) {
	sort.Ints(input)
	input = append([]int{0}, input...)
	freqTable := make([]int, 4)
	freqTable[3]++
	for i, x := range input {
		if i != 0 {
			freqTable[x - input[i-1]]++
		}
	}
	fmt.Println(freqTable[1] * freqTable[3])
}

func day10b(input []int) int64 {
	sort.Ints(input)
	input = append([]int{0}, input...)
	sublists := make([][]int, 0)
	index := 0
	for i, x := range input {
		if i != 0 && x - input[i-1] == 3 {
			if index !=  i-1 {
				sublists = append(sublists, input[index:i])
			}
			index = i
		}
	}
	sublists = append(sublists, input[index:len(input)])
	validCombinations := calcValidCombinationsOfSublists(sublists)
	total := int64(1)
	for _, x := range validCombinations {
		total *= int64(x)
	}
	fmt.Println(total)
	return total
}

func calcValidCombinationsOfSublists(sublists [][]int)[] int {
	totalCombinations := make([]int, 0)
	for _, x := range sublists {
		total := 0
		combinations := createCombinations(x[1:len(x)-1])
		for _, combination := range combinations {
			sublist := append([]int{x[0]}, combination...)
			sublist = append(sublist, x[len(x)-1])
			if isSublistValid(sublist) {
				total++
			}
		}

		totalCombinations = append(totalCombinations, total)
	}
	return totalCombinations
}

func calcValidCombinationsOfSublist(x []int) int {
	total := 0
	combinations := createCombinations(x[1:len(x)-1])
	for _, combination := range combinations {
		sublist := append([]int{x[0]}, combination...)
		sublist = append(sublist, x[len(x)-1])
		if isSublistValid(sublist) {
			total++
		}
	}
	return total
}

func isSublistValid(sublist [] int)bool {
	for i, x := range sublist {
		if i != 0 && x - sublist[i-1] > 3{
			return false
		}
	}
	return true
}

func createCombinations(list []int) [][]int {
	numberOfCombinations := int(math.Pow(2, float64(len(list))))
	combinations := make([][]int, 0)
	for i := 0; i < numberOfCombinations; i++ {
		combinations = append(combinations, make([]int, 0))
		asBinary := fmt.Sprintf("%0" + strconv.Itoa(len(list)) +"b", i)
		for index, x := range asBinary {
			if x == '1' {
				combinations[i] = append(combinations[i], list[index])
			}
		}
	}
	return combinations
}

func parseLines10(lines []string) []int {
	var values []int
	for _, line := range lines {
		value, err := strconv.Atoi(line)
		if err != nil { panic(err) }
		values = append(values, value)
	}
	return values
}


