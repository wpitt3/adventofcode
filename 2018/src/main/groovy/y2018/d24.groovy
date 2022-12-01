package y2018


lines = new File("../../resources/24.txt").readLines()
List unitGroups = lines.collect {line ->
	def split = line.split("with an attack that does")
	def defense = split[0].split("hit points ")
	def attack = split[1].split("damage at initiative").collect{it.trim()}
	def ch = defense[0].split(" units each with ").collect{Integer.parseInt(it.trim())}
	def wi = defense.size() > 1 ? defense[1].trim().replaceAll("[\\(\\)]", "").split(";").toList() : []
	UnitGroup unitGroup = new UnitGroup(ch[0], ch[1], Integer.parseInt(attack[0].split(" ")[0]), attack[0].split(" ")[1], Integer.parseInt(attack[1]))
	wi.forEach{ x ->
		def y = x.split(" to ")
		unitGroup[y[0].trim()].addAll(y[1].split(",").collect{it.trim()})
	}
	return unitGroup
}

groupA = unitGroups.take(10)
groupB = unitGroups.drop(10)

//186 - 190
groupA.forEach{it.attack += 187}

while(groupA.size() > 0 && groupB.size() > 0) {
	List<List<UnitGroup>> attacks = []

	attacks.addAll(setupAttacks(groupA, groupB))
	attacks.addAll(setupAttacks(groupB, groupA))

	attacks.sort { -it[0].initiative }.forEach {
		int kills = Math.floor((it[0].power() * (it[1].weak.contains(it[0].attackType) ? 2 : 1) ) / it[1].hitpoints)
		it[1].count = Math.max(0, it[1].count - kills)
	}

	groupA = groupA.findAll { it.count > 0 }
	groupB = groupB.findAll { it.count > 0 }
	println ""
	println groupA.sum{it.count}
	println groupB.sum{it.count}
}

println groupA.sum{it.count}
println groupB.sum{it.count}

List<List<UnitGroup>> setupAttacks(attackers, defenders) {
	List<List<UnitGroup>> attacks = []
	Set<Integer> attacked = new HashSet<Integer>()
	attackers.sort { it.sortOrder() }.forEach { attacker ->
		List<UnitGroup> toAttack = defenders.findAll { !attacked.contains(it.initiative) && !it.immune.contains(attacker.attackType) }
		List<UnitGroup> weak = toAttack.findAll { it.weak.contains(attacker.attackType) }
		toAttack = (weak.size() > 0 ? weak : toAttack).sort { it.sortOrder() }
		if (toAttack.size() > 0) {
			attacks.add([attacker, toAttack[0]])
			attacked.add(toAttack[0].initiative)
		}
	}
	return attacks
}


class UnitGroup {
	int count
	int hitpoints
	Set<String> weak = new HashSet()
	Set<String> immune = new HashSet()
	int attack
	String attackType
	int initiative

	UnitGroup(int count, int hitpoints, int attack, String attackType, int initiative) {
		this.count = count
		this.hitpoints = hitpoints
		this.attack = attack
		this.attackType = attackType
		this.initiative = initiative
	}

	int power() {
		return count * attack
	}

	int sortOrder() {
		return - (power()*100 + initiative)
	}

	@Override
	public String toString() {
		return "UnitGroup{" +
				"count=" + count +
				", hitpoints=" + hitpoints +
				", weak=" + weak +
				", immune=" + immune +
				", attack=" + attack +
				", attackType='" + attackType + '\'' +
				", initiative=" + initiative +
				'}';
	}
}