from FileReader import FileReader


def triangle(x):
    return int((x * (x+1))/2)

lines = FileReader().readLines('17')[0]

coords = [[int(a) for a in b.split('..')] for b in lines.split('x=')[1].split(', y=')]

maxI = 0
maxTime = 0

# for a given time what velocities will pass through the target at that time
yTimeToCount = {}

for velocity in range(-150, 150):
    yv = velocity
    y = 0
    time = 0
    while(y > coords[1][0]):
        y += yv
        yv -= 1
        time += 1
        if y <= coords[1][1] and y >= coords[1][0]:
            if not time in yTimeToCount:
                yTimeToCount[time] = set()
            yTimeToCount[time].add(velocity)
            if velocity > maxI:
                maxI = velocity
            if time > maxTime:
                maxTime = time

print(triangle(maxI))

# for a given time what velocities will pass through the target at that time or later
yTimeToCumulativeCount = {}
total = set()
for time in range(maxTime, 0, -1):
    if time in yTimeToCount:
        total = total.union(yTimeToCount[time])
    yTimeToCumulativeCount[time] = total

total = 0
for velocity in range(0, coords[0][1]+1):
    xv = velocity
    x = 0
    time = 0
    possibleYVelocity = set()
    while (x < coords[0][1] and xv > 0):
        x += xv
        xv -= 1
        time += 1
        if x <= coords[0][1] and x >= coords[0][0]:
            if not xv == 0:
                possibleYVelocity = possibleYVelocity.union(yTimeToCount[time])
            else:
                possibleYVelocity = possibleYVelocity.union(yTimeToCumulativeCount[time])

    total += len(possibleYVelocity)
print(total)