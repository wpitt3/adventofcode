import Base
import kotlin.test.assertEquals
import org.junit.Test as test

class BaseTest{

    @test
    fun test() {
        val result: String = Base().main()

        assertEquals(result, "")
    }
}