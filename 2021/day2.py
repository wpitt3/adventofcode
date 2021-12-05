from FileReader import FileReader


lines = FileReader().readLines('2')

y = 0
x = 0

for line in lines:
    direction, distance = line.split(' ')
    distance = int(distance)
    if (direction == 'forward'):
        x += distance
    elif (direction == 'down'):
        y += distance
    elif (direction == 'up'):
        y -= distance
print(x*y)