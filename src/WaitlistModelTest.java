
package queuetheencore;

import student.TestCase;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tests the WaitlistModel class.
 *
 * @author Hela Michael
 * @version Nov 13, 2026
 */
public class WaitlistModelTest extends TestCase
{
    private WaitlistModel model;

    /**
     * Creates a new model before each test.
     */
    public void setUp()
    {
        model = new WaitlistModel();
    }


    /**
     * Tests that a new waitlist starts empty.
     */
    public void testConstructor()
    {
        assertTrue(model.isEmpty());
        assertEquals(0, model.getWaitlist().size());
    }


    /**
     * Tests adding fans to the waitlist.
     */
    public void testJoinWaitlist()
    {
        Fan sarah = model.joinWaitlist("Sarah");
        Fan michael = model.joinWaitlist("Michael");

        assertEquals("Sarah", sarah.getName());
        assertEquals("Michael", michael.getName());

        assertEquals(1, sarah.getFanId());
        assertEquals(2, michael.getFanId());

        assertEquals(2, model.getWaitlist().size());
        assertFalse(model.isEmpty());
    }


    /**
     * Tests that duplicate names are allowed.
     */
    public void testDuplicateNames()
    {
        Fan first = model.joinWaitlist("Sarah");
        Fan second = model.joinWaitlist("Sarah");

        assertEquals("Sarah", first.getName());
        assertEquals("Sarah", second.getName());

        assertFalse(first.getFanId() == second.getFanId());
        assertEquals(2, model.getWaitlist().size());
    }


    /**
     * Tests that names are trimmed.
     */
    public void testTrimName()
    {
        Fan fan = model.joinWaitlist("  Sarah  ");

        assertEquals("Sarah", fan.getName());
    }


    /**
     * Tests that invalid names cannot join.
     */
    public void testInvalidName()
    {
        try
        {
            model.joinWaitlist("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // Expected exception
        }

        try
        {
            model.joinWaitlist("   ");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // Expected exception
        }

        try
        {
            model.joinWaitlist(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // Expected exception
        }

        assertTrue(model.isEmpty());
    }


    /**
     * Tests removing a fan from the waitlist.
     */
    public void testLeaveWaitlist()
    {
        Fan sarah = model.joinWaitlist("Sarah");
        Fan michael = model.joinWaitlist("Michael");
        Fan jordan = model.joinWaitlist("Jordan");

        assertTrue(model.leaveWaitlist(michael.getFanId()));

        assertEquals(2, model.getWaitlist().size());
        assertEquals(-1, model.getPosition(michael.getFanId()));
        assertEquals(1, model.getPosition(sarah.getFanId()));
        assertEquals(2, model.getPosition(jordan.getFanId()));
    }


    /**
     * Tests removing the first and last fans.
     */
    public void testLeaveFirstAndLast()
    {
        Fan sarah = model.joinWaitlist("Sarah");
        Fan michael = model.joinWaitlist("Michael");
        Fan jordan = model.joinWaitlist("Jordan");

        assertTrue(model.leaveWaitlist(sarah.getFanId()));
        assertTrue(model.leaveWaitlist(jordan.getFanId()));

        assertEquals(1, model.getWaitlist().size());
        assertEquals(1, model.getPosition(michael.getFanId()));
    }


    /**
     * Tests removing a nonexistent fan.
     */
    public void testLeaveInvalidFan()
    {
        model.joinWaitlist("Sarah");

        assertFalse(model.leaveWaitlist(999));
        assertEquals(1, model.getWaitlist().size());
    }


    /**
     * Tests processing fans in FIFO order.
     */
    public void testProcessNextFan()
    {
        Fan sarah = model.joinWaitlist("Sarah");
        Fan michael = model.joinWaitlist("Michael");

        assertSame(sarah, model.processNextFan());
        assertSame(michael, model.processNextFan());

        assertTrue(model.isEmpty());
    }


    /**
     * Tests processing an empty waitlist.
     */
    public void testProcessEmptyWaitlist()
    {
        try
        {
            model.processNextFan();
            fail("Expected NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected exception
        }
    }


    /**
     * Tests getting a fan's position.
     */
    public void testGetPosition()
    {
        Fan sarah = model.joinWaitlist("Sarah");
        Fan michael = model.joinWaitlist("Michael");
        Fan jordan = model.joinWaitlist("Jordan");

        assertEquals(1, model.getPosition(sarah.getFanId()));
        assertEquals(2, model.getPosition(michael.getFanId()));
        assertEquals(3, model.getPosition(jordan.getFanId()));

        assertEquals(-1, model.getPosition(999));
    }


    /**
     * Tests that positions update after processing.
     */
    public void testPositionAfterProcessing()
    {
        model.joinWaitlist("Sarah");
        Fan michael = model.joinWaitlist("Michael");

        model.processNextFan();

        assertEquals(1, model.getPosition(michael.getFanId()));
    }


    /**
     * Tests retrieving the waitlist in order.
     */
    public void testGetWaitlist()
    {
        Fan sarah = model.joinWaitlist("Sarah");
        Fan michael = model.joinWaitlist("Michael");

        List<Fan> fans = model.getWaitlist();

        assertEquals(2, fans.size());
        assertSame(sarah, fans.get(0));
        assertSame(michael, fans.get(1));
    }


    /**
     * Tests that getWaitlist returns a copy.
     */
    public void testGetWaitlistCopy()
    {
        model.joinWaitlist("Sarah");

        List<Fan> fans = model.getWaitlist();
        fans.clear();

        assertEquals(1, model.getWaitlist().size());
        assertFalse(model.isEmpty());
    }


    /**
     * Tests isEmpty after adding and removing fans.
     */
    public void testIsEmpty()
    {
        assertTrue(model.isEmpty());

        Fan sarah = model.joinWaitlist("Sarah");

        assertFalse(model.isEmpty());

        model.leaveWaitlist(sarah.getFanId());

        assertTrue(model.isEmpty());
    }


    /**
     * Tests that fan IDs are not reused.
     */
    public void testFanIdsNotReused()
    {
        Fan first = model.joinWaitlist("Sarah");

        model.leaveWaitlist(first.getFanId());

        Fan second = model.joinWaitlist("Michael");

        assertEquals(1, first.getFanId());
        assertEquals(2, second.getFanId());
    }
}
