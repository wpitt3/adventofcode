package main

import (
	"fmt"
	"testing"
)


func TestDay13b(t *testing.T) {
	fmt.Println(day13b([]string{"", "17,x,13,19"}))
	fmt.Println(day13b([]string{"", "67,7,59,61"}))
	fmt.Println(day13b([]string{"", "67,x,7,59,61"}))
	fmt.Println(day13b([]string{"", "67,7,x,59,61"}))
	fmt.Println(day13b([]string{"", "1789,37,47,1889"}))

}