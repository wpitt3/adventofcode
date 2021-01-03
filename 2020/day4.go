package main

import (
	"fmt"
	"regexp"
	"strconv"
	"strings"
)


//type StringMap map[string]string

func day4() {
	lines, err := readFile("4")
	if err != nil { panic(err) }

	passports := calcPassports(lines)

	fmt.Println("Day 4")
	day4a(passports)
	day4b(passports)
}

func day4a(passports []map[string]string) {
	total := 0
	for _, passport := range passports {
		if checkPassportHasKeys(passport) {
			total ++
		}
	}
	fmt.Println(total)
}

func day4b(passports []map[string]string) {
	total := 0
	for _, passport := range passports {
		if checkPassportIsValid(passport) {
			total ++
		}
	}
	fmt.Println(total)
}

func checkPassportHasKeys(passport map[string]string) bool {
	keys := []string{"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"}
	for _, key := range keys {
		if passport[key] == "" {
			return false
		}
	}
	return true
}

func checkPassportIsValid(passport map[string]string) bool {
	return checkPassportHasKeys(passport) &&
		isBetweenValues(passport["byr"], 1920, 2002) &&
		isBetweenValues(passport["iyr"], 2010, 2020) &&
		isBetweenValues(passport["eyr"], 2020, 2030) &&
		validHeight(passport["hgt"]) &&
		matchesRegex(passport["hcl"], `^#[0-9a-f]{6}$`) &&
		sliceContains(passport["ecl"], []string{"amb", "blu", "brn", "gry", "grn", "hzl", "oth"}) &&
		matchesRegex(passport["pid"], `^[0-9]{9}$`)
}

func validHeight(hgt string) bool {
	return strings.HasSuffix(hgt, "cm") && isBetweenValues(hgt[:len(hgt)-2], 150, 193) ||
		strings.HasSuffix(hgt, "in") && isBetweenValues(hgt[:len(hgt)-2], 59, 76)
}

func isBetweenValues(x string, from int, to int) bool {
	y, err := strconv.Atoi(x)
	return err == nil && y >= from && y <= to
}

func matchesRegex(value string, regex string) bool {
	return regexp.MustCompile(regex).MatchString(value)
}

func sliceContains(value string, keys []string) bool {
	for _, key := range keys {
		if key == value {
			return true
		}
	}
	return false
}

func calcPassports( lines []string) []map[string]string {
	passports := make([]map[string]string, 0)
	currentPassport := make(map[string]string)
	for _, line := range lines {
		if line == "" {
			passports = append(passports, currentPassport)
			currentPassport = make(map[string]string)
		}
		keyValues := split(line, " ", "")
		for _, keyValue := range keyValues {
			if keyValue != "" {
				kv := split(keyValue, ":", "")
				currentPassport[kv[0]] = kv[1]
			}
		}
	}
	return append(passports, currentPassport)
}