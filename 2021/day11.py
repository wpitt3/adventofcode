from FileReader import FileReader
import math


def find_new_flashes(x, y, input):
    new_flashes = set()

    for j in range(-1, 2):
        for i in range(-1, 2):
            a = x + i
            b = y + j
            if a >= 0 and a < len(input[0]) and b >= 0 and b < len(input):
                input[b][a] += 1
                if input[b][a] > 9:
                    new_flashes.add(a * 100 + b)
    return new_flashes, input

lines = FileReader().readLines('11')
input = [[int(value) for value in line] for line in lines]

total = 0
for z in range(100):
    flashes = []
    for x in range(len(input[0])):
        for y in range(len(input)):
            input[y][x] += 1
            if input[y][x] > 9:
                flashes.append(x*100+y)

    allFlashes = set(flashes)
    previousEdges = set(flashes)
    while len(previousEdges) > 0:
        allNewEdges = set()
        for edge in list(previousEdges):
            newEdges, input = find_new_flashes(math.floor(edge / 100), edge % 100, input)
            allNewEdges = allNewEdges.union(newEdges)
        allNewEdges = allNewEdges.difference(allFlashes).difference(previousEdges)
        previousEdges = allNewEdges
        allFlashes = allFlashes.union(allNewEdges)

    for x in range(len(input[0])):
        for y in range(len(input)):
            if input[y][x] > 9:
                input[y][x] = 0
    total += len(allFlashes)
print(total)


lines = FileReader().readLines('11')
input = [[int(value) for value in line] for line in lines]

index = 0
done = False
while(index < 100000 and not done):
    flashes = []
    for x in range(len(input[0])):
        for y in range(len(input)):
            input[y][x] += 1
            if input[y][x] > 9:
                flashes.append(x*100+y)

    allFlashes = set(flashes)
    previousEdges = set(flashes)
    while len(previousEdges) > 0:
        allNewEdges = set()
        for edge in list(previousEdges):
            newEdges, input = find_new_flashes(math.floor(edge / 100), edge % 100, input)
            allNewEdges = allNewEdges.union(newEdges)
        allNewEdges = allNewEdges.difference(allFlashes).difference(previousEdges)
        previousEdges = allNewEdges
        allFlashes = allFlashes.union(allNewEdges)

    for x in range(len(input[0])):
        for y in range(len(input)):
            if input[y][x] > 9:
                input[y][x] = 0
    index += 1
    if len(allFlashes) == 100:
        done = True
print(index)