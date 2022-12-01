using System;
namespace adventofcode
{
    public class Day16 {
        public Day16() {
            Console.WriteLine(partA());

            var input = FileReader.ReadLine("16")!.Split(",");
            var refinedInput = new List<Day16Command>();
            var length = 16;

            var dancers = Enumerable.Range(0, length).Select(it => (char)(it + 97)).ToArray();

            foreach (var line in input)
            {
                switch (line[0])
                {
                    case 'x':
                        var split = line[1..].Split("/");
                        refinedInput.Add(new Day16Command(line[0], null, (int.Parse(split[0]), int.Parse(split[1])), null));
                        break;
                    case 's':
                        int index = int.Parse(line[1..]);
                        refinedInput.Add(new Day16Command(line[0], index, null, null));
                        break;
                    case 'p':
                        refinedInput.Add(new Day16Command(line[0], null, null, (line[1], line[3])));
                        break;
                    default:
                        throw new Exception("SHITE");
                }
            }

            

            var seen = new Dictionary<String, int>();
            var indexedSeen = new List<String>();

            var dancersToIndex = dancerToIndex(dancers);
            var offset = 0;
            var (a, b) = (0, 0);

            var i = 0;
            var done = false;
            var display = string.Join("", dancers[offset..]) + string.Join("", dancers[..offset]);
            seen.Add(display, i);
            indexedSeen.Add(display);

            while (!done) {

                foreach (var line in refinedInput)
                {
                    switch (line.Move)
                    {
                        case 'x':
                            (a, b) = ((line.PR.Item1 + offset) % 16, (line.PR.Item2 + offset) % 16);
                            (dancersToIndex[dancers[a]], dancersToIndex[dancers[b]]) = (b, a);
                            (dancers[a], dancers[b]) = (dancers[b], dancers[a]);
                            break;
                        case 's':
                            offset = ( offset + 32 - line.O) % 16;
                            break;
                        case 'p':
                            (dancers[dancersToIndex[line.IdRemap.Item2]], dancers[dancersToIndex[line.IdRemap.Item1]]) = (dancers[dancersToIndex[line.IdRemap.Item1]], dancers[dancersToIndex[line.IdRemap.Item2]]);
                            (dancersToIndex[line.IdRemap.Item2], dancersToIndex[line.IdRemap.Item1]) = (dancersToIndex[line.IdRemap.Item1], dancersToIndex[line.IdRemap.Item2]);
                            break;
                        default:
                            throw new Exception("SHITE");
                    }
                }

                i++;
                display = string.Join("", dancers[offset..]) + string.Join("", dancers[..offset]);
                if (seen.ContainsKey(display))
                {
                    done = true;
                }
                else
                {
                    seen.Add(display, i);
                    indexedSeen.Add(display);
                }

            }

            Console.WriteLine(indexedSeen[1_000_000_000 % (i - seen[display]) + seen[display]]);
        }

        private static string partA()
        {
            var input = FileReader.ReadLine("16")!.Split(",");
            var length = 16;
            var dancers = Enumerable.Range(0, length).Select(it => (char)(it + 97)).ToList();
            foreach (var line in input)
            {
                switch (line[0])
                {
                    case 'x':
                        var split = line[1..].Split("/");
                        var (a, b) = (int.Parse(split[0]), int.Parse(split[1]));
                        (dancers[a], dancers[b]) = (dancers[b], dancers[a]);
                        break;
                    case 'p':
                        var dancersToIndex = dancerToIndex(dancers.ToArray());
                        (dancers[dancersToIndex[line[1]]], dancers[dancersToIndex[line[3]]]) = (dancers[dancersToIndex[line[3]]], dancers[dancersToIndex[line[1]]]);
                        break;
                    case 's':
                        int index = int.Parse(line[1..]);
                        dancers = dancers.Skip(length - index).Concat(dancers.Take(length - index)).ToList();
                        break;
                    default:
                        throw new Exception("SHITE");
                }
            }
            return string.Join("", dancers);
        }

        private static Dictionary<char, int> dancerToIndex(char[] dancers)
        {
            var dancerToIndex = new Dictionary<char, int>();
            for (int j = 0; j < 16; j++)
            {
                dancerToIndex[dancers[j]] = j;
            }
            return dancerToIndex;
        }

        class Day16Command
        {
            public Day16Command(char move, int? offset, (int,int)? posRemap, (char, char)? idRemap)
            {
                Move = move;
                O = offset ?? -1;
                PR = (posRemap?.Item1 ?? -1, posRemap?.Item2 ?? -1);
                IdRemap = (idRemap?.Item1 ?? ' ', idRemap?.Item2 ?? ' ');
            }
            public char Move { get; }
            public int O { get; }
            public (int, int) PR { get; }
            public (char, char) IdRemap { get; }
        }
    }
}

