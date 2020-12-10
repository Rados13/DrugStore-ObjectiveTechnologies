package pl.edu.agh.to.drugstore.command.medicationCommands;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.util.List;

public class RemoveMedicationCommand implements Command {

    private final List<Medication> medicationsToRemove;

    private final MedicationDAO medicationDAO;

    public RemoveMedicationCommand(List<Medication> medicationsToRemove, MedicationDAO medicationDAO) {
        this.medicationsToRemove = medicationsToRemove;
        this.medicationDAO = medicationDAO;
    }

    @Override
    public void execute() {
        medicationsToRemove.forEach(medication -> medicationDAO.deleteMedication(medication.getId()));
    }

    @Override
    public String getName() {
        if (medicationsToRemove.size() > 1)
            return "Removed " + medicationsToRemove.size() + " medications";
        return "Removed medication: " + medicationsToRemove.get(0).getName();
    }

    @Override
    public void undo() {
//        medicationsToRemove.forEach(
//                medication -> {
//                    medication newMedication = new Medication(medication);
//                    medicationDAO.addMedication(medication);
//                });
    }

    @Override
    public void redo() {
        execute();
    }
}
