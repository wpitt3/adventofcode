using adventofcode;
using Xunit;

namespace adventOfCodeTests;

public class Day09Test
{
    [Fact]
    public void TestRemoveGarbage()
    {
        Assert.Equal("a", Day09.removeGarbage("<>a").Item1);
        Assert.Equal("a", Day09.removeGarbage("<<<<>a").Item1);
        Assert.Equal("a", Day09.removeGarbage("<{ !>}>a").Item1);
        Assert.Equal("a", Day09.removeGarbage("<!!>a").Item1);
        Assert.Equal("a", Day09.removeGarbage("<!!!>>a").Item1);
    }

    [Fact]
    public void TestScoreGroups()
    {
        Assert.Equal(1, Day09.scoreGroups("{}"));
        Assert.Equal(6, Day09.scoreGroups("{{{}}}"));
        Assert.Equal(16, Day09.scoreGroups("{{{},{},{{}}}}"));
       
    }

    [Fact]
    public void TestRemoveGarbageCount()
    {
        Assert.Equal(0, Day09.removeGarbage("<>").Item2);
        Assert.Equal(3, Day09.removeGarbage("<<<<>").Item2);
        Assert.Equal(17, Day09.removeGarbage("<random characters>").Item2);
        Assert.Equal(10, Day09.removeGarbage("<{o\"i!a,<{i<a>").Item2);
        Assert.Equal(0, Day09.removeGarbage("<!!!>>").Item2);

    }
}
