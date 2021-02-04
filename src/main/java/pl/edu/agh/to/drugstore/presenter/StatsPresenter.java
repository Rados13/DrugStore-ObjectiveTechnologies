package pl.edu.agh.to.drugstore.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;
import pl.edu.agh.to.drugstore.statistics.CalculateStats;
import pl.edu.agh.to.drugstore.statistics.StatsElem;


@Setter
public class StatsPresenter {

    private ClientOrderDAO clientOrderDAO;

    private ObservableList<StatsElem> statsElems;

    @FXML
    private TableView<StatsElem> ordersStatsTableView;

    @FXML
    private TableColumn<StatsElem, String> statNameColumn;

    @FXML
    private TableColumn<StatsElem, String> statValueColumn;

    @FXML
    private TableColumn<StatsElem, String> statAdditionalValueColumn;

    private Stage dialogStage;

    @FXML
    public void initialize() {
        System.out.println("It is working \n\n\n\n\n\n\n\n\n\n");

        ordersStatsTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);


        statNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getStatName());
        statValueColumn.setCellValueFactory(dataValue -> dataValue.getValue().getStatValue());
        statAdditionalValueColumn.setCellValueFactory(dataValue -> dataValue.getValue().getAdditionalValue());
    }

    public void setData() {
        statsElems = FXCollections.observableArrayList(new CalculateStats(clientOrderDAO).getAllStats());
        ordersStatsTableView.setItems(statsElems);
        ordersStatsTableView.refresh();
    }

    public boolean isApproved() {
        return true;
    }

}
