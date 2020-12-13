package pl.edu.agh.to.drugstore.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.presenter.AdminPanelPresenter;
import pl.edu.agh.to.drugstore.presenter.Alerts;
import pl.edu.agh.to.drugstore.presenter.LoginScreenPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

public class AppController {

    private final static Logger logger = LoggerFactory.getLogger(AppController.class);
    private final Stage primaryStage;
    private final EntityManager em;
    private final CommandRegistry commandRegistry = new CommandRegistry();

    public AppController(Stage primaryStage, EntityManager em) {
        this.primaryStage = primaryStage;
        this.em = em;
    }

    public void initRootLayout() throws Exception {
        Person approvedPerson = showLoginScreen();
        while (approvedPerson == null) {
            if (!LoginScreenPresenter.isCancelClicked())
                Alerts.showErrorAlert("Permissions Denied!", "Permissions Denied!", "Check your login and password and try again");
            approvedPerson = showLoginScreen();
        }
        Alert alert;
        switch (approvedPerson.getRole()) {
            case ADMINISTRATOR:
                showAdminPanel();
                break;
            case SELLER:
            case CLIENT:
                alert = Alerts.showInformationDialog("Not Avaiable", "We are sorry :(", "This place is not implemented yet");
                alert.showAndWait();
                primaryStage.close();
                initRootLayout();
                break;
            default:
                throw new Exception("Role not recognized");
        }
    }

    public Person showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class
                    .getResource("/view/LoginPane.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LoginScreenPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setEntityManager(em);
            presenter.setPersonDAO(new PersonDAO(em));
            presenter.setCommandRegistry(commandRegistry);

            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return null;
        }
    }

    public void showAdminPanel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class
                    .getResource("/view/AdminPanelPane.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("AdminPanel");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AdminPanelPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setAppStage(primaryStage);
            presenter.setEntityManager(em);
            presenter.setAppController(this);
            dialogStage.showAndWait();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
        }
    }
}
