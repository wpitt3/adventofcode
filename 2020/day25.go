package main

import "fmt"

func day25() {
	c := uint64(6270530)
	d := uint64(14540258)
	start := uint64(1)
	subj := uint64(7)
	loopSize := 0
	for start != c {
		start = start * subj % 20201227
		loopSize++
	}
	
	fmt.Println(createEncKey(d, loopSize))
}

func createEncKey(subj uint64, loopsize int) uint64{
	start := uint64(1)
	for i := 0; i < loopsize; i++ {
		start = start * subj % 20201227
	}
	return start
}
