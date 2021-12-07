package main

import (
	"fmt"
	"strconv"
	"strings"
)

func day22() {
  fmt.Println("Day 22")
	lines, err := readFile("../resources/y2020d22.txt")
	if err != nil { panic(err) }
	day22a(lines)
	day22b(lines)
}

func day22a(lines []string) {
	cards := extractCards(lines)
	for len(cards[0]) > 0 && len(cards[1]) > 0 {
		if cards[0][0] > cards[1][0] {
			x := []int{cards[0][0], cards[1][0]}
			cards[0] = append(cards[0][1:], x...)
			cards[1] = cards[1][1:]
		} else {
			x := []int{cards[1][0], cards[0][0]}
			cards[1] = append(cards[1][1:], x...)
			cards[0] = cards[0][1:]
		}
	}

	total := 0
	for i, x := range cards[0] {
		total += (len(cards[0]) - i) * x
	}
	fmt.Println(total)
}

func day22b(lines []string) {
	cards := extractCards(lines)

	//playGame([][]int{{43, 19}, {2, 29, 14}})
	_, cards = playGame(cards)
	total := 0
	for i, x := range cards[0] {
		total += (len(cards[0]) - i) * x
	}
	fmt.Println(total)
}

func playGame(cards [][]int) (bool, [][]int) {
	seenRounds := make(map[string]bool)

	for len(cards[0]) > 0 && len(cards[1]) > 0 {
		roundState := roundStateToString(cards)
		if seenRounds[roundState] {
			return true, cards
		} else {
			seenRounds[roundState] = true
		}
		if len(cards[0]) > cards[0][0] && len(cards[1]) > cards[1][0] {
			//start subgame
			player1Wins, _ := playGame(createSubgameCards(cards))
			if player1Wins {
				x := []int{cards[0][0], cards[1][0]}
				cards[0] = append(cards[0][1:], x...)
				cards[1] = cards[1][1:]
			} else {
				x := []int{cards[1][0], cards[0][0]}
				cards[1] = append(cards[1][1:], x...)
				cards[0] = cards[0][1:]
			}
		} else {
			if cards[0][0] > cards[1][0] {
				x := []int{cards[0][0], cards[1][0]}
				cards[0] = append(cards[0][1:], x...)
				cards[1] = cards[1][1:]
			} else {
				x := []int{cards[1][0], cards[0][0]}
				cards[1] = append(cards[1][1:], x...)
				cards[0] = cards[0][1:]
			}
		}
	}

	return len(cards[0]) > 0, cards
}

func createSubgameCards(cards [][]int) [][]int {
	newCards := make([][]int, 2)
	for j := 0; j < 2; j++ {
		for i := 1; i <= cards[j][0]; i++ {
			newCards[j] = append(newCards[j], cards[j][i])
		}
	}
	return newCards
}

func roundStateToString(cards [][]int) string {
	toPrint := ""
	for _, i := range cards[0] {
		toPrint += strconv.Itoa(i) + ","
	}
	toPrint += "|"
	for _, i := range cards[1] {
		toPrint += strconv.Itoa(i) + ","
	}
	return toPrint
}

func extractCards(lines []string) [][]int {
	cards := make([][]int, 2)

	index := 0
	for _, line := range lines {
		if strings.HasPrefix(line, "Player") || line == "" {
			index++
		} else {
			val, err := strconv.Atoi(line)
			if err != nil {
				panic(err)
			}
			if index < 2 {
				cards[0] = append(cards[0], val)
			} else {
				cards[1] = append(cards[1], val)
			}
		}
	}
	return cards
}
