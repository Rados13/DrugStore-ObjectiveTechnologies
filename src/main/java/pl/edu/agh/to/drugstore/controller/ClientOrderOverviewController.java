package pl.edu.agh.to.drugstore.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

public class ClientOrderOverviewController {

    private CommandRegistry commandRegistry;
    private ClientOrderDAO clientOrderDAO;

    ObservableList<ClientOrder> allClientOrders;

    @FXML
    private TableView<ClientOrder> clientOrderTableView;


}
