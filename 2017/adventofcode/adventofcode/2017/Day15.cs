using System;
namespace adventofcode
{
    public class Day15 {
        public Day15()
        {
            var (a, b) = (289L, 629L);
            var (aMul, bMul) = (16807L, 48271L);
            var mod = 2147483647L;

            int count = 0;

            for (int x = 0; x < 40_000_000; x++)
            {
                a = a * aMul % mod;
                b = b * bMul % mod;
                if ( a % 65536 == b % 65536)
                {
                    count++;
                }

            }
            Console.WriteLine(count);

            count = 0;
            (a, b) = (289L, 629L);
            for (int x = 0; x < 5_000_000; x++)
            {
                while (a % 4 != 0)
                {
                    a = a * aMul % mod;
                }

                while (b % 8 != 0)
                {
                    b = b * bMul % mod;
                }

                
                if (a % 65536 == b % 65536)
                {
                    count++;
                }

                a = a * aMul % mod;
                b = b * bMul % mod;
            }
            Console.WriteLine(count);
        }
    }
}

