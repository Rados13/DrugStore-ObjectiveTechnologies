package pl.edu.agh.to.drugstore.command.person;

import pl.edu.agh.to.drugstore.command.RemoveCommand;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.util.List;

public class RemovePeopleCommand extends RemoveCommand<Person> {


    public RemovePeopleCommand(List<Person> peopleToRemove, PersonDAO personDAO) {
        super(peopleToRemove, personDAO);
    }

    @Override
    public void execute() {
        getObjectsToRemove().forEach(person -> getObjectDAO().delete(person.getId()));
    }

}
