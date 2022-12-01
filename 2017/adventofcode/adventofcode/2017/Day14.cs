using System;
namespace adventofcode
{
    public class Day14 {
        public Day14() {
            string input = "hwlqcszp";

            int score = 0;
            for (int i = 0; i < 128; i++)
            {
                score += countOnes(Day10.hashThis(input + "-" + i));
            }
            Console.WriteLine(score);

            var grid = new List<char[]>();
            for (int i = 0; i < 128; i++)
            {
                grid.Add(hexToBinary(Day10.hashThis(input + "-" + i)).ToCharArray());
            }

            var seen = new HashSet<(int, int)>();
            var count = 0;

            for (int x = 0; x < 128; x++)
            {
                for (int y = 0; y < 128; y++)
                { 
                    if (!seen.Contains((x, y)) && grid[x][y] == '1')
                    {
                        var group = findGroup(grid, x, y);
                        seen.UnionWith(group);
                        count++;
                    }     
                }
            }


            Console.WriteLine(count);
        }

        private static int countOnes(string hex) {
            return hex.Select(
              c => Convert.ToString(Convert.ToInt32(c.ToString(), 16), 2).Select( it => it == '1' ? 1 : 0).Sum()
            ).Sum();
        }

        public static String hexToBinary(string hex)
        {
            return string.Join("", hex.Select(
              c => Convert.ToString(Convert.ToInt32(c.ToString(), 16), 2).PadLeft(4, '0')
            ));
        }

        private static HashSet<(int, int)> findGroup(List<char[]> grid, int startX, int startY)
        {
            var inGroup = new HashSet<(int, int)>();
            var toAdd = new HashSet<(int, int)>();
            toAdd.Add((startX, startY));
            inGroup.Add((startX, startY));
            while (toAdd.Count > 0)
            {
                var newToAdd = new HashSet<(int, int)>();
                foreach (var pos in toAdd)
                {
                    if (pos.Item1 > 0 && grid[pos.Item1 - 1][pos.Item2] == '1')
                    {
                        newToAdd.Add((pos.Item1 - 1, pos.Item2));
                    }
                    if (pos.Item1 < 127 && grid[pos.Item1 + 1][pos.Item2] == '1')
                    {
                        newToAdd.Add((pos.Item1 + 1, pos.Item2));
                    }

                    if (pos.Item2 > 0 && grid[pos.Item1][pos.Item2 - 1] == '1')
                    {
                        newToAdd.Add((pos.Item1, pos.Item2 - 1));
                    }
                    if (pos.Item2 < 127 && grid[pos.Item1][pos.Item2 + 1] == '1')
                    {
                        newToAdd.Add((pos.Item1, pos.Item2 + 1));
                    }
                }
                newToAdd = newToAdd.Except(inGroup).ToHashSet();
                toAdd = newToAdd;
                inGroup.UnionWith(newToAdd);
            }

            return inGroup;
        }

    }
}

