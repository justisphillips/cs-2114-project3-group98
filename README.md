# CS-2114-Group-98---QueueTheEncore
Repo for Project 3 in CS 2114
# QueueTheEncore

## Project Overview

QueueTheEncore is a Java-based concert ticket waitlist application designed to manage fans waiting to purchase tickets. The application uses a first-in, first-out (FIFO) queue to ensure that fans are processed in the order they join.

Each fan receives a unique ID that allows them to check their position or leave the waitlist. The project follows a Model-View-Controller (MVC) structure to separate the waitlist logic from the user interface.

## How to Run

### Running the Model

1. Open the `QueueTheEncore` project in Eclipse.
2. Make sure the default package contains `Fan.java`, `LinkedDeque.java`, and `WaitlistModel.java`.
3. Open `WaitlistModelTest.java`.
4. Right-click the file and select **Run As → JUnit Test**.
5. Review the test results to verify that the model functions correctly.

The model can run independently of the graphical interface. It manages joining, leaving, processing fans, and retrieving queue positions without requiring `WaitlistView`.

### Running the Thin View

1. Make sure `WaitlistView.java` and its required controller class are included in the project.
2. Run the application's `WaitlistController.java` class.
3. Use the interface to enter fan names, view the waitlist, check positions, remove fans, and process the next fan.

The view uses Java Swing to provide a basic interface for interacting with the waitlist. The current focus is functionality rather than visual design.

## Reflection

One challenge in developing QueueTheEncore was translating our initial project specification into separate classes with clearly defined responsibilities. We needed to determine which methods belonged in the model and which actions should be handled by the controller or displayed by the view.

Separating the model from the GUI was especially important because it allows us to test the queue operations independently. Implementing unique fan IDs was another important design decision because multiple fans may share the same name.

We found it difficult to balance the work distribution since many of our classes depend on one another. We worked through this by communicating and providing ourselves deadlines for when certain classes needed to be completed to allow for cohesion in our project deliverable.

Things that changed from the specification to prototype include: `WaitlistView` uses DISPOSE_ON_CLOSE instead of EXIT_ON_CLOSE, because the student test library blocks System.exit and the view could not be constructed in `WaitlistViewTest`. No separate launcher class: main is in `WaitlistController`.


## Next Steps

For Deliverable 4, our first priority is to finish connecting the WaitlistModel, WaitlistController, and WaitlistView so that fans can join the waitlist, check their positions, leave the queue, and be processed through the GUI. We will also test invalid inputs, duplicate names, and empty-queue behavior to ensure that the MVP runs without crashing.

**Change Feature: Fan Club Priority**

Our planned non-trivial feature is Fan Club Priority. Currently, our MVP processes all fans in FIFO order. We plan to modify this behavior so that fan club members receive priority over regular fans while maintaining FIFO order within each membership group.

We predict that this feature will require changes to WaitlistModel to manage priority ordering, WaitlistController to process membership selections, and WaitlistView to allow users to select their membership status. Our Fan class already stores membership information, so we may not need to modify it. We also expect LinkedDeque to remain unchanged because it already provides the queue operations needed for this feature.

**Stretch Goals**

In addition to Fan Club Priority, we plan to attempt estimated wait times based on a maximum of seven minutes per fan and GUI notifications when a fan reaches the front of the queue. We will decide whether to implement accelerated demo mode based on our progress with the MVP and change feature.

**Additional Testing**

We will add tests to verify that fan club members receive priority, that FIFO ordering is maintained within each membership group, and that regular fans are still processed correctly. We will also test cases involving empty queues, duplicate names, invalid fan IDs, and changes in membership status.

**Division of Work**

Hela will continue developing and testing `WaitlistModel` and `WaitlistView`, including the model changes and GUI elements required for Fan Club Priority. Justis will continue developing and testing `LinkedDeque` and `WaitlistController`, including the controller actions needed to support the new feature. Aaron will be responsible for developing and testing the `Fan` class, including any updates needed to support fan club membership. All three members will collaborate on integrating the application, reviewing the code, and testing its overall functionality.

## Attribution

| Component | Contributor | Contribution |
|---|---|---|
| `Fan.java` | Aaron | Fan information and membership status |
| `LinkedDeque.java` | Justis | Linked deque implementation |
| `WaitlistModel.java` | Hela | Waitlist operations and queue management |
| `WaitlistView.java` | Hela | Swing GUI and display methods |
| `WaitlistController.java` | Justis | Connects user actions to the model |
| Tests | [Respective to class creators] | Tests for model and GUI functionality |

## GenAI Note

Generative AI was used to help plan the project structure, understand how the MVC classes communicate, and draft Java code, documentation, and test cases.

AI assistance also helped explore possible stretch goals, including fan club priority, estimated wait times, and GUI notifications. The estimated wait time idea was adapted to use a seven-minute maximum per fan.

We kept the ideas for the stretch goals that were recommended to us, we altered some of the initial components that GenAI wanted us to include such as the wait time calculations. We didn't use some of the stretch goals that were suggested; eg. multiple wait rooms. GenAI, however, assisted us all in the layout of our code and the test cases we created for each class allowing us to make sure we had sufficient coverage.

