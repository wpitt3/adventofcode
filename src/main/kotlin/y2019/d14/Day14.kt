import java.io.File

fun main (){
    val lines = File("src/main/resources/y2019d14.txt").readLines()
    val formulas = Day14().readLines(lines)
    println(Day14().countOreForFuel(formulas, 1))
    val maxOre = 1000000000000

    var index: Long = 10000000
    var step: Long = index/2
    (0..40).forEach{
        if(Day14().countOreForFuel(formulas, index) > maxOre) {
            index -= step
        } else {
            step /= 2
            index += step
        }
    }
    println(index)
}

class Day14() {
    fun readLines(lines: List<String>): Map<Ingredient, List<Ingredient>> {
        var x: MutableMap<Ingredient, List<Ingredient>> = mutableMapOf()
        lines.map{ readLine(it)}.forEach({ x.putAll(it) })
        return x.toMap()
    }

    fun readLine(line: String): Map<Ingredient, List<Ingredient>> {
        var formula = line.split("=>")
        var result = formula[1].trim().split(" ")
        var ingredients = formula[0].trim().split(",")
            .map{
                val parts = it.trim().split(" ");
                Ingredient(Integer.parseInt(parts[0]).toLong(), parts[1])
            }
        return mapOf(Pair(Ingredient(Integer.parseInt(result[0]).toLong(), result[1]), ingredients))
    }

    fun countOreForFuel(formulas: Map<Ingredient, List<Ingredient>>, fuelCount: Long): Long {
        var ingredients: Wrapper = getIngredients(formulas, Ingredient(fuelCount, "FUEL"), listOf())

        return resolveUntilComplete(formulas, ingredients).required.map{it.count}.sum()
    }

    fun resolveUntilComplete(formulas: Map<Ingredient, List<Ingredient>>, ingredientsX: Wrapper): Wrapper {
        var ingredients = ingredientsX
        var prevIngredients: Wrapper = Wrapper(listOf(), listOf())
        while(ingredients.required.any{it.name != "ORE"} && prevIngredients.required != ingredients.required) {
            prevIngredients = ingredients
            ingredients = getIngredientsAndFlatten(formulas, ingredients)
        }
        return ingredients
    }

    fun getIngredientsAndFlatten(formulas: Map<Ingredient, List<Ingredient>>, ingredients: Wrapper): Wrapper {
        var required: MutableList<Ingredient> = ingredients.required.toMutableList()
        var spare: MutableList<Ingredient> = ingredients.spare.toMutableList()

        ingredients.required.filter{ it.name != "ORE" }.forEach { product ->
            val result: Wrapper = getIngredients(formulas, product, spare)
            required = removeIngredient(required, product)
            result.required.forEach{
                required = addIngredient(required, it)
            }
            spare = reduceList(result.spare.toMutableList())
        }
        return Wrapper(required.toList(), spare.toList())
    }

    fun getIngredients(formulas: Map<Ingredient, List<Ingredient>>, ingredient: Ingredient, spare: List<Ingredient>): Wrapper {
        if (ingredient.name == "ORE")
            return Wrapper(listOf(ingredient), listOf())

        val formulaKey = getIngredientForName(formulas, ingredient.name)
        val ingredients: List<Ingredient> = getIngredientsForName(formulas, ingredient.name)
        val spareIngredientCount = spare.filter{it.name == formulaKey.name}.map{it.count}.getOrElse(0, {0})
        val multiplier: Long = (Math.ceil((ingredient.count.toDouble()-spareIngredientCount)/formulaKey.count.toDouble())).toLong()
        if ((ingredient.count-spareIngredientCount) % formulaKey.count == 0.toLong()) {
            return Wrapper(ingredients.map{ Ingredient(it.count*multiplier, it.name)}, spare + Ingredient(-spareIngredientCount, formulaKey.name))
        }
        return Wrapper(ingredients.map{ Ingredient(it.count*multiplier, it.name)},
            spare + Ingredient(formulaKey.count*multiplier-ingredient.count, formulaKey.name))
    }

    fun removeIngredient(list: List<Ingredient>, ingredient: Ingredient): MutableList<Ingredient> {
        return reduceList(list.toMutableList() + minusIngredient(ingredient))
    }

    fun addIngredient(list: MutableList<Ingredient>, ingredient: Ingredient): MutableList<Ingredient> {
        return reduceList(list.toMutableList() + ingredient)
    }

    fun minusIngredient(ingredient: Ingredient): Ingredient {
        return Ingredient(-ingredient.count, ingredient.name)
    }

    fun reduceList(list: List<Ingredient>): MutableList<Ingredient> {
        return list
            .groupBy { it.name }
            .map{x -> Ingredient(x.value.map{it.count}.sum(), x.key)}
            .filter{ it.count != 0.toLong() }
            .toMutableList()
    }

    fun getIngredientsForName(ingredients: Map<Ingredient, List<Ingredient>>, ingredientName: String): List<Ingredient> {
        return ingredients.get(getIngredientForName(ingredients, ingredientName)) !!
    }

    fun getIngredientForName(ingredients: Map<Ingredient, List<Ingredient>>, ingredientName: String): Ingredient {
        if (ingredients.keys.filter{ it.name.equals(ingredientName) }.count() > 1) {
            throw RuntimeException("Not implemented")
        }
        return ingredients.keys.filter{ it.name.equals(ingredientName) }.first()
    }
}

class Wrapper(var requiredX: List<Ingredient>, var spareX: List<Ingredient>) {
    var required: List<Ingredient>
    var spare: List<Ingredient>

    init{
        this.required = requiredX
        this.spare = spareX
    }
}

data class Ingredient(var countX: Long, var nameX: String) {
    var count: Long
    var name: String

    init{
        this.count = countX
        this.name = nameX
    }

    override fun toString(): String {
        return "$count $name"
    }
}
