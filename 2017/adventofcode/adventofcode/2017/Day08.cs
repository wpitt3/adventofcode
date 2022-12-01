using System;
using System.Linq.Expressions;

namespace adventofcode
{
    public class Day08 {
        public Day08() {
            List<string> input = FileReader.ReadLines("08")!;

            List<Command> commands = generateCommands(input);

            var registers = new Dictionary<string, int>();
            foreach (var command in commands)
            {
                registers[command.KeyA] = 0;
                registers[command.KeyB] = 0;
            }

            int max = 0;

            foreach (var c in commands)
            {
                if (conditionMet(c, registers))
                {
                    registers[c.KeyA] += c.Change;
                    if (registers[c.KeyA] > max)
                    {
                        max = registers[c.KeyA];
                    }
                }
            }

            Console.WriteLine(registers.Values.Max());
            Console.WriteLine(max);
        }

        private static bool conditionMet(Command c, Dictionary<string, int> registers)
        {
            switch (c.Conditional)
            {
                case ">":
                    return registers[c.KeyB] > c.Required;
                case "<":
                    return registers[c.KeyB] < c.Required;
                case ">=":
                    return registers[c.KeyB] >= c.Required;
                case "<=":
                    return registers[c.KeyB] <= c.Required;
                case "==":
                    return registers[c.KeyB] == c.Required;
                case "!=":
                    return registers[c.KeyB] != c.Required;
                default:
                    return false;
            }
        }

        private static List<Command> generateCommands(List<string> input)
        {
            return input.Select(line => line.Split(" ")).Select(s => new Command(s[0], (s[1] == "inc" ? 1 : -1) * int.Parse(s[2]), s[4], s[5], int.Parse(s[6]))).ToList();
        }

        class Command
        {
            public Command(string keyA, int change, string keyB, string conditional, int required)
            {
                KeyA = keyA;
                Change = change;
                KeyB = keyB;
                Conditional = conditional;
                Required = required;
            }

            public string KeyA { get; }
            public string KeyB { get; }
            public string Conditional { get; }
            public int Change { get;}
            public int Required { get; }

        }
    }
}

