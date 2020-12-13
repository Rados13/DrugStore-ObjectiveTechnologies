package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.time.LocalDate;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranej osoby w interfejsie graficznym
 */
public class RegisterUserPresenter {

    private final static Logger logger = LoggerFactory.getLogger(RegisterUserPresenter.class);
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
    private TextField cityTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField houseIdTextField;

    @FXML
    private TextField apartmentIdTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField repeatPasswordTextField;


    private Stage dialogStage;

    private boolean approved;

    @FXML
    public void initialize() {
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
    private void handleOkAction(ActionEvent event) throws Exception {
        if (checkPasswords(passwordTextField.getText(), repeatPasswordTextField.getText())) {
            updateModel();
            approved = true;
            dialogStage.close();
        } else {
            Alerts.showErrorAlert("Error!", "Passwords do not match", "Check your password and try again");
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() throws Exception {
        person.setFirstname(firstNameTextField.getText());
        person.setLastname(lastNameTextField.getText());
        person.setBirthdate(LocalDate.from(birthDatePicker.getValue()));
        person.setPESEL(PESELTextField.getText());
        person.setRole(Role.CLIENT);

        this.address.setCity(cityTextField.getText());
        this.address.setStreet(streetTextField.getText());
        this.address.setHouseId(houseIdTextField.getText());
        this.address.setApartmentId(apartmentIdTextField.getText());
        this.person.setAddress(address);

        this.person.setLogin(loginTextField.getText());
        this.person.setPassword(passwordTextField.getText());

        //zastąpić dialogiem
    }

    private void updateControls() {
        firstNameTextField.setText(person.getFirstname());
        lastNameTextField.setText(person.getLastname());
        if (person.getBirthdate() != null)
            birthDatePicker.setValue(person.getBirthdate());
        else birthDatePicker.setValue(LocalDate.now());
        PESELTextField.setText(person.getPESEL());
        if (person.getAddress() != null) {
            cityTextField.setText(person.getAddress().getCity());
            streetTextField.setText(person.getAddress().getStreet());
            houseIdTextField.setText(person.getAddress().getHouseId());
            apartmentIdTextField.setText(person.getAddress().getApartmentId());
        }
        loginTextField.setText(person.getLogin());
    }

    boolean checkPasswords(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
}
