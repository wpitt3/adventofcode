from FileReader import FileReader


crabs = FileReader().readLineAsInts('7')

min = float('inf')
done = False
i = 0
while i < 1000 and not done:
    total_fuel = 0
    for crab in crabs:
        total_fuel += abs(i - crab)

    if total_fuel < min:
        min = total_fuel
    else:
        done = True
    i += 1
print(min)

min = float('inf')
done = False
i = 0
while i < 1000 and not done:
    total_fuel = 0
    for crab in crabs:
        x = abs(i - crab)
        total_fuel += x * (x+1)/2

    if total_fuel < min:
        min = total_fuel
    else:
        done = True
    i += 1
print(min)