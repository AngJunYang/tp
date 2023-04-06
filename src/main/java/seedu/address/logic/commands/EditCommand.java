package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.UiSwitchMode;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEntities;
import seedu.address.model.entity.Entity;

/**
 * Enters edit mode to edit the details of a given entity.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit|e";

    public static final String MESSAGE_SUCCESS = "Entered Edit Mode";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: CLASSIFICATION (char, mob or item) NAME (name of entity)\n"
            + "Example: " + COMMAND_WORD + " item short dagger";

    private final String toEditName;
    private final String toEditClassification;

    /**
     * Creates an EditCommand to enter Edit Mode for the specified {@code Entity}
     */
    public EditCommand(String toEditClassification, String toEditName) {
        this.toEditClassification = toEditClassification;
        this.toEditName = toEditName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ReadOnlyEntities rerollEntities;
        switch (toEditClassification) {
        case "c":
        case "char":
            rerollEntities = model.getReroll().getCharacters();
            break;
        case "m":
        case "mob":
            rerollEntities = model.getReroll().getMobs();
            break;
        case "i":
        case "item":
            rerollEntities = model.getReroll().getItems();
            break;
        default:
            throw new CommandException("Invalid Classification!");
        }
        for (Object entity : rerollEntities.getEntityList()) {
            if (((Entity) entity).getName().fullName.equals(toEditName)) {
                model.setCurrentSelectedEntity((Entity) entity);
                return new CommandResult(MESSAGE_SUCCESS, false, false, UiSwitchMode.VIEW);
            }
        }

        throw new CommandException(String.format("No such entity in %s!", toEditClassification));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && toEditClassification.equals(((EditCommand) other).toEditClassification)
                && toEditName.equals(((EditCommand) other).toEditName));
    }
}

