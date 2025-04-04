package tassist.address.model.person;

import static java.util.Objects.requireNonNull;
import static tassist.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a student's Github link in the address book.
 */
public class Github {

    public static final String NO_GITHUB = "No Github assigned";

    public static final String MESSAGE_CONSTRAINTS =
            "GitHub links should be in the format: https://github.com/USERNAME\n"
            + "Alternatively, you may leave the Github field blank to remove the GitHub link from a student.\n\n"
            + "The URL must follow these rules:\n"
            + "1. The base Github url should be in the format 'https://github.com'.\n"
            + "2. This is followed by a '/' and then a username."
            + "The username must:\n"
            + "    - Be 1 to 39 characters long\n"
            + "    - Consist of alphanumeric characters.\n"
            + "    - Separated only by dashes (-), if any.\n"
            + "    - Start and end with alphanumeric characters.\n"
            + "Example:\n"
            + "https://github.com/JohnnyDoe";

    // alphanumeric and special characters
    private static final String GITHUB_URL_REGEX = "^https://github\\.com/";
    private static final String USERNAME_REGEX = "(?=.{1,39}$)[a-zA-Z0-9]+(-[a-zA-Z0-9]+)*";
    public static final String VALIDATION_REGEX = GITHUB_URL_REGEX + USERNAME_REGEX + "$";
    public final String value;

    /**
     * @param github link of the Student
     */
    public Github(String github) {
        requireNonNull(github);
        checkArgument(isValidGithub(github), MESSAGE_CONSTRAINTS);
        value = github;
    }


    /**
     * Returns if a given string is a valid github link.
     */
    public static boolean isValidGithub(String test) {
        return test.matches(VALIDATION_REGEX) || test.matches(NO_GITHUB);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Github)) {
            return false;
        }

        Github otherGithub = (Github) other;
        return value.equals(otherGithub.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
