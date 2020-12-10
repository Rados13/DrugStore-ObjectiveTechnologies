package pl.edu.agh.to.drugstore.command.medicationCommands;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;

public class EditMedicationCommand implements Command {

    private final Medication medication;

    private final MedicationDAO medicationDAO;

    public EditMedicationCommand(Medication medication, MedicationDAO medicationDAO) {
        this.medication = medication;
        this.medicationDAO = medicationDAO;
    }

    @Override
    public void execute() {
        medicationDAO.update(medication);
    }

    @Override
    public String getName() {
        return "Edited medication: " + medication.getName();
    }

    @Override
    public void undo() {
//        medicationDAO.editPerson(personToEdit);
    }

    @Override
    public void redo() {
        execute();
    }
}
