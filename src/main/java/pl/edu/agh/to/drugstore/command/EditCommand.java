package pl.edu.agh.to.drugstore.command;

import lombok.Getter;
import pl.edu.agh.to.drugstore.model.dao.ObjectDAO;


@Getter
public abstract class EditCommand<A> implements Command {

    private final A objectToEdit;

    private final A editedObject;

    private final ObjectDAO<A> objectDAO;

    protected EditCommand(A objectToEdit, A editedObject, ObjectDAO<A> objectDAO) {
        this.objectToEdit = objectToEdit;
        this.editedObject = editedObject;
        this.objectDAO = objectDAO;
    }

    @Override
    public void execute() {
        objectDAO.update(editedObject);
    }

    public abstract String getName();

    @Override
    public void undo() {
        objectDAO.update(objectToEdit);
    }

    @Override
    public void redo() {
        execute();
    }
}
