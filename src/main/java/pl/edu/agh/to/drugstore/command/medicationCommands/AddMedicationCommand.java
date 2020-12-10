package pl.edu.agh.to.drugstore.command.medicationCommands;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;

public class AddMedicationCommand implements Command {
    private final Medication medication;

    private final MedicationDAO medicationDAO;

    public AddMedicationCommand(Medication medication, MedicationDAO medicationDAO) {
        this.medication = medication;
        this.medicationDAO = medicationDAO;
    }

    @Override
    public void execute() {
        medicationDAO.add(medication);
    }

    @Override
    public String getName() {
        return "New medication: " + medication.getName();
    }

    @Override
    public void undo() {
//        MedicationDAO.delete(personToAdd.getId());
    }

    @Override
    public void redo() {
//        execute();
    }
}
