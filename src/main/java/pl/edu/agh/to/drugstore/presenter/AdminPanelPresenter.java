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
public class AdminPanelPresenter {

    private Stage dialogStage;

    private boolean approved = false;

    private final static Logger logger = LoggerFactory.getLogger(AdminPanelPresenter.class);

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
    private void handleClientOrderAction(ActionEvent event) {

    }

    @FXML
    private void handleMedicationAction(ActionEvent event) {

    }
    @FXML
    private void handlePersonAction(ActionEvent event) {

    }
    @FXML
    private void handleSupplierAction(ActionEvent event) {

    }
    @FXML
    private void handleExitAction(ActionEvent event) {
        dialogStage.close();
        System.exit(0);
    }

}
