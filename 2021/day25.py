from FileReader import FileReader

def blankGrid(grid):
    out = []
    for i in range(len(grid)):
        out.append([])
        for j in range(len(grid[0])):
            out[i].append(0)
    return out


def readFile():
    lines = FileReader().readLines('25')
    results = []
    for line in lines:
        result = []
        for character in line:
            if character == 'v':
                result.append(2)
            elif character == '>':
                result.append(1)
            else:
                result.append(0)
        results.append(result)
    return results


def printGrid(grid):
    print()
    w = len(grid[0])
    h = len(grid)
    for y in range(h):
        out = ""
        for x in range(w):
            out += ">" if grid[y][x] == 1 else ("v" if grid[y][x] == 2 else " ")
        print(out)



grid = readFile()
w = len(grid[0])
h = len(grid)
done = False
i = 0
while not done:
    newGrid = blankGrid(grid)
    for y in range(h):
        for x in range(w):
            if grid[y][x] == 1:
                if grid[y][(x + 1) % w] == 0:
                    newGrid[y][(x + 1) % w] = 1
                else:
                    newGrid[y][x] = 1
    for y in range(h):
        for x in range(w):
            if grid[y][x] == 2:
                if grid[(y + 1) % h][x] != 2 and newGrid[(y + 1) % h][x] == 0:
                    newGrid[(y + 1) % h][x] = 2
                else:
                    newGrid[y][x] = 2
    done = True
    for y in range(h):
        for x in range(w):
            if newGrid[y][x] != grid[y][x]:
                done = False
    grid = newGrid
    i+=1

print(i)