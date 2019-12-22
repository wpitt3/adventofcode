import java.io.File

fun main (){
    val lines = File("src/main/resources/y2019d14.txt").readLines()
    val ingredients = Day14().readLines(lines)
    println(Day14().countOreForFuel(ingredients))
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
                Ingredient(Integer.parseInt(parts[0]), parts[1])
            }
        return mapOf(Pair(Ingredient(Integer.parseInt(result[0]), result[1]), ingredients))
    }

    fun countOreForFuel(ingredientsList: Map<Ingredient, List<Ingredient>>): Int {
        var ingredients: List<Ingredient> = getIngredients(ingredientsList, Ingredient(1, "FUEL"), "")

//        ingredients = resolveUntilComplete(ingredientsList, ingredients)
//        ingredients = getIngredientsAndFlatten(ingredientsList, ingredients, "A")
        return recurse(ingredientsList, ingredients)
    }

    fun recurse(ingredientsList: Map<Ingredient, List<Ingredient>>, ingredientsx: List<Ingredient>): Int {
        var ingredients = resolveUntilComplete(ingredientsList, ingredientsx)
        if (ingredients.all{it.name == "ORE"}) {
            return ingredients.map{it.count}.sum()
        }

        return ingredients.filter{it.name != "ORE"}.map{
            val newIngredients = getIngredientsAndFlatten(ingredientsList, ingredients, it.name)
            recurse(ingredientsList, newIngredients)
        }.min() !!
    }

    fun resolveUntilComplete(ingredientsList: Map<Ingredient, List<Ingredient>>, ingredientsX: List<Ingredient>): List<Ingredient> {
        var ingredients = ingredientsX
        var prevIngredients: List<Ingredient> = listOf()
        while(ingredients.any{it.name != "ORE"} && prevIngredients != ingredients) {
            prevIngredients = ingredients
            ingredients = getIngredientsAndFlatten(ingredientsList, ingredients, "")
        }
        return ingredients
    }

    fun getIngredientsAndFlatten(ingredientsList: Map<Ingredient, List<Ingredient>>, ingredients: List<Ingredient>, wastefulIngredient: String): List<Ingredient> {
        return ingredients.map{ product ->
            getIngredients(ingredientsList, product, wastefulIngredient)
        }.flatten().toMutableList().fold(mutableListOf<Ingredient>()){ a, b ->
            if (a.any{ it.name == b.name }) {
                a.map {
                    if( it.name == b.name) Ingredient(b.count+it.count, b.name) else it
                }.toMutableList()
            } else {
                a.add(b); a
            }
        }
    }

    fun getIngredients(ingredientsList: Map<Ingredient, List<Ingredient>>, ingredient: Ingredient, wastefulIngredient: String): List<Ingredient> {
        if (ingredient.name == "ORE")
            return listOf(ingredient)

        val formulaResult = getIngredientForName(ingredientsList, ingredient.name)
        val ingredients: List<Ingredient> = getIngredientsForName(ingredientsList, ingredient.name)
        if (ingredient.count%formulaResult.count!=0) {
            if(ingredient.name == wastefulIngredient) {
                val multiplier = Math.ceil((ingredient.count).toDouble() / (formulaResult.count).toDouble()).toInt()
                return ingredients.map { Ingredient(it.count * multiplier, it.name) }
            }
            return listOf(ingredient)
        }
        return ingredients.map{ Ingredient(it.count*ingredient.count/formulaResult.count, it.name)}
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

data class Ingredient(var countX: Int, var nameX: String) {
    var count: Int
    var name: String

    init{
        this.count = countX
        this.name = nameX
    }

    override fun toString(): String {
        return "$count $name"
    }


}
