package sistema.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import sistema.model.dao.CaminhaoDAO;
import sistema.model.dao.GalpaoDAO;
import sistema.model.dao.MotoristaDAO;
import sistema.model.dao.RecebimentoPorcosDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;
import sistema.model.domain.Caminhao;
import sistema.model.domain.Galpao;
import sistema.model.domain.Motorista;
import sistema.model.domain.RecebimentoPorcos;

public class FXMLAnchorPaneRecebimentoPorcosInserirController implements Initializable {
    @FXML
    private JFXComboBox<Caminhao> comboBoxCaminhao;

    @FXML
    private JFXComboBox<Motorista> comboBoxMotorista;

    @FXML
    private JFXComboBox<Galpao> comboBoxGalpao;

    @FXML
    private JFXDatePicker datePickerData;

    @FXML
    private JFXTextField textFieldQuantidadePorcos;

    @FXML
    private JFXCheckBox checkBoxFilhotes;

    @FXML
    private JFXButton buttonInserir;
    
    private List<Caminhao> listCaminhoes;
    private List<Motorista> listMotoristas;
    private List<Galpao> listGalpoes;
    
    private ObservableList<Caminhao> observableListCaminhoes;
    private ObservableList<Motorista> observableListMotoristas;
    private ObservableList<Galpao> observableListGalpoes;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();

    private final RecebimentoPorcosDAO recebimentoPorcosDAO = new RecebimentoPorcosDAO();
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final GalpaoDAO galpaoDAO = new GalpaoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recebimentoPorcosDAO.setConnection(connection);
        caminhaoDAO.setConnection(connection);
        motoristaDAO.setConnection(connection);
        galpaoDAO.setConnection(connection);
        
        carregarComboBoxCaminhao();
        carregarComboBoxMotorista();
        carregarComboBoxGalpao();
        
        //Declaração dos objetos de validações de dados
        RequiredFieldValidator validadorCampoObrigatorio = new RequiredFieldValidator();
        IntegerValidator validadorInteiro = new IntegerValidator();
        
        //Configuração dos objetos de validações de dados
        validadorCampoObrigatorio.setMessage("Campo deve ser preenchido");
        validadorInteiro.setMessage("Insira um inteiro");
        
        //Adição dos objetos de validações de dados nos componentes visuais
        textFieldQuantidadePorcos.getValidators().add(validadorInteiro);
        textFieldQuantidadePorcos.getValidators().add(validadorCampoObrigatorio);
        
        //Criação de Listeners para validação imediata após inserção de algum valor
        textFieldQuantidadePorcos.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldQuantidadePorcos.validate();
        });
    }
    
    public void carregarComboBoxCaminhao(){
        listCaminhoes = caminhaoDAO.listar();
        observableListCaminhoes = FXCollections.observableArrayList(listCaminhoes);
        comboBoxCaminhao.setItems(observableListCaminhoes);
    }
    
    public void carregarComboBoxMotorista(){
        listMotoristas = motoristaDAO.listar();
        observableListMotoristas = FXCollections.observableArrayList(listMotoristas);
        comboBoxMotorista.setItems(observableListMotoristas);
    }
    
    public void carregarComboBoxGalpao(){
        listGalpoes = galpaoDAO.listar();
        observableListGalpoes = FXCollections.observableArrayList(listGalpoes);
        comboBoxGalpao.setItems(observableListGalpoes);  
    }


    public boolean validarCampos(){
        boolean camposValidos = true;
        
        camposValidos = camposValidos && !(comboBoxCaminhao.getSelectionModel().isEmpty())
                          && !(comboBoxMotorista.getSelectionModel().isEmpty())
                          && !(comboBoxGalpao.getSelectionModel().isEmpty())
                          && !(datePickerData.getValue() == null)
                          && (textFieldQuantidadePorcos.validate())
                          && Integer.parseInt(textFieldQuantidadePorcos.getText()) >= 0;
        
        return camposValidos;
    }
    
    public void limparCampos(){
        comboBoxCaminhao.getSelectionModel().clearSelection();
        comboBoxMotorista.getSelectionModel().clearSelection();
        comboBoxGalpao.getSelectionModel().clearSelection();
        datePickerData.getEditor().clear();
        datePickerData.setValue(null);
        textFieldQuantidadePorcos.clear();
        checkBoxFilhotes.setSelected(false);
    }
    
    public boolean verificarCapacidadeMaxima(RecebimentoPorcos recebimentoPorcos){
        int quantidadeAtual = recebimentoPorcos.getGalpao().getQuantidadeAtual();
        int capacidadeMaxima = recebimentoPorcos.getGalpao().getCapacidadeMaxima();
        
        return ((quantidadeAtual + recebimentoPorcos.getQuantidadePorcos()) <= capacidadeMaxima);
    }
    
    public boolean verificarLimiteDiarioGalpao(RecebimentoPorcos recebimentoPorcos){
        return ((recebimentoPorcosDAO.verificarQuantidadeDiaria(recebimentoPorcos) + recebimentoPorcos.getQuantidadePorcos())  <= recebimentoPorcos.getGalpao().getLimiteDiario());
    }
    
    @FXML
    public void handleButtonInserir(){
        if(validarCampos()){
            RecebimentoPorcos recebimentoPorcos = new RecebimentoPorcos();
            recebimentoPorcos.setCaminhao(comboBoxCaminhao.getSelectionModel().getSelectedItem());
            recebimentoPorcos.setMotorista(comboBoxMotorista.getSelectionModel().getSelectedItem());
            recebimentoPorcos.setGalpao(comboBoxGalpao.getSelectionModel().getSelectedItem());
            recebimentoPorcos.setData(datePickerData.getValue());
            recebimentoPorcos.setQuantidadePorcos(Integer.parseInt(textFieldQuantidadePorcos.getText()));
            recebimentoPorcos.setFilhotes(checkBoxFilhotes.isSelected());
            if(verificarCapacidadeMaxima(recebimentoPorcos)){
                //Regra de Negocio
                if(verificarLimiteDiarioGalpao(recebimentoPorcos)){
                    try{
                        connection.setAutoCommit(false);
                        recebimentoPorcosDAO.setConnection(connection);
                        galpaoDAO.setConnection(connection);
                        recebimentoPorcosDAO.inserir(recebimentoPorcos);
                        Galpao galpao = recebimentoPorcos.getGalpao();
                        galpao.setQuantidadeAtual(galpao.getQuantidadeAtual() + recebimentoPorcos.getQuantidadePorcos());
                        galpaoDAO.alterar(galpao);
                        connection.commit();
                        limparCampos();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Recebimento registrado com sucesso");
                        alert.show();
                    }catch (SQLException ex){
                        try{
                            connection.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(FXMLAnchorPaneRecebimentoPorcosAlterarRemoverController.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        Logger.getLogger(FXMLAnchorPaneRecebimentoPorcosAlterarRemoverController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Não é possível receber os porcos no dia " + 
                            recebimentoPorcos.getData().toString() + " pois essa quantidade ultrapassa o limite diário de " + 
                            recebimentoPorcos.getGalpao().getLimiteDiario() + " porcos");
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Não é possível receber os porcos pois essa quantidade ultrapassa a capacidade máxima do galpão");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Os dados estão incorretos, vazios ou possuem valores negativos, favor revisar!");
            alert.show();
        }
    }
}
