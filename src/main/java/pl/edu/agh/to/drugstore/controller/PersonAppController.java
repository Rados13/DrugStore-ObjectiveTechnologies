package pl.edu.agh.to.drugstore.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.to.drugstore.model.dao.AddressDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;

import javax.persistence.EntityManager;
import java.io.IOException;

public class PersonAppController {

	private final PersonDAO personDAO;

	private final AddressDAO addressDAO;

	private Stage primaryStage;


	public PersonAppController(Stage primaryStage,EntityManager em) {
		this.primaryStage = primaryStage;
		this.personDAO = new PersonDAO(em);
		this.addressDAO = new AddressDAO(em);
	}

	PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void initRootLayout() throws IOException {
			this.primaryStage.setTitle("Drugstore");

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PersonAppController.class
					.getResource("/view/PersonOverviewPane.fxml"));
			BorderPane rootLayout = loader.load();

			PersonOverviewController controller = loader.getController();
			controller.setAppController(this);
			controller.setData();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
	}
}
