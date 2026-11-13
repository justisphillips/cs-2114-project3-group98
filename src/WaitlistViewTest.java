import student.TestCase;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Container;

/**
 * Tests the WaitlistView class.
 *
 * @author Hela Michael
 * @version Nov 13, 2026
 */
public class WaitlistViewTest extends TestCase
{
    private WaitlistView view;

    /**
     * Sets up the GUI before each test.
     */
    public void setUp() throws Exception
    {
        SwingUtilities.invokeAndWait(() -> {
            view = new WaitlistView();
        });
    }


    /**
     * Closes the GUI after each test.
     */
    public void tearDown() throws Exception
    {
        SwingUtilities.invokeAndWait(() -> {
            view.dispose();
        });
    }


    /**
     * Tests that the window is created correctly.
     */
    public void testConstructor()
    {
        assertNotNull(view);
        assertEquals("QueueTheEncore", view.getTitle());
        assertEquals(550, view.getWidth());
        assertEquals(450, view.getHeight());
    }


    /**
     * Tests displaying fans in the correct order.
     */
    public void testDisplayWaitlist() throws Exception
    {
        List<Fan> fans = new ArrayList<Fan>();
        fans.add(new Fan("Sarah", 1));
        fans.add(new Fan("Michael", 2));

        SwingUtilities.invokeAndWait(() -> {
            view.displayWaitlist(fans);
        });

        JTextArea area = findTextArea(view.getContentPane());

        assertNotNull(area);
        assertTrue(area.getText().contains("Sarah"));
        assertTrue(area.getText().contains("Michael"));
        assertTrue(area.getText().contains("ID: 1"));
        assertTrue(area.getText().contains("ID: 2"));
    }


    /**
     * Tests displaying an empty waitlist.
     */
    public void testDisplayEmptyWaitlist() throws Exception
    {
        List<Fan> fans = new ArrayList<Fan>();

        SwingUtilities.invokeAndWait(() -> {
            view.displayWaitlist(fans);
        });

        JTextArea area = findTextArea(view.getContentPane());

        assertNotNull(area);
        assertTrue(
            area.getText().contains("The waitlist is empty."));
    }


    /**
     * Tests displaying a valid fan position.
     */
    public void testDisplayPosition() throws Exception
    {
        SwingUtilities.invokeAndWait(() -> {
            view.displayPosition(3);
        });

        JLabel label = findPositionLabel(view.getContentPane());

        assertNotNull(label);
        assertEquals(
            "Your current position is: 3",
            label.getText());
    }


    /**
     * Tests displaying a position that does not exist.
     */
    public void testInvalidPosition() throws Exception
    {
        SwingUtilities.invokeAndWait(() -> {
            view.displayPosition(-1);
        });

        JLabel label = findPositionLabel(view.getContentPane());

        assertNotNull(label);
        assertEquals("Fan ID not found.", label.getText());
    }


    /**
     * Finds the waitlist text area.
     *
     * @param container
     *            the GUI container
     * @return the text area
     */
    private JTextArea findTextArea(Container container)
    {
        for (Component component : container.getComponents())
        {
            if (component instanceof JTextArea)
            {
                return (JTextArea)component;
            }

            if (component instanceof Container)
            {
                JTextArea result =
                    findTextArea((Container)component);

                if (result != null)
                {
                    return result;
                }
            }
        }

        return null;
    }


    /**
     * Finds the label displaying the fan's position.
     *
     * @param container
     *            the GUI container
     * @return the position label
     */
    private JLabel findPositionLabel(Container container)
    {
        for (Component component : container.getComponents())
        {
            if (component instanceof JLabel)
            {
                JLabel label = (JLabel)component;

                if (label.getText().contains("position")
                    || label.getText().contains("Fan ID not found"))
                {
                    return label;
                }
            }

            if (component instanceof Container)
            {
                JLabel result =
                    findPositionLabel((Container)component);

                if (result != null)
                {
                    return result;
                }
            }
        }

        return null;
    }
}
