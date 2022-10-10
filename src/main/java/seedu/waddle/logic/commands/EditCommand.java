package seedu.waddle.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_PEOPLE;
import static seedu.waddle.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.waddle.model.Model.PREDICATE_SHOW_ALL_ITINERARIES;

import java.util.List;
import java.util.Optional;

import seedu.waddle.commons.core.Messages;
import seedu.waddle.commons.core.index.Index;
import seedu.waddle.commons.util.CollectionUtil;
import seedu.waddle.logic.commands.exceptions.CommandException;
import seedu.waddle.model.Model;
import seedu.waddle.model.itinerary.Country;
import seedu.waddle.model.itinerary.Date;
import seedu.waddle.model.itinerary.Itinerary;
import seedu.waddle.model.itinerary.Name;
import seedu.waddle.model.itinerary.People;

/**
 * Edits the details of an existing itinerary.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the itinerary identified "
            + "by the index number used in the displayed itinerary list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COUNTRY + "COUNTRY] "
            + "[" + PREFIX_START_DATE + "START DATE] "
            + "[" + PREFIX_END_DATE + "END DATE] "
            + "[" + PREFIX_PEOPLE + "PEOPLE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COUNTRY + "Australia "
            + PREFIX_START_DATE + "2022-07-30 ";

    public static final String MESSAGE_EDIT_ITINERARY_SUCCESS = "Edited Itinerary: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ITINERARY = "This itinerary already exists.";

    private final Index index;
    private final EditItineraryDescriptor editItineraryDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editItineraryDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditItineraryDescriptor editItineraryDescriptor) {
        requireNonNull(index);
        requireNonNull(editItineraryDescriptor);

        this.index = index;
        this.editItineraryDescriptor = new EditItineraryDescriptor(editItineraryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Itinerary> lastShownList = model.getFilteredItineraryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITINERARY_DISPLAYED_INDEX);
        }

        Itinerary itineraryToEdit = lastShownList.get(index.getZeroBased());
        Itinerary editedItinerary = createEditedItinerary(itineraryToEdit, editItineraryDescriptor);

        if (!itineraryToEdit.isSameItinerary(editedItinerary) && model.hasItinerary(editedItinerary)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITINERARY);
        }

        model.setItinerary(itineraryToEdit, editedItinerary);
        model.updateFilteredItineraryList(PREDICATE_SHOW_ALL_ITINERARIES);
        return new CommandResult(String.format(MESSAGE_EDIT_ITINERARY_SUCCESS, editedItinerary));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Itinerary createEditedItinerary(Itinerary itineraryToEdit, EditItineraryDescriptor editItineraryDescriptor) {
        assert itineraryToEdit != null;

        Name updatedName = editItineraryDescriptor.getName().orElse(itineraryToEdit.getName());
        Country updatedCountry = editItineraryDescriptor.getCountry().orElse(itineraryToEdit.getCountry());
        Date updatedStartDate = editItineraryDescriptor.getStartDate().orElse(itineraryToEdit.getStartDate());
        Date updatedEndDate = editItineraryDescriptor.getEndDate().orElse(itineraryToEdit.getEndDate());
        People updatedPeople = editItineraryDescriptor.getPeople().orElse(itineraryToEdit.getPeople());


        return new Itinerary(updatedName, updatedCountry, updatedStartDate, updatedEndDate, updatedPeople);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editItineraryDescriptor.equals(e.editItineraryDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditItineraryDescriptor {
        private Name name;
        private Country country;
        private Date startDate;
        private Date endDate;
        private People people;

        public EditItineraryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditItineraryDescriptor(EditItineraryDescriptor toCopy) {
            setName(toCopy.name);
            setCountry(toCopy.country);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setPeople(toCopy.people);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, country, startDate, endDate, people);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        public Optional<Country> getCountry() {
            return Optional.ofNullable(country);
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Optional<Date> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Optional<Date> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        public void setPeople(People people) {
            this.people = people;
        }

        public Optional<People> getPeople() {
            return Optional.ofNullable(people);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditItineraryDescriptor)) {
                return false;
            }

            // state check
            EditItineraryDescriptor e = (EditItineraryDescriptor) other;

            return getName().equals(e.getName())
                    && getCountry().equals(e.getCountry())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getPeople().equals(e.getPeople());
        }
    }
}
