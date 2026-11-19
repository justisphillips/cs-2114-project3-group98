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


    // ---- Fan Club Priority ----

    /**
     * Tests that a fan club member is marked as a member and a regular fan is
     * not.
     */
    public void testJoinFanClubMember()
    {
        Fan member = model.joinWaitlist("Sarah", true);
        Fan regular = model.joinWaitlist("Michael");

        assertTrue(member.isFanClubMember());
        assertFalse(regular.isFanClubMember());
    }


    /**
     * Tests that a fan club member who joins later goes ahead of a regular
     * fan.
     */
    public void testFanClubMemberGoesAhead()
    {
        Fan regular = model.joinWaitlist("Sarah");
        Fan member = model.joinWaitlist("Michael", true);

        assertEquals(1, model.getPosition(member.getFanId()));
        assertEquals(2, model.getPosition(regular.getFanId()));
    }


    /**
     * Tests that FIFO order is kept inside each group and fan club members
     * come first in the waitlist.
     */
    public void testFifoWithinEachGroup()
    {
        Fan r1 = model.joinWaitlist("Reg One");
        Fan c1 = model.joinWaitlist("Club One", true);
        Fan r2 = model.joinWaitlist("Reg Two");
        Fan c2 = model.joinWaitlist("Club Two", true);

        List<Fan> fans = model.getWaitlist();

        assertEquals(4, fans.size());
        assertSame(c1, fans.get(0));
        assertSame(c2, fans.get(1));
        assertSame(r1, fans.get(2));
        assertSame(r2, fans.get(3));
        assertEquals(3, model.getPosition(r1.getFanId()));
        assertEquals(4, model.getPosition(r2.getFanId()));
    }


    /**
     * Tests that processNextFan serves fan club members before regular fans.
     */
    public void testProcessFanClubFirst()
    {
        Fan regular = model.joinWaitlist("Sarah");
        Fan member = model.joinWaitlist("Michael", true);

        assertSame(member, model.processNextFan());
        assertSame(regular, model.processNextFan());
        assertTrue(model.isEmpty());
    }


    /**
     * Tests order after interleaved joins and processing with both groups.
     */
    public void testInterleavedJoinsAndProcessing()
    {
        Fan r1 = model.joinWaitlist("Reg One");
        Fan c1 = model.joinWaitlist("Club One", true);
        assertSame(c1, model.processNextFan());

        Fan r2 = model.joinWaitlist("Reg Two");
        Fan c2 = model.joinWaitlist("Club Two", true);

        assertSame(c2, model.processNextFan());
        assertSame(r1, model.processNextFan());

        Fan c3 = model.joinWaitlist("Club Three", true);

        assertSame(c3, model.processNextFan());
        assertSame(r2, model.processNextFan());
        assertTrue(model.isEmpty());
    }


    /**
     * Tests that a fan club member can leave and positions update.
     */
    public void testLeaveFanClubMember()
    {
        Fan c1 = model.joinWaitlist("Club One", true);
        Fan c2 = model.joinWaitlist("Club Two", true);
        Fan regular = model.joinWaitlist("Sarah");

        assertTrue(model.leaveWaitlist(c1.getFanId()));

        assertEquals(-1, model.getPosition(c1.getFanId()));
        assertEquals(1, model.getPosition(c2.getFanId()));
        assertEquals(2, model.getPosition(regular.getFanId()));
    }


    /**
     * Tests that regular fans move up when a fan club member is processed.
     */
    public void testPositionAfterProcessingFanClub()
    {
        Fan regular = model.joinWaitlist("Sarah");
        model.joinWaitlist("Michael", true);

        model.processNextFan();

        assertEquals(1, model.getPosition(regular.getFanId()));
    }


    // ---- Bad input ----

    /**
     * Tests that names with invalid characters are rejected and do not
     * change the waitlist.
     */
    public void testInvalidCharactersRejected()
    {
        String[] bad = {"R2D2!", "Sarah@home", "<b>Ben</b>", "Cy;DROP"};

        for (String name : bad)
        {
            try
            {
                model.joinWaitlist(name);
                fail("Expected IllegalArgumentException for " + name);
            }
            catch (IllegalArgumentException e)
            {
                // Expected exception
            }

            try
            {
                model.joinWaitlist(name, true);
                fail("Expected IllegalArgumentException for " + name);
            }
            catch (IllegalArgumentException e)
            {
                // Expected exception
            }
        }

        assertTrue(model.isEmpty());
    }


    /**
     * Tests that letters, numbers, spaces, apostrophes, hyphens, and periods
     * are accepted in names.
     */
    public void testValidCharactersAccepted()
    {
        Fan fan = model.joinWaitlist("Mary-Jane O'Neil Jr. 2");

        assertEquals("Mary-Jane O'Neil Jr. 2", fan.getName());
        assertEquals(1, model.getWaitlist().size());
    }


    /**
     * Tests that a blank name is rejected for a fan club member too.
     */
    public void testBlankNameFanClubRejected()
    {
        try
        {
            model.joinWaitlist("  ", true);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // Expected exception
        }

        assertTrue(model.isEmpty());
    }


    /**
     * Tests that a rejected name does not use up a fan ID.
     */
    public void testRejectedNameDoesNotUseId()
    {
        try
        {
            model.joinWaitlist("R2D2!");
        }
        catch (IllegalArgumentException e)
        {
            // Expected exception
        }

        assertEquals(1, model.joinWaitlist("Sarah").getFanId());
    }


    /**
     * Tests that a fan who was already served cannot leave or be found.
     */
    public void testServedFanCannotLeave()
    {
        Fan sarah = model.joinWaitlist("Sarah");
        model.joinWaitlist("Michael");

        model.processNextFan();

        assertFalse(model.leaveWaitlist(sarah.getFanId()));
        assertEquals(-1, model.getPosition(sarah.getFanId()));
        assertEquals(1, model.getWaitlist().size());
    }


    /**
     * Tests that a fan who already left cannot leave a second time.
     */
    public void testLeaveTwiceReturnsFalse()
    {
        Fan sarah = model.joinWaitlist("Sarah", true);
        model.joinWaitlist("Michael");

        assertTrue(model.leaveWaitlist(sarah.getFanId()));
        assertFalse(model.leaveWaitlist(sarah.getFanId()));
        assertEquals(1, model.getWaitlist().size());
    }


    /**
     * Tests that zero and negative IDs are not found.
     */
    public void testZeroAndNegativeIds()
    {
        model.joinWaitlist("Sarah");

        assertFalse(model.leaveWaitlist(0));
        assertFalse(model.leaveWaitlist(-5));
        assertEquals(-1, model.getPosition(0));
        assertEquals(-1, model.getPosition(-5));
        assertEquals(1, model.getWaitlist().size());
    }


    /**
     * Tests that processing an empty waitlist still throws after every fan
     * has been served from both groups.
     */
    public void testProcessEmptyAfterServingAll()
    {
        model.joinWaitlist("Sarah");
        model.joinWaitlist("Michael", true);
        model.processNextFan();
        model.processNextFan();

        try
        {
            model.processNextFan();
            fail("Expected NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            assertTrue(model.isEmpty());
        }
    }


    /**
     * Tests that IDs are unique across both groups.
     */
    public void testUniqueIdsAcrossGroups()
    {
        Fan a = model.joinWaitlist("Sarah");
        Fan b = model.joinWaitlist("Sarah", true);

        assertFalse(a.getFanId() == b.getFanId());
    }
}
