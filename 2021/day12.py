from FileReader import FileReader


class MapNode:
    def __init__(self, name, links):
        self.isBig = name.isupper()
        self.links = links
        self.name = name


def addToMultimap(multimap, key, value):
    if key not in multimap:
        multimap[key] = []
    multimap[key].append(value)


def findRoutes(mapNode, map, visited):
    if mapNode.name == 'end':
        return 1
    total = 0
    for route in mapNode.links:
        if map[route].isBig or not route in visited:
            total += findRoutes(map[route], map, visited.union({route}))
    return total

def findRoutesDoubleVisitOneSmall(mapNode, map, visited, doubled):
    if mapNode.name == 'end':
        return 1
    total = 0
    for route in mapNode.links:
        if map[route].isBig or not route in visited:
            total += findRoutesDoubleVisitOneSmall(map[route], map, visited.union({route}), doubled)
        if not map[route].isBig and route in visited and doubled == None and not route == 'start':
            total += findRoutesDoubleVisitOneSmall(map[route], map, visited, route)
    return total

lines = FileReader().readLines('12')
map = {}
for line in lines:
    a, b = line.split("-")
    addToMultimap(map, a, b)
    addToMultimap(map, b, a)

for key in map.keys():
    map[key] = MapNode(key, map[key])

print(findRoutes(map['start'], map, {'start'}))
print(findRoutesDoubleVisitOneSmall(map['start'], map, {'start'}, None))

