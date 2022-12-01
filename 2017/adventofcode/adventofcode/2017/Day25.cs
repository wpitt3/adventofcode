using System;
using System.Linq;
using Microsoft.Win32;
using System.Reflection;
using System.ComponentModel;

namespace adventofcode
{
    public class Day25 {

        public Day25()
        {
            var state = 'A';
            var maxSteps = 12261543;
            var i = 10000;
            var step = 0;
            var registers = new bool[i * 2];

            while (step < maxSteps)
            {
                switch (state)
                {
                    case 'A':
                        if (!registers[i])
                        {
                            registers[i] = !registers[i];
                            i++;
                            state = 'B';
                        } else
                        {
                            registers[i] = !registers[i];
                            i--;
                            state = 'C';
                        }
                        break;
                    case 'B':
                        if (!registers[i])
                        {
                            registers[i] = !registers[i];
                            i--;
                            state = 'A';
                        }
                        else
                        {
                            i++;
                            state = 'C';
                        }
                        break;
                    case 'C':
                        if (!registers[i])
                        {
                            registers[i] = !registers[i];
                            i++;
                            state = 'A';
                        }
                        else
                        {
                            registers[i] = !registers[i];
                            i--;
                            state = 'D';
                        }
                        break;
                    case 'D':
                        if (!registers[i])
                        {
                            registers[i] = !registers[i];
                            i--;
                            state = 'E';
                        }
                        else
                        {
                            i--;
                            state = 'C';
                        }
                        break;
                    case 'E':
                        if (!registers[i])
                        {
                            registers[i] = !registers[i];
                            i++;
                            state = 'F';
                        }
                        else
                        {
                            i++;
                            state = 'A';
                        }
                        break;
                    case 'F':
                        if (!registers[i])
                        {
                            registers[i] = !registers[i];
                            i++;
                            state = 'A';
                        }
                        else
                        {
                            i++;
                            state = 'E';
                        }
                        break;
                    default:
                        throw new Exception("SHITE");
                }
                step++;
            }

            int count = 0;
            foreach(var x in registers)
            {
                if (x)
                {
                    count++;
                }
            }
            Console.WriteLine(count);
        }

    }
}







