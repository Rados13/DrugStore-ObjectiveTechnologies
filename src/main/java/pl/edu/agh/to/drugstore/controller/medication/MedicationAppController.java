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
import pl.edu.agh.to.drugstore.controller.person.PersonAppController;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.presenter.MedicationEditDialogPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

@Data
public class MedicationAppController {

    private final MedicationDAO medicationDAO;

    private final Stage primaryStage;

    private final CommandRegistry commandRegistry = new CommandRegistry();

    private final static Logger logger = LoggerFactory.getLogger(MedicationAppController.class);

    public MedicationAppController(Stage primaryStage, EntityManager em) {
        this.primaryStage = primaryStage;
        this.medicationDAO = new MedicationDAO(em);
    }


    public void initRootLayout() throws IOException {

        this.primaryStage.setTitle("Medications");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MedicationAppController.class
                .getResource("/view/MedicationsOverviewPane.fxml"));
        BorderPane rootLayout = loader.load();

        MedicationOverviewController controller = loader.getController();
        controller.setAppController(this);
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
            BorderPane page = (BorderPane) loader.load();

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
}
