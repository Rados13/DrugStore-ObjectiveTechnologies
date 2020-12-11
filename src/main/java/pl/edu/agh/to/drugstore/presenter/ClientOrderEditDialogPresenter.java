package pl.edu.agh.to.drugstore.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@Setter
@Getter
public class ClientOrderEditDialogPresenter {

    private ClientOrder clientOrder;

    private PersonDAO personDAO;

    @FXML
    private DatePicker submissionDatePicker;

    @FXML
    private DatePicker shippingDatePicker;

    @FXML
    private ComboBox<Person> clientComboBox;

    private Stage dialogStage;

    private boolean approved;

    @FXML
    public void initialize() {
        clientComboBox.getItems().addAll(
                FXCollections.observableArrayList());

    }

    public void setData(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
        updateControls();
    }


    public void updateClientComboBox(){
        clientComboBox.getItems().addAll(
                FXCollections.observableArrayList(
                        personDAO.searchPeople(Person.builder().role(Role.CLIENT).build())));
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        if(clientComboBox.getValue()!=null) {
            updateModel();
            approved = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() {
        clientOrder.setShippingDate(java.sql.Date.valueOf(shippingDatePicker.getValue()));
        clientOrder.setSubmissionDate(java.sql.Date.valueOf(submissionDatePicker.getValue()));
        clientOrder.setPerson(clientComboBox.getValue());
    }

    private LocalDate changeDateToLocalDate(Date date){
        if(date.getClass() == java.sql.Date.class)return ((java.sql.Date) date).toLocalDate();
        return LocalDate.ofInstant(clientOrder.getSubmissionDate().toInstant(), ZoneId.systemDefault());
    }

    private void updateControls() {
        if (clientOrder.getSubmissionDate() != null)
            submissionDatePicker.setValue(changeDateToLocalDate(clientOrder.getSubmissionDate()));
        else submissionDatePicker.setValue(LocalDate.now());

        if (clientOrder.getShippingDate() != null)
            shippingDatePicker.setValue(changeDateToLocalDate(clientOrder.getShippingDate()));
        else shippingDatePicker.setValue(LocalDate.now());


        if (clientOrder.getPerson() != null)
            clientComboBox.setValue(clientOrder.getPerson());
        else clientComboBox.setValue(new Person());
    }

}
