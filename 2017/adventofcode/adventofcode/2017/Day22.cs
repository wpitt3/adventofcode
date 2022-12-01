using System;
using System.Linq;

namespace adventofcode
{
    public class Day22 {

        public Day22()
        {
            PartA();
            PartB();
        }

        public static void PartA()
        {
            var input = FileReader.ReadLines("22")!;
            var offset = 500;
            var grid = createNewGrid(input.Count() + offset * 2);

            for (var i = 0; i < input.Count(); i++)
            {
                var x = input[i].ToCharArray();
                for (var j = 0; j < input.Count(); j++)
                {
                    grid[i + offset][j + offset] = x[j] == '#';
                }
            }

            var direction = (-1, 0);
            var location = (input.Count() / 2 + offset, input.Count() / 2 + offset);
            var count = 0;
            for (var i = 0; i < 10000; i++)
            {
                if (grid[location.Item1][location.Item2])
                {
                    // turn right
                    direction = (direction.Item2, direction.Item1 * -1);
                }
                else
                {
                    direction = (direction.Item2 * -1, direction.Item1);
                }
                if (!grid[location.Item1][location.Item2])
                {
                    count++;
                }
                grid[location.Item1][location.Item2] = !grid[location.Item1][location.Item2];
                location = (location.Item1 + direction.Item1, location.Item2 + direction.Item2);
            }


            Console.WriteLine(count);
        }

        public static void PartB()
        {
            var input = FileReader.ReadLines("22")!;
            var offset = 500;
            var grid = createNewIntGrid(input.Count() + offset * 2);

            for (var i = 0; i < input.Count(); i++)
            {
                var x = input[i].ToCharArray();
                for (var j = 0; j < input.Count(); j++)
                {
                    grid[i + offset][j + offset] = x[j] == '#' ? 2 : 0;
                }
            }

            var direction = (-1, 0);
            var l = (input.Count() / 2 + offset, input.Count() / 2 + offset);
            var count = 0;
            for (var i = 0; i < 10000000; i++)
            {
                switch(grid[l.Item1][l.Item2])
                {
                    case 3: // w > i
                        count++;
                        break;
                    case 2: // i > f
                        direction = (direction.Item2, direction.Item1 * -1);
                        break;
                    case 1: // f > c
                        // back
                        direction = (direction.Item1 * -1, direction.Item2 * -1);
                        break;
                    case 0: // c > w
                        direction = (direction.Item2 * -1, direction.Item1);
                        break;
                    default:
                        throw new Exception("AAAH!");
                }

                grid[l.Item1][l.Item2] = (grid[l.Item1][l.Item2] + 3) % 4;
                l = (l.Item1 + direction.Item1, l.Item2 + direction.Item2);
            }


            Console.WriteLine(count);
        }

        private static bool[][] createNewGrid(int size)
        {
            var r = new bool[size][];
            for (int i = 0; i < size; i++)
            {
                r[i] = new bool[size];
            }
            return r;
        }

        private static int[][] createNewIntGrid(int size)
        {
            var r = new int[size][];
            for (int i = 0; i < size; i++)
            {
                r[i] = new int[size];
            }
            return r;
        }

        private static void printGrid(bool[][] grid)
        {
            foreach (var line in grid)
            {
                Console.WriteLine(String.Join("", line.Select(it => it ? '#' : '.')));
            }
        }
    }

}

