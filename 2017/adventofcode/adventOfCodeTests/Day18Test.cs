using adventofcode;
using Xunit;

namespace adventOfCodeTests;

public class Day18Test
{
    [Fact]
    public async void TestReceive()
    {
        var sharedList = new List<long>();
        var x = new int[] { 0 };

        Parallel.Invoke(
            () => waitAndThenAdd(sharedList, new List<long>() { 9, 8 })
        );

        Assert.Equal(9, await new Day18().receive(sharedList, x));
        Assert.Equal(8, await new Day18().receive(sharedList, x));
    }

    [Fact]
    public void TestIsDone()
    {
        var (aList, bList) = (new List<long>(), new List<long>());
        var (aIndex, bIndex) = (0, 0);

        var isDone = () => aList.Count <= aIndex && bList.Count <= bIndex;

        Assert.True(isDone());

        aList.Add(1L);

        Assert.False(isDone());

        aIndex++;

        Assert.True(isDone());

    }





    private async Task setResult(Task<long> func, long[] result, int max)
    {
        var i = 0;
        while (i < max)
        {
            result[0] = await func;
            i++;
        }
    }

    private async Task waitAndThenAdd(List<long> instruction, List<long> toAdd)
    {
        foreach (var add in toAdd)
        {
            await Task.Delay(30);
            instruction.Add(add);
        }
    }
}
