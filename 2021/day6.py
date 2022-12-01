from FileReader import FileReader

start_fish = FileReader().readLineAsInts('6')

fishes = [10]
fish_count = []
for i in range(100):
    count = 0
    for x in range(len(fishes)):
        fishes[x] -= 1
        if fishes[x] == -1:
            fishes[x] += 7
            count += 1
    for x in range(count):
        fishes.append(8)
    fish_count.append(len(fishes))

fishes_2 = [1]*9
for i in range(9, 265):
    fishes_2.append(fishes_2[i-7] + fishes_2[i-9])

total = 0
for fish in start_fish:
    total += fishes_2[88-fish]

print(total)

total = 0
for fish in start_fish:
    total += fishes_2[264-fish]

print(total)
