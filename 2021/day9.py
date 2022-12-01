from FileReader import FileReader
import math


def is_lowest_point(x, y, input):
    if x > 0 and input[y][x] >= input[y][x-1]:
        return False
    if y > 0 and input[y][x] >= input[y-1][x]:
        return False
    if x < len(input[0])-1 and input[y][x] >= input[y][x+1]:
        return False
    if y < len(input)-1 and input[y][x] >= input[y+1][x]:
        return False
    return True


def find_upward_slopes(x, y, input):
    upwards = set()
    if x > 0 and input[y][x] < input[y][x - 1] != 9:
        upwards.add((x - 1)*100 + y)
    if y > 0 and input[y][x] < input[y-1][x] != 9:
        upwards.add(x * 100 + y - 1)
    if x < len(input[0])-1 and input[y][x] < input[y][x+1] != 9:
        upwards.add((x + 1)*100 + y)
    if y < len(input)-1 and input[y][x] < input[y+1][x] != 9:
        upwards.add(x * 100 + y + 1)
    return upwards


lines = FileReader().readLines('9')

input = [[int(value) for value in line] for line in lines]

total = 0
for x in range(len(input[0])):
    for y in range(len(input)):
        if is_lowest_point(x, y, input):
            total += input[y][x]+1

print(total)

basinbases = []
for x in range(len(input[0])):
    for y in range(len(input)):
        if is_lowest_point(x, y, input):
            basinbases.append(x*100+y)

maxBasins = [0, 0, 0]

for basinbase in basinbases:
    currentBasin = { basinbase }
    previousEdges = { basinbase }
    while(len(previousEdges) > 0):
        allNewEdges = set()
        for edge in list(previousEdges):
            newEdges = find_upward_slopes(math.floor(edge/100), edge%100, input)
            allNewEdges = allNewEdges.union(newEdges)
        allNewEdges = allNewEdges.difference(currentBasin).difference(previousEdges)
        previousEdges = allNewEdges
        currentBasin = currentBasin.union(allNewEdges)

    if len(currentBasin) > maxBasins[0]:
        maxBasins[0] = len(currentBasin)
        maxBasins.sort()

print(maxBasins[0] * maxBasins[1] * maxBasins[2])
