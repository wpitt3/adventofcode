package main

import (
	"fmt"
)

func day15() {

	fmt.Println("Day 15")

	x := []int64{1,0,15,2,10,13}

	day15a(x)
	day15b(x)
}

func day15a(input []int64) {
	previouslySpoken := make(map[int64]int64)
	spokenNumber := int64(0)
	for i := int64(1); i < int64(len(input)); i ++ {
		previouslySpoken[input[i-1]] = i
	}
	spokenNumber = input[ len(input)-1]

	for i := int64(len(input)); i < int64(2020); i ++ {
		if previouslySpoken[spokenNumber] == 0 {
			previouslySpoken[spokenNumber] = i
			spokenNumber = 0
		} else {
			x := i - previouslySpoken[spokenNumber]
			previouslySpoken[spokenNumber] = i
			spokenNumber = x
		}
	}
	fmt.Println(spokenNumber)
}

func day15b(input []int64) {
	previouslySpoken := make(map[int64]int64)
	spokenNumber := int64(0)
	for i := int64(1); i < int64(len(input)); i ++ {
		previouslySpoken[input[i-1]] = i
	}
	spokenNumber = input[ len(input)-1]

	for i := int64(len(input)); i < int64(30000000); i ++ {
		if previouslySpoken[spokenNumber] == 0 {
			previouslySpoken[spokenNumber] = i
			spokenNumber = 0
		} else {
			x := i - previouslySpoken[spokenNumber]
			previouslySpoken[spokenNumber] = i
			spokenNumber = x
		}
	}
	fmt.Println(spokenNumber)
}
