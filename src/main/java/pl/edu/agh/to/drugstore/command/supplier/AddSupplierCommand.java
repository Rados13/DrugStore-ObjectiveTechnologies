package pl.edu.agh.to.drugstore.command.supplier;

import pl.edu.agh.to.drugstore.command.AddCommand;
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;

public class AddSupplierCommand extends AddCommand<Supplier> {

    public AddSupplierCommand(Supplier supplier, SupplierDAO supplierDAO) {
        super(supplier, supplierDAO);
    }


    @Override
    public String getName() {
        return "New supplier: " + getObjectDAO().toString();
    }

    @Override
    public void undo() {
        getObjectDAO().delete(getObjectToAdd().getId());
    }

}
