package main

import (
	"fmt"
	"strconv"
)

func day23() {
	input := toIntList("215694783")

	input2 := toIntList("389125467")
	for i := 10; i <= 1000000; i ++ {
		input2 = append(input2, i)
	}
	day23a(input)
	fmt.Println(46978532)
	//day23b(input2)
}

func day23a(cups []int) {
	index := 0
	for i := 0; i < 100; i++ {
		currentCup := cups[index]
		newCups, next3 := takeNext3(cups, index)
		target := calcTarget(cups, next3, index)
		targetIndex := intSpliceIndexOf(newCups, target)
		cups = flattenSplices([][]int{newCups[:targetIndex+1], next3, newCups[targetIndex+1:]})
		index = (intSpliceIndexOf(cups, currentCup) + 1) % len(cups)
	}

	indexOf1 := intSpliceIndexOf(cups, 1)
	fmt.Println(intSpliceToString(flattenSplices([][]int{cups[indexOf1+1:], cups[:indexOf1]})))
}

func day23b(cups []int) {
	index := 0
	for i := 0; i < 10000; i++ {
		currentCup := cups[index]
		newCups, next3 := takeNext3(cups, index)
		target := calcTarget(cups, next3, index)
		targetIndex := intSpliceIndexOf(newCups, target)
		cups = flattenSplices([][]int{newCups[:targetIndex+1], next3, newCups[targetIndex+1:]})
		index = (intSpliceIndexOf(cups, currentCup) + 1) % len(cups)
	}

	//indexOf1 := intSpliceIndexOf(cups, 1)
}

func intSpliceToString(x []int) string {
	toPrint := ""
	for _, y := range x {
		toPrint += strconv.Itoa(y)
	}
	return toPrint
}

func flattenSplices(splices [][]int)[] int {
	result := []int(nil)
	for _, splice := range splices {
		result = append(result, splice...)
	}
	return result
}

func calcTarget(cups []int, next3 []int, index int) int {
	target := cups[index] - 1
	if target < 1 {
		target += len(cups)
	}
	for intSpliceContains(next3, target) {
		target -= 1
		if target < 1 {
			target += len(cups)
		}
	}
	return target
}

func intSpliceIndexOf(input []int, index int) int {
	for i, x := range input {
		if x == index {
			return i
		}
	}
	return -1
}


func intSpliceContains(input []int, index int) bool {
	for _, x := range input {
		if x == index {
			return true
		}
	}
	return false
}

func takeNext3(input []int, index int) ([]int, []int) {
	from := index+1
	to := index+4

	if index + 4 > len(input) {
		return input[to%len(input):from], append(cloneIntSlice(input)[from:], input[:(to%len(input))]...)
	} else {
		return append(cloneIntSlice(input)[:from], input[to:]...), input[from:to]
	}
}

func toIntList(line string) []int {
	cards := make([]int, 0)

	for _, char := range line {
		val, err := strconv.Atoi(string(char))
		if err != nil {
			panic(err)
		}
		cards = append(cards, val)
	}
	return cards
}

//type T int

func cloneIntSlice(a []int) []int{
	return append([]int(nil), a...)
}