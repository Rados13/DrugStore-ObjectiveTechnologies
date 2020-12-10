package pl.edu.agh.to.drugstore.command.clientorder;

import pl.edu.agh.to.drugstore.command.RemoveCommand;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.ObjectDAO;

import java.util.List;

public class RemoveClientOrderCommand extends RemoveCommand<ClientOrder> {

    public RemoveClientOrderCommand(List<ClientOrder> objectsToRemove, ObjectDAO<ClientOrder> objectDAO) {
        super(objectsToRemove, objectDAO);
    }

    @Override
    public void execute() {
        getObjectsToRemove().forEach(clientOrder -> getObjectDAO().delete(clientOrder.getId()));
    }
}
