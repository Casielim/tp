package tassist.address.model.person;

import static java.util.Objects.requireNonNull;
import static tassist.address.commons.util.AppUtil.checkArgument;
import static tassist.address.logic.commands.RepoCommand.MESSAGE_INVALID_REPOSITORY_NAME;
import static tassist.address.logic.commands.RepoCommand.MESSAGE_INVALID_USERNAME;

/**
 * Represents a student's Repository in TAssist.
 */
public class Repository {

    public static final String MESSAGE_USERNAME_VALIDITY = "[USERNAME]:\n"
            + " - Must be 1 to 39 characters long.\n"
            + " - Made up of alphanumeric characters\n"
            + " - Separated by only dashes (-) between segments\n"
            + " - Cannot start and end with a dash\n";

    public static final String MESSAGE_REPOSITORY_NAME_VALIDITY = "[REPOSITORY_NAME]:\n"
            + " - Made up of alphanumeric characters\n"
            + " - Can contain but cannot start with dashes (-), underscores (_), and dots (.)\n"
            + " - Cannot be empty\n"
            + " - Must start and end with an alphanumeric character\n";

    public static final String MESSAGE_CONSTRAINTS =
            "Repositories should be written in the format of https://github.com/[USERNAME]/[REPOSITORY_NAME]\n"
            + MESSAGE_USERNAME_VALIDITY
            + MESSAGE_REPOSITORY_NAME_VALIDITY
            + "Example:\n"
            + "https://github.com/johnny-fargo/new.repo\n" // individual repo example
            + "https://github.com/AY2425S2-CS2103T-W12-4/tp"; // team repo example

    public static final String GITHUB_URL_REGEX = "^https://github\\.com/";
    public static final String VALID_USERNAME_REGEX = "(?=.{1,42}$)[a-zA-Z0-9]+(-[a-zA-Z0-9]+)*";
    public static final String VALID_REPOSITORY_REGEX = "[a-zA-Z0-9](?:[a-zA-Z0-9._-]*[a-zA-Z0-9])?";

    public static final String VALIDATION_REGEX = GITHUB_URL_REGEX + VALID_USERNAME_REGEX
            + "/" + VALID_REPOSITORY_REGEX + "$";
    public static final String NO_REPOSITORY = "No Repository";

    public final String value;

    /**
     * Constructs an {@code Repository}.
     *
     * @param repository A valid repository.
     */
    public Repository(String repository) {
        requireNonNull(repository);
        checkArgument(isValidRepository(repository), MESSAGE_CONSTRAINTS);
        value = repository;
    }

    /**
     * Constructs an {@code Repository}.
     *
     * @param username A valid username.
     * @param repositoryName A valid username.
     */
    public Repository(String username, String repositoryName) {
        requireNonNull(repositoryName);
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_INVALID_USERNAME);
        checkArgument(isValidRepositoryName(repositoryName), MESSAGE_INVALID_REPOSITORY_NAME);
        value = "https://github.com/" + username + "/" + repositoryName;
    }


    /**
     * Returns true if a given string is a valid repository link.
     */
    public static boolean isValidRepository(String test) {
        return test.matches(VALIDATION_REGEX) || test.matches(NO_REPOSITORY);
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(VALID_USERNAME_REGEX);
    }

    /**
     * Returns true if a given string is a valid repository name.
     */
    public static boolean isValidRepositoryName(String test) {
        return test.matches(VALID_REPOSITORY_REGEX);
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
        if (!(other instanceof Repository)) {
            return false;
        }

        Repository otherRepository = (Repository) other;
        return value.equals(otherRepository.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
