package pl.edu.agh.to.drugstore.command.person;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.util.List;

public class RemovePeopleCommand implements Command {

    private final List<Person> peopleToRemove;

    private final PersonDAO personDAO;

    public RemovePeopleCommand(List<Person> peopleToRemove, PersonDAO personDAO) {
        this.peopleToRemove = peopleToRemove;
        this.personDAO = personDAO;
    }

    @Override
    public void execute() {
        peopleToRemove.forEach(person -> personDAO.deletePerson(person.getId()));
    }

    @Override
    public String getName() {
        if (peopleToRemove.size() > 1)
            return "Removed " + peopleToRemove.size() + " transactions";
        return "Removed transaction: " + peopleToRemove.toString();
    }

    @Override
    public void undo() {
        peopleToRemove.forEach(
                person -> {
                    Person newPerson = new Person(person);
                    personDAO.addPerson(newPerson);
                });
    }

    @Override
    public void redo() {
        execute();
    }
}
