import student.TestCase;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * Tests for the LinkedDeque class.
 * 
 * @author Justis Phillips justisp
 * @version Nov 12, 2026
 */
public class LinkedDequeTest
    extends TestCase
{
    private LinkedDeque<String> deque;

    public void setUp()
    {
        deque = new LinkedDeque<>();
    }


    public void testEnqueueAddsToBack()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        assertEquals("A", deque.peek());
        assertEquals(2, deque.size());
    }


    public void testEnqueueNullRejected()
    {
        deque.enqueue("A");
        try
        {
            deque.enqueue(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1, deque.size());
        }
    }


    public void testDequeueRemovesFront()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        assertEquals("A", deque.dequeue());
        assertEquals("B", deque.peek());
        assertEquals(1, deque.size());
    }


    public void testDequeueEmptyThrows()
    {
        try
        {
            deque.dequeue();
            fail("Expected NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            assertTrue(deque.isEmpty());
        }
    }


    public void testDequeueLastElementEmptiesDeque()
    {
        deque.enqueue("A");
        deque.dequeue();
        assertTrue(deque.isEmpty());
        deque.enqueue("B"); // front/back must have been reset
        assertEquals("B", deque.peek());
    }


    public void testPeekDoesNotRemove()
    {
        deque.enqueue("A");
        assertEquals("A", deque.peek());
        assertEquals(1, deque.size());
    }


    public void testPeekEmptyThrows()
    {
        try
        {
            deque.peek();
            fail("Expected NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            assertTrue(deque.isEmpty());
        }
    }


    public void testIsEmptyAndSize()
    {
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
        deque.enqueue("A");
        assertFalse(deque.isEmpty());
        assertEquals(1, deque.size());
    }


    public void testRemoveMiddle()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        deque.enqueue("C");
        assertTrue(deque.remove("B"));
        assertEquals("[A, C]", contents());
        assertEquals(2, deque.size());
    }


    public void testRemoveFrontAndBack()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        deque.enqueue("C");
        assertTrue(deque.remove("A"));
        assertTrue(deque.remove("C"));
        assertEquals("[B]", contents());
        deque.enqueue("D"); // back reference must be correct
        assertEquals("[B, D]", contents());
    }


    public void testRemoveMissingReturnsFalse()
    {
        deque.enqueue("A");
        assertFalse(deque.remove("Z"));
        assertFalse(deque.remove(null));
        assertEquals("[A]", contents());
        assertFalse(new LinkedDeque<String>().remove("A"));
    }


    public void testAddFrontInsertsAtFront()
    {
        deque.enqueue("B");
        deque.addFront("A");
        assertEquals("A", deque.peek());
        assertEquals("[A, B]", contents());
    }


    public void testAddFrontOnEmptyThenEnqueue()
    {
        deque.addFront("A");
        deque.enqueue("B");
        assertEquals("[A, B]", contents());
    }


    public void testAddFrontNullRejected()
    {
        try
        {
            deque.addFront(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(deque.isEmpty());
        }
    }


    public void testGetReturnsElementsByIndex()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        deque.enqueue("C");
        deque.enqueue("D");
        assertEquals("A", deque.get(0));
        assertEquals("B", deque.get(1));
        assertEquals("C", deque.get(2));
        assertEquals("D", deque.get(3));
        assertEquals(4, deque.size());
    }


    public void testGetOutOfRangeThrows()
    {
        try
        {
            deque.get(0);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            assertTrue(deque.isEmpty());
        }
        deque.enqueue("A");
        try
        {
            deque.get(-1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            assertEquals(1, deque.size());
        }
        try
        {
            deque.get(1);
            fail("Expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            assertEquals(1, deque.size());
        }
    }


    public void testFifoOrderKeptThroughMixedOperations()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        deque.enqueue("C");
        assertEquals("A", deque.dequeue());
        deque.enqueue("D");
        deque.remove("C");
        assertEquals("B", deque.dequeue());
        assertEquals("D", deque.dequeue());
        assertTrue(deque.isEmpty());
    }


    private String contents()
    {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < deque.size(); i++)
        {
            if (i > 0)
            {
                sb.append(", ");
            }
            sb.append(deque.get(i));
        }
        return sb.append("]").toString();
    }
}
