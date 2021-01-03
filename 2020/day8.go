package main

import (
	"fmt"
	"strconv"
	"strings"
)


func day8() {
	lines, err := readFile("8")
	if err != nil { panic(err) }
	instructions := parseLines(lines)
	fmt.Println("Day 8")
	day8a(instructions)
	day8b(instructions)
}

func day8a(instructions []instruction) {
	seen := make(map[int]bool)
	index := 0
	acc := 0
	for !seen[index] {
		seen[index] = true
		if instructions[index].name == "nop" {
			index++
		} else if instructions[index].name == "acc" {
			acc += instructions[index].value
			index++
		} else {
			index += instructions[index].value
		}
	}
	fmt.Println(acc)
}

func day8b(instructions []instruction) {
	for i, modifiedInstruction := range instructions {
		if modifiedInstruction.name != "acc" {
			newInstructions := cloneInstructions(instructions)
			if modifiedInstruction.name == "nop" {
				newInstructions[i].name = "jmp"
			} else {
				newInstructions[i].name = "nop"
			}

			seen := make(map[int]bool)
			index := 0
			prevIndex := 0
			acc := 0
			for !seen[index] && index < len(instructions)  {
				seen[index] = true
				prevIndex = index
				if newInstructions[index].name == "nop" {
					index++
				} else if newInstructions[index].name == "acc" {
					acc += newInstructions[index].value
					index++
				} else {
					index += newInstructions[index].value
				}
			}
			if prevIndex == len(instructions)-1 {
				fmt.Println(acc)
			}
		}
	}
}

func parseLines(lines []string) []instruction {
	var instructions []instruction
	for _, line := range lines {
		splitLine := split(line, " ", "")
		value, err := strconv.Atoi(strings.TrimSpace(splitLine[1]))
		if err != nil { panic(err) }
		instructions = append(instructions, instruction{splitLine[0], value})
	}
	return instructions
}

func cloneInstructions(instructions []instruction) []instruction {
	var newInstructions []instruction
	for _, inst := range instructions {
		newInstructions = append(newInstructions, instruction{inst.name, inst.value})
	}
	return newInstructions
}

type instruction struct {
	name string
	value  int
}