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
	day24a(lines)
	day24b(lines)
}

func day24a(lines []string) {
	directions := extractDirections(lines)
	//grid := make([][]bool, 1000)
	//for i := 0; i < 1000; i++ {
	//	grid[i] = make([]bool, 1000)
	//}
	//x, y := 500, 500
	fmt.Println(directions[0])

}

func day24b(lines []string) {

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
				if lineAsChars[ii] == 'e' {
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
