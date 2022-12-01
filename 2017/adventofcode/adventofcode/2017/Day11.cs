using System;
namespace adventofcode
{
    public class Day11
    {
        public Day11()
        {
            List<string> input = FileReader.ReadLine("11")!.Split(",").ToList();
            var (a, b) = partA(input);

            Console.WriteLine(a);
            Console.WriteLine(b);
        }

        private static (int, int) partA(List<string> input)
        {

            int max = 0;
            var (x, y) = (0, 0);
            foreach (var direction in input)
            {
                var (mx, my) = getDirection(direction);
                x += mx;
                y += my;

                if (getDistance(x, y) > max)
                {
                    max = getDistance(x, y);
                }
            }

            return (getDistance(x, y), max);
        }

        private static int getDistance(int x, int y)
        {
            return (Math.Abs(y) - Math.Abs(x)) / 2 + Math.Abs(x);
        }

        private static (int, int) getDirection(string direction)
        {
            switch (direction)
            {
                case "ne":
                    return (1, -1);
                case "nw":
                    return (-1, -1);
                case "se":
                    return (1, 1);
                case "sw":
                    return (-1, 1);
                case "n":
                    return (0, -2);
                case "s":
                    return (0, 2);
                default:
                    return (0, 0);
            }
        }
    }
}

