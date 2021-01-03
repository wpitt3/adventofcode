package main

import (
	"fmt"
	"strconv"
	"strings"
)

func day14() {
	lines, err := readFile("14")
	if err != nil { panic(err) }

	fmt.Println("Day 14")
	day14a(lines)
	day14b(lines)
}

func day14a(lines []string) {
	mask := ""
	memoryLocations := make(map[int64]int64)
	for _, line := range lines {
		if strings.HasPrefix(line, "mask") {
			mask = strings.Split(line, " = ")[1]
		} else {
			val, location := parseMem(line)
			asBinary := fmt.Sprintf("%0" + strconv.Itoa(36) +"b", val)

			asRune := []rune(asBinary)
			for i, x := range mask {
				if x != 'X' {
					asRune[i] = x
				}
			}
			val, err := strconv.ParseInt(string(asRune), 2, 64)
			if err != nil { panic(err) }
			memoryLocations[location] = val
		}
	}
	total := int64(0)
	for _, val := range memoryLocations {
		total += val
	}
	fmt.Println(total)
}

func day14b(lines []string) {
	mask := ""
	memoryLocations := make(map[int64]int64)
	for _, line := range lines {
		if strings.HasPrefix(line, "mask") {
			mask = strings.Split(line, " = ")[1]
		} else {
			val, location := parseMem(line)
			asBinary := fmt.Sprintf("%0" + strconv.Itoa(36) +"b", location)

			asRune := []rune(asBinary)
			for i, x := range mask {
				if x != '0' {
					asRune[i] = x
				}
			}

			allLocations := parseLocationWithXs(string(asRune))
			for _, x := range allLocations {
				memoryLocations[x] = val
			}
		}
	}

	total := int64(0)
	for _, val := range memoryLocations {
		total += val
	}
	fmt.Println(total)
}

func parseMem(line string) (int64, int64) {
	mem := strings.Split(line, " = ")
	val, err := strconv.ParseInt(mem[1], 10, 64)
	if err != nil { panic(err) }

	location := strings.Replace(mem[0], "mem[", "", -1)
	location = strings.Replace(location, "]", "", -1)
	parsedLocation, err := strconv.ParseInt(location, 10, 64)
	if err != nil { panic(err) }

	return val, parsedLocation
}

func parseLocationWithXs(location string) []int64 {
	x := strings.SplitN(location, "X", 2)
	allParsed := make([]int64, 0)
	if len(x) == 2 {
		allParsed = append(allParsed, parseLocationWithXs(x[0] + "0" + x [1])...)
		allParsed = append(allParsed, parseLocationWithXs(x[0] + "1" + x [1])...)
	} else {
		val, err := strconv.ParseInt(location, 2, 64)
		if err != nil { panic(err) }
		allParsed = append(allParsed, val)
	}
	return allParsed
}

