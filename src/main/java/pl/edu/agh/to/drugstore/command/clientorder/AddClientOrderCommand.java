package pl.edu.agh.to.drugstore.command.clientorder;

import pl.edu.agh.to.drugstore.command.AddCommand;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.ObjectDAO;

public class AddClientOrderCommand extends AddCommand<ClientOrder> {

    public AddClientOrderCommand(ClientOrder objectToAdd, ObjectDAO<ClientOrder> objectDAO) {
        super(objectToAdd, objectDAO);
    }

    @Override
    public String getName() {
        return "New client order: " + getObjectToAdd().toString();
    }

    @Override
    public void undo() {
        getObjectDAO().delete(getObjectToAdd().getId());
    }

    @Override
    public void execute() {
        this.getObjectDAO().add(getObjectToAdd());
        EmailSend.sendEmail(getObjectToAdd());
    }
}
