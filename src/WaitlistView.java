import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * View class for QueueTheEncore.
 * Displays the concert waitlist and handles user input.
 *
 * @author Hela Michael
 * @version Nov 12, 2026
 */
public class WaitlistView extends JFrame
{
    private JTextField nameField;
    private JTextField fanIdField;
    private JTextArea waitlistArea;
    private JLabel positionLabel;

    private JButton joinButton;
    private JButton leaveButton;
    private JButton positionButton;
    private JButton nextButton;

    private WaitlistController controller;

    /**
     * Creates the QueueTheEncore GUI.
     */
    public WaitlistView()
    {
        setTitle("QueueTheEncore");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));

        JPanel namePanel = new JPanel(new FlowLayout());
        nameField = new JTextField(15);
        joinButton = new JButton("Join Waitlist");

        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        namePanel.add(joinButton);

        JPanel idPanel = new JPanel(new FlowLayout());
        fanIdField = new JTextField(10);
        positionButton = new JButton("Check Position");
        leaveButton = new JButton("Leave Waitlist");

        idPanel.add(new JLabel("Fan ID:"));
        idPanel.add(fanIdField);
        idPanel.add(positionButton);
        idPanel.add(leaveButton);

        inputPanel.add(namePanel);
        inputPanel.add(idPanel);

        add(inputPanel, BorderLayout.NORTH);

        waitlistArea = new JTextArea();
        waitlistArea.setEditable(false);
        add(new JScrollPane(waitlistArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        positionLabel = new JLabel(
            "Enter your Fan ID to check your position.",
            JLabel.CENTER);

        nextButton = new JButton("Process Next Fan");

        bottomPanel.add(positionLabel);
        bottomPanel.add(nextButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        joinButton.addActionListener(e -> {
            if (controller != null)
            {
                controller.handleJoin(nameField.getText());
            }
        });

        leaveButton.addActionListener(e -> {
            if (controller != null)
            {
                Integer id = readFanId();
                if (id != null)
                {
                    controller.handleLeave(id);
                }
            }
        });

        positionButton.addActionListener(e -> {
            if (controller != null)
            {
                Integer id = readFanId();
                if (id != null)
                {
                    controller.handlePositionCheck(id);
                }
            }
        });

        nextButton.addActionListener(e -> {
            if (controller != null)
            {
                controller.handleNextFan();
            }
        });
    }


    /**
     * Connects the GUI to its controller.
     *
     * @param controller
     *            the waitlist controller
     */
    public void setController(WaitlistController controller)
    {
        this.controller = controller;
    }


    /**
     * Displays all fans currently waiting.
     *
     * @param fans
     *            the current waitlist
     */
    public void displayWaitlist(List<Fan> fans)
    {
        StringBuilder text = new StringBuilder();
        text.append("CURRENT WAITLIST\n\n");

        for (int i = 0; i < fans.size(); i++)
        {
            Fan fan = fans.get(i);

            text.append(i + 1)
                .append(". ")
                .append(fan.getName())
                .append(" (ID: ")
                .append(fan.getFanId())
                .append(")\n");
        }

        if (fans.isEmpty())
        {
            text.append("The waitlist is empty.");
        }

        waitlistArea.setText(text.toString());
    }


    /**
     * Displays a fan's position in the queue.
     *
     * @param position
     *            the fan's position
     */
    public void displayPosition(int position)
    {
        if (position == -1)
        {
            positionLabel.setText("Fan ID not found.");
        }
        else
        {
            positionLabel.setText(
                "Your current position is: " + position);
        }
    }


    /**
     * Reads the Fan ID entered by the user.
     *
     * @return the ID, or null if the input is invalid
     */
    private Integer readFanId()
    {
        try
        {
            return Integer.parseInt(fanIdField.getText().trim());
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid numeric Fan ID.",
                "Invalid Fan ID",
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}

