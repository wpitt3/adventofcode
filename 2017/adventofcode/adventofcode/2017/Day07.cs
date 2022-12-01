using System;
using System.Reflection.Emit;

namespace adventofcode
{
    public class Day07 {
        public Day07()
        {
            List<string> input = FileReader.ReadLines("07")!;

            List<Day07Node> nodes = generateNodes(input);

            Day07Node rootNode = nodes[0];
            while (rootNode.Parent != null)
            {
                rootNode = rootNode.Parent;
            }

            Console.WriteLine(rootNode.Label);
            scoreNodes(rootNode);

            var unbalancedNode = findUnbalancedNode(rootNode);
            var requiredScore = unbalancedNode.Parent.Children.GroupBy(it => it.Score).Where(it => it.Count() != 1).Select(it => it.ToList().First()).First();

            Console.WriteLine(requiredScore.Score - unbalancedNode.Score + unbalancedNode.Value);
        }

        private static int scoreNodes(Day07Node currentNode)
        {
            List<int> scores = currentNode.Children.Select(it => scoreNodes(it)).ToList();
            currentNode.Score = currentNode.Value! + scores.Sum();
            return (int)currentNode.Score!;
        }

        private static Day07Node findUnbalancedNode(Day07Node currentNode)
        {
            var oddChildren = currentNode.Children.GroupBy(it => it.Score).Where(it => it.Count() == 1).Select(it => it.ToList().First()).ToList();
            if (oddChildren.Count() == 1)
            {
                return findUnbalancedNode(oddChildren[0]);
            } else if (oddChildren.Count() > 1)
            {
                throw new Exception(" ");
            } 

            return currentNode;
        }



        private static List<Day07Node> generateNodes(List<string> input)
        {
            var seen = new Dictionary<string, Day07Node>();
            
            foreach (var x in input)
            {
                var split = x.Split(" -> ");
                var y = split[0].Split(" ").ToList();
                var label = y[0];
                var value = int.Parse(y[1][1..^1]);
                var children = split.Length > 1 ? split[1].Split(", ") : new string[] { };

                var node = seen.ContainsKey(label) ? seen[label] : new Day07Node(label);
                node.Value = value;
                seen[label] = node;

                foreach (var child in children)
                {
                    var childNode = seen.ContainsKey(child) ? seen[child] : new Day07Node(child);
                    childNode.Parent = node;
                    node.Children.Add(childNode);
                    seen[child] = childNode;
                }
            }
            return seen.Values.ToList();
        }
    }

    class Day07Node
    {
        public Day07Node(string label, int? value = null, Day07Node? parent = null, List<Day07Node>? children = null)
        {
            Label = label;
            Value = value;
            Score = 0;
            Parent = parent;
            Children = children ?? new List<Day07Node>();
        }

        public string Label { get; set; }
        public int? Value { get; set; }
        public int? Score { get; set; }
        public Day07Node? Parent { get; set; }
        public List<Day07Node> Children { get; set; }
    }
}

