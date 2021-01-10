package pl.edu.agh.to.drugstore.command.medicationCommands;

import pl.edu.agh.to.drugstore.command.EditCommand;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;

public class EditMedicationCommand extends EditCommand<Medication> {


    public EditMedicationCommand(Medication medicationToEdit, Medication medicationEdited, MedicationDAO medicationDAO) {
        super(medicationToEdit, medicationEdited, medicationDAO);
    }

    @Override
    public String getName() {
        return "Edited medication: " + getObjectToEdit().getName();
    }

}
