using System;
namespace adventofcode
{
    public class Day02 {
        public Day02() {
            List<string> input = FileReader.ReadLines("02")!;

            var linesOfInts = input.Select(x => x.Split("\t").Select(it => int.Parse(it)).ToList()).ToList();

            Console.WriteLine(partA(linesOfInts));
            Console.WriteLine(partB(linesOfInts));
        }

        private static int partA(List<List<int>> input) {
            return input.Select(x => x.Max() - x.Min()).Sum();
        }

        private static int partB(List<List<int>> input)
        {
            return input.Select(inputValues => {

                inputValues.Sort();
                for (int i = 0; i < inputValues.Count - 1; i++)
                {
                    for (int j = i+1; j < inputValues.Count; j++)
                    {
                        if (inputValues[j] % inputValues[i] == 0)
                        {
                            return inputValues[j] / inputValues[i];
                        }
                    }
                }

                return 0;
            }).Sum();
        }
    }
}

