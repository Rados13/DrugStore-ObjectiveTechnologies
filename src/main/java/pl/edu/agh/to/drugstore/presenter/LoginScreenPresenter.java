package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.command.person.AddPersonCommand;
import pl.edu.agh.to.drugstore.controller.person.PersonAppController;
import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.security.PasswordManager;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Optional;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranej osoby w interfejsie graficznym
 */
public class LoginScreenPresenter {

    private final static Logger logger = LoggerFactory.getLogger(LoginScreenPresenter.class);
    private static boolean cancelClicked;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    private Stage dialogStage;
    private EntityManager em;
    private Person approved = null;
    private PasswordManager passwordManager;
    private PersonAppController appController;
    private CommandRegistry commandRegistry;
    private PersonDAO personDAO;
    private AddressDAO addressDAO;

    public static boolean isCancelClicked() {
        return cancelClicked;
    }

    @FXML
    public void initialize() {
        cancelClicked = false;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setOnCloseRequest(event -> showConfirmationAlert());
    }

    public Person isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        approved = passwordManager.verifyAndGetPerson(login.getText(), password.getText());
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        showConfirmationAlert();
    }

    @FXML
    public void handleRegisterAction(ActionEvent actionEvent) {
        Person person = new Person();
        Address address = new Address();
        if (showRegisterDialog(person)) {
            AddPersonCommand addPersonCommand = new AddPersonCommand(person, address, personDAO, addressDAO);
            commandRegistry.executeCommand(addPersonCommand);
        }
        cancelClicked = true;

    }

    private void showConfirmationAlert() {
        Alert alert = Alerts.showConfirmationDialog("Confirmation Dialog", null, "Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
            dialogStage.close();
            System.exit(0);
        } else {
            cancelClicked = true;
            alert.close();
        }
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
        this.passwordManager = new PasswordManager(em);
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public void setCommandRegistry(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public boolean showRegisterDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
                    .getResource("/view/RegisterUserDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Register");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RegisterUserPresenter presenter = loader.getController();
            presenter.setDialogStage(this.dialogStage);
            if (person.getAddress() == null)
                presenter.setData(person, new Address());
            else presenter.setData(person, person.getAddress());

            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return false;
        }
    }

}
