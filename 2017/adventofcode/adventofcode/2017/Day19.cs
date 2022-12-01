using System;
namespace adventofcode
{
    public class Day19 {

        public Day19() {
            //   y  x
            char[ ][ ] input = FileReader.ReadLines("19")!.Select(it => it.ToCharArray()).ToArray();

            var (y, x) = (0, 0);
            for (var i = 0; i < input.Count(); i++)
            {
                if (input[0][i] != ' ')
                {
                    x = i;
                }
            }
           

            var direction = (1, 0);
            var done = false;
            var letters = new List<char>();
            var count = 0;
            while (!done)
            {
                while (input[y][x] != '+' && input[y][x] != ' ')
                {
                    if (input[y][x] != '-' && input[y][x] != '|')
                    {
                        letters.Add(input[y][x]);
                    }
                    (y, x) = (y + direction.Item1, x + direction.Item2);
                    count++;
                }
                if (input[y + direction.Item2][x + direction.Item1] != ' ')
                {
                    direction = (direction.Item2, direction.Item1);
                    //Console.WriteLine(y + " " + x + " " + direction.Item1 + " " + direction.Item2);
                    //Console.WriteLine(String.Join("", letters));
                }
                else if (input[y + direction.Item2 * -1][x + direction.Item1 * -1] != ' ')
                {
                    direction = (direction.Item2 * -1, direction.Item1 * -1);
                    //Console.WriteLine(y + " " + x + " " + direction.Item1 + " " + direction.Item2);
                    //Console.WriteLine(String.Join("", letters));
                }
                else
                {
                    done = true;
                }
                if (input[y + direction.Item1][x + direction.Item2] != ' ')
                {
                    (y, x) = (y + direction.Item1, x + direction.Item2);
                    count++;
                }
            }
            Console.WriteLine(String.Join("", letters));
            Console.WriteLine(count);
        }
    }
}

