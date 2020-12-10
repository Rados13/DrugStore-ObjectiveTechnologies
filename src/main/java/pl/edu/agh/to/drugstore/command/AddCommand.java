package pl.edu.agh.to.drugstore.command;

import lombok.Getter;
import pl.edu.agh.to.drugstore.model.dao.ObjectDAO;

@Getter
public abstract class AddCommand<A> implements Command {

    private final A objectToAdd;

    private final ObjectDAO<A> objectDAO;

    public AddCommand(A objectToAdd, ObjectDAO<A> objectDAO) {
        this.objectToAdd = objectToAdd;
        this.objectDAO = objectDAO;
    }


    @Override
    public void execute() {
        objectDAO.add(objectToAdd);
    }

    @Override
    public abstract String getName();

    @Override
    public abstract void undo();

    @Override
    public void redo() {
        execute();
    }
}
