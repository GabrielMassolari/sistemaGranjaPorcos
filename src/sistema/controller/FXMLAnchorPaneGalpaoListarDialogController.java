package sistema.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sistema.model.dao.GalpaoDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;
import sistema.model.domain.Galpao;

public class FXMLAnchorPaneGalpaoListarDialogController implements Initializable {
    @FXML
    private TableView<Galpao> tableViewGalpao;

    @FXML
    private TableColumn<Galpao, Integer> tableColumnCodigo;

    @FXML
    private TableColumn<Galpao, String> tableColumnNome;

    @FXML
    private TableColumn<Galpao, Double> tableColumnArea;

    @FXML
    private TableColumn<Galpao, Integer> tableColumnCapacidadeMax;

    @FXML
    private TableColumn<Galpao, Integer> tableColumnNumeroBaias;

    @FXML
    private TableColumn<Galpao, Boolean> tableColumnRefrigerado;

    @FXML
    private TableColumn<Galpao, Boolean> tableColumnMaternidade;

    @FXML
    private TableColumn<Galpao, Integer> tableColumnLimiteDiario;

    @FXML
    private TableColumn<Galpao, Integer> tableColumnQuantidadeAtual;
    
    private List<Galpao> listGalpoes;
    private ObservableList<Galpao> observableListGalpoes;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final GalpaoDAO galpaoDAO = new GalpaoDAO();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        galpaoDAO.setConnection(connection);
        carregarTableView();
    }   
    
    public void carregarTableView(){
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("cdGalpao"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnArea.setCellValueFactory(new PropertyValueFactory<>("metrosQuadrados"));
        tableColumnCapacidadeMax.setCellValueFactory(new PropertyValueFactory<>("capacidadeMaxima"));
        tableColumnNumeroBaias.setCellValueFactory(new PropertyValueFactory<>("numeroBaias"));
        tableColumnRefrigerado.setCellValueFactory(new PropertyValueFactory<>("refrigerado"));
        tableColumnMaternidade.setCellValueFactory(new PropertyValueFactory<>("maternidade"));
        tableColumnLimiteDiario.setCellValueFactory(new PropertyValueFactory<>("limiteDiario"));
        tableColumnQuantidadeAtual.setCellValueFactory(new PropertyValueFactory<>("quantidadeAtual"));
        
        listGalpoes = galpaoDAO.listar();
        observableListGalpoes = FXCollections.observableArrayList(listGalpoes);
        tableViewGalpao.setItems(observableListGalpoes);
    }
    
}
