package main

import (
	"fmt"
	"strconv"
	"strings"
)


func day7() {
	lines, err := readFile("../resources/y2020d07.txt")
	if err != nil { panic(err) }

	outerToInner := getAllBags(lines)

	innerToOuter := make(map[string][]string)
	for outer, innerBags := range outerToInner {
		for _, innerbag := range innerBags {
			innerToOuter[innerbag.name] = append(innerToOuter[innerbag.name], outer)
		}
	}

	fmt.Println("Day 7")
	day7a(innerToOuter)
	day7b(outerToInner)
}

func day7a(innerToOuter map[string][]string) {
	var queue []string
	seen := make(map[string]bool)
	queue = append(queue, "shiny gold")

	for len(queue) > 0 {
		if !seen[queue[0]] {
			for _, bag := range innerToOuter[queue[0]] {
				queue = append(queue, bag)
			}
		}
		seen[queue[0]] = true
		queue = queue[1:]
	}

	fmt.Println(len(seen)-1)
}

func day7b(outerToInner map[string][]innerbag) {
	fmt.Println(countBagsIn(outerToInner, "shiny gold")-1)
}

func countBagsIn(outerToInner map[string][]innerbag, name string) int {
	total := 1
	for _, innerBag := range outerToInner[name] {
		total += countBagsIn(outerToInner, innerBag.name) * innerBag.count
	}
	return total
}

func getAllBags(lines []string) map[string][]innerbag {
	allBags := make(map[string][]innerbag)

	for _, line := range lines {
		line = strings.ReplaceAll(line, "bags", "")
		line = strings.ReplaceAll(line, "bag", "")
		line = strings.ReplaceAll(line, ".", "")

		fromBags := split(line, "contain",  "")
		outerBag := strings.TrimSpace(fromBags[0])
		splitInnerBags := split(strings.TrimSpace(fromBags[1]), ", ", "")

		innerBags := make([]innerbag, 0)
		if splitInnerBags[0] != "no other" {
			for _, innerBag := range splitInnerBags {
				count, err := strconv.Atoi(innerBag[:1])
				if err != nil { panic(err) }
				innerBags = append(innerBags, newInnerbag(strings.TrimSpace(innerBag[2:]), count))
			}
		}
		allBags[outerBag] = innerBags

	}
	return allBags
}

func newInnerbag(name string, count int) innerbag {
	i := innerbag{name: name, count: count}
	return i
}

type innerbag struct {
	name string
	count  int
}