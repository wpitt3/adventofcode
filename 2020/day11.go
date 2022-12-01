package main

import (
	"fmt"
)


func day11() {
	lines, err := readFile("../resources/y2020d11.txt")
	if err != nil { panic(err) }

	fmt.Println("Day 11")
	day11a(lines)
	day11b(lines)
}

func day11a(lines []string) {
	for i := 0; i < 100; i ++ {
		newGrid := make([]string, len(lines))
		for y := 0; y < len(lines); y++ {
			row := ""
			for x := 0; x < len(lines[0]); x++ {
				if lines[y][x] == '.' {
					row += "."
				} else {
					adjacent := countAdjacent(lines, x, y)
					if lines[y][x] == 'L' {
						if adjacent == 0 {
							row += "#"
						} else {
							row += "L"
						}
					} else {
						if adjacent >= 4 {
							row += "L"
						} else {
							row += "#"
						}
					}
				}

			}
			newGrid[y] = row
		}
		lines = newGrid
	}
	count := countFilledSeats(lines)
	fmt.Println(count)
}

func day11b(lines []string) {
	for i := 0; i < 100; i ++ {
		newGrid := make([]string, len(lines))
		for y := 0; y < len(lines); y++ {
			row := ""
			for x := 0; x < len(lines[0]); x++ {
				if lines[y][x] == '.' {
					row += "."
				} else {
					adjacent := countVisuallyAdjacent(lines, x, y)
					if lines[y][x] == 'L' {
						if adjacent == 0 {
							row += "#"
						} else {
							row += "L"
						}
					} else {
						if adjacent >= 5 {
							row += "L"
						} else {
							row += "#"
						}
					}
				}

			}
			newGrid[y] = row
		}
		lines = newGrid
	}
	count := countFilledSeats(lines)
	fmt.Println(count)
}

func countAdjacent(lines []string, x int, y int) int {
	total := 0
	for i := -1; i < 2; i++ {
		for j := -1; j < 2; j++ {
			if i != 0 || j != 0 {
				if x+i >= 0 && x+i < len(lines[0]) && y+j >= 0 && y+j < len(lines) {
					if lines[y+j][x+i] == '#' {
						total++
					}
				}
			}
		}
	}
	return total
}

func countVisuallyAdjacent(lines []string, x int, y int) int {
	total := 0
	for i := -1; i < 2; i++ {
		for j := -1; j < 2; j++ {
			if i != 0 || j != 0 {
				multiplier := 1
				for x+i*multiplier >= 0 && x+i*multiplier < len(lines[0]) && y+j*multiplier >= 0 && y+j*multiplier < len(lines) {
					if lines[y+j*multiplier][x+i*multiplier] == '.' {
						multiplier += 1
					} else {
						if lines[y+j*multiplier][x+i*multiplier] == '#' {
							total++
						}
						multiplier += 1000
					}
				}
			}
		}
	}
	return total
}

func countFilledSeats(lines []string) int {
	total := 0
	for y := 0; y < len(lines); y++ {
		for x := 0; x < len(lines[0]); x++ {
			if lines[y][x] == '#' {
				total ++
			}
		}
	}
	return total
}
