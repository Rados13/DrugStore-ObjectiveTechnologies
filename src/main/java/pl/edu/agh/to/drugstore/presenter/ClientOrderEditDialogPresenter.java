package pl.edu.agh.to.drugstore.presenter;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.business.Tuple;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;


@Setter
@Getter
public class ClientOrderEditDialogPresenter {

    private ClientOrder clientOrder;

    private PersonDAO personDAO;

    private ObservableList<Pair<Medication,Tuple>> allOrderElems;

    @FXML
    private DatePicker submissionDatePicker;

    @FXML
    private DatePicker shippingDatePicker;

    @FXML
    private ComboBox<Person> clientComboBox;

    @FXML
    private TableView<Pair<Medication, Tuple>> orderElemsTableView;

    @FXML
    private TableColumn<Pair<Medication, Tuple>,String>  medicationNameColumn;

    @FXML
    private TableColumn<Pair<Medication, Tuple>, BigDecimal>  medicationPriceColumn;

    @FXML
    private TableColumn<Pair<Medication, Tuple>,Integer>  amountOfMedicationBoxesColumn;

    private Stage dialogStage;

    private boolean approved;

    @FXML
    public void initialize() {
        clientComboBox.getItems().addAll(
                FXCollections.observableArrayList());
        orderElemsTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);

        medicationNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getKey().getNameProperty());
        medicationPriceColumn.setCellValueFactory(dataValue -> dataValue.getValue().getKey().getPriceProperty());
        amountOfMedicationBoxesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getQuantityProperty());
    }

    public void setData(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
        allOrderElems = FXCollections.observableArrayList(clientOrder.getMedicationsAsList());
        orderElemsTableView.refresh();
        orderElemsTableView.setItems(allOrderElems);
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
