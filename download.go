package main

import (
	"errors"
	"io"
	"net/http"
	"strconv"
)

func main() {
	//waitUnit(16, 20, 45)
	//fmt.Println(time.Now())
	//deadline := time.Now().Add(10 * time.Second)
	//for {
	//	out, _ := exec.Command("echo", "hello").Output()
	//	if string(out) == "hello" || time.Now().After(deadline) {
	//		break
	//	}
	//}
	//
	//log.Println("Hello")
	//fmt.Println("hey")
	//argsWithProg := os.Args[1:]
	//fmt.Println(argsWithProg)

	//day := fmt.Sprintf("%02d", 1)

	//fmt.Printf("this%sday", day)
	//
	//
	//os.MkdirAll("tmp/tmp", os.ModePerm)
	//
	//writeFile("tmp/tmp/x.txt", "more")
	//
	//response, err := curlAOCUrl(2022, 11)
	//if err != nil {
	//	fmt.Println(response)
	//	return
	//}
	//writeFile(response, "z.txt")
	//fmt.Println()
	//fmt.Println(curlAOCUrl(2021, 1))
}

func saveDay(year int, day int, location string, format string) {

}

func curlAOCUrl(year int, day int) (string, error) {
	file, _ := readFile("secret.txt")
	auth := file[0]
	r, _ := http.NewRequest("POST", "https://adventofcode.com/"+strconv.Itoa(year)+"/day/"+strconv.Itoa(day)+"/input", nil)
	r.Header.Add("cookie", auth)
	client := &http.Client{}
	res, _ := client.Do(r)
	defer res.Body.Close()
	b, _ := io.ReadAll(res.Body)
	if res.StatusCode != 200 {
		return string(b), errors.New("")
	}

	return string(b), nil
}
