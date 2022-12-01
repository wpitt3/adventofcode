using System;
namespace adventofcode
{
    public class Day13 {
        public Day13() {
            List<int[]> input = FileReader.ReadLines("13")!.Select(it => it.Split(": ").Select(x => int.Parse(x)).ToArray()).ToList();


            int score = 0;
            foreach(var line in input)
            {
                if (findPosAtStep(line[0], line[1]) == 0)
                {
                    score += line[0] * line[1];
                }
            }

            int delay = 0;
            bool caught = true;
            while(caught)
            {
                caught = input.Any(line => findPosAtStep(line[0] + delay, line[1]) == 0);
                delay++;
            }
            delay--;


            Console.WriteLine(score);
            Console.WriteLine(delay);
        }

        private static int findPosAtStep(int time, int range) {
            var period = range == 1 ? 1 : (range - 1) * 2;
            var pos = time % period;
            return pos > range - 1 ? period - pos : pos;
        }
    }
}

