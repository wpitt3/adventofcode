using System;
using static System.Net.Mime.MediaTypeNames;

namespace adventofcode
{
    public class Day17 {
        public Day17() {
            partA();
            partB();
        }

        private static void partA()
        {
            var currentNode = D17LL.first(0);
            for (int x = 1; x < 2018; x++)
            {
                for (int i = 0; i < 359; i++)
                {
                    currentNode = currentNode.Next;
                }
                insertAfter(currentNode, x);
                currentNode = currentNode.Next;
            }

            while (currentNode.Value != 2017)
            {
                currentNode = currentNode.Next;
            }
            Console.WriteLine(currentNode.Next.Value);
        }

        private static void partB()
        {
            var currentNode = D17LL.first(0);
            for (int x = 1; x < 50_000_000; x++)
            {
                for (int i = 0; i < 359; i++)
                {
                    currentNode = currentNode.Next;
                }
                insertAfter(currentNode, x);
                currentNode = currentNode.Next;
            }

            while (currentNode.Value != 0)
            {
                currentNode = currentNode.Next;
            }
            Console.WriteLine(currentNode.Next.Value);
        }

        private static void insertAfter(D17LL current, int value)
        {
            var next = current.Next;
            var newNode = new D17LL(next, value);
            current.Next = newNode;
        }
    }

    class D17LL
    {

        public static D17LL first(int value)
        {
            return new D17LL(value);
        }

        private D17LL(int value)
        {
            Value = value;
            Next = this;
        }

        public D17LL(D17LL next,  int value)
        {
            Next = next;
            Value = value;
        }
        public D17LL Next { get; set; }
        public int Value { get; set; }
    }
}

