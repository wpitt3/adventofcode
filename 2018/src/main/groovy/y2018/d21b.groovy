package y2018
long x = 65536L
long z = 14339185L

Set<Long> hashset = new HashSet<>()
done = false
result = 0L
while(!done) {
	while (x >= 256L) {
		x = x / 256L
		z = (((z + (x & 255L)) & 16777215L) * 65899L) & 16777215L
	}
	if (hashset.contains(z)) {
		done = true
	} else {
		hashset.add(z)
		result = z
	}
	x = z | 65536L
	z = (((521363L + (x & 255L)) & 16777215L) * 65899L) & 16777215L
}

println result


//repeat until y < 256
//
//if it is output and then
//x = bori z 65536
//z = 521363
//y = bani x 255
//z = z + y
//z = bani z 16777215
//z = z * 65899
//z = bani z 16777215
//
//
//
//
//List registers = [0L, 0L, 0L, 0L, 0L, 14339185]
//
//
//(0..0).forEach {
//
//	registers = functions["bori"](5L, 65536L, 3L, registers)
//	//println(registers)
//	registers = functions["seti"](521363L, -1L, 5L, registers)
//	//println(registers)
//	registers = functions["bani"](3L, 255L, 4L, registers)
//	//println(registers)
//	registers = functions["addr"](5L, 4L, 5L, registers)
//	//println(registers)
//	registers = functions["bani"](5L, 16777215L, 5L, registers)
//	//println(registers)
//	registers = functions["muli"](5L, 65899L, 5L, registers)
//	//println(registers)
//	registers = functions["bani"](5L, 16777215L, 5L, registers)
//	println(registers[5])
//}
//
// y < 256
////
////if it is output and then
////x = bori z 65536
////z = 521363
////y = bani x 255
////z = z + y
////z = bani z 16777215
////z = z * 65899
////z = bani z 16777215
////
////
////
////
////List registers = [0L, 0L, 0L, 0L, 0L, 14339185]
////
////
////(0..0).forEach {
////
////	registers = functions["bori"](5L, 65536L, 3L, registers)
////	//println(registers)
////	registers = functions["seti"](521363L, -1L, 5L, registers)
////	//println(registers)
////	registers = functions["bani"](3L, 255L, 4L, registers)
////	//println(registers)
////	registers = functions["addr"](5L, 4L, 5L, registers)
////	//println(registers)
////	registers = functions["bani"](5L, 16777215L, 5L, registers)
////	//println(registers)
////	registers = functions["muli"](5L, 65899L, 5L, registers)
////	//println(registers)
////	registers = functions["bani"](5L, 16777215L, 5L, registers)
////	println(registers[5])
////}
////
////
