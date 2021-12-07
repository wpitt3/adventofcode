package main

import (
	"fmt"
	"sort"
	"strconv"
	"strings"
)


func day5() {
	lines, err := readFile("../resources/y2020d05.txt")
	if err != nil { panic(err) }

	fmt.Println("Day 5")

	seats := make([]int, len(lines))
	for i, x := range lines {
		seats[i] = seatToId(x)
	}
	day5a(seats)
	day5b(seats)
}

func day5a(seats []int) {
	sort.Ints(seats)
	fmt.Println(seats[len(seats)-1])
}

func day5b(seats []int) {
	for i, seat := range seats {
		if i != 0 && seat - seats[i-1] == 2{
			fmt.Println(seat-1)
		}
	}
}

func seatToId(line string) int {
	line = strings.Replace(line, "L", "0", -1)
	line = strings.Replace(line, "R", "1", -1)
	line = strings.Replace(line, "F", "0", -1)
	line = strings.Replace(line, "B", "1", -1)

	i, err := strconv.ParseInt(line, 2, 32)
	if err != nil {
		panic(err)
	}
	return int(i)
}