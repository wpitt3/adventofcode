from FileReader import FileReader

lines = FileReader().readLines('3')

freq = a = [0] * 12
for line in lines:
    for i in range(0, 12):
        if line[i] == '1':
            freq[i] += 1
        else:
            freq[i] -= 1
a = 0
b = 0
for i in range(0, 12):
    a *= 2
    b *= 2
    if freq[i] > 0:
        a += 1
    else:
        b += 1

print(a*b)

currentLines = lines
i = 0
while(len(currentLines) > 1 and i < 12):
    lines_with_1 = []
    lines_with_0 = []
    for line in currentLines:
        if line[i] == '1':
            lines_with_1.append(line)
        else:
            lines_with_0.append(line)
    if len(lines_with_0) > len(lines_with_1):
        currentLines = lines_with_0
    else:
        currentLines = lines_with_1
    i += 1

x = int(currentLines[0], 2)

currentLines = lines
i = 0
while(len(currentLines) > 1 and i < 12):
    lines_with_1 = []
    lines_with_0 = []
    for line in currentLines:
        if line[i] == '1':
            lines_with_1.append(line)
        else:
            lines_with_0.append(line)
    if len(lines_with_0) > len(lines_with_1):
        currentLines = lines_with_1
    else:
        currentLines = lines_with_0
    i += 1

y = int(currentLines[0], 2)

print(x * y)
