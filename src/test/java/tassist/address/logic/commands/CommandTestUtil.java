package tassist.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tassist.address.logic.parser.CliSyntax.PREFIX_DATE;
import static tassist.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tassist.address.logic.parser.CliSyntax.PREFIX_NAME;
import static tassist.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static tassist.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static tassist.address.logic.parser.CliSyntax.PREFIX_PROJECT_TEAM;
import static tassist.address.logic.parser.CliSyntax.PREFIX_REPOSITORY;
import static tassist.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static tassist.address.logic.parser.CliSyntax.PREFIX_TAG;
import static tassist.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tassist.address.commons.core.index.Index;
import tassist.address.logic.commands.exceptions.CommandException;
import tassist.address.model.AddressBook;
import tassist.address.model.Model;
import tassist.address.model.person.NameContainsKeywordsPredicate;
import tassist.address.model.person.Person;
import tassist.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_CLASS_AMY = "T01";
    public static final String VALID_CLASS_BOB_1 = "L15";
    public static final String VALID_CLASS_BOB_2 = "L15A";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@u.nus.edu";
    public static final String VALID_EMAIL_BOB = "bob@u.nus.edu";
    public static final String VALID_STUDENTID_AMY = "A1111111A";
    public static final String VALID_STUDENTID_BOB = "A2222222B";
    public static final String VALID_GITHUB_AMY = "https://github.com/amy123";
    public static final String VALID_GITHUB_BOB = "https://github.com/bobb-33";
    public static final String VALID_PROJECT_TEAM_AMY = "AmyTeam3000";
    public static final String VALID_PROJECT_TEAM_BOB = "BobTeam3000";
    public static final String VALID_USERNAME = "ValidUser";
    public static final String VALID_REPOSITORY_NAME = "valid.repo_1";
    public static final String VALID_REPOSITORY_AMY = "https://github.com/amy123/tp";
    public static final String VALID_REPOSITORY_BOB = "https://github.com/bobb-33/tp";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_PROGRESS_AMY = "60";
    public static final String VALID_PROGRESS_BOB = "80";
    public static final String NONEXISTENT_STUDENTID = "A9999999Z";
    public static final String VALID_DATE_LONG = "30-01-2030";
    public static final String VALID_DATE_MID = "15-03-30";
    public static final String VALID_DATE_SHORT = "20-12";
    public static final String VALID_ASSIGNMENT_NAME = "CS2103T project";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String STUDENTID_DESC_AMY = " " + PREFIX_STUDENT_ID + VALID_STUDENTID_AMY;
    public static final String STUDENTID_DESC_BOB = " " + PREFIX_STUDENT_ID + VALID_STUDENTID_BOB;
    public static final String PROJECT_TEAM_DESC_AMY = " " + PREFIX_PROJECT_TEAM + VALID_PROJECT_TEAM_AMY;
    public static final String PROJECT_TEAM_DESC_BOB = " " + PREFIX_PROJECT_TEAM + VALID_PROJECT_TEAM_BOB;
    public static final String REPOSITORY_DESC_AMY = " " + PREFIX_REPOSITORY + VALID_REPOSITORY_AMY;
    public static final String REPOSITORY_DESC_BOB = " " + PREFIX_REPOSITORY + VALID_REPOSITORY_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String PROGRESS_DESC_AMY = " " + PREFIX_PROGRESS + VALID_PROGRESS_AMY;
    public static final String PROGRESS_DESC_BOB = " " + PREFIX_PROGRESS + VALID_PROGRESS_BOB;
    public static final String ASSIGNMENT_DESC_2103 = " " + PREFIX_NAME + VALID_ASSIGNMENT_NAME;
    public static final String DATE_DESC_LONG = " " + PREFIX_DATE + VALID_DATE_LONG;
    public static final String DATE_DESC_MID = " " + PREFIX_DATE + VALID_DATE_MID;
    public static final String DATE_DESC_SHORT = " " + PREFIX_DATE + VALID_DATE_SHORT;
    // Invalid assignment test strings
    public static final String VALID_ASSIGNMENT_NAME_SPECIAL = " " + PREFIX_NAME + "&CS2103T Project";
    public static final String INVALID_ASSIGNMENT_NAME_EMPTY = " " + PREFIX_NAME + DATE_DESC_LONG;
    public static final String INVALID_ASSIGNMENT_NAME_SPACES = " " + PREFIX_NAME + "   " + DATE_DESC_LONG;
    public static final String INVALID_DATE_FORMAT = ASSIGNMENT_DESC_2103 + " " + PREFIX_DATE + "2030-01-30";
    public static final String INVALID_DATE_VALUES = ASSIGNMENT_DESC_2103 + " " + PREFIX_DATE + "32-01-2030";
    public static final String INVALID_DATE_PAST = ASSIGNMENT_DESC_2103 + " " + PREFIX_DATE + "01-01-2020";
    public static final String EXTRA_ARGUMENTS = ASSIGNMENT_DESC_2103 + DATE_DESC_LONG + " extra";
    public static final String EXTRA_PREFIX = ASSIGNMENT_DESC_2103 + DATE_DESC_LONG + " " + PREFIX_NAME + "Extra";
    public static final String MULTIPLE_FIELDS_INPUT = ASSIGNMENT_DESC_2103 + DATE_DESC_LONG + " " + PREFIX_NAME
            + "Another Project " + PREFIX_DATE + "31-01-2030";
    public static final String MULTIPLE_VALID_FIELDS_INPUT = ASSIGNMENT_DESC_2103 + DATE_DESC_LONG + " " + PREFIX_NAME
            + "CS2103T Quiz " + PREFIX_DATE + "31-01-2030";
    public static final String MULTIPLE_INVALID_FIELDS_INPUT = " " + PREFIX_NAME + " " + DATE_DESC_LONG + " "
            + PREFIX_NAME + " " + PREFIX_DATE + "31-01-2030";

    // Assign command test strings
    public static final String VALID_INDEX_ONE = "1";
    public static final String VALID_INDEX_TWO = "2";
    public static final String VALID_STUDENT_ID = "A1234567M";
    public static final String VALID_CLASS_NUMBER = "T01";
    public static final String INVALID_INDEX = "a";
    public static final String INVALID_STUDENT_ID = "A123456";
    public static final String INVALID_CLASS_NUMBER = "T00";
    public static final String INVALID_STUDENT_INDEX = "a";
    public static final String TOO_MANY_ARGUMENTS = "1 2 3";
    public static final String INVALID_USERNAME = "-username";
    public static final String INVALID_REPOSITORY_NAME = ".repo";
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob@yahoo"; // incorrect domain name
    public static final String INVALID_STUDENTID_DESC = " " + PREFIX_STUDENT_ID + "A0B"; // missing 6 other digits
    public static final String INVALID_PROJECT_TEAM_DESC = " " + PREFIX_PROJECT_TEAM + " "; // whitespace
    public static final String INVALID_REPOSITORY_DESC = " " + PREFIX_REPOSITORY + ".repo"; // starts with .
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PROGRESS_DESC = " " + PREFIX_PROGRESS + "180"; //> 100, invalid progress value

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_FILE_PATH_1 = "sample-1.csv";
    public static final String VALID_FILE_PATH_2 = "sample-2.csv";
    public static final String VALID_FILE_PATH_3 = "sample-3.csv";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withClassNumber(VALID_CLASS_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withStudentId(VALID_STUDENTID_AMY)
                .withGithub(VALID_GITHUB_AMY)
                .withProjectTeam(VALID_PROJECT_TEAM_AMY)
                .withRepository(VALID_REPOSITORY_AMY)
                .withTags(VALID_TAG_FRIEND)
                .withProgress(VALID_PROGRESS_AMY).build();

        DESC_BOB = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withClassNumber(VALID_CLASS_BOB_1)
                .withEmail(VALID_EMAIL_BOB)
                .withStudentId(VALID_STUDENTID_AMY)
                .withGithub(VALID_GITHUB_BOB)
                .withProjectTeam(VALID_PROJECT_TEAM_BOB)
                .withRepository(VALID_REPOSITORY_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .withProgress(VALID_PROGRESS_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().value.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
