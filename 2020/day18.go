package main

import (
	"fmt"
	"strconv"
	"strings"
)

func day18() {
	lines, err := readFile("../resources/y2020d18.txt")
	if err != nil { panic(err) }
	

	fmt.Println("Day 18")
	day18a(lines)
	day18b(lines)
}

func day18a(lines []string) {
	total := int64(0)
	for _, line := range lines {
		line = strings.Replace(line, " ", "", -1)
		total += recursiveResolveA([]rune(line))
	}
	fmt.Println(total)
}

func day18b(lines []string) {
	total := int64(0)
	for _, line := range lines {
		line = strings.Replace(line, " ", "", -1)
		total += recursiveResolveB([]rune(line))
	}
	fmt.Println(total)
}

func parseInt(x int32) int64 {
	val, err := strconv.ParseInt(string(x), 10, 64)
	if err != nil { panic(err) }
	return val
}

func performOperator(x int64, y int64, operator int32) int64 {
	if x == 0 || operator == '('{
		return y
	}
	if operator == '+' {
		return x + y
	} else {
		return x * y
	}
}

func recursiveResolveA(line []rune) int64{
	index := 0
	total := int64(0)
	currentOperator := ' '
	for index < len(line) {
		char := line[index]
		if char == '+' || char == '*' {
			currentOperator = char
			index +=1
		} else if char == '(' {
			closingIndex := index + findClosingBracketIndex(line[index+1:])
			total = performOperator(total, recursiveResolveA(line[index+1:closingIndex]), currentOperator)
			index = closingIndex+1
		} else {
			total = performOperator(total, parseInt(char), currentOperator)
			index +=1
		}

	}
	return total
}

func recursiveResolveB(line []rune) int64{
	index := 0
	total := int64(0)
	currentOperator := '('
	var stack []int64
	for index < len(line) {
		char := line[index]
		if char == '+' {
			currentOperator = char
			index +=1
		} else if char == '*' {
			stack = append(stack, total)
			total = int64(0)
			currentOperator = '('
			index +=1
		} else if char == '(' {
			closingIndex := index + findClosingBracketIndex(line[index+1:])
			total = performOperator(total, recursiveResolveB(line[index+1:closingIndex]), currentOperator)
			index = closingIndex+1
		} else {
			total = performOperator(total, parseInt(char), currentOperator)
			index +=1
		}
	}
	for _, val := range stack {
		total *= val
	}
	return total
}

func findClosingBracketIndex(line []rune) int {
	index := 0
	brackets := 1
	for brackets > 0 && index < len(line) {
		if line[index] == '(' {
			brackets++
		}
		if line[index] == ')' {
			brackets--
		}
		index++
	}
	return index
}

//func performOperator(x int64, y int64, operator int32) int64 {
//	if x == 0 || operator == '('{
//		return y
//	}
//	if operator == '+' {
//		return x + y
//	} else {
//		return x * y
//	}
//}