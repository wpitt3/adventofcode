List input = new File("../resources/24.txt").readLines()

input = input.collect{Integer.parseInt(it)}

input = input.reverse()
int total = input.sum();
int oneThird = total/3


List<BigInteger> results = []

recursive(input, oneThird, [], results)

println results.min()

// int remaining = oneThird
// ((input.size()-1)..0).each {

// 	if (input[it] <= remaining) {
// 		remaining -= input[it]
// 		println input[it]	
// 	}
// }


void recursive(List input, int remaining, List soFar, List results) {
	// println input.toString() + " " + remaining + " " + soFar
	if (remaining == 0) {
		if(soFar.size() < 7) {
			BigInteger x = new BigInteger(1);
			soFar.each{
				x = x.multiply(new BigInteger(it))
			}
			results.add(x);
		}
		return
	} 
	if(input.size() == 0) {
		return;
	}

	if (input[0] <= remaining) {
		recursive(input.tail(), remaining - input[0], soFar.clone() << input[0], results)
	}

	recursive(input.tail(), remaining, soFar.clone(), results)

}