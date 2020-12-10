package pl.edu.agh.to.drugstore.command.supplier;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;

public class AddSupplierCommand implements Command {

    private final Supplier supplierToAdd;

    private final SupplierDAO supplierDAO;

    public AddSupplierCommand(Supplier supplier, SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
        this.supplierToAdd = supplier;
    }

    @Override
    public void execute() {
        supplierDAO.add(supplierToAdd);
    }

    @Override
    public String getName() {
        return "New supplier: " + supplierToAdd.toString();
    }

    @Override
    public void undo() {
        supplierDAO.delete(supplierToAdd.getId());
    }

    @Override
    public void redo() {
        execute();
    }
}
