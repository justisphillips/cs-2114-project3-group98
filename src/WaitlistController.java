import java.util.NoSuchElementException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Controller class that connects the view to the model. Turns user actions into
 * model calls and catches expected model problems.
 *
 * @author Justis Phillips justisp
 * @version Nov 12, 2026
 */
public class WaitlistController
{
    private final WaitlistModel model;
    private final WaitlistView view;

    /**
     * Connects the view to this controller and shows the starting waitlist.
     *
     * @param model
     *            the waitlist model
     * @param view
     *            the waitlist view
     */
    public WaitlistController(WaitlistModel model, WaitlistView view)
    {
        this.model = model;
        this.view = view;

        view.setController(this);
        refreshView();
    }


    /**
     * Processes a request to join the waitlist.
     *
     * @param name
     *            the name typed by the user
     */
    public void handleJoin(String name)
    {
        try
        {
            model.joinWaitlist(name);
        }
        catch (IllegalArgumentException e)
        {
            showError(e.getMessage());
        }

        refreshView();
    }


    /**
     * Processes a request to leave the waitlist.
     *
     * @param fanId
     *            the ID of the fan leaving
     */
    public void handleLeave(int fanId)
    {
        if (!model.leaveWaitlist(fanId))
        {
            showError("Fan not found.");
        }

        refreshView();
    }


    /**
     * Processes the next fan when tickets become available.
     */
    public void handleNextFan()
    {
        try
        {
            Fan fan = model.processNextFan();
            JOptionPane.showMessageDialog(
                null,
                "Now serving: " + fan.getName() + " (ID: " + fan.getFanId()
                    + ")",
                "Next Fan",
                JOptionPane.INFORMATION_MESSAGE);
        }
        catch (NoSuchElementException e)
        {
            showError("The waitlist is empty.");
        }

        refreshView();
    }


    /**
     * Looks up a fan's position and sends it to the view. The view shows "Fan
     * ID not found." when given -1.
     *
     * @param fanId
     *            the fan's unique ID
     */
    public void handlePositionCheck(int fanId)
    {
        view.displayPosition(model.getPosition(fanId));
    }


    /**
     * Redraws the waitlist in the view.
     */
    public void refreshView()
    {
        view.displayWaitlist(model.getWaitlist());
    }


    /**
     * Starts the application.
     *
     * @param args
     *            unused
     */
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            WaitlistModel model = new WaitlistModel();
            WaitlistView view = new WaitlistView();
            new WaitlistController(model, view);
            view.setVisible(true);
        });
    }


    // The view has no message area yet so errors go in a dialog.
    private void showError(String message)
    {
        JOptionPane.showMessageDialog(
            null,
            message,
            "QueueTheEncore",
            JOptionPane.ERROR_MESSAGE);
    }
}
