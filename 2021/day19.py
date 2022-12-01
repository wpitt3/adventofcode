from FileReader import FileReader


class ScannerRelation:
    def __init__(self, sa, sb, rotation):
        self.sa = sa
        self.sb = sb
        self.rotation = rotation
        self.direction = [1, 1, 1]
        self.offset = [0, 0, 0]
        self.bpairs = []

    def __repr__(self):
        return str(self.sa) + " " + str(self.sb) + " " + str(self.rotation) + " " + str(self.direction) + " " + str(self.offset) + " " + str(self.bpairs)


def readScanners():
    lines = FileReader().readLines('19')

    scannerIndex = 0
    index = 1
    scanners = [[]]
    while index < len(lines):
        if lines[index] == "":
            scannerIndex += 1
            index += 1
            scanners.append([])
        else:
            scanners[scannerIndex].append([int(i) for i in lines[index].split(',')])
        index += 1
    return scanners


def rotate(input, r):
    x = [[0, 1, 2], [0, 2, 1], [1, 0, 2], [1, 2, 0], [2, 0, 1], [2, 1, 0]]
    return [input[x[r][0]], input[x[r][1]], input[x[r][2]]]


def iRotate(input, r):
    x = [[0, 1, 2], [0, 2, 1], [1, 0, 2], [2, 0, 1], [1, 2, 0], [2, 1, 0]]
    return [input[x[r][0]], input[x[r][1]], input[x[r][2]]]


def calculateOrientations(scanners):
    a = 2000 * 2000
    b = 2000
    c = 1
    o = [[a, b, c], [a, c, b], [b, a, c], [b, c, a], [c, a, b], [c, b, a]]
    orientations = twoDArray(len(scanners))
    allOrientations = threeDArray(len(scanners), 6)
    distances = twoDArray(len(scanners))
    for i in range(0, len(scanners)):
        for j in range(0, len(scanners[i])):
            distances[i].append([])
            orientations[i].append([])
            for k in range(0, len(scanners[i])):
                if k != j:
                    distances[i][j].append([abs(scanners[i][j][l] - scanners[i][k][l]) for l in range(0, 3)])
            for l in range(0, 6):
                orientations[i][j].append([])
                for k in range(0, len(scanners[i]) - 1):
                    orientations[i][j][l].append(
                        o[l][0] * distances[i][j][k][0] + o[l][1] * distances[i][j][k][1] + o[l][2] *
                        distances[i][j][k][2])
                allOrientations[i][l].extend(orientations[i][j][l])
                orientations[i][j][l] = set(orientations[i][j][l])
        for l in range(0, 6):
            allOrientations[i][l] = set(allOrientations[i][l])

    results = []
    for i in range(0, len(orientations) - 1):
        for j in range(i + 1, len(orientations)):
            for k in range(0, 6):
                if len(allOrientations[j][0].intersection(allOrientations[i][k])) > 20:
                    result = ScannerRelation(i, j, k)

                    for l in range(0, len(orientations[i])):
                        for m in range(0, len(orientations[j])):
                            if len(orientations[i][l][k].intersection(orientations[j][m][0])) > 10:
                                result.bpairs.append([l, m])
                    results.append(result)
    return results


def calculateDAndO(relations, scanners):
    for rel in relations:
        sa = scanners[rel.sa]
        sb = scanners[rel.sb]
        rot = rel.rotation
        bpairs = rel.bpairs
        for i in range(0, 3):
            if sa[bpairs[0][0]][i] - rotate(sb[bpairs[0][1]], rot)[i] == \
                  sa[bpairs[1][0]][i] - rotate(sb[bpairs[1][1]], rot)[i]:
                rel.direction[i] *= -1
            rel.offset[i] = sa[bpairs[0][0]][i] + rel.direction[i] * rotate(sb[bpairs[0][1]], rot)[i]
        # rel.bpairs = []


def twoDArray(i):
    return [[] for _ in range(i)]


def threeDArray(i, j):
    return [[[] for _ in range(j)] for _ in range(i)]


def bToA(r, scanners):
    newPairs = []
    for s in scanners:
        newPairs.append([r.offset[j] + -r.direction[j] * rotate(s, r.rotation)[j] for j in range(0, 3)])
    return newPairs


def aToB(r, scanners):
    newPairs = []
    for s in scanners:
        newPairs.append(iRotate([-r.direction[j] * (s[j] - r.offset[j]) for j in range(0, 3)], r.rotation))
    return newPairs


if __name__ == "__main__":
    scanners = readScanners()

    results = calculateOrientations(scanners)
    calculateDAndO(results, scanners)

    saToSb = {}
    sbToSa = {}

    for result in results:
        if not result.sa in saToSb:
            saToSb[result.sa] = []
        if not result.sb in sbToSa:
            sbToSa[result.sb] = []
        saToSb[result.sa].append(result.sb)
        sbToSa[result.sb].append(result.sa)

    order = []
    seen = set([])
    seen.add(0)
    current = set([0])
    while len(current) > 0:
        newCurrent = set([])
        for c in current:
            if c in saToSb:
                newCurrent = newCurrent.union(saToSb[c])
            if c in sbToSa:
                newCurrent = newCurrent.union(sbToSa[c])
        current = newCurrent.difference(seen)
        order.append(list(current))
        seen = seen.union(current)

    scannerLocs = [[[0,0,0]] for x in range(0, len(scanners))]

    seen = set([])
    for x in range(len(order)-2, -1, -1):
        for scannerId in order[x]:
            for result in results:
                if scannerId not in seen:
                    if result.sa == scannerId and not result.sb in seen:
                        scanners[result.sb].extend(aToB(result, scanners[result.sa]))
                        scannerLocs[result.sb].extend(aToB(result, scannerLocs[result.sa]))

                        seen.add(scannerId)
                    if result.sb == scannerId and not result.sa in seen:
                        scanners[result.sa].extend(bToA(result, scanners[result.sb]))
                        scannerLocs[result.sa].extend(bToA(result, scannerLocs[result.sb]))
                        seen.add(scannerId)

    max = 0
    for i in range(0, len(scannerLocs)-1):
        for j in range(i+1, len(scannerLocs)):
            score = sum([abs(scannerLocs[0][i][x] - scannerLocs[0][j][x]) for x in range(0, 3)])
            if score > max:
                max = score


    # > 4468

    print(len(set([x[0]*5000*5000 + x[1]*5000 + x[0] for x in scanners[0]])))
    print(max)