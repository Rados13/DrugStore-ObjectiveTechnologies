package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.model.people.Person;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranej osoby w interfejsie graficznym
 */
public class LoginScreenPresenter {

    private Person person;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    private Stage dialogStage;

    private boolean approved = false;

    private final static Logger logger = LoggerFactory.getLogger(LoginScreenPresenter.class);

    @FXML
    public void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        logger.info("Credentials:" + login.getText() + " " + password.getText());

        //check credentials in dataBase
        //if correct:
        approved = true;
        //if not:
        //show error

        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
        System.exit(0);
    }

}
