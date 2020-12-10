package pl.edu.agh.to.drugstore.command;

import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

public class EditPersonCommand implements Command {

    private final Person personToEdit;

    private final Person editedPerson;

    private final PersonDAO personDAO;

    public EditPersonCommand(Person personToEdit, Person editedPerson, PersonDAO personDAO) {
        this.personToEdit = personToEdit;
        this.editedPerson = editedPerson;
        this.personDAO = personDAO;
    }

    @Override
    public void execute() {
        personDAO.update(editedPerson);
    }

    @Override
    public String getName() {
        return "Edited person: " + personToEdit.toString();
    }

    @Override
    public void undo() {
        personDAO.update(personToEdit);
    }

    @Override
    public void redo() {
        execute();
    }
}
