package sistema.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sistema.model.dao.RecebimentoPorcosDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;
import sistema.model.domain.Caminhao;
import sistema.model.domain.Galpao;
import sistema.model.domain.Motorista;
import sistema.model.domain.RecebimentoPorcos;

public class FXMLAnchorPaneRecebimentoPorcosListarController implements Initializable {
    @FXML
    private TableView<RecebimentoPorcos> tableViewRecebimento;

    @FXML
    private TableColumn<RecebimentoPorcos, Integer> tableColumnCodigo;

    @FXML
    private TableColumn<RecebimentoPorcos, Caminhao> tableColumnCaminhao;

    @FXML
    private TableColumn<RecebimentoPorcos, Motorista> tableColumnMotorista;

    @FXML
    private TableColumn<RecebimentoPorcos, Galpao> tableColumnGalpao;

    @FXML
    private TableColumn<RecebimentoPorcos, LocalDate> tableColumnData;

    @FXML
    private TableColumn<RecebimentoPorcos, Integer> tableColumnQuantidadePorcos;

    @FXML
    private TableColumn<RecebimentoPorcos, Boolean> tableColumnFilhotes;

    private List<RecebimentoPorcos> listRecebimentos;
    private ObservableList<RecebimentoPorcos> observableListRecebimentos;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final RecebimentoPorcosDAO recebimentoPorcosDAO = new RecebimentoPorcosDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recebimentoPorcosDAO.setConnection(connection);
        carregarTableView();
    }

    public void carregarTableView(){
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("cdRecebimentoPorcos"));
        tableColumnCaminhao.setCellValueFactory(new PropertyValueFactory<>("caminhao"));
        tableColumnMotorista.setCellValueFactory(new PropertyValueFactory<>("motorista"));
        tableColumnGalpao.setCellValueFactory(new PropertyValueFactory<>("galpao"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnQuantidadePorcos.setCellValueFactory(new PropertyValueFactory<>("quantidadePorcos"));
        tableColumnFilhotes.setCellValueFactory(new PropertyValueFactory<>("filhotes"));
        
        listRecebimentos = recebimentoPorcosDAO.listar();
        observableListRecebimentos = FXCollections.observableArrayList(listRecebimentos);
        tableViewRecebimento.setItems(observableListRecebimentos);
    }
    
}
