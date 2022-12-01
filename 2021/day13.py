from FileReader import FileReader
import math


lines = FileReader().readLines('13')

max = 2000

points = set()
instructions = []

for line in lines:
    if ',' in line:
        x, y = line.split(',')
        points.add(int(x) * max + int(y))
    elif 'fold' in line:
        instructions.append(line)

firstCount = 0

for instruction in instructions:
    newPoints = set()
    xAxis = 'x' in instruction
    fold = int(instruction.split('=')[1])
    for point in points:
        x = math.floor(point / max)
        y = point % max
        if xAxis:
            if x > fold:
                x = fold - (x - fold)
        else:
            if y > fold:
                y = fold - (y - fold)
        newPoints.add(x * max + y)
    points = newPoints

    if firstCount == 0:
        firstCount = len(points)
print(firstCount)

result = [[' '] * 60 for i in range(6)]

for point in points:
    x = math.floor(point / max)
    y = point % max
    result[y][x] = '#'

for line in result:
    print(''.join(line))