using System;
using System.Collections.Generic;
using System.Linq;

namespace adventofcode
{
    public class Day12 {
        public Day12() {
            List<string> lines = FileReader.ReadLines("12")!;
            var pipes = new Dictionary<string, List<string>>();
            foreach(var line in lines)
            {
                var split = line.Split(" <-> ");
                pipes[split[0]] = split[1].Split(", ").ToList();
            }

            Console.WriteLine(findAllInGroup("0", pipes).Count);

            var seen = new HashSet<string>();
            var count = 0;

            foreach (var key in pipes.Keys)
            {
                if (!seen.Contains(key))
                {
                    var group = findAllInGroup(key, pipes);
                    seen.UnionWith(group);
                    count++;
                }
            }

            Console.WriteLine(count);
        }

        private static HashSet<string> findAllInGroup(string key, Dictionary<string, List<string>> pipes)
        {
            var inGroup = new HashSet<string>();
            var toAdd = new HashSet<string>();
            toAdd.Add(key);
            inGroup.Add(key);
            while (toAdd.Count > 0)
            {
                var newToAdd = new HashSet<string>();
                foreach (var k in toAdd)
                {
                    newToAdd.UnionWith(pipes[k]);
                }
                newToAdd = newToAdd.Except(inGroup).ToHashSet();
                toAdd = newToAdd;
                inGroup.UnionWith(newToAdd);
            }

            return inGroup;
        }

    }
}

