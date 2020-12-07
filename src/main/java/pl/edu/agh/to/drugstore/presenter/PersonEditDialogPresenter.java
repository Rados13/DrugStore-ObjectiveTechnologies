package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PersonEditDialogPresenter {

    private Person person;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField PESELTextField;

    private Stage dialogStage;

    private boolean approved;


    @FXML
    public void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Person person) {
        this.person = person;
        updateControls();
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() {
        person.setFirstname(firstNameTextField.getText());
        person.setLastname(lastNameTextField.getText());
        person.setBirthdate(Date.from(birthDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        person.setPESEL(PESELTextField.getText());
    }

    private void updateControls() {
        firstNameTextField.setText(person.getFirstname());
        lastNameTextField.setText(person.getLastname());
        if (person.getBirthdate() != null)
            birthDatePicker.setValue(person.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        else birthDatePicker.setValue(LocalDate.now());
        PESELTextField.setText(person.getPESEL());
    }
}
