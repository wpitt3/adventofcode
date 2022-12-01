package main

import "fmt"

const (
	NE = iota
	E = iota
	SE = iota
	SW = iota
	W = iota
	NW = iota
)

func day24() {
	lines, err := readFile("24")
	if err != nil { panic(err) }

	offset := 500
	flippedLocations := day24a(lines, offset)

	for i := 0; i < 100; i++ {
		newFlipped := make(map[int]bool)
		tilesWhichMayFlip := findAllAdjacentTiles(flippedLocations, offset)
		for tile, _ := range tilesWhichMayFlip {
			score := countAdjacentBlackTiles(flippedLocations, tile, offset)
			if (score == 2 || flippedLocations[tile] && score == 1) {
				newFlipped[tile] = true
			}
		}
		flippedLocations = newFlipped
	}
	fmt.Println(len(flippedLocations))
}

func day24a(lines []string, offset int) map[int]bool {

	allDirections := extractDirections(lines)
	flippedLocations := make(map[int]bool)
	for _, directions := range allDirections {
		x, y := offset, offset
		for _, dir := range directions {
			switch dir {
			case NE:
				x++; y--
			case NW:
				x--; y--
			case SE:
				x++; y++
			case SW:
				x--; y++
			case E:
				x+=2
			case W:
				x-=2
			default:
				panic("AAH")
			}
		}
		if (flippedLocations[x * offset + y]) {
			delete(flippedLocations,x * offset + y)
		} else {
			flippedLocations[x * offset + y] = true
		}
	}
	fmt.Println(len(flippedLocations))
	return flippedLocations
}

func findAllAdjacentTiles(current map[int]bool, offset int) map[int]bool {
	adjacent := make(map[int]bool)
	for coord, _ := range current {
		for _, x := range findAdjacentTiles(coord, offset) {
			adjacent[x] = true
		}
	}
	return adjacent
}

func countAdjacentBlackTiles(currentStatus map[int]bool, coord int, offset int) int{
	count := 0
	for _, x := range findAdjacentTiles(coord, offset) {
		if (currentStatus[x] == true) {
			count++
		}
	}
	return count
}

func findAdjacentTiles(coord int, offset int) []int {
	return []int{
		coord + (offset * 2),
		coord - (offset * 2),
		coord + offset + 1,
		coord + offset - 1,
		coord - offset + 1,
		coord - offset - 1,
	}
}

func extractDirections(lines []string) [][]int {
	result := make([][]int, 0)
	for i, line := range lines {
		result = append(result, make([]int, 0))
		lineAsChars := []rune(line)
		for ii := 0; ii < len(lineAsChars); ii++ {
			if lineAsChars[ii] == 's' {
				if lineAsChars[ii+1] == 'e' {
					result[i] = append(result[i], SE)
				} else {
					result[i] = append(result[i], SW)
				}
				ii++
			} else if lineAsChars[ii] == 'n' {
				if lineAsChars[ii+1] == 'e' {
					result[i] = append(result[i], NE)
				} else {
					result[i] = append(result[i], NW)
				}
				ii++
			} else {
				if lineAsChars[ii] == 'e' {
					result[i] = append(result[i], E)
				} else {
					result[i] = append(result[i], W)
				}
			}
		}
	}
	return result
}
