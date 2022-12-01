from FileReader import FileReader


def count(image):
    x = 0
    for i in range(len(image)):
        for j in range(len(image[0])):
            if image[i][j]:
                x +=1
    return x


def blankImage(image):
    out = []
    for i in range(len(image)):
        out.append([])
        for j in range(len(image[0])):
            out[i].append(False)
    return out


def printImage(image):
    for i in range(len(image)):
        print("".join(['#' if x else '.' for x in image[i]]))


def readFile():
    lines = FileReader().readLines('20')
    algo = [x == '#' for x in lines[0]]
    image = [[] for i in range(len(lines) - 2)]
    for i in range(len(lines) - 2):
        image[i].extend([x == '#' for x in lines[i + 2]])
    return algo, image


def padImage(image, pad):
    for i in range(len(image)):
        x = [False for _ in range(pad)]
        x.extend(image[i])
        x.extend([False for _ in range(pad)])
        image[i] = x
    prefix = [[False for _ in range(len(image[0]))] for _ in range(pad)]
    suffix = [[False for _ in range(len(image[0]))] for _ in range(pad)]
    image.extend(suffix)
    prefix.extend(image)
    return prefix

algo, image = readFile()

image_size = len(image)
pad = 52

image = padImage(image, pad)

for i in range(50):
    new = blankImage(image)
    for y in range(1, len(image) - 1):
        for x in range(1, len(image) - 1):
            toPrint = ""
            for a in range(-1, 2):
                for b in range(-1, 2):
                    toPrint += '1' if image[y+a][x+b] else '0'
            new[y][x] = algo[int(toPrint, 2)]
    for y in range(len(image)):
        new[0][y] = new[1][1]
        new[y][0] = new[1][1]
        new[len(image)-1][y] = new[1][1]
        new[y][len(image)-1] = new[1][1]

    image = new
    if i == 1:
        print(count(image))
print(count(image))




