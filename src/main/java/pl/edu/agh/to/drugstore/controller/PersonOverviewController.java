package pl.edu.agh.to.drugstore.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to.drugstore.command.*;
import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.time.LocalDate;
import java.util.List;

/**
 * Klasa interfejsu graficznego odpowiedzialna za wyświetlanie wszystkich osób dostępnych w bazie danych.
 */
public class PersonOverviewController extends OverviewController<Person>{

    private PersonDAO personDAO;

    private AddressDAO addressDAO;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    public TableColumn<Person, LocalDate> birthdateColumn;

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

    /**
     * Inicjalizuje główne okno aplikacji, w którym wyświetlane są osoby zapisane w bazie danych.
     */
    @FXML
    void initialize() {
        startInitialize();
        tableView.getSelectionModel().setSelectionMode(
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
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku delete odpowiedzialnego za usuwanie osoby z bazy danych.
     * @param event
     */
    @FXML
    void handleDeleteAction(ActionEvent event) {
        List<Person> peopleToRemove = List.copyOf(tableView.getSelectionModel().getSelectedItems());
        RemovePeopleCommand removePeopleCommand = new RemovePeopleCommand(peopleToRemove, personDAO);
        commandRegistry.executeCommand(removePeopleCommand);
        for(Person person : peopleToRemove){
            allExisting.remove(person);
        }
        refresh();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku edit odpowiedzialnego za edytowanie danych wybranej osoby.
     * Wyświetla osobne okno w interfejsie graficznym, które umożliwia edycję danych.
     * @param event
//     * @throws InterruptedException
     */
    @FXML
    void handleEditAction(ActionEvent event){
        Person personToEdit = tableView.getSelectionModel()
                .getSelectedItem();
        Person editedPerson = personToEdit;
        if (personToEdit != null) {
            appController.showPersonEditDialog(editedPerson);
            EditPersonCommand editPersonCommand = new EditPersonCommand(personToEdit, editedPerson, personDAO);
            commandRegistry.executeCommand(editPersonCommand);
        }
        refresh();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku add odpowiedzialnego za dodawanie osoby do bazy danych.
     * @param event
     */
    @FXML
    void handleAddAction(ActionEvent event) {
        Person person = new Person();
        Address address = new Address();
        if (appController.showPersonEditDialog(person)) {
            AddPersonCommand addPersonCommand = new AddPersonCommand(person, address, personDAO, addressDAO);
            commandRegistry.executeCommand(addPersonCommand);
        }
        allExisting.add(person);
        refresh();
    }

    public void setData() {
        allExisting = FXCollections.observableArrayList(appController.getPersonDAO().searchAllPersons());
        System.out.println(allExisting);
        tableView.refresh();
        tableView.setItems(allExisting);
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
