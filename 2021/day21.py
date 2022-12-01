# 4 8 / 7 5

# {3: 1, 4: 2, 5: 3, 6: 2, 7: 1}

class State:
    def __init__(self, count, score, index):
        self.count = count
        self.score = score
        self.index = index

    def __repr__(self):
        return str(self.count) + " " + str(self.score) + " " + str(self.index)


def partA():
  a_score = 0
  a_space = 7
  b_score = 0
  b_space = 5

  d = 1

  p = 0
  rolls = 0
  while (a_score < 1000 and b_score < 1000):
    roll = 0
    for i in range(3):
      rolls += 1
      roll += d
      d = (d - 1) % 100 + 2
    if p == 0:
      a_space = (a_space + roll - 1) % 10 + 1
      a_score += a_space
    else:
      b_space = (b_space + roll - 1) % 10 + 1
      b_score += b_space

    p = (p + 1) % 2

  print((a_score if a_score < b_score else b_score) * rolls)


def findDistributions(start_pos):
    timeToScore = [0,0,0,0,0,0,0,0,0,0 ]
    die_dist = {3: 1, 4: 3, 5: 6, 6: 7, 7: 6, 8: 3, 9: 1}
    keyToState = {start_pos: State(1, 0, start_pos)}
    for x in range(10):
        newKeyToState = {}
        for key in keyToState:
            state = keyToState[key]
            for die in die_dist:
                newIndex = (state.index + die - 1) % 10 + 1
                if (state.score + newIndex < 21):
                    newState = State(state.count*die_dist[die], state.score + newIndex, newIndex)
                    newKey = state.score * 20 + newIndex
                    if newKey in newKeyToState:
                        newKeyToState[newKey].count += newState.count
                    else:
                        newKeyToState[newKey] = newState
                else:
                    timeToScore[x] += state.count*die_dist[die]
        keyToState = newKeyToState

    return timeToScore

partA()

a = findDistributions(7)
b = findDistributions(5)

a_score = 0
b_score = 0
a_remaining = 1
b_remaining = 1
for x in range(10):
    a_remaining *= 27
    a_remaining -= a[x]
    a_score += a[x] * b_remaining
    b_remaining *= 27
    b_remaining -= b[x]
    b_score += b[x] * a_remaining


print(a_score if a_score > b_score else b_score)
