package y2018

int depth = 8787L
int tX = 10L
int tY = 725L

buffer = 20

int[][] grid = generateGrid(tX + 1, tY + 1, depth, buffer)
println ((0..tX).sum{ x -> (0..tY).sum{ y -> grid[y][x] } })

int[][][] timeGrid = new int[tY+1+buffer][tX+1+buffer][3]
for (int i = 0; i <= tX+buffer; i++) {
	for (int j = 0; j <= tY+buffer; j++) {
		for (int k = 0; k < 3; k++) {
			timeGrid[j][i][k] = Integer.MAX_VALUE
		}
	}
}

Set<Long> current = new HashSet<Long>()
current.add(new State(0, 0, 0, 0).hash())
current.add(new State(0, 0, 1, 7).hash())

timeGrid[0][0][0] = 0
timeGrid[0][0][1] = 7

start = System.currentTimeMillis()

while (current.size() > 0) {
	Set<Long> newCurrent = new HashSet<Long>()
	current.forEach {hash ->
		State state = State.fromHash(hash)
		findValidCoord(grid, state).forEach { coord ->
			int newX = coord[0]
			int newY = coord[1]
			if (isAllowedIn(state.item, grid[newY][newX])) {
				State newStateA = new State(newX, newY, state.item, state.time + 1)
				State newStateB = new State(newX, newY, changeItem(state.item, grid[newY][newX]), state.time + 8)
				if (newStateA.time < timeGrid[newY][newX][newStateA.item]) {
					newCurrent.add(newStateA.hash())
					timeGrid[newY][newX][newStateA.item] = newStateA.time
				}
				if (newStateB.time + 1 < timeGrid[newY][newX][newStateB.item]) {
					newCurrent.add(newStateB.hash())
					timeGrid[newY][newX][newStateB.item] = newStateB.time
				}
			}
		}
	}
	current = newCurrent
}

println(System.currentTimeMillis() - start)
println timeGrid[tY][tX][0]



private static List<Tuple> findValidCoord(grid, state) {
	List<Tuple> result = []
	for (int x = -1; x < 2; x++) {
		for (int y = -1; y < 2; y++) {
			if (Math.abs(x) + Math.abs(y) == 1 && state.x + x >= 0 && state.y + y >= 0 && state.y + y < grid.Length && state.x + x < grid[0].Length) {
				result.add(new Tuple(state.x + x, state.y + y))
			}
		}
	}
	return result
}

public static boolean isAllowedIn(int item, int type) {
	return (item + 1) % 3 != type
}

public static int newItem(int curType, int newType) {
	return (5 - (curType + newType))%3
}

public static int changeItem(int item, int type) {
	if (type == 0) {
		return item == 0 ? 1 : 0
	}
	if (type == 1) {
		return item == 1 ? 2 : 1
	}
	if (type == 2) {
		return item == 0 ? 2 : 0
	}
}

//t = 0 c = 1 n = 2

//In 0 rocky regions, c or t
//In 1 wet regions, c or n
//In 2 narrow regions, t or n


class State {
	int x
	int y
	int item //t = 0 c = 1 n =2
	int time

	State(int x, int y, int item, int time) {
		this.x = x
		this.y = y
		this.item = item
		this.time = time
	}

	static State fromHash(Long hash) {
		int time = (int)(hash % 10000)
		hash /= 10000
		int item = (int)(hash % 100)
		hash /= 100
		int y = (int)(hash % 1000)
		hash /= 1000
		int x = (int)(hash % 1000)
		return new State(x, y, item, time)
	}

	Long hash() {
		return (((long)x * 1000L + (long)y) * 100L + (long)item) * 10000L + (long)time
	}
}


int[][] generateGrid(int x, int y, int depth, int buffer) {
	Long mod = 20183L
	int[][] grid = new int[y+buffer][x+buffer]
	grid[0][0] = (int)(depth % mod)
	for (int j = 1; j < y+buffer; j++) {
		grid[j][0] = (int)((j * 48271L + depth) % mod)
	}
	for (int i = 1; i < x+buffer; i++) {
		grid[0][i] = (int)((i * 16807L + depth) % mod)

		for (int j = 1; j < y+buffer; j++) {
			grid[j][i] = (int)((grid[j-1][i] * grid[j][i-1] + depth) % mod)
		}
	}
	grid[(int)y-1][(int)x-1] = (int)(depth % mod)
	for (int i = 0; i < x+buffer; i++) {
		for (int j = 0; j < y+buffer; j++) {
			grid[j][i] = grid[j][i] % 3
		}
	}

	return grid
}

//14911
////1029