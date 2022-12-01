from FileReader import FileReader


addX = [14, 13, 15, 13, -2, 10, 13, -15, 11, -9, -9, -7, -4, -6]


class Instruction:
    def __init__(self, character):
        if len(character) > 1 or ord(character)-119 < 0:
            self.isValue = True
            self.value = int(character)
        else:
            self.isValue = False
            self.r = ord(character) - 119

    def __repr__(self):
        return str(self.value) if self.isValue else chr(self.r + 119)

    def read(self, registers):
        return self.value if self.isValue else registers[self.r]


def readFile():
    lines = FileReader().readLines('24')
    results = []
    for line in lines:
        result = []
        split = line.split(" ")
        result.append(split[0])
        for character in split[1:]:
            result.append(Instruction(character))
        results.append(result)
    return results


def executeSection(lines, section, x, z):
    registers = [0, 0, 0, z]


    for l in lines[section * 18:(section + 1) * 18]:
        if l[0] == "inp":
            registers[l[1].r] = x
        if l[0] == "add":
            registers[l[1].r] += l[2].read(registers)
        if l[0] == "mul":
            registers[l[1].r] *= l[2].read(registers)
        if l[0] == "div":
            if l[2].read(registers) > 0:
                registers[l[1].r] = int(registers[l[1].r] / l[2].read(registers))
        if l[0] == "mod":
            if registers[l[1].r] >= 0 and l[2].read(registers) > 0:
                registers[l[1].r] %= l[2].read(registers)
        if l[0] == "eql":
            registers[l[1].r] = 1 if registers[l[1].r] == l[2].read(registers) else 0
    return registers[3]


def recurse(lines, addX, z, depth, inst):
    if depth == 14:
        if z == 0:
            print(inst)
        return

    if addX[depth] < 0:
        x = (z + addX[depth]) % 26
        if 0 < x < 10:
            newZ = executeSection(lines, depth, x, z)
            recurse(lines, addX, newZ, depth + 1, inst + [x])
    else:
        for x in range(1, 10):
            newZ = executeSection(lines, depth, x, z)
            recurse(lines, addX, newZ, depth + 1, inst + [x])
lines = readFile()


recurse(lines, addX, 0, 0, [])





#
# resultAfter8 = 0
# for i in range(0, 8):
#     resultAfter8 = executeSection(lines, i, 9, resultAfter8)
#
# print(resultAfter8)



# for i in range(0, 1):
#     print()
#     print()
#     for z in range(12, 13):
#         for x in range(1, 10):
#             print()
#             result = executeSection(lines, 0, x, z)
#             print(str(x) +" "+ str(z) +" " + str(result))





# validResult = set([0])
#
# resultAfter8 = 0
# for i in range(100000, 200000):
#     if i % 10000 == 0:
#         print(i)
#     result = resultAfter8
#     if containsZero(i):
#         continue
#     z = i
#     for j in range(0, 5):
#         # print(z % 10)
#         result = executeSection(lines, j, z % 10, result)
#         z = int(z / 10)
#     # print(result)
#     validResult.add(result)
#
# print(len(validResult))
# print(min(validResult))
# print(max(validResult))

# validResult = set([0])
# for i in range(13, 11, -1):
#     currentValid = []
#     for z in range(0, 10000):
#         # x = z + addX[i] % 26
#         # for x in range(1, 10):
#         x = (z + addX[i]) % 26
#         if 0 < x < 10:
#             result = executeSection(lines, i, x, z)
#             if result in validResult:
#                 currentValid.append(z)
#                 # print()
#                 print(str(x) + "  " + str(z) + " " + str(z % 26) + " " + str(result))
#     validResult = set(currentValid)
#     print(currentValid)

# validResult = set([0])
# for i in range(1, 2, ):
#     # currentValid = []
#     for z in range(1, 10):
#         for x in range(1, 10):
#             result = executeSection(lines, i, x, z)
#             # if result in validResult:
#             #     currentValid.append(z)
#             print(str(x) + "  " + str(z) + " " + str(result))
    # validResult = set(currentValid)
    # print(currentValid)

        # inputIndex = section
        # registers = [0, 0, 0, z]
        # input = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, x, x]
        # for l in lines:
        #     if l[0] == "inp":
        #         # print(l)
        #         # print(registers[2])
        #         # print(registers)
        #         registers[l[1].r] = input[inputIndex]
        #         inputIndex += 1
        #     if l[0] == "add":
        #         registers[l[1].r] += l[2].read(registers)
        #     if l[0] == "mul":
        #         registers[l[1].r] *= l[2].read(registers)
        #     if l[0] == "div":
        #         if l[2].read(registers) > 0:
        #             registers[l[1].r] = int(registers[l[1].r]/l[2].read(registers))
        #     if l[0] == "mod":
        #         if registers[l[1].r] >= 0 and l[2].read(registers) > 0:
        #             registers[l[1].r] %= l[2].read(registers)
        #     if l[0] == "eql":
        #         registers[l[1].r] = 1 if registers[l[1].r] == l[2].read(registers) else 0
        # if (registers[3] > 6 and registers[3] < 16 ):
        #     print(str(x) +"  "+ str(z) + " "+ str(registers[3]))



