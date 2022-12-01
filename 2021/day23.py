
# can't stop outside room
# can't move int non home  (this is always the optimal move)
# can't move in hallway, must stick to spot

# Amphipods 1-8
# pos:
#   hallway 0-6
#   home 7-22

# 01 2 3 4 56
#   7 8 9 a
# 7
# 11
# 15
# 19


def printState(state):
    print()
    hallway = "."
    for i in range(0, 7):
        if i > 1 and i < 6:
            hallway += " "
        hallway += chr(state[i]+64) if state[i] else " "
    hallway += "."
    print(hallway)
    for j in range(0, 4):
        rooms = "..."
        for i in range(0, 4):
            index = i + 7 + 4 * j
            rooms += chr(state[index]+64) if state[index] else " "
            rooms += "."
        rooms += ".."
        print(rooms)


def posToXY(pos):
    if pos < 7:
        return min(max(0,pos + pos-1), 10), 0
    else:
        return (pos-3) % 4 * 2 + 2, int((pos-3)/4)


def calculateCost(fromPos, toPos, type):
    fx, fy = posToXY(fromPos)
    tx, ty = posToXY(toPos)
    cost = abs(fx - tx) + fy + ty
    for i  in range(type-1):
        cost *= 10
    return cost


def performMove(state, cost, fromPos, toPos):
    newState = state.copy()
    newState[toPos] = newState[fromPos]
    newState[fromPos] = 0
    return newState, cost + calculateCost(fromPos, toPos, newState[toPos])


def topOfHome(state, type):
    for i in range(3, -1, -1):
        if state[i * 4 + type + 6] == 0:
            return i * 4 + type + 6
    return None


def findAvailableMoves(state, fromPos):
    result = []
    if fromPos < 7:
        type = state[fromPos]
        if fromPos < type:
            for i in range(fromPos+1, type + 1):
                if state[i] != 0:
                    return []
        if fromPos > type + 1:
            for i in range(fromPos-1, type, -1):
                if state[i] != 0:
                    return []
        # if home contains wrong type
        for i in range(4):
            if state[i * 4 + type + 6] != 0 and state[i * 4 + type + 6] != type:
                return []
        # find top of home
        toh = topOfHome(state, type)
        if not toh == None:
            return [toh]
    else:
        # is top of stack
        for i in range(fromPos - 4, 6, -4):
            if state[i] != 0:
                return []

        type = state[fromPos]
        isHomeValid = True
        for i in range(4):
            if state[i * 4 + type + 6] != 0 and state[i * 4 + type + 6] != type:
                isHomeValid = False

        column = (fromPos - 7) % 4
        if isHomeValid:
            d = -1 if column > type else 1
            o = 0 if column > type else 1
            if all([state[x] == 0 for x in range(column + 1 + o, type + o, d)]):
                for i in range(3, -1, -1):
                    if state[i * 4 + type + 6] == 0:
                        return [i * 4 + type + 6]

        i = column + 1
        while i > -1:
            if state[i] == 0:
                result.append(i)
            else:
                i -= 10
            i -= 1
        i = column + 2
        while i < 7:
            if state[i] == 0:
                result.append(i)
            else:
                i += 10
            i += 1
    return result


def findMovable(state):
    result = []
    for i in range(7):
        if state[i] != 0:
            result.append(i)
    for i in range(4):
        j = 7 + i
        while j < 23:
            if state[j] != 0:
                # ensure column is not in correct place
                k = j
                while k < 23 and state[k] == i+1:
                    k += 4
                if k < 23:
                    result.append(j)
                j += 23
            j += 4
    return result


def findAllMoves(state):
    allMoves = []
    moveable = findMovable(state)
    for i in moveable:
        moves = findAvailableMoves(state, i)
        for move in moves:
            if move > 7:
                return [[i, move]]
            allMoves.append([i, move])
    return allMoves


def hashState(state):
    hash = 0
    for i in state:
        hash *= 4
        hash += i
    return hash


def recursiceDoMoves(state, cost, depth):
    if depth > 8:
        # if cost == 15683:

        # print(cost)
        return 1

    count = 0
    moves = findAllMoves(state)
    # if len(moves) == 0:
    #     printState(state)
    for move in moves:
        nState, nCost = performMove(state, cost, move[0], move[1])
        count += recursiceDoMoves(nState, nCost, depth + 1)

    return count


state = [0 for _ in range(7)]
state.extend([2, 3, 1, 2, 3, 4, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0])

cost = 0
state, cost = performMove(state, cost, 9, 1)
state, cost = performMove(state, cost, 10, 2)
state, cost = performMove(state, cost, 14, 5)
state, cost = performMove(state, cost, 13, 14)
state, cost = performMove(state, cost, 8, 13)
state, cost = performMove(state, cost, 12, 10)
state, cost = performMove(state, cost, 2, 12)
state, cost = performMove(state, cost, 7, 8)
state, cost = performMove(state, cost, 11, 9)
state, cost = performMove(state, cost, 5, 11)
state, cost = performMove(state, cost, 1, 7)
print(cost)


# state = [0 for _ in range(7)]
# state.extend([2, 3, 1, 2, 4, 3, 2, 1, 4, 2, 1, 3, 3, 4, 4, 1])
# cost = 0
# printState(state)

#0-6
#7-10
#11-14
#15-18
#19-22

state = [0 for _ in range(7)]
state.extend([2, 3, 1, 2, 4, 3, 2, 1, 4, 2, 1, 3, 3, 4, 4, 1])

printState(state)


# cost = 0
# moves = findAllMoves(state)
# for move in moves:
#     nState, nCost = performMove(state, cost, move[0], move[1])
#     print(nCost)
#     printState(nState)
#
# print()


cost = 0
seen = set([])
seen.add(hashState(state))
current = {}
current[hashState(state)] = (state, 0)
i = 0
while len(current) > 0:
    newCurrent = {}
    for c in current:
        for move in findAllMoves(current[c][0]):
            nState, nCost = performMove(current[c][0], current[c][1], move[0], move[1])
            if not hashState(nState) in seen:
                newCurrent[hashState(nState)] = (nState, nCost)
            # printState(nState)

    current = newCurrent
    for c in current:
        if len(current) < 20:
            # printState(current[c][0])
            cost = current[c][1]
        seen.add(hashState(current[c][0]))
    i += 1

print(cost)



cost = 0
state, cost = performMove(state, cost, 8, 6)
state, cost = performMove(state, cost, 12, 5)
state, cost = performMove(state, cost, 9, 0)
state, cost = performMove(state, cost, 13, 4)
state, cost = performMove(state, cost, 17, 1)
state, cost = performMove(state, cost, 16, 3)
state, cost = performMove(state, cost, 20, 2)
state, cost = performMove(state, cost, 3, 20)
state, cost = performMove(state, cost, 4, 16)
state, cost = performMove(state, cost, 10, 12)
state, cost = performMove(state, cost, 21, 3)
state, cost = performMove(state, cost, 5, 21)
state, cost = performMove(state, cost, 6, 17)
state, cost = performMove(state, cost, 14, 6)
state, cost = performMove(state, cost, 18, 13)
state, cost = performMove(state, cost, 22, 5)
state, cost = performMove(state, cost, 3, 22)
state, cost = performMove(state, cost, 2, 18)
state, cost = performMove(state, cost, 7, 8)
state, cost = performMove(state, cost, 11, 14)
state, cost = performMove(state, cost, 15, 10)
state, cost = performMove(state, cost, 19, 9)
state, cost = performMove(state, cost, 1, 19)
state, cost = performMove(state, cost, 0, 15)
state, cost = performMove(state, cost, 5, 11)
state, cost = performMove(state, cost, 6, 7)
# printState(state)
# printState(state)
# print(findAllMoves(state))



print(cost)

