package main

import (
	"fmt"
	"strings"
)

func day19() {
	lines, err := readFile("19")
	if err != nil { panic(err) }
	

	fmt.Println("Day 19")
	day19a(lines)
	day19b(lines)
}

func day19a(lines []string) {
	rules := generateRules(lines)
	keysToUpdate := findResolved(rules)
	for len(keysToUpdate) > 0 {
		newKeysToUpdate := make([]string, 0)
		for _, key := range keysToUpdate {
			newRules, updated := replaceRules(rules, key)
			newKeysToUpdate = append(newKeysToUpdate, updated...)
			rules = newRules
			delete(rules, key)
		}
		keysToUpdate = newKeysToUpdate
	}

	total := 0
	for _, line := range lines {
		if strings.HasPrefix(line, "a") || strings.HasPrefix(line, "b") {
			if recurseFindPossible(rules["0"], rules, line)[""] {
				total++
			}
		}
	}
	fmt.Println(total)
}

func day19b(lines []string) {
	rules := generateRules(lines)
	keysToUpdate := findResolved(rules)
	for len(keysToUpdate) > 0 {
		newKeysToUpdate := make([]string, 0)
		for _, key := range keysToUpdate {
			newRules, updated := replaceRules(rules, key)
			newKeysToUpdate = append(newKeysToUpdate, updated...)
			rules = newRules
			delete(rules, key)
		}
		keysToUpdate = newKeysToUpdate
	}
	rules["8"] = rule19{key: "8", keysValues:[][]pattern19{{{"42", false}}, {{"42", false}, {"8", false}}}}
	rules["11"] = rule19{key: "11", keysValues:[][]pattern19{{{"42", false}, {"31", false}}, {{"42", false}, {"11", false}, {"31", false}}}}

	total := 0
	for _, line := range lines {
		if strings.HasPrefix(line, "a") || strings.HasPrefix(line, "b") {
			if recurseFindPossible(rules["0"], rules, line)[""] {
				total++
			}
		}
	}
	fmt.Println(total)
}

func recurseFindPossible(rule rule19, rules map[string]rule19, toMatch string) map[string]bool {
	matching := make(map[string]bool, 0)

	if len(rule.keysValues) > 0 {
		for i, _ := range rule.keysValues {
			currentMatches := map[string]bool{toMatch: true}
			for j, _ := range rule.keysValues[i] {
				if rule.keysValues[i][j].isPattern {
					newCurrentMatches := make(map[string]bool, 0)
					for currentMatch, _ := range currentMatches {
						if strings.HasPrefix(currentMatch, rule.keysValues[i][j].pattern) {
							newCurrentMatches[string([]rune(currentMatch)[len(rule.keysValues[i][j].pattern):])] = true
						}
					}
					currentMatches = newCurrentMatches
				} else {
					newCurrentMatches := make( map[string]bool, 0)
					for currentMatch, _ := range currentMatches {
						for newMatch, _ :=  range recurseFindPossible(rules[rule.keysValues[i][j].pattern], rules, currentMatch) {
							newCurrentMatches[newMatch] = true
						}
					}
					currentMatches = newCurrentMatches
				}
			}
			for currentMatch, _ := range currentMatches {
				matching[currentMatch] = true
			}
		}
	}

	return matching
}

func replaceRules(rules map[string]rule19, resolvedKey string) (map[string]rule19, []string) {
	newResolvedKeys := make([]string, 0)
	resolvedRule := rules[resolvedKey]

	for _, rule := range rules {
		if rule.key != resolvedKey {
			if len(rule.keysValues) > 0 {
				anyUpdated := false
				for i, keyValue := range rule.keysValues {
					updated := false
					for j, ruleKeyValue := range keyValue {
						if ruleKeyValue.pattern == resolvedRule.key {
							rules[rule.key].keysValues[i][j] = resolvedRule.fullValue
							updated = true
							anyUpdated = true
						}
					}
					if updated && len(rule.keysValues[i]) == 2 && rule.keysValues[i][0].isPattern && rule.keysValues[i][1].isPattern {
						rules[rule.key].keysValues[i] = []pattern19{{rule.keysValues[i][0].pattern + rule.keysValues[i][1].pattern, true}}
					}
				}
				if anyUpdated && len(rule.keysValues) == 1 && len(rule.keysValues[0]) == 1 &&  rule.keysValues[0][0].isPattern {
					rules[rule.key] = rule19{key: rule.key, resolved: true, fullValue: pattern19{rule.keysValues[0][0].pattern, true}}
					newResolvedKeys = append(newResolvedKeys, rule.key)
				}
			}
		}
	}
	return rules, newResolvedKeys
}

func findResolved(rules map[string]rule19) []string {
	keysToUpdate := make([]string, 0)
	for _, rule := range rules {
		if rule.resolved {
			keysToUpdate = append(keysToUpdate, rule.key)
		}
	}
	return keysToUpdate
}

func generateRules(lines []string) map[string]rule19 {
	rules := make(map[string]rule19, 0)
	for _, line := range lines {
		if line == "" {
			return rules
		}
		key, rule := newRule19(line)
		rules[key] = rule
	}
	return rules
}

func newRule19(line string) (string, rule19) {
	splitLine := split(line, ":", "")
	var rule rule19
	splitLine[1] = strings.TrimSpace(splitLine[1])
	if strings.Contains(splitLine[1], "\"") {
		rule = rule19{key: splitLine[0], resolved: true, fullValue: pattern19{strings.Replace(splitLine[1], "\"", "", -1), true}}
	} else {
		orKeysValues := make([][]pattern19, 0)
		for _, splitOr := range split(splitLine[1], " | ", "") {
			orKeysValues = append(orKeysValues, toPattern(split( strings.TrimSpace(splitOr), " ", ""), false))
		}
		rule = rule19{key: splitLine[0], keysValues: orKeysValues}
	}
	return splitLine[0], rule
}

func toPattern(vals []string, isPattern bool) []pattern19 {
	patterns := make([]pattern19, 0)
	for _, val := range vals {
		patterns = append(patterns, pattern19{val, isPattern})
	}
	return patterns
}
type rule19 struct {
	key        string
	resolved   bool
	fullValue  pattern19
	keysValues [][]pattern19
}

type pattern19 struct {
	pattern string
	isPattern bool
}

func (b rule19) String() string {
if len(b.keysValues) > 0 {
		print := ":"
		for j, _ := range b.keysValues {
			for i, _ := range b.keysValues[j] {
				print += " " + b.keysValues[j][i].pattern
			}
			if j == 0 {
				print += " |"
			}
		}

		return fmt.Sprintf(b.key + print)
	} else {
		return fmt.Sprintf(b.key + " " + b.fullValue.pattern)
	}
}