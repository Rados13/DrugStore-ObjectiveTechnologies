package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.security.PasswordManager;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranej osoby w interfejsie graficznym
 */
public class LoginScreenPresenter {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    private Stage dialogStage;

    private EntityManager em;

    private boolean approved = false;

    private final static Logger logger = LoggerFactory.getLogger(LoginScreenPresenter.class);

    private PasswordManager passwordManager;

    @FXML
    public void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setOnCloseRequest(event -> {
           showConfirmationAlert();
        });
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        logger.info("Credentials:" + login.getText() + " " + password.getText());

        approved = passwordManager.verify(login.getText(), password.getText());

        if(!approved) {
            throw new RuntimeException("Permission denied!");
        }

        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
        System.exit(0);
    }

    private void showConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
            dialogStage.close();
            System.exit(0);
        } else {
            alert.close();
        }
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
        this.passwordManager = new PasswordManager(em);
    }
}
