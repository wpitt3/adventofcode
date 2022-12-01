from FileReader import FileReader


def to_char_set(original):
    return set([char for char in original])


lines = FileReader().readLines('8')

freq = [0] * 10
for line in lines:
    output = line.split('|')[1].strip()
    output_numbers = output.split(' ')
    for i in output_numbers:
        freq[len(i)] += 1

print(freq[2] + freq[3] + freq[4] + freq[7])

total = 0
for line in lines:
    all = line.replace(' | ', ' ')
    output_numbers = all.split(' ')
    output_numbers = list(set([''.join(sorted(x)) for x in output_numbers]))
    mappings = {}
    countToCode = {}
    for x in output_numbers:
        if not len(x) in countToCode:
            countToCode[len(x)] = []
        countToCode[len(x)].append(x)
    cf = to_char_set(countToCode[2][0])
    bd = to_char_set(countToCode[4][0]).difference(cf)
    mappings[1] = to_char_set(countToCode[2][0])
    mappings[4] = to_char_set(countToCode[4][0])
    mappings[7] = to_char_set(countToCode[3][0])
    mappings[8] = to_char_set(countToCode[7][0])
    for x in countToCode[5]:
        if len(to_char_set(x).intersection(cf)) == 2:
            mappings[3] = to_char_set(x)
        elif len(to_char_set(x).intersection(bd)) == 2:
            mappings[5] = to_char_set(x)
        else:
            mappings[2] = to_char_set(x)
    for x in countToCode[6]:
        if len(to_char_set(x).intersection(cf)) == 2:
            if len(to_char_set(x).intersection(bd)) == 2:
                mappings[9] = to_char_set(x)
            else:
                mappings[0] = to_char_set(x)
        else:
            mappings[6] = to_char_set(x)

    output = line.split('|')[1].strip()
    output_numbers = output.split(' ')
    result = ''
    for x in output_numbers:
        x_set = to_char_set(x)
        for key in mappings:
            if mappings[key] == x_set:
                result += str(key)
    total += int(result)
print(total)
