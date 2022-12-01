using System;
using System.Linq;

namespace adventofcode
{
    public class Day24 {

        public Day24()
        {
            var input = FileReader.ReadLines("24")!.Select(it => it.Split("/").Select(it => int.Parse(it)).ToArray()).ToArray();

            var keyToPair = new Dictionary<int, List<(int, int)>>();
            foreach (var x in input)
            {
                if (!keyToPair.ContainsKey(x[0]))
                {
                    keyToPair[x[0]] = new List<(int, int)>();
                }
                keyToPair[x[0]].Add((x[0], x[1]));

                if (x[0] != x[1])
                {
                    if (!keyToPair.ContainsKey(x[1]))
                    {
                        keyToPair[x[1]] = new List<(int, int)>();
                    }
                    keyToPair[x[1]].Add((x[1], x[0]));
                }
            }

            int maxS = 0;
            int maxDS = 0;

            foreach (var key in keyToPair[0])
            {
                var s = recurseForStength(key.Item2, keyToPair, new HashSet<int>() { hash(key) }) + key.Item2;
                if (s > maxS)
                {
                    maxS = s;
                }
                var ds = recurseForDepth(key.Item2, keyToPair, 1, new HashSet<int>() { hash(key) }) + key.Item2;
                if (ds > maxDS)
                {
                    maxDS = ds;
                }
            }

            Console.WriteLine(maxS);
            Console.WriteLine(maxDS);
        }

        private static int recurseForStength(int current, Dictionary<int, List<(int, int)>> keyToPair, HashSet<int> seen)
        {
            int max = 0;
            foreach (var key in keyToPair[current])
            {
                if (!seen.Contains(hash(key)))
                {
                    var newSeen = new HashSet<int>(seen);
                    newSeen.Add(hash(key));
                    int newScore = recurseForStength(key.Item2, keyToPair, newSeen) + key.Item2 + key.Item1;
                    if (newScore > max)
                    {
                        max = newScore;
                    }
                }
            }
            return max;
        }

        private static int recurseForDepth(int current, Dictionary<int, List<(int, int)>> keyToPair, int depth, HashSet<int> seen)
        {
            int max = 0;
            foreach (var key in keyToPair[current])
            {
                if (!seen.Contains(hash(key)))
                {   
                    var newSeen = new HashSet<int>(seen);
                    newSeen.Add(hash(key));
                    int newScore = recurseForDepth(key.Item2, keyToPair, depth + 1, newSeen);
                    if ((depth == 35 || newScore > 0) && newScore + key.Item2 + key.Item1 > max)
                    {
                        max = newScore + key.Item2 + key.Item1;
                    }
                }
            }
            return max;
        }

        private static int hash((int, int) pair)
        {
            return pair.Item1 > pair.Item2 ? pair.Item1 * 100 + pair.Item2 : pair.Item2 * 100 + pair.Item1;
        }
    }
}

