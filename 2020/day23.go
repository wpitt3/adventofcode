package main

import (
	"fmt"
	"strconv"
)

func day23() {
	input := toIntList("247819356")
	a(input)
	b(input)
}

func a(input []int) {
	keyToCup, currentCup := setupCups(input, 0)
	currentCup = shuffleCups(keyToCup, currentCup, 100)
	y := keyToCup[1].next
	for i := 0; i < 8; i++ {
		fmt.Print(y.id)
		fmt.Print("")
		y = y.next
	}
	fmt.Println()
}

func b(input []int) {
	keyToCup, currentCup := setupCups(input, 1_000_000)
	currentCup = shuffleCups(keyToCup, currentCup, 10_000_000)
	y := keyToCup[1].next
	fmt.Println(y.id)
	fmt.Println(y.next.id)
	fmt.Println(y.id * y.next.id)
}

func isPickedUp(x int, y[]int) bool {
	return x == y[0] || x == y[1] || x == y[2]
}

func setupCups(cups []int, total int) (map[int]*Cup, *Cup) {
	keyToCup := make(map[int]*Cup)
	startCup := &Cup{cups[0], nil }
	keyToCup[cups[0]] = startCup
	currentCup := startCup
	for i := total; i >= 10; i -- {
		newCup := &Cup{i, currentCup}
		currentCup = newCup
		keyToCup[i] = newCup
	}
	for i := 8; i > 0; i-- {
		newCup := &Cup{cups[i], currentCup}
		currentCup = newCup
		keyToCup[cups[i]] = newCup
	}
	startCup.next = currentCup
	return keyToCup, startCup
}

func shuffleCups(keyToCup map[int]*Cup, cup *Cup, times int) *Cup {
	pickedUp := []int{0, 0, 0}
	currentCup := cup

	length := len(keyToCup)

	for x := 0; x < times; x++ {
		cupAfterSelected := currentCup.next
		for i := 0; i < 3; i++ {
			pickedUp[i] = cupAfterSelected.id
			cupAfterSelected = cupAfterSelected.next
		}

		searchingFor := (currentCup.id+length-2)%length + 1
		for isPickedUp(searchingFor, pickedUp) {
			searchingFor = (searchingFor+length-2)%length + 1
		}

		currentCup.next = cupAfterSelected
		keyToCup[pickedUp[2]].next = keyToCup[searchingFor].next
		keyToCup[searchingFor].next = keyToCup[pickedUp[0]]

		currentCup = currentCup.next
	}
	return keyToCup[1]
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

type Cup struct {
	id     int
	next     *Cup
}