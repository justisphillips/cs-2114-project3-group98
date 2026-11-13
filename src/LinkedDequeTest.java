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

    /**
     * Creates a new empty deque before each test.
     */
    public void setUp()
    {
        deque = new LinkedDeque<>();
    }


    /**
     * Tests that enqueue adds elements to the back, so the first element added
     * stays at the front.
     */
    public void testEnqueueAddsToBack()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        assertEquals("A", deque.peek());
        assertEquals(2, deque.size());
    }


    /**
     * Tests that enqueue rejects a null element with an
     * IllegalArgumentException and leaves the deque unchanged.
     */
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


    /**
     * Tests that dequeue removes and returns the front element and the next
     * element becomes the front.
     */
    public void testDequeueRemovesFront()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        assertEquals("A", deque.dequeue());
        assertEquals("B", deque.peek());
        assertEquals(1, deque.size());
    }


    /**
     * Tests that dequeue on an empty deque throws a NoSuchElementException.
     */
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


    /**
     * Tests that removing the only element empties the deque and that the deque
     * still works when an element is added afterward.
     */
    public void testDequeueLastElementEmptiesDeque()
    {
        deque.enqueue("A");
        deque.dequeue();
        assertTrue(deque.isEmpty());
        deque.enqueue("B"); // front/back must have been reset
        assertEquals("B", deque.peek());
    }


    /**
     * Tests that peek returns the front element without removing it.
     */
    public void testPeekDoesNotRemove()
    {
        deque.enqueue("A");
        assertEquals("A", deque.peek());
        assertEquals(1, deque.size());
    }


    /**
     * Tests that peek on an empty deque throws a NoSuchElementException.
     */
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


    /**
     * Tests that isEmpty and size report the right values for an empty deque
     * and after one element is added.
     */
    public void testIsEmptyAndSize()
    {
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
        deque.enqueue("A");
        assertFalse(deque.isEmpty());
        assertEquals(1, deque.size());
    }


    /**
     * Tests that remove can take an element out of the middle of the deque
     * while keeping the others in order.
     */
    public void testRemoveMiddle()
    {
        deque.enqueue("A");
        deque.enqueue("B");
        deque.enqueue("C");
        assertTrue(deque.remove("B"));
        assertEquals("[A, C]", contents());
        assertEquals(2, deque.size());
    }


    /**
     * Tests that remove works on the front and back elements and that the front
     * and back references stay correct afterward.
     */
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


    /**
     * Tests that remove returns false and changes nothing when the element is
     * missing, is null, or the deque is empty.
     */
    public void testRemoveMissingReturnsFalse()
    {
        deque.enqueue("A");
        assertFalse(deque.remove("Z"));
        assertFalse(deque.remove(null));
        assertEquals("[A]", contents());
        assertFalse(new LinkedDeque<String>().remove("A"));
    }


    /**
     * Tests that addFront inserts an element at the front of the deque.
     */
    public void testAddFrontInsertsAtFront()
    {
        deque.enqueue("B");
        deque.addFront("A");
        assertEquals("A", deque.peek());
        assertEquals("[A, B]", contents());
    }


    /**
     * Tests that addFront works on an empty deque and that enqueue still adds
     * to the back afterward.
     */
    public void testAddFrontOnEmptyThenEnqueue()
    {
        deque.addFront("A");
        deque.enqueue("B");
        assertEquals("[A, B]", contents());
    }


    /**
     * Tests that addFront rejects a null element with an
     * IllegalArgumentException.
     */
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


    /**
     * Tests that get returns each element at its index, with index 0 as the
     * front, without removing anything.
     */
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


    /**
     * Tests that get throws an IndexOutOfBoundsException for an empty deque, a
     * negative index, and an index equal to the size.
     */
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


    /**
     * Tests that first-in, first-out order is kept through a mix of enqueue,
     * remove, and dequeue calls.
     */
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


    /**
     * Builds a string of the deque's contents from front to back, such as "[A,
     * B]", so tests can check the order in one assertion.
     *
     * @return the contents of the deque as a string
     */
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
