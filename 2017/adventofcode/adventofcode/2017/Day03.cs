using System;
namespace adventofcode
{
    public class Day03 {
        public Day03() {
            int input = 325489;
            Console.WriteLine(partA(input));
            Console.WriteLine(partB(input));
            //Console.WriteLine(partB(100));
        }

        private static int partA(int input) {
            var x = 1;
            while (input > x * x )
            {
                x+=2;
            }
            var y = x * x;
            while (y > input)
            {
                y -= x - 1;
            }
            y += (x - 1) / 2;
            return Math.Abs(input-y) + (x - 1) / 2;  
        }

        private static int partB(int input)
        {
            var grid = new int[100, 100];
            var x = 49;
            var y = 49;

            grid[x, y] = 1;

            var z = 3;
            while (z < 100)
            {
                foreach (var move in generateMoves(z)) {
                    x += move.Item1;
                    y += move.Item2;
                    grid[x, y] = score(x, y, grid);

                    if (grid[x, y] > input)
                    {
                        return grid[x, y];
                    }
                };
                z += 2;
            }
            return 0;
        }

        private static List<(int, int)> generateMoves(int z)
        {
            var moves = new List<(int, int)> { };
            moves.Add((1, 0));
            Utils.Repeat(z - 2, () => moves.Add((0, -1)));
            Utils.Repeat(z - 1, () => moves.Add((-1, 0)));
            Utils.Repeat(z - 1, () => moves.Add((0, 1)));
            Utils.Repeat(z - 1, () => moves.Add((1, 0)));
            return moves;
        }

        private static int score(int x, int y, int[,] grid)
        {
            int result = 0;
            for (int i = -1; i < 2; i++)
            {
                for (int j = -1; j < 2; j++)
                {
                    result += grid[x + i, y + j];
                }
            }
            return result;
        }
        

    }
}

