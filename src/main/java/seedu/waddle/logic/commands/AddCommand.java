package seedu.waddle.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_PEOPLE;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.waddle.logic.commands.exceptions.CommandException;
import seedu.waddle.model.Model;
import seedu.waddle.model.itinerary.Itinerary;

/**
 * Adds an itinerary to waddle.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an Itinerary to waddle. "
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COUNTRY + "COUNTRY] "
            + "[" + PREFIX_START_DATE + "START DATE] "
            + "[" + PREFIX_END_DATE + "END DATE] "
            + "[" + PREFIX_PEOPLE + "PEOPLE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Summer Trip "
            + PREFIX_COUNTRY + "India "
            + PREFIX_START_DATE + "2022-10-28 "
            + PREFIX_END_DATE + "2022-11-03 "
            + PREFIX_PEOPLE + "4 ";

    public static final String MESSAGE_SUCCESS = "New itinerary added: %1$s";
    public static final String MESSAGE_DUPLICATE_ITINERARY = "This itinerary already exists";

    private final Itinerary toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Itinerary}
     */
    public AddCommand(Itinerary itinerary) {
        requireNonNull(itinerary);
        toAdd = itinerary;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasItinerary(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITINERARY);
        }

        model.addItinerary(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}