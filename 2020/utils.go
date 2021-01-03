package main

import (
	"bufio"
	"os"
	"strconv"
	"strings"
)

func readFile(fileName string) ([]string, error) {
	path, err := os.Getwd()
	if err != nil { return nil, err }

	file, err := os.Open(path+"/resources/"+fileName)
	if err != nil { return nil, err }

	defer file.Close()
	scanner := bufio.NewScanner(file)
	var lines []string
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	return lines, nil
}

func toIntArray(input []string) []int {
	result := make([]int, len(input))
	for i, x := range input {
		val, err := strconv.Atoi(x)
		if err != nil { panic(err) }
		result[i] = val
	}
	return result
}

func splitToInts(input string, delimiters string, remove string) []int {
	split := split(input, delimiters, remove)
	return toIntArray(split)
}

func split(input string, delimiter string, remove string) []string {
	if remove != "" {
		input = strings.Replace(input, remove, "", -1)
	}
	return strings.Split(input, delimiter)
}

func reverseIntSlice(original []int) [] int {
	length := len(original)
	reversed := make([]int, length)
	for i, _ := range original {
		reversed[i] = original[length - i - 1]
	}
	return reversed
}

func rotateIntSlice(original []int, rotation int) [] int {
	length := len(original)
	newIntSlice := make([]int, length)
	for i, _ := range original {
		newIntSlice[i] = original[(i + rotation) % length]
	}
	return newIntSlice
}

func intSliceContains(original []int, value int) bool {
	return intIndexOf(original, value) != -1
}

func intIndexOf(original []int, value int) int {
	for i, _ := range original {
		if original[i] == value {
			return i
		}
	}
	return -1
}

func parseStoI(original string) int {
	val, err := strconv.ParseInt(original, 10, 32)
	if err != nil { panic(err) }
	return int(val)
}

func parseBtoI(original string) int {
	val, err := strconv.ParseInt(original, 2, 32)
	if err != nil { panic(err) }
	return int(val)
}
