

import org.apache.commons.lang3.StringUtils

def x = new File("../resources/2.txt").readLines()

x.each{ a ->
	x.each{ b ->
		if(a != b) {
			if( StringUtils.getLevenshteinDistance(a, b) < 2) {
				def aSplit = a.split('') 
				def bSplit = b.split('') 
				println ((0..<(a.size())).findAll{aSplit[it] == bSplit[it]}.collect{aSplit[it]}.join(''))
			}
		}
	}
}