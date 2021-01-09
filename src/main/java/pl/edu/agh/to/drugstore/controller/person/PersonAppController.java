package pl.edu.agh.to.drugstore.controller.person;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.controller.AppController;
import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.presenter.editDialog.PersonEditDialogPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

public class PersonAppController {

    private final static Logger logger = LoggerFactory.getLogger(PersonAppController.class);
    private final PersonDAO personDAO;
    private final AddressDAO addressDAO;
    private final Stage primaryStage;
    private final CommandRegistry commandRegistry = new CommandRegistry();
    private AppController appController;

    public PersonAppController(Stage primaryStage, EntityManager em, AppController appController) {
        this.primaryStage = primaryStage;
        this.personDAO = new PersonDAO(em);
        this.addressDAO = new AddressDAO(em);
        this.appController = appController;
    }

    public void initRootLayout() throws IOException {
        this.primaryStage.setTitle("Drugstore");

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(PersonOverviewController.class
                .getResource("/view/PersonOverviewPane.fxml"));
        BorderPane rootLayout = loader.load();

        PersonOverviewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData();
        controller.setCommandRegistry(commandRegistry);
        controller.setPersonDAO(personDAO);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
                    .getResource("/view/PersonEditDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
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

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
