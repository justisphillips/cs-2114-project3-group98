import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Model class for QueueTheEncore.
 * Manages fans joining, leaving, and being processed
 * from a concert ticket waitlist.
 *
 * @author Hela Michael
 * @version Nov 12, 2026
 */
public class WaitlistModel
{
    private LinkedDeque<Fan> waitlist;
    private int nextFanID;

    /**
     * Creates an empty waitlist.
     */
    public WaitlistModel()
    {
        waitlist = new LinkedDeque<Fan>();
        nextFanID = 1;
    }


    /**
     * Adds a new fan to the back of the waitlist.
     *
     * @param name
     *            the fan's name
     * @return the newly created fan
     * @throws IllegalArgumentException
     *             if the name is null or blank
     */
    public Fan joinWaitlist(String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Fan name cannot be empty.");
        }

        Fan newFan = new Fan(name.trim(), nextFanID);
        nextFanID++;

        waitlist.enqueue(newFan);

        return newFan;
    }


    /**
     * Removes a fan from the waitlist using their unique ID.
     *
     * @param fanId
     *            the ID of the fan to remove
     * @return true if the fan was removed, false otherwise
     */
    public boolean leaveWaitlist(int fanId)
    {
        for (int i = 0; i < waitlist.size(); i++)
        {
            Fan currentFan = waitlist.get(i);

            if (currentFan.getFanId() == fanId)
            {
                return waitlist.remove(currentFan);
            }
        }

        return false;
    }


    /**
     * Removes and returns the fan at the front of the waitlist.
     *
     * @return the next fan in line
     * @throws NoSuchElementException
     *             if the waitlist is empty
     */
    public Fan processNextFan()
    {
        return waitlist.dequeue();
    }


    /**
     * Finds a fan's current position in the waitlist.
     * Positions begin at 1.
     *
     * @param fanId
     *            the fan's unique ID
     * @return the fan's position, or -1 if not found
     */
    public int getPosition(int fanId)
    {
        for (int i = 0; i < waitlist.size(); i++)
        {
            Fan currentFan = waitlist.get(i);

            if (currentFan.getFanId() == fanId)
            {
                return i + 1;
            }
        }

        return -1;
    }


    /**
     * Returns a copy of the current waitlist.
     *
     * @return a list containing the waiting fans in order
     */
    public List<Fan> getWaitlist()
    {
        List<Fan> fans = new ArrayList<Fan>();

        for (int i = 0; i < waitlist.size(); i++)
        {
            fans.add(waitlist.get(i));
        }

        return fans;
    }


    /**
     * Checks whether the waitlist is empty.
     *
     * @return true if no fans are waiting
     */
    public boolean isEmpty()
    {
        return waitlist.isEmpty();
    }
}

