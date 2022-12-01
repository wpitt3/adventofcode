int input = 50000000

// input = 50

List recipes = [3, 7]

int x = 0
int y = 1	

while (recipes.size() < input + 10) {
	int z = recipes[x] + recipes[y]
	if ( z > 9) {
		recipes.add(1)
	}
	recipes.add(z%10)
	x = (x + recipes[x] + 1) % recipes.size()
	y = (y + recipes[y] + 1) % recipes.size()
}

// println recipes

println recipes.join('').contains('306281')
String split = recipes.join('').split('306281')[0]
println split.length()

// println recipes.drop(input).take(10).join('')