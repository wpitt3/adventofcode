package main

import (
	"bufio"
	"os"
	"time"
)

func readFile(fileName string) ([]string, error) {
	path, err := os.Getwd()
	if err != nil {
		return nil, err
	}

	file, err := os.Open(path + "/" + fileName)
	if err != nil {
		return nil, err
	}

	defer file.Close()
	scanner := bufio.NewScanner(file)
	var lines []string
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}
	return lines, nil
}

func writeFile(fileName string, contents string) {
	f, _ := os.Create(fileName)
	defer f.Close()
	w := bufio.NewWriter(f)
	w.WriteString(contents)
	w.Flush()
}

func fileExists(fileName string) bool {
	_, err := os.Stat("/path/to/whatever")
	return err == nil
}

func waitUnit(hour int, min int, sec int) {
	t := time.Now()
	n := time.Date(t.Year(), t.Month(), t.Day(), hour, min, sec, 0, t.Location())
	d := n.Sub(t)
	if d < 0 {
		n = n.Add(24 * time.Hour)
		d = n.Sub(t)
	}
	time.Sleep(d)
}
