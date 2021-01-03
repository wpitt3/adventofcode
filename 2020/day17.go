package main

import (
	"fmt"
)

func day17() {
	lines, err := readFile("17")
	if err != nil { panic(err) }
	

	fmt.Println("Day 17")
	day17a(lines)
	day17b(lines)
}

func day17a(lines []string) {
	size := 20
	offset := 6
	grid := makeGrid(size)
	for y, line := range lines {
		for x, val := range line {
			if val == '#' {
				grid[9][x+offset][y+offset] = true
			}
		}
	}

	for cycle := 0; cycle < 6; cycle++ {
		newGrid := makeGrid(size)
		for x := 1; x < size-1; x++ {
			for y := 1; y < size-1; y++ {
				for z := 1; z < size-1; z++ {
					newGrid[x][y][z] = isActive(grid, x, y, z)
				}
			}
		}
		grid = newGrid
	}
	fmt.Println(countActive(grid))
}

func day17b(lines []string) {
	size := 20
	offset := 6
	grid := make4dGrid(size)
	for y, line := range lines {
		for x, val := range line {
			if val == '#' {
				grid[9][9][x+offset][y+offset] = true
			}
		}
	}

	for cycle := 0; cycle < 6; cycle++ {
		newGrid := make4dGrid(size)
		for w := 1; w < size-1; w++ {
			for x := 1; x < size-1; x++ {
				for y := 1; y < size-1; y++ {
					for z := 1; z < size-1; z++ {
						newGrid[w][x][y][z] = isActive4d(grid, w, x, y, z)
					}
				}
			}
		}
		grid = newGrid
	}
	fmt.Println(countActive4d(grid))
}

func makeGrid(size int)[][][]bool {
	grid := make([][][]bool, size)
	for x := 0; x < size; x++ {
		grid[x] = make([][]bool, size)
		for y := 0; y < size; y++ {
			grid[x][y] = make([]bool, size)
		}
	}
	return grid
}

func isActive(grid [][][]bool, i int, j int, k int) bool {
	total := 0
	for x := -1; x < 2; x++ {
		for y := -1; y < 2; y++ {
			for z := -1; z < 2; z++ {
				if (x != 0 || y != 0 || z != 0) && grid[i+x][j+y][k+z] {
					total += 1
				}
			}
		}
	}
	return total == 3 || (grid[i][j][k] && total == 2)
}

func countActive(grid [][][]bool) int {
	size := len(grid)
	total := 0
	for x := 1; x < size-1; x++ {
		for y := 1; y < size-1; y++ {
			for z := 1; z < size-1; z++ {
				if grid[x][y][z] {
					total += 1
				}
			}
		}
	}
	return total
}

func make4dGrid(size int)[][][][]bool {
	grid := make([][][][]bool, size)
	for w := 0; w < size; w++ {
		grid[w] = make([][][]bool, size)
		for x := 0; x < size; x++ {
			grid[w][x] = make([][]bool, size)
			for y := 0; y < size; y++ {
				grid[w][x][y] = make([]bool, size)
			}
		}
	}
	return grid
}

func isActive4d(grid [][][][]bool, i int, j int, k int, l int) bool {
	total := 0
	for w := -1; w < 2; w++ {
		for x := -1; x < 2; x++ {
			for y := -1; y < 2; y++ {
				for z := -1; z < 2; z++ {
					if (w != 0 || x != 0 || y != 0 || z != 0) && grid[i+x][j+y][k+z][l+w] {
						total += 1
					}
				}
			}
		}
	}
	return total == 3 || (grid[i][j][k][l] && total == 2)
}
func countActive4d(grid [][][][]bool) int {
	size := len(grid)
	total := 0
	for w := 1; w < size-1; w++ {
		for x := 1; x < size-1; x++ {
			for y := 1; y < size-1; y++ {
				for z := 1; z < size-1; z++ {
					if grid[w][x][y][z] {
						total += 1
					}
				}
			}
		}
	}
	return total
}