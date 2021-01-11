package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.command.person.EditPersonCommand;
import pl.edu.agh.to.drugstore.controller.AppController;
import pl.edu.agh.to.drugstore.controller.clientOrder.MyOrders.MyOrdersAppController;
import pl.edu.agh.to.drugstore.controller.person.ClientAppController;
import pl.edu.agh.to.drugstore.controller.person.PersonAppController;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.presenter.editDialog.ClientEditDialogPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Optional;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranej osoby w interfejsie graficznym
 */
public class ClientPanelPresenter {

    private final static Logger logger = LoggerFactory.getLogger(ClientPanelPresenter.class);
    private final boolean approved = false;
    private Stage dialogStage;
    private Stage appStage;
    private EntityManager em;
    private AppController appController;
    private Person person;
    private PersonDAO personDAO;
    private CommandRegistry commandRegistry;

    @FXML
    public void initialize() {
    }

    public void setAppStage(Stage appStage) {
        this.appStage = appStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleClientOrderAction(ActionEvent event) throws IOException {
        dialogStage.close();
        MyOrdersAppController myOrdersAppController = new MyOrdersAppController(appStage, em, appController, person);
        myOrdersAppController.initRootLayout();
    }

    @FXML
    private void handlePersonAction(ActionEvent event) throws IOException {
        ClientAppController personAppController = new ClientAppController(appStage, em, appController, person);
        personAppController.initRootLayout();
    }

    @FXML
    private void handleExitAction(ActionEvent event) throws Exception {
        showConfirmationAlert();
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    private void showConfirmationAlert() throws Exception {
        Alert alert = Alerts.showConfirmationDialog("Confirmation Dialog", null, "Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
            dialogStage.close();
        } else {
            alert.close();
        }
    }

    public AppController getAppController() {
        return this.appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void setCommandRegistry(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }
}
