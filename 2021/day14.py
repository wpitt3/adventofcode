from FileReader import FileReader
import math
from Utils import addFreq, addFreqWithValue


def findPairResultAfterXTurns(pair, mapping, turns):
    freq = {}
    pairs = [pair]
    for i in range(turns):
        newPairs = []
        for x in pairs:
            newPairs.extend(mapping[x]['np'])
        pairs = newPairs
    for x in pairs:
        addFreq(freq, x)
    return freq


def charFreqFromPairFreq(pairFreq, chucked_input):
    charFreq = {}
    addFreq(charFreq, chucked_input[0][0])
    addFreq(charFreq, chucked_input[len(chucked_input)-1][1])

    for x in pairFreq:
        addFreqWithValue(charFreq, x[0], pairFreq[x])
        addFreqWithValue(charFreq, x[1], pairFreq[x])

    for x in charFreq:
        charFreq[x] = int(charFreq[x]/2)
    return charFreq

lines = FileReader().readLines('14')

mapping = {}
input = ''

for line in lines:
    if '->' in line:
        x, y = line.split(' -> ')
        newPairs = [x[0]+y, y+x[1]]
        mapping[x] = {'np': newPairs, 'nv': y}
    elif not '' == line:
        input = line

chucked_input = [input[i] + input[i+1] for i in range(len(input)-1)]

mappingsAfterTenTurns = {}
for key in mapping.keys():
    mappingsAfterTenTurns[key] = findPairResultAfterXTurns(key, mapping, 10)

pairFreq = {}
for i in chucked_input:
    freq = mappingsAfterTenTurns[i]
    for key in freq:
        addFreqWithValue(pairFreq, key, freq[key])

charFreq = charFreqFromPairFreq(pairFreq, chucked_input)
print(max(charFreq.values()) - min(charFreq.values()))

for i in range(3):
    newPairFreq = {}
    for key in pairFreq:
        for result in mappingsAfterTenTurns[key]:
            addFreqWithValue(newPairFreq, result, mappingsAfterTenTurns[key][result] * pairFreq[key])
    pairFreq = newPairFreq
charFreq = charFreqFromPairFreq(pairFreq, chucked_input)
print(max(charFreq.values()) - min(charFreq.values()))
