package pl.edu.agh.to.drugstore.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to.drugstore.command.*;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.util.Date;
import java.util.List;


public class PersonOverviewController {

    private PersonAppController appController;
    private CommandRegistry commandRegistry;
    private PersonDAO personDAO;

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


        deleteButton.disableProperty().bind(
                Bindings.isEmpty(personTableView.getSelectionModel()
                        .getSelectedItems()));
        editButton.disableProperty().bind(
                Bindings.size(
                        personTableView.getSelectionModel()
                                .getSelectedItems()).isNotEqualTo(1));
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        List<Person> peopleToRemove = List.copyOf(personTableView.getSelectionModel().getSelectedItems());
        RemovePeopleCommand removePeopleCommand = new RemovePeopleCommand(peopleToRemove, personDAO);
        commandRegistry.executeCommand(removePeopleCommand);
        setData();
    }

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

    @FXML
    private void handleAddAction(ActionEvent event) {
        Person person = new Person();
        if (appController.showPersonEditDialog(person)) {
            AddPersonCommand addPersonCommand = new AddPersonCommand(person, personDAO);
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
}
