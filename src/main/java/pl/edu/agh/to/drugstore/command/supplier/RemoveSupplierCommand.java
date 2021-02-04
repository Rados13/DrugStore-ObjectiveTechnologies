package pl.edu.agh.to.drugstore.command.supplier;

import pl.edu.agh.to.drugstore.command.RemoveCommand;
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;

import java.util.List;

public class RemoveSupplierCommand extends RemoveCommand<Supplier> {


    public RemoveSupplierCommand(List<Supplier> suppliersToRemove, SupplierDAO supplierDAO) {
        super(suppliersToRemove, supplierDAO);
    }

    @Override
    public void execute() {
        getObjectsToRemove().forEach(supplier -> getObjectDAO().delete(supplier.getId()));
    }

}
