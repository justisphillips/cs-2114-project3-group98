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
    // Fan club members are always served before regular fans.
    // Each line is FIFO, so order is kept within each group.
    private LinkedDeque<Fan> fanClubLine;
    private LinkedDeque<Fan> regularLine;
    private int nextFanID;

    /**
     * Creates an empty waitlist.
     */
    public WaitlistModel()
    {
        fanClubLine = new LinkedDeque<Fan>();
        regularLine = new LinkedDeque<Fan>();
        nextFanID = 1;
    }


    /**
     * Adds a new regular fan to the back of the regular line.
     *
     * @param name
     *            the fan's name
     * @return the newly created fan
     * @throws IllegalArgumentException
     *             if the name is null, blank, or has invalid characters
     */
    public Fan joinWaitlist(String name)
    {
        return joinWaitlist(name, false);
    }


    /**
     * Adds a new fan to the waitlist. Fan club members go to the back of the
     * fan club line, which is served before the regular line. Regular fans go
     * to the back of the regular line.
     *
     * @param name
     *            the fan's name
     * @param fanClubMember
     *            true if the fan is a fan club member
     * @return the newly created fan
     * @throws IllegalArgumentException
     *             if the name is null, blank, or has invalid characters
     */
    public Fan joinWaitlist(String name, boolean fanClubMember)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Fan name cannot be empty.");
        }

        String cleanName = name.trim();
        if (!cleanName.matches("[\\p{L}0-9 '.-]+"))
        {
            throw new IllegalArgumentException(
                "Fan name can only use letters, numbers, spaces, "
                    + "apostrophes, hyphens, and periods.");
        }

        Fan newFan = new Fan(cleanName, nextFanID, fanClubMember);
        nextFanID++;

        if (fanClubMember)
        {
            fanClubLine.enqueue(newFan);
        }
        else
        {
            regularLine.enqueue(newFan);
        }

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
        Fan fan = findFan(fanId);

        if (fan == null)
        {
            return false;
        }

        return fanClubLine.remove(fan) || regularLine.remove(fan);
    }


    /**
     * Removes and returns the next fan. Fan club members are served first,
     * then regular fans, each in the order they joined.
     *
     * @return the next fan in line
     * @throws NoSuchElementException
     *             if the waitlist is empty
     */
    public Fan processNextFan()
    {
        if (!fanClubLine.isEmpty())
        {
            return fanClubLine.dequeue();
        }

        return regularLine.dequeue();
    }


    /**
     * Finds a fan's current position in the waitlist.
     * Positions begin at 1. Fan club members come before regular fans.
     *
     * @param fanId
     *            the fan's unique ID
     * @return the fan's position, or -1 if not found
     */
    public int getPosition(int fanId)
    {
        for (int i = 0; i < fanClubLine.size(); i++)
        {
            if (fanClubLine.get(i).getFanId() == fanId)
            {
                return i + 1;
            }
        }

        for (int i = 0; i < regularLine.size(); i++)
        {
            if (regularLine.get(i).getFanId() == fanId)
            {
                return fanClubLine.size() + i + 1;
            }
        }

        return -1;
    }


    /**
     * Returns a copy of the current waitlist in serving order: fan club
     * members first, then regular fans.
     *
     * @return a list containing the waiting fans in order
     */
    public List<Fan> getWaitlist()
    {
        List<Fan> fans = new ArrayList<Fan>();

        for (int i = 0; i < fanClubLine.size(); i++)
        {
            fans.add(fanClubLine.get(i));
        }

        for (int i = 0; i < regularLine.size(); i++)
        {
            fans.add(regularLine.get(i));
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
        return fanClubLine.isEmpty() && regularLine.isEmpty();
    }


    // Searches both lines for a fan with the given ID, or returns null.
    private Fan findFan(int fanId)
    {
        for (int i = 0; i < fanClubLine.size(); i++)
        {
            if (fanClubLine.get(i).getFanId() == fanId)
            {
                return fanClubLine.get(i);
            }
        }

        for (int i = 0; i < regularLine.size(); i++)
        {
            if (regularLine.get(i).getFanId() == fanId)
            {
                return regularLine.get(i);
            }
        }

        return null;
    }
}
