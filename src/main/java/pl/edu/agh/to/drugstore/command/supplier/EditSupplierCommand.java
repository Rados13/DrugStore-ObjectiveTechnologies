package pl.edu.agh.to.drugstore.command.supplier;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;

public class EditSupplierCommand implements Command {

    private final Supplier supplierToEdit;

    private final Supplier editedSupplier;

    private final SupplierDAO supplierDAO;

    public EditSupplierCommand(Supplier supplierToEdit, Supplier editedSupplier, SupplierDAO supplierDAO) {
        this.supplierToEdit = supplierToEdit;
        this.editedSupplier = editedSupplier;
        this.supplierDAO = supplierDAO;
    }

    @Override
    public void execute() {
        supplierDAO.update(editedSupplier);
    }

    @Override
    public String getName() {
        return "Edited supplier: " + supplierToEdit.toString();
    }

    @Override
    public void undo() {
        supplierDAO.update(supplierToEdit);
    }

    @Override
    public void redo() {
        execute();
    }
}
