﻿using System;
using System.IO;

namespace adventofcode
{
    public class Utils {
        public static void Repeat(int count, Action action)
        {
            for (int i = 0; i < count; i++)
            {
                action();
            }
        }
    }
}

