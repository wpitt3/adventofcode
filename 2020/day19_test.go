package main

import (
	"testing"
	"github.com/stretchr/testify/assert"
)


func TestDay19(t *testing.T) {
	rules := rules()
	assert.True(t,  recurseFindPossible(rules["1"], rules, "aa")[""])
	assert.True(t,  recurseFindPossible(rules["1"], rules, "ab")[""])
	assert.True(t,  recurseFindPossible(rules["1"], rules, "aaa")["a"])

	assert.Empty(t, recurseFindPossible(rules["1"], rules, "bb"))
	assert.Empty(t, recurseFindPossible(rules["1"], rules, "a"))

	assert.True(t,  recurseFindPossible(rules["2"], rules, "bb")[""])
	assert.True(t,  recurseFindPossible(rules["2"], rules, "bb")["b"])
	assert.True(t,  recurseFindPossible(rules["2"], rules, "ba")["a"])

	assert.True(t,  recurseFindPossible(rules["3"], rules, "bbaa")[""])
	assert.True(t,  recurseFindPossible(rules["3"], rules, "bbaa")["baa"])

	assert.True(t,  recurseFindPossible(rules["0"], rules, "abbb")[""])
	assert.True(t,  recurseFindPossible(rules["0"], rules, "abbb")["b"])
	assert.True(t,  recurseFindPossible(rules["0"], rules, "abb")[""])
	assert.True(t,  recurseFindPossible(rules["0"], rules, "aab")[""])
	assert.True(t,  recurseFindPossible(rules["0"], rules, "aabb")[""])
	assert.True(t,  recurseFindPossible(rules["0"], rules, "aabb")["b"])
}

func rules() map[string]rule19{
	rules := make(map[string]rule19, 0)
	rules["0"] = rule19{key: "0", keysValues: [][]pattern19{{{"1", false}, {"2", false} }}}
	rules["1"] = rule19{key: "1", keysValues: [][]pattern19{{{"ab", true}}, {{"aa", true}}}}
	rules["2"] = rule19{key: "2", keysValues: [][]pattern19{{{"bb", true}}, {{"b", true}}}}
	rules["3"] = rule19{key: "3", keysValues: [][]pattern19{{{"bb", true}, {"1", false}}, {{"b", true}}}}
	rules["4"] = rule19{key: "4", keysValues: [][]pattern19{{{"bb", true}, {"1", false}}}}
	return rules
}