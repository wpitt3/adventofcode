using System;
namespace adventofcode
{
    public class Day20 {

        public Day20()
        {
            int[][][] input = FileReader.ReadLines("20")!.Select(it => it.Split(", ").Select(it => it[3..^1].Split(",").Select(it => int.Parse(it)).ToArray()).ToArray()).ToArray();

            Console.WriteLine(partA(input));
            Console.WriteLine(partB(input));
        }

        private static int partA(int[][][] input)
        {
            int minA = int.MaxValue;
            int minV = int.MaxValue;
            int index = 0;

            for (var i = 0; i < input.Count(); i++)
            {
                int aCount = 0;
                int vCount = 0;
                for (var j = 0; j < 3; j++)
                {
                    aCount += Math.Abs(input[i][2][j]);
                    vCount += Math.Abs(input[i][1][j]);
                }
                if (aCount <=  minA) {
                    if (aCount < minA)
                    {
                        minV = vCount;
                    }
                    minA = aCount;
                    if (vCount <= minV)
                    {
                        minV = vCount;
                        index = i;
                    }
                }
            }
            return index;
        }

        private static int partB(int[][][] input)
        {
            var asteroids = new Dictionary<String, List<Day20Asteroid>>();
            for (var i = 0; i < input.Count(); i++)
            {
                var ast = new Day20Asteroid(input[i][0], input[i][1], input[i][2]);
                if (!asteroids.ContainsKey(ast.Code))
                {
                    asteroids[ast.Code] = new List<Day20Asteroid>();
                }
                asteroids[ast.Code].Add(ast);
            }


            for (var x = 0; x < 100; x++)
            {
                var newAsteroids = new Dictionary<String, List<Day20Asteroid>>();
                foreach (var astList in asteroids.Values)
                {
                    var ast = astList[0];
                    int[] pos = new int[3];
                    int[] vel = new int[3];
                    for (var i = 0; i < 3; i++)
                    {
                        pos[i] = ast.Pos[i] + ast.Vel[i] + ast.Acc[i];
                        vel[i] = ast.Vel[i] + ast.Acc[i];
                    }
                    var newAst = new Day20Asteroid(pos, vel, ast.Acc);

                    if (!newAsteroids.ContainsKey(newAst.Code))
                    {
                        newAsteroids[newAst.Code] = new List<Day20Asteroid>();
                    }
                    newAsteroids[newAst.Code].Add(newAst);
                }
                asteroids = newAsteroids.Where(it => it.Value.Count() == 1).ToDictionary(i => i.Key, i => i.Value);
                //Console.WriteLine(asteroids.Count());
            }

            return asteroids.Count();
        }

        public struct Day20Asteroid
        {
            public Day20Asteroid(int[] pos, int[] vel, int[] acc)
            {
                Pos = pos;
                Vel = vel;
                Acc = acc;
                Code = String.Join("|", Pos);
            }

            public int[] Pos { get; }
            public int[] Vel { get; }
            public int[] Acc { get; }
            public string Code { get; }

        }
    }
}

