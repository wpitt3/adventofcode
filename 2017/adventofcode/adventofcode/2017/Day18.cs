using System;
using System.Reflection;
// 6374 < < 13716 !6985  6858?
namespace adventofcode
{
    public class Day18
    {

        Day18Command[] instructions;
        public async Task<bool> partB()
        {
            instructions = FileReader.ReadLines("18")!.Select(it => it.Split(" ")).Select(it => {
                var y = extractY(it);
                return new Day18Command(it[0], ((int)it[1][0]) - 97, y.Item1, y.Item2);
            }).ToArray();


            var (aList, bList) = (new List<long>(), new List<long>());
            var (aIndex, bIndex) = (new int[]{0}, new int[] { 0 });

            //var isDone = () => aList.Count <= aIndex[0] && bList.Count <= bIndex[0];
            var isDone = () => false;

            Parallel.Invoke(
                () => asyncRun(instructions, (x) => bList.Add(x), isDone, aList, aIndex, 0L),
                () => asyncRun(instructions, (x) => aList.Add(x), isDone, bList, bIndex, 1L)
            );

            await Task.Delay(100);
           
            Console.WriteLine(aList.Count);
           
            return true;
        }

        public async Task<long> receive(List<long> instruction, int[] index) {
            await awaitUntil(() => instruction.Count > index[0]);
            return instruction[index[0]++];
        }

        private async Task awaitUntil(Func<bool> func)
        {
            while (!func())
            {
                await Task.Delay(1);
            }
        }

        private async void asyncRun(Day18Command[] instructions, Action<long> send, Func<bool> isDone, List<long> list, int[] counter, long p)
        {
            var registers = new long[16];
            registers[15] = p;
            var index = 0;
            var done = false;

            var i = 0;
            while (!done && index < instructions.Length)
            {
                var inst = instructions[index];
                //printRegisters(registers);
                //Console.WriteLine(p + "  " + i + " " + inst.Action + " " + inst.Index + " " + (inst.Action != "snd" && inst.Action != "rcv" ? findY(inst, registers) : ""));
               
                switch (inst.Action)
                {
                    case "snd":
                        send(inst.Index < 0 ? inst.Index + 49 : registers[inst.Index]);
                        break;
                    case "set":
                        registers[inst.Index] = findY(inst, registers);
                        break;
                    case "add":
                        registers[inst.Index] += findY(inst, registers);
                        break;
                    case "mul":
                        registers[inst.Index] *= findY(inst, registers);
                        break;
                    case "mod":
                        registers[inst.Index] %= findY(inst, registers);
                        break;
                    case "rcv":
                        if (isDone())
                        {
                            done = true;
                        } else {
                            var x = await receive(list, counter);
                            registers[inst.Index] = x;
                        }
                        break;
                    case "jgz":
                        if (inst.Index < 0 || registers[inst.Index] > 0)
                        {
                            index += (int)(findY(inst, registers)); index--;
                        }
                        break;
                    default:
                        throw new Exception("SHITE");
                }
                index++;
                i++;
            }
            Console.WriteLine("DONE");
        }

        public void partA()
        {
            instructions = FileReader.ReadLines("18")!.Select(it => it.Split(" ")).Select(it => {
                var y = extractY(it);
                return new Day18Command(it[0], ((int)it[1][0]) - 97, y.Item1, y.Item2);
            }).ToArray();

            var registers = new long[16];
            var index = 0;
            var done = false;
            var sound = -1L;

            while (!done)
            {
                var inst = instructions[index];
                switch (inst.Action)
                {
                    case "snd":
                        sound = registers[inst.Index];
                        break;
                    case "set":
                        registers[inst.Index] = findY(inst, registers);
                        break;
                    case "add":
                        registers[inst.Index] += findY(inst, registers);
                        break;
                    case "mul":
                        registers[inst.Index] *= findY(inst, registers);
                        break;
                    case "mod":
                        registers[inst.Index] %= findY(inst, registers);
                        break;
                    case "rcv":
                        if (registers[inst.Index] != 0)
                        {
                            done = true;
                        }
                        break;
                    case "jgz":
                        if (registers[inst.Index] > 0)
                        {
                            index += (int)(findY(inst, registers)); index--;
                        }
                        break;
                    default:
                        throw new Exception("SHITE");
                }
                index++;

            }
            Console.WriteLine(sound);
        }

        private static long findY(Day18Command inst, long[] registers)
        {
            return inst.YAsValue ?? registers[inst.YAsIndex ?? -1];
        }


        private static (int?, int?) extractY(string[] y)
        {
            return y.Length < 3 ? (null, null) : (113 > (int)y[2][0] && (int)y[2][0] > 96 ? (null, ((int)y[2][0])-97) : (int.Parse(y[2]), null));
        }

        private static void printRegisters(long[] registers)
        {
            var newRegisters = new long[] { registers[0], registers[1], registers[5], registers[8], registers[15] };
            Console.WriteLine("                            |     " + String.Join(" ", newRegisters.Select(it => it.ToString().PadLeft(17, ' '))));
        }
    }

    public struct Day18Command
    {
        public Day18Command(string action, int index, int? yAsValue, int? yAsIndex)
        {
            Action = action;
            Index = index;
            YAsValue = yAsValue;
            YAsIndex = yAsIndex;
        }

        public string Action { get; }
        public int Index { get; }
        public int? YAsValue { get; }
        public int? YAsIndex { get; }
    }
}

