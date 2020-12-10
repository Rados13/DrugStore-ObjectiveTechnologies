package pl.edu.agh.to.drugstore.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;
import pl.edu.agh.to.drugstore.presenter.AdminPanelPresenter;
import pl.edu.agh.to.drugstore.presenter.LoginScreenPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

public class AppController {

    private final Stage primaryStage;

    private final CommandRegistry commandRegistry = new CommandRegistry();

    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    public AppController(Stage primaryStage, EntityManager em) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() throws IOException {
        boolean approved = showLoginScreen();
        while (!approved) {
            //show login error
            approved = showLoginScreen();
            //we need to return person from logging screen
        }
        Person person = new Person();
        person.setRole(Role.ADMINISTRATOR);

        switch (person.getRole()){
            case ADMINISTRATOR:
               showAdminPanel();
            case CLIENT:

            case SELLER:

        }
    }

    public boolean showLoginScreen() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class
                    .getResource("/view/LoginPane.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the presenter.
            LoginScreenPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return false;
        }
    }

    public void showAdminPanel() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class
                    .getResource("/view/AdminPanelPane.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("AdminPanel");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the presenter.
            AdminPanelPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
        }
    }
}
