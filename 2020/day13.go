package main

import (
	"fmt"
	"math/big"
	"strconv"
)

func day13() {
	lines, err := readFile("../resources/y2020d13.txt")
	if err != nil { panic(err) }

	fmt.Println("Day 13")
	day13a(lines)
	day13b(lines)
}

func day13a(lines []string) {
	targetTime, err := strconv.ParseInt(lines[0], 10, 64)
	if err != nil { panic(err) }

	buses := []int64{}
	for _, x := range split(lines[1], ",", "x") {
		bus, err := strconv.ParseInt(x, 10, 64)
		if err == nil {
			buses = append(buses, bus)
		}
	}

	minBusWait, minBusTotal := int64(1000),int64(0)

	for _, bus := range buses {
		waitTime := (bus - targetTime %bus) % bus
		if waitTime < minBusWait {
			minBusWait = waitTime
			minBusTotal = waitTime * bus
		}
	}
	fmt.Println(minBusTotal)
}

func day13b(lines []string) *big.Int {
	buses := make(map[*big.Int]*big.Int)
	for i, x := range split(lines[1], ",", "x") {
		bus, err := strconv.ParseInt(x, 10, 64)
		if err == nil {
			buses[big.NewInt(int64(i))] = big.NewInt(bus)
		}
	}


	overallMod := big.NewInt(1)
	for _, value := range buses {
		overallMod.Mul(overallMod, value)
	}

	total := big.NewInt(0)
	for key, value := range buses {
		if key.Cmp(big.NewInt(0)) != 0 {
			multiplier := big.NewInt(0).Div(overallMod, value)
			result := big.NewInt(0).ModInverse(multiplier, value)
			result.Mul(result, big.NewInt(0).Sub(value, key))
			result.Mul(result, multiplier)
			total.Add(total, result)
		}
	}
	total.Mod(total, overallMod)
	fmt.Println(total)

	return big.NewInt(-1)
}

func remainder(mod *big.Int, multiplier *big.Int, index *big.Int) *big.Int {
	result := big.NewInt(1).ModInverse(multiplier, mod)
	result.Mul(result, big.NewInt(1).Sub(mod, index))
	result.Mod(result, mod)
	return result
}

func axPlusc(a *big.Int, x *big.Int, c *big.Int) *big.Int {
	return big.NewInt(1).Add(c, big.NewInt(1).Mul(a, x))
}

func findNextVal(mod *big.Int, x *big.Int, xIndex *big.Int, y *big.Int, yIndex *big.Int) *big.Int {
	xRemainder := remainder(x, mod, xIndex)
	yRemainder := remainder(y, mod, yIndex)

	xMul := big.NewInt(1)
	yMul := big.NewInt(1)

	for axPlusc(x, xMul, xRemainder).Cmp(axPlusc(y, yMul, yRemainder)) != 0 {
		if axPlusc(x, xMul, xRemainder).Cmp(axPlusc(y, yMul, yRemainder)) < 0 {
			xMul.Add(xMul, big.NewInt(1))
		} else {
			yMul.Add(yMul, big.NewInt(1))
		}
	}
	return xMul.Mul(xMul, x).Add(xMul, xRemainder)
}

