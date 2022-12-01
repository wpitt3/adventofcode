using adventofcode;
using Xunit;

namespace adventOfCodeTests;

public class Day10Test
{
    [Fact]
    public void TestReadStringAsBytes()
    {
        Assert.Equal(new int[]{ 49, 44, 50, 44, 51 }, Day10.readStringAsBytes("1,2,3"));
       
    }

    [Fact]
    public void TestDenseHash()
    {
        var input = new int[] { 65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22 }.ToList();
        input.AddRange(new int[256 - 16].ToList());

        Assert.Equal(64, Day10.createDenseHash(input.ToArray())[0]);
    }

    [Fact]
    public void TestDenseHash2()
    {
        var input = new int[16].ToList();

        input.AddRange(new int[] { 65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22 }.ToList());
        input.AddRange(new int[256 - 32].ToList());

        Assert.Equal(64, Day10.createDenseHash(input.ToArray())[1]);
    }

    [Fact]
    public void TestToHex()
    {
        Assert.Equal("40", Day10.toHex( 64 / 16) + Day10.toHex( 64 % 16));
        Assert.Equal("07", Day10.toHex(  7 / 16) + Day10.toHex(  7 % 16));
        Assert.Equal("ff", Day10.toHex(255 / 16) + Day10.toHex(255 % 16));
        Assert.Equal("a2", Day10.toHex(162 / 16) + Day10.toHex(162 % 16));
    }
}
