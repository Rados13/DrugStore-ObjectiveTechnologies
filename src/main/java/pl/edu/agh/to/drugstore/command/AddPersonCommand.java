package pl.edu.agh.to.drugstore.command;

import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

public class AddPersonCommand implements Command{
    private final Person personToAdd;
    private final PersonDAO personDAO;

    public AddPersonCommand(Person person, PersonDAO personDAO) {
        this.personToAdd = person;
        this.personDAO = personDAO;
    }

    @Override
    public void execute() {
        personDAO.addPerson(personToAdd);
    }

    @Override
    public String getName() {
        return "New person: " + personToAdd.toString();
    }

    @Override
    public void undo() {
        personDAO.deletePerson(personToAdd.getId());
    }

    @Override
    public void redo() {
        execute();
    }
}
