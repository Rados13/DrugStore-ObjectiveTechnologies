package pl.edu.agh.to.drugstore.command.medicationCommands;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.command.RemoveCommand;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;

import java.util.List;

public class RemoveMedicationCommand extends RemoveCommand<Medication> {


    public RemoveMedicationCommand(List<Medication> medicationsToRemove, MedicationDAO medicationDAO) {
        super(medicationsToRemove,medicationDAO);
    }

    @Override
    public void execute() {
        getObjectsToRemove().forEach(medication -> getObjectDAO().delete(medication.getId()));
    }

}
