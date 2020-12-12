package pl.edu.agh.to.drugstore.presenter;

import javafx.scene.control.Alert;

public class Alerts {
    public static void showErrorAlert(String title, String header, String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public static Alert showConfirmationDialog(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        return alert;
    }
}
