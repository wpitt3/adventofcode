package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)


func TestDay20(t *testing.T) {

	assert.Equal(t, 1, bitFlip(512))
	assert.Equal(t, 512, bitFlip(1))
	assert.Equal(t, 768, bitFlip(3))

	assert.Equal(t, 1, patternToInt(".........#"))
	assert.Equal(t, 512, patternToInt("#........."))
}
