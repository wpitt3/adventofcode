using System;
namespace adventofcode
{
    public class Day09 {
        public Day09() {
            string input = FileReader.ReadLine("09")!;

            var withoutGarbage = removeGarbage(input);
            Console.WriteLine(scoreGroups(withoutGarbage.Item1));
            Console.WriteLine(withoutGarbage.Item2);

        }

        public static (string, int) removeGarbage(string input) {
            var inputArray = input.ToCharArray();
            List<char> output = new List<char>();
            int i = 0;
            int count = 0;
            Boolean inGarbage = false;

            while(i < input.Length)
            {
                if (inputArray[i] == '<' && !inGarbage)
                {
                    inGarbage = true;
                    count--;
                }

                if (inGarbage)
                {
                    if (inputArray[i] == '!')
                    {
                        i++;
                    } else if (inputArray[i] == '>')
                    {
                        inGarbage = false;
                    } else
                    {
                        count++;
                    }
                } else
                {
                    output.Add(inputArray[i]);
                }
                i++;
            }


            return (string.Join("", output), count);  
        }

        public static int scoreGroups(string input)
        {
            var inputArray = input.ToCharArray();
            int depth = 0;
            int score = 0;
            int i = 0;
            while (i < input.Length)
            {
                if (inputArray[i] == '{')
                {
                    depth += 1;
                    score += depth;
                }
                else if(inputArray[i] == '}')
                {
                    depth -= 1;
                }
                i++;
            }


            return score;
        }

    }
}

