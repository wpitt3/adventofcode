package main

import (
	"fmt"
	"math"
	"strconv"
)


func day12() {
	lines, err := readFile("../resources/y2020d12.txt")
	if err != nil { panic(err) }

	fmt.Println("Day 12")
	day12a(lines)
	day12b(lines)
}

func day12a(lines []string) {
	x, y, d := 0,0,1

	for _, line := range lines {
		action := line[:1]
		distance, err := strconv.Atoi(line[1:])
		if err != nil { panic(err) }

		if action == "L" ||  action == "R" {
			if action == "L" {
				d -= distance/90
			} else {
				d += distance/90
			}
			d = (d + 4) % 4
		} else {
			direction := d
			if action == "N" {
				direction = 0
			} else if action == "E" {
				direction = 1
			} else if action == "S" {
				direction = 2
			} else if action == "W" {
				direction = 3
			}
			if direction == 0 {
				y += distance
			} else if direction == 1 {
				x += distance
			} else if direction == 2 {
				y -= distance
			} else if direction == 3 {
				x -= distance
			}
		}
	}

	fmt.Println(math.Abs(float64(x)) + math.Abs(float64(y)))
}

func day12b(lines []string) {
	x, y, wx, wy := 0,0,10,1

	for _, line := range lines {
		action := line[:1]
		distance, err := strconv.Atoi(line[1:])
		if err != nil { panic(err) }

		if action == "L" ||  action == "R" {
			direction := 0
			if action == "L" {
				direction = -distance/90
			} else {
				direction = distance/90
			}
			direction = (direction + 4) % 4
			if direction == 1 {
				temp := wy
				wy = - wx
				wx = temp
			} else if direction == 2 {
				wy = -wy
				wx = -wx
			} else if direction == 3 {
				temp := wy
				wy = wx
				wx = -temp
			}

		} else {
			if action == "N" {
				wy += distance
			} else if action == "E" {
				wx += distance
			} else if action == "S" {
				wy -= distance
			} else if action == "W" {
				wx -= distance
			} else {
				x += wx * distance
				y += wy * distance
			}
		}
	}

	fmt.Println(math.Abs(float64(x)) + math.Abs(float64(y)))
}

