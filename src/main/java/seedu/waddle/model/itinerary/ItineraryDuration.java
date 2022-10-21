package seedu.waddle.model.itinerary;

import static java.util.Objects.requireNonNull;
import static seedu.waddle.commons.util.AppUtil.checkArgument;

/**
 * Represents an Itinerary's duration in days.
 */
public class ItineraryDuration {
    public static final String MESSAGE_CONSTRAINTS =
            "Duration should only contain a positive number.";
    public static final String VALIDATION_REGEX = "\\d+";

    private final int duration;


    /**
     * Constructs a {@code ItineraryDuration}.
     *
     * @param duration A valid duration.
     */
    public ItineraryDuration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        this.duration = Integer.parseInt(duration);
    }

    /**
     * Returns true if a given string is a valid duration.
     */
    public static boolean isValidDuration(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        return Integer.parseInt(test) >= 0;
    }

    // TODO: implement with Date
    public Date getEndFromStart(Date date) {
        return date;
    }

    @Override
    public String toString() {
        return String.valueOf(duration);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItineraryDuration // instanceof handles nulls
                && duration == (((ItineraryDuration) other).duration)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}