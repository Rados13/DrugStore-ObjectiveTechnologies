package pl.edu.agh.to.drugstore.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.util.Date;


public class PersonOverviewController {

	private PersonAppController appController;


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
	private Button deleteButton;

	@FXML
	private Button editButton;

	@FXML
	private Button addButton;

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
//		for (Person transaction : personTableView.getSelectionModel()
//				.getSelectedItems()) {
//			data.removeTransaction(transaction);
//		}
	}

	@FXML
	private void handleEditAction(ActionEvent event) {
//		Person transaction = personTableView.getSelectionModel()
//				.getSelectedItem();
//		if (transaction != null) {
//			appController.showTransactionEditDialog(transaction);
//		}
	}

	@FXML
	private void handleAddAction(ActionEvent event) {
//		Person transaction = new Person();
//		if (appController.showTransactionEditDialog(transaction)) {
//			data.addTransaction(transaction);
//		}
	}

	public void setData() {
		ObservableList<Person> allExisting = FXCollections.observableArrayList(appController.getPersonDAO().searchAllPersons());
		System.out.println(allExisting);
		personTableView.setItems(allExisting);
	}

	public void setAppController(PersonAppController appController) {
		this.appController = appController;
	}

}
