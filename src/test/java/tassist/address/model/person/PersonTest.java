package tassist.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tassist.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tassist.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tassist.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tassist.address.logic.commands.CommandTestUtil.VALID_PROGRESS_BOB;
import static tassist.address.logic.commands.CommandTestUtil.VALID_PROJECT_TEAM_BOB;
import static tassist.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static tassist.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static tassist.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tassist.address.testutil.Assert.assertThrows;
import static tassist.address.testutil.TypicalPersons.ALICE;
import static tassist.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import tassist.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // name differs in case, studentId and all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, studentId and all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same studentId, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND).withProgress(VALID_PROGRESS_BOB)
                .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, studentId and all other attributes different -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withStudentId(VALID_STUDENTID_BOB)
                .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND).withProgress(VALID_PROGRESS_BOB)
                .build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // all other attributes different -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withStudentId(VALID_STUDENTID_BOB).withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND)
                .withProgress(VALID_PROGRESS_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different studentId, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_AMY).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();

        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        //different studentId -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different project team -> returns true
        editedAlice = new PersonBuilder(ALICE).withProjectTeam(VALID_PROJECT_TEAM_BOB).build();
        assertTrue(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName()
                + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", classNumber=" + ALICE.getClassNumber() + ", studentId=" + ALICE.getStudentId()
                + ", github=" + ALICE.getGithub()
                + ", project team=" + ALICE.getProjectTeam()
                + ", tags=" + ALICE.getTags() + ", progress=" + ALICE.getProgress()
                + ", timedEvents=" + ALICE.getTimedEvents() + "}";

        assertEquals(expected, ALICE.toString());
    }
}
