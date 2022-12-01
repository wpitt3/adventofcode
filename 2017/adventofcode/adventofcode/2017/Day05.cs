using System;
namespace adventofcode
{
    public class Day05 {
        public Day05() {
            var input = FileReader.ReadLines("05")?.Select(it => int.Parse(it)).ToList()!;
            Console.WriteLine(partA(input));
            input = FileReader.ReadLines("05")?.Select(it => int.Parse(it)).ToList()!;
            Console.WriteLine(partB(input));
        }

        private static int partA(List<int> input) {
            var index = 0;
            var jumps = 0;

            while(index >= 0 && index < input.Count())
            {
                var newIndex = index + input[index];
                input[index]++;
                index = newIndex;
                jumps++;
            }


            return jumps;
        }

        private static int partB(List<int> input)
        {
            var index = 0;
            var jumps = 0;

            while (index >= 0 && index < input.Count())
            {
                var newIndex = index + input[index];
                input[index] += input[index] > 2 ? -1 : 1;
                index = newIndex;
                jumps++;
            }


            return jumps;
        }

    }
}

