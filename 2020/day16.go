package main

import (
	"fmt"
	"strings"
)

func day16() {
	lines, err := readFile("../resources/y2020d16.txt")
	if err != nil { panic(err) }

	rules := parseRules(lines)

	fmt.Println("Day 16")
	day16a(lines, rules)
	day16b(lines, rules)
}

func day16a(lines []string, rules []rule) {
	index := 0
	total := 0
	for _, line := range lines {
		if index == 3 {
			vals := splitToInts(line, ",", "")
			for _, val := range vals {
				if !valMatchesAnyRule(val, rules) {
					total += val
				}
			}
		}
		if line == "" || strings.HasPrefix(line, "nearby"){
			index += 1
		}
	}
	fmt.Println(total)
}

func day16b(lines []string, rules []rule) {
	index := 0
	validTickets := make([][]int, 0)
	for _, line := range lines {
		if index >= 1 && line != "" && !strings.Contains(line, "ticket"){
			vals := splitToInts(line, ",", "")
			if ticketIsValid(vals, rules) {
				//fmt.Println(vals)
				validTickets = append(validTickets, vals)
			}
		}
		if line == "" {
			index += 1
		}
	}

	onlyRuleToTicket := make(map[int]int)
	ruleToTickets := make(map[int][]int)
	ticketToRules := make(map[int][]int)

	for ruleIndex := 0; ruleIndex < len(rules); ruleIndex++ {
		for ticketIndex := 0; ticketIndex < len(rules); ticketIndex++ {
			if ruleIsTrueForAllTicketsAtIndex(validTickets, rules[ruleIndex], ticketIndex) {
				ruleToTickets[ruleIndex] = append(ruleToTickets[ruleIndex], ticketIndex)
				ticketToRules[ticketIndex] = append(ticketToRules[ticketIndex], ruleIndex)
			}
		}
	}

	for len(ruleToTickets) + len(ticketToRules) > 0 {
		loneValue, lonerIndex, ruleToTickets, ticketToRules := messyShit(ruleToTickets, ticketToRules)
		if loneValue != -1 {
			onlyRuleToTicket[loneValue] = lonerIndex
		}
		loneValue, lonerIndex, ticketToRules, ruleToTickets = messyShit(ticketToRules, ruleToTickets)
		if loneValue != -1 {
			onlyRuleToTicket[lonerIndex] = loneValue
		}
	}


	total := 1
	for ruleIndex := 0; ruleIndex < 6; ruleIndex++ {
		total *= validTickets[0][onlyRuleToTicket[ruleIndex]]
	}

	fmt.Println(total)
}

func messyShit(aTob map[int][]int, bToa map[int][]int) (int, int, map[int][]int, map[int][]int) {
	lonerIndex := findLonerIndex(bToa)
	if lonerIndex != -1 {
		loneValue := bToa[lonerIndex][0]
		delete(bToa, lonerIndex)
		delete(aTob, loneValue)
		for k, v := range bToa {
			bToa[k] = removeByValue(v, loneValue)
		}
		for k, v := range aTob {
			aTob[k] = removeByValue(v, lonerIndex)
		}
		return loneValue, lonerIndex, aTob, bToa
	}
	return -1, -1, aTob, bToa
}

func removeByValue(list []int, value int) []int {
	for i, val := range list {
		if val == value {
			return append(list[:i], list[i+1:]...)
		}
	}
	return list
}

func findLonerIndex(lonerMap map[int][]int) int {
	for k, v := range lonerMap {
		if len(v) == 1 {
			return k
		}
	}
	return -1
}

func ruleIsTrueForAllTicketsAtIndex(validTickets [][]int, rule rule, ticketIndex int) bool {
	for _, validTicket := range validTickets {
		if !rule(validTicket[ticketIndex]) {
			return false
		}
	}
	return true
}

func ticketIsValid(vals []int, rules []rule) bool {
	for _, val := range vals {
		if !valMatchesAnyRule(val, rules) {
			return false
		}
	}
	return true
}

func valMatchesAnyRule(val int, rules []rule) bool {
	for _, rule := range rules {
		if rule(val) {
			return true
		}
	}
	return false
}

func parseRules(lines []string) []rule {
	rules := make([]rule, 0)
	for _, line := range lines {
		if line == "" {
			return rules
		}
		ranges := strings.Split(strings.Split(line, ": ")[1], " or ")
		ranges0 := splitToInts(ranges[0], "-", "")
		ranges1 := splitToInts(ranges[1], "-", "")

		var x rule = func(x int) bool {
			return x >= ranges0[0] && x <= ranges0[1] || x >= ranges1[0] && x <= ranges1[1]
		}
		rules = append(rules, x)
	}
	return rules
}

type rule func(a int) bool

