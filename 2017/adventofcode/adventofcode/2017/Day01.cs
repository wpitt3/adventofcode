using System;
namespace adventofcode
{
    public class Day01 {
        public Day01() {
            string input = FileReader.ReadLine("01")!;
            Console.WriteLine(partA(input));
            Console.WriteLine(partB(input));
        }

        private static int partA(string input) {
            return sumWithOffset(input, 1);  
        }

        private static int partB(string input)
        {
            return sumWithOffset(input, input.Length/2);
        }

        private static int sumWithOffset(string input, int offset)
        {
            var result = 0;
            for (int i = 0; i < input.Length; i++)
            {
                if (input[i] == input[(i + offset) % input.Length])
                {
                    result += input[i] - 48;
                }
            }
            return result;
        }
    }
}

