package pl.edu.agh.to.drugstore.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.presenter.LoginScreenPresenter;
import pl.edu.agh.to.drugstore.presenter.PersonEditDialogPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

@Getter
public class ClientOrderAppController {
    private final ClientOrderDAO clientOrderDAO;

    private final PersonDAO personDAO;

    private final Stage primaryStage;

    private final CommandRegistry commandRegistry = new CommandRegistry();

    private final static Logger logger = LoggerFactory.getLogger(PersonAppController.class);

    public ClientOrderAppController(Stage primaryStage, EntityManager em) {
        this.primaryStage = primaryStage;
        this.personDAO = new PersonDAO(em);
        this.clientOrderDAO = new ClientOrderDAO(em);
    }

    public void initRootLayout() throws IOException {

        boolean approved = showLoginScreen();
        while(!approved){
            //show login error
            approved = showLoginScreen();
        }

        this.primaryStage.setTitle("Drugstore");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonAppController.class
                .getResource("/view/PersonOverviewPane.fxml"));
        BorderPane rootLayout = loader.load();

        ClientOrderOverviewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData();
        controller.setCommandRegistry(commandRegistry);
        controller.setClientOrderDAO(clientOrderDAO);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean showPersonEditDialog(ClientOrder clientOrder) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
                    .getResource("/view/PersonEditDialog.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the presenter.
            PersonEditDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            if (clientOrder.getPerson() == null)
                presenter.setData(clientOrder, new Person());
            else presenter.setData(clientOrder, clientOrder.getAddress());

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return false;
        }
    }

    public boolean showLoginScreen() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
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
}
