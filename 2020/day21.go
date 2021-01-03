package main

import (
	"fmt"
	"sort"
	"strings"
)


func day21() {
	lines, err := readFile("21")
	if err != nil { panic(err) }
	fmt.Println("Day 21")
	day21a(lines)
	day21b(lines)
}

func day21a(lines []string) {
	allergensToIngredientLists, allRecipes := parseRecipes(lines)
	unSafeIngredients := findUnsafeIngredients(allergensToIngredientLists)

	total := 0

	for _, ingredients := range allRecipes {
		for _, ingredient := range ingredients {
			if len(unSafeIngredients[ingredient]) == 0 {
				total++
			}
		}
	}
	fmt.Println(total)
}

func day21b(lines []string) {
	allergensToIngredientLists, _ := parseRecipes(lines)
	resolvedAllergenToUnsafe := make(map[string]string, 0)
	unSafeToAllergens := findUnsafeIngredients(allergensToIngredientLists)
	allergenToUnSafes := make(map[string][]string, 0)

	for unsafe, allergens := range unSafeToAllergens {
		for _, allergen := range allergens {
			allergenToUnSafes[allergen] = append(allergenToUnSafes[allergen], unsafe)
		}
	}

	for len(unSafeToAllergens) > 0 {
		for unsafe, allergens := range unSafeToAllergens {
			if len(allergens) == 1 {
				resolvedAllergenToUnsafe[allergens[0]] = unsafe
				for allergen, unsafes := range allergenToUnSafes {
					allergenToUnSafes[allergen] = removeStringByValue(unsafes, unsafe)
				}
				for unsafe2, allergens2 := range unSafeToAllergens {
					unSafeToAllergens[unsafe2] = removeStringByValue(allergens2, allergens[0])
				}
				delete(unSafeToAllergens, unsafe)
				delete(allergenToUnSafes, allergens[0])

			}
		}
		for allergen, unsafes := range allergenToUnSafes {
			if len(unsafes) == 1 {
				resolvedAllergenToUnsafe[allergen] = unsafes[0]
				for unsafe, allergens := range unSafeToAllergens {
					unSafeToAllergens[unsafe] = removeStringByValue(allergens, allergen)
				}
				for allergen2, unsafes2 := range allergenToUnSafes {
					allergenToUnSafes[allergen2] = removeStringByValue(unsafes2, unsafes[0])
				}
				delete(allergenToUnSafes, allergen)
				delete(unSafeToAllergens, unsafes[0])
			}
		}
	}

	keys := make([]string, 0)
	for k, _ := range resolvedAllergenToUnsafe {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	result := ""

	for i, k := range keys {
		result += resolvedAllergenToUnsafe[k]
		if i != len(keys)-1 {
			result += ","
		}
	}

	fmt.Println(result)
}

func removeStringByValue(list []string, value string) []string {
	for i, val := range list {
		if val == value {
			return append(list[:i], list[i+1:]...)
		}
	}
	return list
}

func findUnsafeIngredients(allergensToIngredientLists map[string][][]string) map[string][]string {
	unSafeIngredients := make(map[string][]string, 0)

	for allergen, ingredientsList := range allergensToIngredientLists {
		freqTable := make(map[string]int, 0)
		for _, ingredients := range ingredientsList {
			for _, ingredient := range ingredients {
				freqTable[ingredient] ++
			}
		}
		for ingredient, count := range freqTable {
			if count == len(ingredientsList) {
				unSafeIngredients[ingredient] = append(unSafeIngredients[ingredient], allergen)
			}
		}
	}
	return unSafeIngredients
}

func parseRecipes(lines []string) (map[string][][]string, [][]string){
	allergensToIngredientLists := make(map[string][][]string, 0)
	allRecipes := make([][]string, 0)
	for _, line := range lines {
		splitLine := split(line, "(contains ", "")
		ingredients := split(strings.TrimSpace(splitLine[0]), " ", "")
		allergens := split(strings.TrimSpace(splitLine[1]), ", ", ")")
		allRecipes = append(allRecipes, ingredients)
		for _, allergen := range allergens {
			allergensToIngredientLists[allergen] = append(allergensToIngredientLists[allergen], ingredients)
		}
	}
	return allergensToIngredientLists, allRecipes
}