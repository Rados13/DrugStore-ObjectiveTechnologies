package pl.edu.agh.to.drugstore.command.person;

import pl.edu.agh.to.drugstore.command.EditCommand;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

public class EditPersonCommand extends EditCommand<Person> {


    public EditPersonCommand(Person personToEdit, Person editedPerson, PersonDAO personDAO) {
        super(personToEdit, editedPerson, personDAO);
    }


    @Override
    public String getName() {
        return "Edited person: " + getObjectToEdit().toString();
    }

}
