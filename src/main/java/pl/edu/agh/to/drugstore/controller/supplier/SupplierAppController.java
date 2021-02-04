package pl.edu.agh.to.drugstore.controller.supplier;

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
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;
import pl.edu.agh.to.drugstore.presenter.editDialog.SupplierEditDialogPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

@Data
public class SupplierAppController {

    private final static Logger logger = LoggerFactory.getLogger(SupplierAppController.class);
    private final SupplierDAO supplierDAO;
    private final Stage primaryStage;
    private final CommandRegistry commandRegistry = new CommandRegistry();
    private AppController appController;

    public SupplierAppController(Stage primaryStage, EntityManager em, AppController appController) {
        this.primaryStage = primaryStage;
        this.supplierDAO = new SupplierDAO(em);
        this.appController = appController;
    }


    public void initRootLayout() throws IOException {

        this.primaryStage.setTitle("Suppliers");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SupplierAppController.class
                .getResource("/view/SupplierOverviewPane.fxml"));
        BorderPane rootLayout = loader.load();

        SupplierOverviewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData();
        controller.setCommandRegistry(commandRegistry);
        controller.setSupplierDAO(supplierDAO);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean showSupplierEditDialog(Supplier supplier) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SupplierAppController.class
                    .getResource("/view/SupplierEditDialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit supplier");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the presenter.
            SupplierEditDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(supplier);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return false;
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
