import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.Test as test

class Day14Test{

    @test
    fun readLineOfInput() {
        val result: Map<Ingredient, List<Ingredient>> = Day14().readLine("7 A, 1 E => 1 FUEL")
        assertEquals(
            mapOf(Pair(
                Ingredient(1, "FUEL"),
                listOf(Ingredient(7, "A"), Ingredient(1, "E")))), result)
    }

    @test
    fun readLines() {
        val result: Map<Ingredient, List<Ingredient>> = Day14().readLines(
            listOf("7 A, 1 E => 1 FUEL", "7 B, 1 E => 2 A"))
        assertEquals(mapOf(
            Pair(Ingredient(1, "FUEL"), listOf(Ingredient(7, "A"), Ingredient(1, "E"))),
            Pair(Ingredient(2, "A"), listOf(Ingredient(7, "B"), Ingredient(1, "E")))
        ), result)
    }

    @test
    fun calcIngredient() {
        val ingredients = mapOf(Pair(Ingredient(1, "FUEL"), listOf(Ingredient(10, "ORE"))))

        val result: List<Ingredient> = Day14().getIngredientsForName(ingredients, "FUEL")
        assertEquals(listOf(Ingredient(10, "ORE")), result)
    }

    @test
    fun getIngredientForName() {
        val ingredients = mapOf(Pair(Ingredient(1, "FUEL"), listOf(Ingredient(10, "ORE"))))

        val result: Ingredient = Day14().getIngredientForName(ingredients, "FUEL")
        assertEquals(Ingredient(1, "FUEL"), result)
    }

    @test
    fun calcIngredientNotImplemented() {
        val ingredients = mapOf(
            Pair(Ingredient(1, "FUEL"), listOf(Ingredient(10, "ORE"))),
            Pair(Ingredient(2, "FUEL"), listOf(Ingredient(10, "ORE"))))

        assertFailsWith<RuntimeException> {
            Day14().getIngredientsForName(ingredients, "FUEL")
        }
    }

    @test
    fun calculateOreToFuelOneStep() {
        val ingredients = mapOf(Pair(Ingredient(1, "FUEL"), listOf(Ingredient(10, "ORE"))))

        val result: Int = Day14().countOreForFuel(ingredients)
        assertEquals(10, result)
    }

    @test
    fun calculateOreToFuelTwoStep() {
        val ingredients = mapOf(
            Pair(Ingredient(1, "FUEL"), listOf(Ingredient(1, "A"))),
            Pair(Ingredient(1, "A"), listOf(Ingredient(2, "ORE")))
        )

        val result: Int = Day14().countOreForFuel(ingredients)
        assertEquals(2, result)
    }

    @test
    fun calculateOreToFuelTwoStepWithMultiply() {
        val ingredients = mapOf(
            Pair(Ingredient(1, "FUEL"), listOf(Ingredient(2, "A"))),
            Pair(Ingredient(1, "A"), listOf(Ingredient(2, "ORE")))
        )

        val result: Int = Day14().countOreForFuel(ingredients)
        assertEquals(4, result)
    }

    @test
    fun calculateOreToFuelTwoStepWithFactors() {
        val ingredients = mapOf(
            Pair(Ingredient(1, "FUEL"), listOf(Ingredient(2, "A"))),
            Pair(Ingredient(2, "A"), listOf(Ingredient(2, "ORE")))
        )

        val result: Int = Day14().countOreForFuel(ingredients)
        assertEquals(2, result)
    }

    @test
    fun Example1() {
        val lines = listOf(
            "10 ORE => 10 A",
            "1 ORE => 1 B",
            "7 A, 1 B => 1 C",
            "7 A, 1 C => 1 D",
            "7 A, 1 D => 1 E",
            "7 A, 1 E => 1 FUEL"
        )
        val ingredients = Day14().readLines(lines)
        val result: Int = Day14().countOreForFuel(ingredients)
        assertEquals(31, result)
    }

    @test
    fun getIngredientsAndFlatten() {
        val lines = listOf(
            "1 B, 1 C => 1 FUEL"
        )
        val ingredients = Day14().readLines(lines)
        val result = Day14().getIngredientsAndFlatten(ingredients, listOf(Ingredient(1, "FUEL")), "")
        assertEquals(listOf(Ingredient(1, "B"), Ingredient(1, "C")), result)
    }

    @test
    fun getIngredientsWithNoExactMatch() {
        val lines = listOf(
            "1 B => 5 A"
        )
        val ingredients = Day14().readLines(lines)
        val result = Day14().getIngredientsAndFlatten(ingredients, listOf(Ingredient(4, "A")), "")
        assertEquals(listOf(Ingredient(4, "A")), result)
    }

    @test
    fun getIngredientsWithNoExactMatchAndWaste() {
        val lines = listOf(
            "1 B => 5 A"
        )
        val ingredients = Day14().readLines(lines)
        val result = Day14().getIngredientsAndFlatten(ingredients, listOf(Ingredient(4, "A")), "A")
        assertEquals(listOf(Ingredient(1, "B")), result)
    }

}
