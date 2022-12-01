using System;
namespace adventofcode
{
    public class Day10
    {
        public Day10()
        {
            string input = FileReader.ReadLine("10");
            int[] result1 = shuffleRegisters(input.Split(",").Select(it => (int.Parse(it))).ToArray(), 1);
            Console.WriteLine(result1[0] * result1[1]);
            Console.WriteLine(hashThis(input));
            
        }

        public static string hashThis(String input)
        {
            var input2 = readStringAsBytes(input).ToList();
            input2.AddRange(new int[] { 17, 31, 73, 47, 23 });

            var registers = shuffleRegisters(input2.ToArray(), 64);

            var denseHash = createDenseHash(registers);

            var result = "";
            for (int i = 0; i < 16; i++)
            {
                result += toHex(denseHash[i] / 16) + toHex(denseHash[i] % 16);
            }
            return result;
        }

        public static int[] readStringAsBytes(string input)
        {
            return input.ToCharArray().Select(x => (int)x).ToArray();
        }

        public static int[] createDenseHash(int[] registers)
        {
            var denseHash = new int[16];
            for (int i = 0; i < 16; i++)
            {
                int hash = registers[i * 16];
                for (int j = 1; j < 16; j++)
                {
                    hash = hash ^ registers[i * 16 + j];
                }
                denseHash[i] = hash;
            };
            return denseHash;
        }

        public static string toHex(int input)
        {
            return input < 10 ? "" + input : "" + (char)(input + 87);
        }

        private static int[] shuffleRegisters(int[] input, int repeats, int maxSize = 256)
        {
            var registers = new int[maxSize];
            for (int i = 0; i < maxSize; i++)
            {
                registers[i] = i;
            }

            int index = 0;
            int step = 0;
            for (int j = 0; j < repeats; j++)
            {
                foreach (var len in input)
                {
                    for (int i = 0; i < len / 2; i++)
                    {
                        int x = (index + i + maxSize) % maxSize;
                        int y = (index + len - 1 - i + maxSize) % maxSize;
                        (registers[y], registers[x]) = (registers[x], registers[y]);
                    }
                    index += len + step % maxSize;
                    step++;
                }
            }

            return registers;
        }

    }
}

