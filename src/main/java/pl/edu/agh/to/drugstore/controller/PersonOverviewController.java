package pl.edu.agh.to.drugstore.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to.drugstore.command.*;
import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.util.Date;
import java.util.List;

/**
 * Klasa interfejsu graficznego odpowiedzialna za wyświetlanie wszystkich osób dostępnych w bazie danych.
 */
public class PersonOverviewController {

    private PersonAppController appController;

    private CommandRegistry commandRegistry;

    private PersonDAO personDAO;

    private AddressDAO addressDAO;

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    public TableColumn<Person, Date> birthdateColumn;

    @FXML
    public TableColumn<Person, String> PESELColumn;

    @FXML
    public TableColumn<Person, Role> roleColumn;

    @FXML
    public TableColumn<Person, String> cityColumn;

    @FXML
    public TableColumn<Person, String> streetColumn;

    @FXML
    public TableColumn<Person, String> houseIdColumn;

    @FXML
    public TableColumn<Person, String> apartmentIdColumn;


    @FXML
    private ListView<Command> commandLogView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    /**
     * Inicjalizuje główne okno aplikacji, w którym wyświetlane są osoby zapisane w bazie danych.
     */
    @FXML
    private void initialize() {
        personTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);

        firstNameColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getFirstNameProperty());
        lastNameColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getLastNameProperty());
        birthdateColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getBirthdateProperty());
        PESELColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getPESELProperty());
        roleColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getRoleProperty());
        cityColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getAddress().getCityProperty());
        streetColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getAddress().getStreetProperty());
        houseIdColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getAddress().getHouseIdProperty());
        apartmentIdColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getAddress().getApartmentIdProperty());


        deleteButton.disableProperty().bind(
                Bindings.isEmpty(personTableView.getSelectionModel()
                        .getSelectedItems()));
        editButton.disableProperty().bind(
                Bindings.size(
                        personTableView.getSelectionModel()
                                .getSelectedItems()).isNotEqualTo(1));
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku delete odpowiedzialnego za usuwanie osoby z bazy danych.
     * @param event
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        List<Person> peopleToRemove = List.copyOf(personTableView.getSelectionModel().getSelectedItems());
        RemovePeopleCommand removePeopleCommand = new RemovePeopleCommand(peopleToRemove, personDAO);
        commandRegistry.executeCommand(removePeopleCommand);
        setData();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku edit odpowiedzialnego za edytowanie danych wybranej osoby.
     * Wyświetla osobne okno w interfejsie graficznym, które umożliwia edycję danych.
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void handleEditAction(ActionEvent event) throws InterruptedException {
        Person personToEdit = personTableView.getSelectionModel()
                .getSelectedItem();
        Person editedPerson = personToEdit;
        if (personToEdit != null) {
            appController.showPersonEditDialog(editedPerson);
            EditPersonCommand editPersonCommand = new EditPersonCommand(personToEdit, editedPerson, personDAO);
            commandRegistry.executeCommand(editPersonCommand);
        }
        setData();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku add odpowiedzialnego za dodawanie osoby do bazy danych.
     * @param event
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        Person person = new Person();
        Address address = new Address();
        if (appController.showPersonEditDialog(person)) {
            AddPersonCommand addPersonCommand = new AddPersonCommand(person, address, personDAO, addressDAO);
            commandRegistry.executeCommand(addPersonCommand);
        }
        setData();
    }

    @FXML
    private void handleUndoAction(ActionEvent event) {
        commandRegistry.undo();
        setData();
    }

    @FXML
    private void handleRedoAction(ActionEvent event) {
        commandRegistry.redo();
        setData();
    }

    public void setData() {
        ObservableList<Person> allExisting = FXCollections.observableArrayList(appController.getPersonDAO().searchAllPersons());
        System.out.println(allExisting);
        personTableView.setItems(allExisting);
    }

    public void setAppController(PersonAppController appController) {
        this.appController = appController;
    }

    public void setCommandRegistry(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
        commandLogView.setItems(commandRegistry.getCommandStack());
        commandLogView.setCellFactory(lv -> new ListCell<Command>() {
            protected void updateItem(Command item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null && !empty) ? item.getName() : null);
            }
        });
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
