package pl.edu.agh.to.drugstore.controller.medication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.controller.AppController;
import pl.edu.agh.to.drugstore.controller.person.PersonAppController;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.presenter.editDialog.MedicationEditDialogPresenter;
import pl.edu.agh.to.drugstore.presenter.MedicationsFilterDialogPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

@Data
public class MedicationAppController {

    private final static Logger logger = LoggerFactory.getLogger(MedicationAppController.class);
    private final MedicationDAO medicationDAO;

    private final Stage primaryStage;

    private final CommandRegistry commandRegistry = new CommandRegistry();
    private AppController appController;

    public MedicationAppController(Stage primaryStage, EntityManager em, AppController appController) {
        this.primaryStage = primaryStage;
        this.medicationDAO = new MedicationDAO(em);
        this.appController = appController;
    }


    public void initRootLayout() throws IOException {

        this.primaryStage.setTitle("Medications");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MedicationAppController.class
                .getResource("/view/MedicationsOverviewPane.fxml"));
        BorderPane rootLayout = loader.load();

        MedicationOverviewController controller = loader.getController();
        controller.setMedicationAppController(this);
        controller.setData();
        controller.setCommandRegistry(commandRegistry);
        controller.setMedicationDAO(medicationDAO);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean showMedicationEditDialog(Medication editedMedication) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
                    .getResource("/view/MedicationEditDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Medication");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            MedicationEditDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(editedMedication);
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return false;
        }
    }

    public void showMedicationFilterDialog(MedicationOverviewController overviewController, Object[] filterOptions) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
                    .getResource("/view/MedicationsFilterDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Filter medications");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            MedicationsFilterDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(overviewController, filterOptions);
            dialogStage.showAndWait();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
        }
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
