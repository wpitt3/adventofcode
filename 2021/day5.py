from FileReader import FileReader

lines = FileReader().readLines('5')

all_coords = []
for line in lines:
    all_coords.append([int(y) for coord in line.split(' -> ') for y in coord.split(',')])

grid = [[0 for j in range(1000)] for i in range(1000)]

for coords in all_coords:
    if coords[0] == coords[2]:
        for y in range(min(coords[1], coords[3]), max(coords[1], coords[3])+1):
            grid[y][coords[0]] += 1
    elif coords[1] == coords[3]:
        for x in range(min(coords[0], coords[2]), max(coords[0], coords[2])+1):
            grid[coords[1]][x] += 1

count = 0

for y in range(1000):
    for x in range(1000):
        if (grid[y][x] > 1):
            count += 1

print(count)

for coords in all_coords:
    if coords[0] != coords[2] and coords[1] != coords[3]:
        x_range = list(range(coords[0], coords[2]+1))
        if (coords[0]> coords[2]):
            x_range = list(range(coords[0], coords[2]-1, -1))
        y_range = list(range(coords[1], coords[3] + 1))
        if (coords[1] > coords[3]):
            y_range = list(range(coords[1], coords[3] - 1, -1))

        for i in range(0, len(x_range)):
            grid[y_range[i]][x_range[i]] += 1

count = 0

for y in range(1000):
    for x in range(1000):
        if (grid[y][x] > 1):
            count += 1

print(count)