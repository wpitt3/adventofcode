from FileReader import FileReader


class Grid:
    def __init__(self, grid):
        self.row_count = [0] * 5
        self.col_count = [0] * 5
        self.columns = {}
        self.rows = {}
        self.flat_grid = [j for i in grid for j in i]

        for y in range(0, 5):
            for x in range(0, 5):
                value = grid[y][x]
                self.columns[value] = x
                self.rows[value] = y


lines = FileReader().readLines('4')

bingo_numbers = [int(i) for i in lines[0].split(',')]
lines = lines[2:]

boards = []

for i in range(0, len(lines), 6):
    grid = []
    for j in range(0, 5):
        grid.append([int(x) for x in lines[i+j].strip().replace('  ', ' ').split(' ')])
    boards.append(Grid(grid))

done = False
index = 0
boardIndex = -1
while(not done):
    current = bingo_numbers[index]
    for i in range(0, len(boards)):
        if current in boards[i].columns:
            boards[i].col_count[boards[i].columns[current]] += 1
            boards[i].row_count[boards[i].rows[current]] += 1
            if boards[i].col_count[boards[i].columns[current]] == 5 or boards[i].row_count[boards[i].rows[current]] == 5:
                boardIndex = i
                done = True
    index += 1
remaining_numbers = set(boards[boardIndex].flat_grid)

for i in range(0, index ):
    if bingo_numbers[i] in remaining_numbers:
        remaining_numbers.remove((bingo_numbers[i]))

total = 0
for x in list(remaining_numbers):
    total += x

print(total * bingo_numbers[index-1])


boards = []

for i in range(0, len(lines), 6):
    grid = []
    for j in range(0, 5):
        grid.append([int(x) for x in lines[i+j].strip().replace('  ', ' ').split(' ')])
    boards.append(Grid(grid))

boardIndices = set()

done = False
index = 0
boardIndex = -1
while(not done):
    current = bingo_numbers[index]
    for i in range(0, len(boards)):
        if current in boards[i].columns:
            boards[i].col_count[boards[i].columns[current]] += 1
            boards[i].row_count[boards[i].rows[current]] += 1
            if boards[i].col_count[boards[i].columns[current]] == 5 or boards[i].row_count[boards[i].rows[current]] == 5:
                boardIndices.add(i)
                if len(boardIndices) == len(boards) and boardIndex == -1:
                    boardIndex = i
                    done = True

    index += 1
remaining_numbers = set(boards[boardIndex].flat_grid)

for i in range(0, index ):
    if bingo_numbers[i] in remaining_numbers:
        remaining_numbers.remove((bingo_numbers[i]))

total = 0
for x in list(remaining_numbers):
    total += x

print(total * bingo_numbers[index-1])

