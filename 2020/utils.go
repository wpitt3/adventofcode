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

//func split(input string, delimiters []string, remove []string) []string {
//	for _, x := range remove {
//		input = strings.Replace(input, x, "", -1)
//	}
//
//	for _, x := range delimiters {
//		input = strings.Replace(input, x, "|", -1)
//	}
//
//	return strings.Split(input, "|")
//}
