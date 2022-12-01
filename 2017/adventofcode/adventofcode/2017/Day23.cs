using System;
using System.Reflection;
// 6374 < < 13716
namespace adventofcode
{
    public class Day23
    {
        private int aIndex = 0;
        private int bIndex = 0;

        Day23Command[] instructions;


        public Day23()
        {
            instructions = FileReader.ReadLines("23")!.Select(it => it.Split(" ")).Select( it => {
                var y = extractY(it);
                return new Day23Command(it[0], ((int)it[1][0]) - 97, y.Item1, y.Item2);
            }).ToArray();

            //partA(instructions);
            partB(instructions);
        }

        private static void partA(Day23Command[] instructions)
        {
            var registers = new long[8];
            var index = 0;
            var done = false;
            var mulCont = 0;

            while (!done && index < instructions.Length)
            {
                var inst = instructions[index];
                switch (inst.Action)
                {
                    case "set":
                        registers[inst.Index] = findY(inst, registers);
                        break;
                    case "sub":
                        registers[inst.Index] -= findY(inst, registers);
                        break;
                    case "mul":
                        mulCont++;
                        registers[inst.Index] *= findY(inst, registers);
                        break;
                    case "jnz":
                        if (inst.Index < 0 || registers[inst.Index] != 0)
                        {
                            index += (int)(findY(inst, registers)); index--;
                        }
                        break;
                    default:
                        throw new Exception("SHITE");
                }
                index++;

            }
            Console.WriteLine(mulCont);
        }

        private static void partB(Day23Command[] instructions)
        {
            int count = 0;
            for (var i = 109300; i < 126301; i += 17)
            {
                if (!isPrime(i))
                {
                    count++;
                }
            }
            Console.WriteLine(count);
        }

        public static bool isPrime(int number)
        {
            var primes = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367 };
            foreach(var x in primes)
            {
                if (number % x == 0)
                {
                    return false;
                }
            }

            return true;
        }

        public static bool IsPrime(int number)
        {
            if (number <= 1) return false;
            if (number == 2) return true;
            if (number % 2 == 0) return false;

            var boundary = (int)Math.Floor(Math.Sqrt(number));

            for (int i = 3; i <= boundary; i += 2)
                if (number % i == 0)
                    return false;

            return true;
        }


        private static void printRegisters(long[] registers)
        {
            Console.WriteLine(String.Join(" ", registers.Select(it => it.ToString().PadLeft(10, ' '))));
        }

        private static long findY(Day23Command inst, long[] registers)
        {
            return inst.YAsValue ?? registers[inst.YAsIndex ?? -1];
        }


        private static (int?, int?) extractY(string[] y)
        {
            return y.Length < 3 ? (null, null) : (113 > (int)y[2][0] && (int)y[2][0] > 96 ? (null, ((int)y[2][0])-97) : (int.Parse(y[2]), null));
        }
    }

    public struct Day23Command
    {
        public Day23Command(string action, int index, int? yAsValue, int? yAsIndex)
        {
            Action = action;
            Index = index;
            YAsValue = yAsValue;
            YAsIndex = yAsIndex;
        }

        public string Action { get; }
        public int Index { get; }
        public int? YAsValue { get; }
        public int? YAsIndex { get; }
    }
}

