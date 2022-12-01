using System;
namespace adventofcode
{
    public class Day06 {
        public Day06() {
            int[] input = FileReader.ReadLine("06").Split(" ").Select(it => (int.Parse(it))).ToArray()!;
            Console.WriteLine(bothParts(input));
        }

        private static (int, int) bothParts(int[] input) {
            var count = 0;
            var seen = new Dictionary<string, int>();
            while(!seen.ContainsKey(string.Join(",", input)))
            {
                seen.Add(string.Join(",", input), count);
                var index = maxIndex(input);
                var value = input[index];
                input[index] = 0;
                for (int i = 0; i < value; i++)
                {
                    input[(index + 1 + i) % input.Length]++;
                }
                count++;
            }
            Console.WriteLine(seen[string.Join(",", input)]);

            return (count, count -seen[string.Join(",", input)]);  
        }


        private static int maxIndex(int[] input)
        {
            int max = -1;
            int maxIndex = 0;
            for (int i = 0; i < input.Length; i++)
            {
                if (max < input[i])
                {
                    max = input[i];
                    maxIndex = i;
                }
            }

            return maxIndex;
        }

    }
}

