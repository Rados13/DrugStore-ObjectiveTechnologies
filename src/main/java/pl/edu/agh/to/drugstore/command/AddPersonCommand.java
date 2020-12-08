package pl.edu.agh.to.drugstore.command;

import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;

public class AddPersonCommand implements Command{
    private final Person personToAdd;
    private final Address address;
    private final PersonDAO personDAO;
    private final AddressDAO addressDAO;

    public AddPersonCommand(Person person, Address address, PersonDAO personDAO, AddressDAO addressDAO) {
        this.personToAdd = person;
        this.address = address;
        this.personDAO = personDAO;
        this. addressDAO = addressDAO;
    }

    @Override
    public void execute() {
        addressDAO.addAddress(personToAdd.getAddress());
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
