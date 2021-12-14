from FileReader import FileReader
import math

lines = FileReader().readLines('10')

points_1 = {')': 3, ']': 57, '}': 1197, '>': 25137}
points_2 = {')': 1, ']': 2, '}': 3, '>': 4}
mappedBrackets = {'(': ')', '[': ']', '{': '}', '<': '>'}


def find_non_matching_bracket(line):
    stack = []
    for bracket in [char for char in line]:
        if bracket in mappedBrackets:
            stack.append(mappedBrackets[bracket])
        else:
            closingBracket = stack.pop()
            if not closingBracket == bracket:
                return bracket, None
    return None, stack


total_1 = 0
scores = []
for line in lines:
    result, stack = find_non_matching_bracket(line)
    if result is not None:
        total_1 += points_1[result]
    else:
        total = 0
        while len(stack) > 0:
            total *= 5
            total += points_2[stack.pop()]
        scores.append(total)
print(total_1)
scores.sort()
print(scores[math.floor(len(scores) / 2)])
