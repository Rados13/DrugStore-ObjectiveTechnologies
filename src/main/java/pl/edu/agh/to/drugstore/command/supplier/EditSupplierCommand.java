package pl.edu.agh.to.drugstore.command.supplier;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.command.EditCommand;
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;

public class EditSupplierCommand extends EditCommand<Supplier> {

    public EditSupplierCommand(Supplier supplierToEdit, Supplier editedSupplier, SupplierDAO supplierDAO) {
        super(supplierToEdit,editedSupplier,supplierDAO);
    }

    @Override
    public String getName() {
        return "Edited supplier: " + getObjectDAO().toString();
    }

}
