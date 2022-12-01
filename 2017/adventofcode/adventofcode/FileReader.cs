using System;
using System.IO;

namespace adventofcode
{
    public class FileReader {
        public static string ReadLine(string day)
        {
            try
            {
                using (var sr = new StreamReader("/usr/local/development/dotnet/adventofcode/adventofcode/resources/" + day + ".txt"))
                {
                    return sr.ReadToEnd();
                }
            }
            catch (IOException e)
            {
                Console.WriteLine("The file could not be read:");
                Console.WriteLine(e.Message);
            }
            return "";
        }

        public static List<string> ReadLines(string day)
        {
            List<string> result = new List<string>();
            try
            {
                using (var sr = new StreamReader("/usr/local/development/dotnet/adventofcode/adventofcode/resources/" + day + ".txt"))
                {
                    String? line;
                    while ((line = sr.ReadLine()) != null)
                    {
                        result.Add(line);
                    }
                }
            }
            catch (IOException e)
            {
                Console.WriteLine("The file could not be read:");
                Console.WriteLine(e.Message);
            }
            return result;
        }
    }
}

