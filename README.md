# CS-2114-Group-98---QueueTheEncore
Repo for Project 3 in CS 2114
# QueueTheEncore

## Project Overview

QueueTheEncore is a Java concert ticket waitlist. Fans join a line, check their position, leave, and get processed in order. Fan club members are served before regular fans, and each group stays first-in, first-out (FIFO). The code follows MVC: `Fan`, `LinkedDeque`, and `WaitlistModel` are the model, `WaitlistView` is the view, and `WaitlistController` connects them.

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

The model tests never create the view or controller.

## Reflection

**How did the design absorb the change?** The feature is Fan Club Priority. It touched four classes:
- `WaitlistModel`: now holds two `LinkedDeque<Fan>` lines (fan club and regular). `joinWaitlist(name, member)` picks the line, `processNextFan()` serves the fan club line first, and `getPosition()`, `getWaitlist()`, `leaveWaitlist()`, and `isEmpty()` read both lines.
- `Fan`: added a constructor `Fan(name, fanID, fanClubMember)`. The old two-argument constructor still works.
- `WaitlistController`: `handleJoin(name, fanClubMember)` passes the checkbox value to the model.
- `WaitlistView`: added a "Fan Club Member" checkbox and a `[Fan Club]` tag in the list.

D3 predicted changes to `WaitlistModel`, `WaitlistController`, `WaitlistView`, and the `Fan` constructor, and said `LinkedDeque` would stay unchanged. All of that matched, and `LinkedDeque` was not touched. What surprised us: we expected to use `addFront()` for priority, but it would put the newest member ahead of older members and break FIFO inside the group. Two deques gave priority with no new `LinkedDeque` method.

**Did the change cross the model/view line?** No. The view never calls the model. It still only receives `Fan` objects from the controller. The only new thing it reads is `fan.isFanClubMember()` on a `Fan` it was already given. The checkbox value goes view to controller to model. The only ripple was the extra `handleJoin` parameter, which is the controller's job.

**What changed from the D3 plan?**
- `Fan` got the 3-argument constructor as planned, and we kept the 2-argument one so existing code and tests still work.
- `joinWaitlist` got a second overload instead of replacing the old one, for the same reason.
- The spec said names with invalid characters are rejected, but the D3 code did not do it. `joinWaitlist` now accepts only letters, numbers, spaces, apostrophes, hyphens, and periods, and throws `IllegalArgumentException` otherwise.
- We planned tests for changing membership while waiting. We did not write them because the model has no way to do that yet.
- Added tests: fan club ordering, FIFO inside each group, interleaved joins and processing, invalid characters, served or already-removed fans, and zero or negative IDs.

**What doesn't work yet?**
- Estimated wait times, notifications, and Demo Mode are not built.
- Ticket quantity, menu, and Demo Mode settings from the scope do not exist yet, so there is no input to reject.
- A fan's membership cannot change after joining. Calling `Fan.setFanClubMember()` on a waiting fan does not move them.
- The controller dialogs and the non-numeric Fan ID check in the view have no automated tests. Our tests target the model only.

**What design change would make the next feature cheaper?** Errors and "Now serving" messages are `JOptionPane` dialogs created in the controller, and each one blocks until it is closed. Notifications and Demo Mode would be blocked by them. Adding a message area to `WaitlistView` (`displayMessage(String)`) and routing all messages through it would make both features much cheaper.


## Next Steps

**Left for the final build, in order:**
1. Message area in the view; replace the dialogs.
2. Estimated wait time (position x 7 minutes) in the model and view.
3. Notification when a fan reaches the front.
4. Model method to change a waiting fan's membership, with tests.
5. Full test pass, README update, demo rehearsal.

**Stretch goals:** committing to estimated wait times and GUI notifications. Dropping Demo Mode (and with it timer settings), because it needs a timer-driven refresh and we would rather finish the other two well.

**3-minute demo path:**
- 0:00-0:20: show the MVC classes and run the program.
- 0:20-0:50: join Sarah, Ben, and Cy as regular fans.
- 0:50-1:30: join Dana with the Fan Club box checked. She appears first with `[Fan Club]`. Join Eli (fan club); he lands behind Dana and ahead of Sarah.
- 1:30-1:50: check Sarah's position, which moved down.
- 1:50-2:20: bad input: blank name, "R2D2!", a letters-only Fan ID, and an unknown ID.
- 2:20-2:40: Ben leaves the waitlist.
- 2:40-2:55: Process Next Fan serves Dana, then Eli.
- 2:55-3:00: show the passing JUnit run.
  
**Division of Work**

- Hela: message area in the view (item 1) and wait time display and notification display (items 2 and 3).
- Justis: controller changes for the message area and notifications (items 1 and 3).
- Aaron: wait time method and membership-change method in the model, with tests (items 2 and 4).
- All three: item 5.


## Attribution

| Component | Contributor | Contribution |
|---|---|---|
| `Fan.java` | Aaron | Wrote; revised with 3-argument constructor |
| `FanTest.java` | Aaron | Wrote; added fan club constructor tests |
| `LinkedDeque.java` | Justis | Linked deque implementation |
| `LinkedDequeTest.java` | Justis | Wrote; added interleaved enqueue/dequeue order test |
| `WaitlistModel.java` | Hela | Wrote; revised for fan club priority and name validation |
| `WaitlistModelTest.java` | Hela | Wrote; added priority and bad-input tests |
| `WaitlistView.java` | Hela | Wrote; added checkbox and tag |
| `WaitlistViewTest.java` | Hela | Wrote; added fan club tag test |
| `WaitlistController.java` | Justis | Wrote; added `handleJoin(name, fanClubMember)` |

## GenAI Note

Generative AI was used to help plan the project structure, understand how the MVC classes communicate, and draft Java code, documentation, and test cases.

AI assistance also helped explore possible stretch goals, including fan club priority, estimated wait times, and GUI notifications. The estimated wait time idea was adapted to use a seven-minute maximum per fan.

We kept the ideas for the stretch goals that were recommended to us, we altered some of the initial components that GenAI wanted us to include such as the wait time calculations. We didn't use some of the stretch goals that were suggested; eg. multiple wait rooms. GenAI, however, assisted us all in the layout of our code and the test cases we created for each class allowing us to make sure we had sufficient coverage.

