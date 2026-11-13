// -------------------------------------------------------------------------
/**
 * Represents a fan joining the QueueTheEncore concert waitlist.
 * Stores the fan's name, unique ID, and fan club membership status.
 * 
 *  @author helam
 *  @version Nov 13, 2026
 */
public class Fan
{
 // Instance variables
    private final String name;
    private final int fanID;
    private boolean fanClubMember;

    // ----------------------------------------------------------
    /**
     * Creates a new Fan with a name and unique ID.
     * Fan club membership is initially set to false.
     *
     * @param name
     *            the name of the fan
     * @param fanID
     *            the unique identification number of the fan
     */
    // Constructor
    public Fan(String name, int fanID) {
        this.name = name;
        this.fanID = fanID;
        this.fanClubMember = false;
    }

    // ----------------------------------------------------------
    /**
     * /**
     * Returns the fan's unique identification number.
     *
     * @return the fan's unique ID
     */
    public int getFanId() {
        return fanID;
    }

    
    // ----------------------------------------------------------
    /**
     /**
     * Returns the fan's name.
     *
     * @return the name of the fan
     */
    public String getName() {
        return name;
    }

    // ----------------------------------------------------------    
    /**
     * Checks whether the fan is a fan club member.
     *
     * @return true if the fan is a fan club member,
     *         false otherwise
     */
    public boolean isFanClubMember() {
        return fanClubMember;
    }

    // ----------------------------------------------------------
    /**
     * Updates the fan's fan club membership status.
     *
     * @param status
     *            true if the fan is a fan club member,
     *            false otherwise
     */
    public void setFanClubMember(boolean status) {
        fanClubMember = status;
    }
    //~ Fields ................................................................

    //~ Constructors ..........................................................

    //~Public  Methods ........................................................

}
