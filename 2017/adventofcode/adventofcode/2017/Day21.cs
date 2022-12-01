using System;
using System.Linq;

namespace adventofcode
{
    public class Day21 {

        public Day21()
        {
            var (twoToThree, threeToFour) = processInput();
            var gridSections = new bool[][]
            {
                new bool[]{ false, true, false },
                new bool[]{ false, false, true },
                new bool[]{ true, true, true }
            };

            for (var i = 0; i < 5; i++)
            {
                gridSections = splitAndFractal(gridSections, twoToThree, threeToFour);
            }
            Console.WriteLine(gridSections.Select(it => it.Count(i => i)).Sum());

            for (var i = 0; i < 13; i++)
            {
                gridSections = splitAndFractal(gridSections, twoToThree, threeToFour);
            }
            Console.WriteLine(gridSections.Select(it => it.Count(i => i)).Sum());
        }

        private static bool[][] splitAndFractal(bool[][] grid, Dictionary<string, bool[][]> twoToThree, Dictionary<string, bool[][]> threeToFour)
        {
            var newGrid = createNewGrid(grid.Length % 2 == 0 ? grid.Length / 2 * 3 : grid.Length / 3 * 4);
            if (grid.Length % 2 == 0)
            {
                int a = grid.Length / 2;
                for (var i = 0; i < a; i++)
                {
                    for (var j = 0; j < a; j++)
                    {
                        var newPattern = new bool[][]
                        {
                            new bool[]{ grid[2 * i][2 * j], grid[2 * i][2 * j + 1]},
                            new bool[]{ grid[2 * i + 1][2 * j], grid[2 * i + 1][2 * j + 1]},
                        };

                        newPattern = twoToThree[hash(newPattern)];

                        
                        for (var x = 0; x < 3; x++)
                        {
                            for (var y = 0; y < 3; y++)
                            {
                                newGrid[3 * i + y][3 * j + x] = newPattern[y][x];
                            }
                        }
                    }
                }
            }
            else
            {
                int a = grid.Length / 3;
                for (var i = 0; i < a; i++)
                {
                    for (var j = 0; j < a; j++)
                    {
                        var newPattern = new bool[][]
                        {
                            new bool[]{ grid[3 * i][3 * j], grid[3 * i][3 * j + 1], grid[3 * i][3 * j + 2]},
                            new bool[]{ grid[3 * i + 1][3 * j], grid[3 * i + 1][3 * j + 1], grid[3 * i + 1][3 * j + 2]},
                            new bool[]{ grid[3 * i + 2][3 * j], grid[3 * i + 2][3 * j + 1], grid[3 * i + 2][3 * j + 2]},
                        };

                        newPattern = threeToFour[hash(newPattern)];
                        for (var x = 0; x < 4; x++)
                        {
                            for (var y = 0; y < 4; y++)
                            {
                                newGrid[4 * i + y][4 * j + x] = newPattern[y][x];
                            }
                        }
                    }
                }
            }
            return newGrid;
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

        private static (Dictionary<string, bool[][]>, Dictionary<string, bool[][]>) processInput()
        {
            var twoToThreeInput = FileReader.ReadLines("21")!.ToArray()[..6];
            var threeToFourInput = FileReader.ReadLines("21")!.ToArray()[6..];
            var twoToThree = new Dictionary<string, bool[][]>();
            var threeToFour = new Dictionary<string, bool[][]>();

            foreach (var input in twoToThreeInput)
            {
                var (from, to) = inputLineToTuple(input);
                var flipped = flip(from);

                twoToThree[hash(from)] = to;
                twoToThree[hash(flipped)] = to;
                for (var i = 0; i < 3; i++)
                {
                    from = rotate90(from);
                    flipped = rotate90(flipped);
                    twoToThree[hash(from)] = to;
                    twoToThree[hash(flipped)] = to;
                }
            }

            foreach (var input in threeToFourInput)
            {
                var (from, to) = inputLineToTuple(input);
                var flipped = flip(from);

                threeToFour[hash(from)] = to;
                threeToFour[hash(flipped)] = to;
                for (var i = 0; i < 3; i++)
                {
                    from = rotate90(from);
                    flipped = rotate90(flipped);
                    threeToFour[hash(from)] = to;
                    threeToFour[hash(flipped)] = to;
                }
            }
            return (twoToThree, threeToFour);
        }

        private static (bool[][], bool[][]) inputLineToTuple(string input)
        {
            var split = input.Split(" => ").Select(x => inputToBoolArray(x)).ToArray();
            return (split[0], split[1]);
        }

        private static bool[][] inputToBoolArray(string input)
        {
            return input.Split("/").Select(i => i.Select(j => j == '#').ToArray()).ToArray();
        }

        private static bool[][] rotate90(bool[][] grid)
        {
            var r = new bool[grid.Length][];
            for (int i = 0; i < grid.Length; i++)
            {
                r[i] = (bool[])grid[i].Clone();
            }
            if (r![0].Length == 2)
            {
                (r[0][0], r[0][1], r[1][0], r[1][1]) = (r[1][0], r[0][0], r[1][1], r[0][1]);
            } else
            {
                (r[0][0], r[0][2], r[2][0], r[2][2]) = (r[2][0], r[0][0], r[2][2], r[0][2]);
                (r[0][1], r[1][0], r[1][2], r[2][1]) = (r[1][0], r[2][1], r[0][1], r[1][2]);
            }
            return r;
        }

        private static bool[][] flip(bool[][] grid)
        {
            var r = new bool[grid.Length][];
            for (int i = 0; i < grid.Length; i++)
            {
                r[i] = (bool[])grid[i].Clone();
            }
            
            if (r![0].Length == 2)
            {
                (r[0][0], r[0][1], r[1][0], r[1][1]) = (r[0][1], r[0][0], r[1][1], r[1][0]);
            }
            else
            {
                (r[0][0], r[0][1], r[0][2], r[2][0], r[2][1], r[2][2]) = (r[2][0], r[2][1], r[2][2], r[0][0], r[0][1], r[0][2]);
            }
            return r;
        }

        private static void printGrid(bool[][] grid)
        {
            foreach(var line in grid)
            {
                Console.WriteLine(String.Join("", line.Select(it => it ? '#' : '.')));
            }
        }

        private static string hash(bool[][] grid)
        {
            return String.Join("", grid.Select(line => String.Join("", line.Select(it => it ? '#' : '.'))));
        }
    }
}

