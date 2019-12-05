package y2019.d04

class Day04() {

    fun countValidInRange(from: Int, to: Int):Int {
        return (from..to).count({isValid(it)})
    }

    fun isValid(password: Int):Boolean {
        val digits: List<Int> = toUnitIntList(password)
        return digits.size == 6 && (0..4).all({digits[it] <= digits[it+1]}) && (0..4).any({digits[it] == digits[it+1]})
    }

    fun countValidRule2(from: Int, to: Int):Int {
        return (from..to).count({isValidRule2(it)})
    }

    fun isValidRule2(password: Int):Boolean {
        val digits: List<Int> = toUnitIntList(password)
        return isValid(password) && (0..4).any({
            digits[it] == digits[it+1]
                && (it == 0 || digits[it] != digits[it-1])
                    && (it == 4 || digits[it+1] != digits[it+2])})
    }

    fun toUnitIntList(password:Int): List<Int> {
        return password.toString().map({ if(it != null) Integer.parseInt(it.toString()) else 0})
    }
}
