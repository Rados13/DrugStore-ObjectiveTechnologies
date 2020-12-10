package pl.edu.agh.to.drugstore.command;

import lombok.Getter;
import pl.edu.agh.to.drugstore.model.dao.ObjectDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.util.List;

@Getter
public abstract class RemoveCommand<A> implements Command {

    private final List<A> objectsToRemove;

    private final ObjectDAO<A> objectDAO;

    public RemoveCommand(List<A> objectsToRemove, ObjectDAO<A> objectDAO) {
        this.objectsToRemove = objectsToRemove;
        this.objectDAO = objectDAO;
    }

    @Override
    public abstract void execute();


    @Override
    public String getName(){
        if (getObjectsToRemove().size() > 1)
            return "Removed " + getObjectsToRemove().size() + " transactions";
        return "Removed transaction: " + getObjectsToRemove().toString();
    }

    @Override
    public void undo() {
        objectsToRemove.forEach(objectDAO::add);
    }

    @Override
    public void redo() {
        execute();
    }
}
