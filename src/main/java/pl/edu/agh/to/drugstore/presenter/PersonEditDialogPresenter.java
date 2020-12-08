package pl.edu.agh.to.drugstore.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PersonEditDialogPresenter {

    private Person person;

    private Address address;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField PESELTextField;

    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField houseIdTextField;

    @FXML
    private TextField apartmentIdTextField;

    private Stage dialogStage;

    private boolean approved;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll(
                FXCollections.observableArrayList(
                        Role.CLIENT,
                        Role.SELLER,
                        Role.ADMINISTRATOR
                ));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Person person, Address address) {
        this.person = person;
        this.address = address;
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
        person.setRole(roleComboBox.getValue());

        this.address.setCity(cityTextField.getText());
        this.address.setStreet(streetTextField.getText());
        this.address.setHouseId(houseIdTextField.getText());
        this.address.setApartmentId(apartmentIdTextField.getText());
        this.person.setAddress(address);

    }

    private void updateControls() {
        firstNameTextField.setText(person.getFirstname());
        lastNameTextField.setText(person.getLastname());
        if (person.getBirthdate() != null)
            birthDatePicker.setValue(person.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        else birthDatePicker.setValue(LocalDate.now());
        PESELTextField.setText(person.getPESEL());
        if (person.getRole() != null)
            roleComboBox.setValue(person.getRole());
        else roleComboBox.setValue(Role.CLIENT);
        if (person.getAddress() != null) {
            cityTextField.setText(person.getAddress().getCity());
            streetTextField.setText(person.getAddress().getStreet());
            houseIdTextField.setText(person.getAddress().getHouseId());
            apartmentIdTextField.setText(person.getAddress().getApartmentId());
        }
    }
}
