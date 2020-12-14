package pl.edu.agh.to.drugstore.command.person;

import pl.edu.agh.to.drugstore.command.AddCommand;
import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;

public class AddPersonCommand extends AddCommand<Person> {


    public AddPersonCommand(Person person, Address address, PersonDAO personDAO, AddressDAO addressDAO) {
        super(person,personDAO);
    }


    @Override
    public String getName() {
        return "New person: " + getObjectToAdd().toString();
    }

    @Override
    public void undo() {
        getObjectDAO().delete(getObjectToAdd().getId());
    }

}
