package pl.edu.agh.to.drugstore.command.medicationCommands;

import pl.edu.agh.to.drugstore.command.AddCommand;
import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;

public class AddMedicationCommand extends AddCommand<Medication> {

    public AddMedicationCommand(Medication medication, MedicationDAO medicationDAO) {
        super(medication,medicationDAO);
    }


    @Override
    public String getName() {
        return "New medication: " + getObjectToAdd().getName();
    }

    @Override
    public void undo() {
        getObjectDAO().delete(getObjectToAdd().getId());
    }

}
