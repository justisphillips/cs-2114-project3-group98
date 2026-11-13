import student.TestCase;

/**
 * Tests the Fan class for the concert waitlist.
 */
public class FanTest extends TestCase {

    private Fan fan;

    /**
     * Sets up a Fan object before each test.
     */
    public void setUp() {
        fan = new Fan("Sarah", 1);
    }

    /**
     * Tests that the constructor correctly initializes
     * the fan's name and ID.
     */
    public void testConstructor() {
        assertEquals("Sarah", fan.getName());
        assertEquals(1, fan.getFanId());
        assertFalse(fan.isFanClubMember());
    }

    /**
     * Tests that getName returns the correct name.
     */
    public void testGetName() {
        assertEquals("Sarah", fan.getName());
    }

    /**
     * Tests getName with another fan.
     */
    public void testGetNameDifferentFan() {
        Fan anotherFan = new Fan("Ben", 2);

        assertEquals("Ben", anotherFan.getName());
    }

    /**
     * Tests that getFanId returns the correct ID.
     */
    public void testGetFanId() {
        assertEquals(1, fan.getFanId());
    }

    /**
     * Tests getFanId with another fan.
     */
    public void testGetFanIdDifferentFan() {
        Fan anotherFan = new Fan("Ben", 2);

        assertEquals(2, anotherFan.getFanId());
    }

    /**
     * Tests that fan club membership is false
     * by default.
     */
    public void testDefaultMembership() {
        assertFalse(fan.isFanClubMember());
    }

    /**
     * Tests changing fan club membership to true.
     */
    public void testSetFanClubMemberTrue() {
        fan.setFanClubMember(true);

        assertTrue(fan.isFanClubMember());
    }

    /**
     * Tests changing fan club membership back to false.
     */
    public void testSetFanClubMemberFalse() {
        fan.setFanClubMember(true);
        fan.setFanClubMember(false);

        assertFalse(fan.isFanClubMember());
    }

    /**
     * Tests that changing membership does not
     * change the fan's name or ID.
     */
    public void testMembershipDoesNotChangeFanInfo() {
        fan.setFanClubMember(true);

        assertEquals("Sarah", fan.getName());
        assertEquals(1, fan.getFanId());
    }
}
