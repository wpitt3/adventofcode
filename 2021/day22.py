from FileReader import FileReader


def intersectBoxes(boxA, boxB, intersection):
    boxAScore = 0
    boxBScore = 0
    for i in range(3):
        for j in range(2):
            if intersection[i][j] == boxA[i][j]:
                boxAScore += 1
            if intersection[i][j] == boxB[i][j]:
                boxBScore += 1
    if boxAScore < boxBScore:
        result = [boxA]
        result.extend(deleteInterForBox(boxB, intersection))
        return result
    else:
        result = [boxB]
        result.extend(deleteInterForBox(boxA, intersection))
        return result


def deleteInterForBox(box, intersection):
    sections = [[], [], []]
    for i in range(3):
        if box[i][0] < intersection[i][0]:
            sections[i].append([box[i][0], intersection[i][0]-1])
        sections[i].append([intersection[i][0], intersection[i][1]])
        if intersection[i][1] < box[i][1]:
            sections[i].append([intersection[i][1]+1, box[i][1]])
    result = []
    for x in range(len(sections[0])):
        for y in range(len(sections[1])):
            for z in range(len(sections[2])):
                if any([sections[0][x][i] != intersection[0][i]
                        or sections[1][y][i] != intersection[1][i]
                        or sections[2][z][i] != intersection[2][i] for i in range(2)]):
                    result.append([sections[0][x], sections[1][y], sections[2][z]])

    count = 0
    for r in result:
        count += size(r)
    return result


def calculateBoxIntersection(boxA, boxB):
    intersection = []
    for i in range(3):
        maxFrom = max(boxA[i][0], boxB[i][0])
        minTo = min(boxA[i][1], boxB[i][1])
        if maxFrom > minTo:
            return
        intersection.append([maxFrom, minTo])
    return intersection


def size(box):
    size = 1
    for i in range(3):
        size *= abs(box[i][0] - box[i][1]) + 1
    return size


def deleteBoxFromPuzzle(newBox, nonIntersectingBoxes):
    result = []
    for box in nonIntersectingBoxes:
        intersection = calculateBoxIntersection(newBox, box)
        if not intersection == None:
            result.extend(deleteInterForBox(box, intersection))
        else:
            result.append(box)
    return result


def addBoxToPuzzle(newBox, nonIntersectingBoxes):
    result = []
    hasInterSected = False
    for box in nonIntersectingBoxes:
        intersection = calculateBoxIntersection(newBox, box)
        if not intersection == None:
            if hasInterSected:
                result.extend(deleteInterForBox(box, intersection))
            else:
                hasInterSected = True
                result.extend(intersectBoxes(newBox, box, intersection))
        else:
            result.append(box)
    if not hasInterSected:
        result.append(newBox)
    return result


lines = FileReader().readLines('22')
boxs = []
isOn = []
for line in lines:
    isOn.append(line.split(" ")[0] == "on")
    boxs.append([[int(y) for y in x[2:].split("..")] for x in line.split(" ")[1].split(",")])

allBoxs = []

for i in range(0, 20):
    if isOn[i]:
        allBoxs = addBoxToPuzzle(boxs[i], allBoxs)
    else:
        allBoxs = deleteBoxFromPuzzle(boxs[i], allBoxs)

count = 0
for box in allBoxs:
    count += size(box)

print(count)

for i in range(20, len(boxs)):
    if isOn[i]:
        allBoxs = addBoxToPuzzle(boxs[i], allBoxs)
    else:
        allBoxs = deleteBoxFromPuzzle(boxs[i], allBoxs)


count = 0
for box in allBoxs:
    count += size(box)

print(count)