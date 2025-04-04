package tassist.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tassist.address.model.person.Person;
import tassist.address.model.timedevents.TimedEvent;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label classNumber;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label studentId;
    @FXML
    private Label github;
    @FXML
    private Label projectTeam;
    @FXML
    private Label repository;
    @FXML
    private FlowPane tags;
    @FXML
    private Label progress;
    @FXML
    private Label timedEvents;
    @FXML
    private VBox details;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        cardPane.getStyleClass().add("person-card");
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        classNumber.setText("Class Number: " + person.getClassNumber().value);
        phone.setText("Phone: " + person.getPhone().value);
        github.setText("GitHub: " + person.getGithub().value);
        email.setText("Email: " + person.getEmail().value);
        studentId.setText("Student ID: " + person.getStudentId().value);
        projectTeam.setText("ProjectTeam: " + person.getProjectTeam().value);
        repository.setText("Repository: " + person.getRepository().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        progress.setText("Progress: " + person.getProgress().value + "%");
        // Display timed events with time differences
        if (person.getTimedEvents().isEmpty()) {
            timedEvents.setText("No assignments");
        } else {
            StringBuilder eventsText = new StringBuilder("Assignments:\n");
            person.getTimedEvents().stream()
                    .sorted(Comparator.comparing(TimedEvent::getTime))
                    .forEach(event -> {
                        String timeLeft = event.calculateRemainingTime();
                        String eventText = String.format("%s - Due in: %s\n", event.getName(), timeLeft);
                        eventsText.append(eventText);
                    });
            // Remove the last newline
            eventsText.setLength(eventsText.length() - 1);
            timedEvents.setText(eventsText.toString());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
