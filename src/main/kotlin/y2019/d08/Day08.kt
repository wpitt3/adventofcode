package y2019.d08

class Day08() {
    fun findPixelCount(pixels: List<Int>, x:Int, y:Int): Int {
        val layerWithFewestZeros = (pixels.chunked(x*y).sortedBy { i -> i.count{it == 0} }.get(0))
        return layerWithFewestZeros.count{it==1} * layerWithFewestZeros.count{it==2}
    }

    fun createImage(pixels: List<Int>, x:Int, y:Int) {
        val layers = pixels.chunked(x*y)

        for(yIndex in 0..(y-1)) {
            var a :String = "";
            for(xIndex in 0..(x-1)) {
                a += getPixel(layers, yIndex*x+xIndex)
            }
            println(a)
        }
    }

    fun getPixel(layers: List<List<Int>>, index: Int): String{
        for(i in 0..(layers.size-1)){
            if (layers[i][index] == 0) {
                return " ";
            } else if (layers[i][index] == 1) {
                return "#";
            }
        }
        return "/"
    }

}
