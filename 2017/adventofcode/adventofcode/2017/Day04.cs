using System;
using System.Linq;
using Microsoft.VisualBasic;

namespace adventofcode
{
    public class Day04 {
        public Day04() {
            List<string> input = FileReader.ReadLines("04")!;

            var passphrases = input.Select(x => x.Split(" ").ToList()).ToList();

            Console.WriteLine(partA(passphrases));
            Console.WriteLine(partB(passphrases));
        }

        private static int partA(List<List<string>> passphrases) {
            return passphrases.FindAll(passphrases => (passphrases.Count() == passphrases.ToHashSet().Count())).Count();
        }


        private static int partB(List<List<string>> passphrases)
        {
            return passphrases.Select(x => x.Select(y => sortString(y)).ToList()).ToList().FindAll(passphrases => (passphrases.Count() == passphrases.ToHashSet().Count())).Count();
        }

        private static string sortString(string x)
        {
            var y = x.ToCharArray().ToList();
            y.Sort();
            return string.Join("", y);
        }
    }
}

