package main

import (
	"fmt"
	"strconv"
	"strings"
)

func day20() {
	lines, err := readFile("20")
	if err != nil { panic(err) }

	fmt.Println("Day 20")
	day20a(lines)
	day20b(lines)
}

func day20a(lines []string) {
	tiles := processTiles(lines)
	freqTable := generateFreqTable(tiles)
	total := 1
	for _, tile := range tiles {
		uniqueCount := 0
		for _, side := range tile.tileBorders {
			if freqTable[side] == 1 {
				uniqueCount++
			}
		}
		if uniqueCount == 2 {
			total *= tile.tileId
		}
	}

	fmt.Println(total)
}

func day20b(lines []string) {
	tiles := processTiles(lines)
	freqTable := generateFreqTable(tiles)
	cornerTile := findCornerTile(tiles, freqTable)

	borderIdToIndex := make(map[int][]int)
	for index, tile := range tiles {
		for _, side := range tile.tileBorders {
			borderIdToIndex[side] = append(borderIdToIndex[side], index)
			borderIdToIndex[bitFlip(side)] = append(borderIdToIndex[bitFlip(side)], index)
		}
	}

	grid := make([][]tile, 12)
	for i := 0; i < 12; i++ {
		grid[i] = make([]tile, 12)
	}
	grid[0][0] = alignTile(cornerTile, orientateFirstCorner(cornerTile, freqTable), false)

	for j := 0; j < 12; j++ {
		for i := 0; i < 12; i++ {
			if i != 0 || j != 0 {
				if i == 0 {
					//match up
					borderIdToMatch := bitFlip(grid[j-1][i].tileBorders[2])
					matchingTile := getMatchingTile(grid[j-1][i].tileId, borderIdToMatch, borderIdToIndex, tiles)
					if !intSliceContains(matchingTile.tileBorders, borderIdToMatch) {
						matchingTile = alignTile(matchingTile, 0, true)
					}
					grid[j][i] = alignTile(matchingTile, (4 - intIndexOf(matchingTile.tileBorders, borderIdToMatch) ) % 4, false)

				} else {
					//match left
					borderIdToMatch := bitFlip(grid[j][i-1].tileBorders[1])
					matchingTile := getMatchingTile(grid[j][i-1].tileId, borderIdToMatch, borderIdToIndex, tiles)
					if !intSliceContains(matchingTile.tileBorders, borderIdToMatch) {
						matchingTile = alignTile(matchingTile, 0, true)
					}
					grid[j][i] = alignTile(matchingTile, (3-intIndexOf(matchingTile.tileBorders, borderIdToMatch)) % 4, false)
				}
			}
		}
	}

	runeGrid := make([][]rune, 96)
	for gridY := 0; gridY < 12; gridY++ {
		for innerY := 1; innerY < 9; innerY++ {
			for gridX := 0; gridX < 12; gridX++ {
				runeGrid[gridY*8+innerY-1] = append(runeGrid[gridY*8+innerY-1], []rune(grid[gridY][gridX].wholePattern[innerY])[1:9]...)
			}
		}
	}

	monster := [][]int{{0, 18}, {1, 0}, {1, 5}, {1, 6}, {1, 11}, {1, 12}, {1, 17}, {1, 18}, {1, 19}, {2, 1}, {2, 4}, {2, 7}, {2, 10}, {2, 13}, {2, 16}}

	totalMonsters := 0
	totalSea := 0
	for j := 0; j < len(runeGrid); j++ {
		for i := 0; i < len(runeGrid); i++ {
			if runeGrid[i][j] == '#' {
				totalSea++
			}
			if checkGridForMonster(monster, runeGrid, i, j) {
				totalMonsters += 1
			}
		}
	}
	fmt.Println(totalSea - totalMonsters*15)
}

func checkGridForMonster(monster [][]int, runeGrid [][]rune, i int, j int) bool {
	for _, a := range monster {
		x := i + a[0]
		y := j + a[1]
		if x >= len(runeGrid) || x < 0 || y >= len(runeGrid[0]) || y < 0 || runeGrid[x][y] != '#'{
			return false
		}
	}
	return true
}

func getMatchingTile(currentTileId int, toMatch int, borderIdToIndex map[int][]int, tiles [] tile) tile {
	matchingTiles := borderIdToIndex[toMatch]
	for j, _ := range matchingTiles {
		if currentTileId != tiles[matchingTiles[j]].tileId {
			return tiles[matchingTiles[j]]
		}
	}
	return tile{}
}

func orientateFirstCorner(cornerTile tile, freqTable map[int]int) int {
	for i, tileId := range cornerTile.tileBorders {
		if freqTable[tileId] == 1 && freqTable[cornerTile.tileBorders[(i+1)%4]] == 1 {
			return 3 - i
		}
	}
	return -1
}

func findCornerTile(tiles []tile, freqTable  map[int]int) tile {
	for _, tile := range tiles {
		uniqueCount := 0
		for _, side := range tile.tileBorders {
			if freqTable[side] == 1 {
				uniqueCount++
			}
		}
		if uniqueCount == 2 {
			return tile
		}
	}
	return tile{}
}

func generateFreqTable(tiles []tile) map[int]int {
	freqTable := make(map[int]int)
	for _, tile := range tiles {
		for _, side := range tile.tileBorders {
			freqTable[side]++
			freqTable[bitFlip(side)]++
		}
	}
	return freqTable
}

func bitFlip(original int) int {
	asBinary := fmt.Sprintf("%0" + strconv.Itoa(10) +"b", original)
	newPattern := ""
	for _, x := range []rune(asBinary) {
		newPattern = string(x) + newPattern
	}
	return parseBtoI(newPattern)
}

func patternToInt(pattern string) int {
	newPattern := ""
	for _, x := range []rune(pattern) {
		if x == '#' {
			newPattern += "1"
		} else {
			newPattern += "0"
		}
	}
	return parseBtoI(newPattern)
}

func processTiles(lines []string) []tile {
	tiles, tileName, tileSides := make([]tile, 0), "", []string{"", "", "", ""}
	for index, line := range lines {
		if line == "" {
			tiles, tileName, tileSides = append(tiles, newTile(tileName, tileSides, lines[index-10: index])), "", []string{"", "", "", ""}
		} else {
			if index % 12 == 0 {
				tileName = strings.Replace(split(line, " ", "")[1], ":", "", -1)
			} else {
				tileSides[1] = tileSides[1] + string(line[9])
				tileSides[3] = tileSides[3] + string(line[0])
				if index % 12 == 1 {
					tileSides[0] = line
				}
				if index % 12 == 10 {
					tileSides[2] = line
				}
			}
		}
	}
	return append(tiles, newTile(tileName, tileSides, lines[len(lines)-10:]))
}

func newTile(tileName string, tileSides []string, wholePattern []string) tile {
	sides := make([]int, 4)
	for i, _ := range tileSides {
		sides[i] = patternToInt(tileSides[i])
		if i > 1 {
			sides[i] = bitFlip(sides[i])
		}
	}
	return tile{parseStoI(tileName), sides, wholePattern}
}

type tile struct {
	tileId     int
	tileBorders []int
	wholePattern []string
}

func alignTile(oldTile tile, orientation int, flipped bool) tile {
	var newTileBorders []int
	if !flipped {
		newTileBorders = rotateIntSlice(oldTile.tileBorders, 4 - orientation)
	} else {
		newTileBorders = reverseIntSlice(rotateIntSlice(oldTile.tileBorders, 5 - orientation))
		for i, _ := range newTileBorders {
			newTileBorders[i] = bitFlip(newTileBorders[i])
		}
	}
	return tile{oldTile.tileId, newTileBorders, convertTileToStrings(oldTile.wholePattern, orientation, flipped)}
}

func (tile tile) String() string {
	fmt.Println(tile.tileBorders)
	toPrint := ""
	for _, x := range tile.wholePattern {
		toPrint += x + "\n"
	}
	return toPrint
}

func convertTileToStrings(wholePattern []string, orientation int, flipped bool) []string {
	length := len(wholePattern)

	var newPattern [][]rune
	if !flipped {
		if orientation == 0 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return i }, func(i, j int) int { return j })
		} else if orientation == 1 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return length-1-j }, func(i, j int) int { return i })
		} else if orientation == 2 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return length-1-i }, func(i, j int) int { return length-1-j })
		} else if orientation == 3 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return j }, func(i, j int) int { return length-1-i })
		}
	} else {
		if orientation == 0 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return i }, func(i, j int) int { return length-1-j })
		} else if orientation == 1 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return j }, func(i, j int) int { return i })
		} else if orientation == 2 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return length-1-i }, func(i, j int) int { return j })
		} else if orientation == 3 {
			newPattern = createPatterns(wholePattern, length, func(i, j int) int { return length-1-j }, func(i, j int) int { return length-1-i })
		}
	}
	result := make([]string, length)
	for j := 0; j < length; j++ {
		result[j] = string(newPattern[j])
	}

	return result
}

func createPatterns(wholePattern []string, size int, iFunc func(i int, j int) int, jFunc func(i int, j int) int) [][]rune {
	patternAsChars := make([][]rune, size)
	newPattern := make([][]rune, size)

	for j := 0; j < size; j++ {
		patternAsChars[j] = []rune(wholePattern[j])
		newPattern[j] = make([]rune, size)
	}

	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			newPattern[i][j] = patternAsChars[iFunc(i, j)][jFunc(i, j)]
		}
	}
	return newPattern
}

