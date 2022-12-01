using adventofcode;
using Xunit;

namespace adventOfCodeTests;

public class Day14Test
{
    [Fact]
    public void TestHexToBinary()
    {
        Assert.Equal("11010100", Day14.hexToBinary("d4"));
        Assert.Equal("0000000100100011010001010110011110001001101010111100110111101111", Day14.hexToBinary("0123456789abcdef"));
    }
}
