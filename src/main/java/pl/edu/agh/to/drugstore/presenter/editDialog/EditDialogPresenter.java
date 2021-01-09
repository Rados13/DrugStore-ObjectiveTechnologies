package pl.edu.agh.to.drugstore.presenter.editDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class EditDialogPresenter {

    protected Stage dialogStage;

    protected boolean approved;

    @FXML
    public abstract void initialize();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    public void handleOkAction(ActionEvent event){
        updateModel();
        approved = true;
        dialogStage.close();
    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    protected abstract void updateModel();

    protected abstract void updateControls();
}
