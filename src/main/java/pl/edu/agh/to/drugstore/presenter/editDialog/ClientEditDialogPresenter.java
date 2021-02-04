package pl.edu.agh.to.drugstore.presenter.editDialog;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.time.LocalDate;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranej osoby w interfejsie graficznym
 */
public class ClientEditDialogPresenter extends EditDialogPresenter {

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
    public TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    public void initialize() {
    }

    public void setData(Person person, Address address) {
        this.person = person;
        this.address = address;
        updateControls();
    }

    protected void updateModel() {
        person.setFirstname(firstNameTextField.getText());
        person.setLastname(lastNameTextField.getText());
        person.setBirthdate(LocalDate.from(birthDatePicker.getValue()));
        person.setPESEL(PESELTextField.getText());

        this.address.setCity(cityTextField.getText());
        this.address.setStreet(streetTextField.getText());
        this.address.setHouseId(houseIdTextField.getText());
        this.address.setApartmentId(apartmentIdTextField.getText());
        this.person.setAddress(address);

        this.person.setLogin(loginTextField.getText());
        this.person.setEmail(emailTextField.getText());
        this.person.setPassword(passwordTextField.getText());
    }

    protected void updateControls() {
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
        emailTextField.setText(person.getEmail());
    }
}
