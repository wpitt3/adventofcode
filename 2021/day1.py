from FileReader import FileReader


lines = FileReader().readLinesAsInts('1')

count = 0
for i in range(1, len(lines)):
    if lines[i] > lines[i-1]:
        count += 1

print(count)

count = 0
for i in range(3, len(lines)):
    if lines[i] > lines[i-3]:
        count += 1

print(count)