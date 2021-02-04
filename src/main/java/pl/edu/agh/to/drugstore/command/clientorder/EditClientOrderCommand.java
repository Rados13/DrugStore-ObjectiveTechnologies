package pl.edu.agh.to.drugstore.command.clientorder;

import pl.edu.agh.to.drugstore.command.EditCommand;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.business.OrderStatus;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;

public class EditClientOrderCommand extends EditCommand<ClientOrder> {

    public EditClientOrderCommand(ClientOrder objectToEdit, ClientOrder editedObject, ClientOrderDAO objectDAO) {
        super(objectToEdit, editedObject, objectDAO);
    }

    @Override
    public void execute() {
        OrderStatus status1 = getObjectToEdit().getOrderStatus().getValue();
        OrderStatus status2 = getEditedObject().getOrderStatus().getValue();
        this.getObjectDAO().update(getEditedObject());

        if (status1 != status2) {
            EmailSend.sendEmail(getEditedObject());
        }
    }

    @Override
    public String getName() {
        return "Edited client order: " + this.getObjectToEdit().toString();
    }
}
