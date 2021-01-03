package main

import (
	"fmt"
	"testing"
)


func TestDay10b(t *testing.T) {
	//fmt.Println(day10b([]int{1, 4, 5, 6, 7, 10, 11, 12, 15, 16, 19}))
	fmt.Println(day10b([]int{1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31,
		32, 33, 34, 35, 38, 39, 42, 45, 46, 47, 48, 49}))


	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7,8}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7,8,9}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7,8,9,10}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7,8,9,10,11}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7,8,9,10,11,12}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7,8,9,10,11,12,13}))
	fmt.Println(calcValidCombinationsOfSublist([]int{1,2,3,4,5,6,7,8,9,10,11,12,13,14}))
}