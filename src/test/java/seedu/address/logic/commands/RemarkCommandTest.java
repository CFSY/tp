package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

class RemarkCommandTest {

    private static final String REMARK_STUB = "stub remark";

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person testPerson = actualModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person testPersonWithRemark = new PersonBuilder(testPerson).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(testPerson.getRemark().value));

        CommandResult actualCommandResult = null;
        try {
             actualCommandResult = remarkCommand.execute(actualModel);
        } catch (CommandException ce) {
            System.out.println("something went wrong with remarkCommand execution.");
        }

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, testPerson);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        Model expectedModel = new ModelManager(new AddressBook(actualModel.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(testPerson, testPersonWithRemark);

        assertEquals(actualCommandResult, expectedCommandResult);
        assertEquals(actualModel, expectedModel);
    }
}
