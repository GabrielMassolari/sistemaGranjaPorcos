package sistema.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
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
import javafx.util.StringConverter;
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

public class FXMLAnchorPaneRecebimentoPorcosAlterarRemoverController implements Initializable {
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
    private JFXListView<RecebimentoPorcos> listViewRecebimentoPorcos;

    @FXML
    private JFXButton buttonAlterar;

    @FXML
    private JFXButton buttonRemover;
    
    private List<Caminhao> listCaminhoes;
    private List<Motorista> listMotoristas;
    private List<Galpao> listGalpoes;
    private List<RecebimentoPorcos> listRecebimentos;
    
    private ObservableList<Caminhao> observableListCaminhoes;
    private ObservableList<Motorista> observableListMotoristas;
    private ObservableList<Galpao> observableListGalpoes;
    private ObservableList<RecebimentoPorcos> observableListRecebimentos;
    
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
        carregarListView();
        
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
        
        listViewRecebimentoPorcos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemListView(newValue));   
    }

    public void carregarListView(){
        listRecebimentos = recebimentoPorcosDAO.listar();
        observableListRecebimentos = FXCollections.observableArrayList(listRecebimentos);
        listViewRecebimentoPorcos.setItems(observableListRecebimentos);
    }
    
    public void selecionarItemListView(RecebimentoPorcos recebimentoPorcos){
        if(recebimentoPorcos != null){
            comboBoxCaminhao.getSelectionModel().select(recebimentoPorcos.getCaminhao());            
            comboBoxMotorista.getSelectionModel().select(recebimentoPorcos.getMotorista());
            comboBoxGalpao.getSelectionModel().select(recebimentoPorcos.getGalpao());
            datePickerData.setValue(recebimentoPorcos.getData());
            textFieldQuantidadePorcos.setText(String.valueOf(recebimentoPorcos.getQuantidadePorcos()));
            checkBoxFilhotes.setSelected(recebimentoPorcos.isFilhotes());
        }else{
            limparCampos();
        }
    }

    public void carregarComboBoxCaminhao(){
        comboBoxCaminhao.setEditable(true);
        comboBoxCaminhao.getEditor().setEditable(false);
        comboBoxCaminhao.setConverter(new StringConverter<Caminhao>() {
            @Override
            public String toString(Caminhao object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Caminhao fromString(String string) {
                return comboBoxCaminhao.getSelectionModel().getSelectedItem();
            }
        });
                
        listCaminhoes = caminhaoDAO.listar();
        observableListCaminhoes = FXCollections.observableArrayList(listCaminhoes);
        comboBoxCaminhao.setItems(observableListCaminhoes);
    }
    
    public void carregarComboBoxMotorista(){
        comboBoxMotorista.setEditable(true);
        comboBoxMotorista.getEditor().setEditable(false);
        comboBoxMotorista.setConverter(new StringConverter<Motorista>() {
            @Override
            public String toString(Motorista object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Motorista fromString(String string) {
                return comboBoxMotorista.getSelectionModel().getSelectedItem();
            }
        });
        listMotoristas = motoristaDAO.listar();
        observableListMotoristas = FXCollections.observableArrayList(listMotoristas);
        comboBoxMotorista.setItems(observableListMotoristas);
    }
    
    public void carregarComboBoxGalpao(){
        comboBoxGalpao.setEditable(true);
        comboBoxGalpao.getEditor().setEditable(false);
        comboBoxGalpao.setConverter(new StringConverter<Galpao>() {
            @Override
            public String toString(Galpao object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Galpao fromString(String string) {
                return comboBoxGalpao.getSelectionModel().getSelectedItem();
            }
        });
        listGalpoes = galpaoDAO.listar();
        observableListGalpoes = FXCollections.observableArrayList(listGalpoes);
        comboBoxGalpao.setItems(observableListGalpoes);  
    }
    
    public boolean validarCampos(){
        boolean camposValidos = true;
        
        camposValidos = camposValidos && (comboBoxCaminhao.getSelectionModel().getSelectedItem() != null)
                          && (comboBoxMotorista.getSelectionModel().getSelectedItem() != null)
                          && (comboBoxGalpao.getSelectionModel().getSelectedItem() != null)
                          && (datePickerData.getValue() != null)
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
    public void handleButtonAlterar(){
        if(listViewRecebimentoPorcos.getSelectionModel().getSelectedItem() != null){
            if(validarCampos()){
                RecebimentoPorcos recebimentoPorcos = listViewRecebimentoPorcos.getSelectionModel().getSelectedItem();
                try{
                    connection.setAutoCommit(false);
                    recebimentoPorcosDAO.setConnection(connection);
                    galpaoDAO.setConnection(connection);
                    Galpao galpao = recebimentoPorcos.getGalpao();
                    if(galpao.getQuantidadeAtual() != 0){
                        galpao.setQuantidadeAtual(galpao.getQuantidadeAtual() - recebimentoPorcos.getQuantidadePorcos());
                    }
                    recebimentoPorcos.setQuantidadePorcos(0);
                    recebimentoPorcosDAO.alterar(recebimentoPorcos);
                    galpaoDAO.alterar(galpao);
                    
                    recebimentoPorcos.setCaminhao((Caminhao)comboBoxCaminhao.getSelectionModel().getSelectedItem());
                    recebimentoPorcos.setMotorista((Motorista)comboBoxMotorista.getSelectionModel().getSelectedItem());
                    recebimentoPorcos.setGalpao((Galpao)comboBoxGalpao.getSelectionModel().getSelectedItem());
                    recebimentoPorcos.setData(datePickerData.getValue());
                    recebimentoPorcos.setQuantidadePorcos(Integer.parseInt(textFieldQuantidadePorcos.getText()));
                    recebimentoPorcos.setFilhotes(checkBoxFilhotes.isSelected());
                    
                    if(verificarCapacidadeMaxima(recebimentoPorcos)){
                    //Regra de Negocio
                        if(verificarLimiteDiarioGalpao(recebimentoPorcos)){
                            recebimentoPorcosDAO.alterar(recebimentoPorcos);
                            galpao = recebimentoPorcos.getGalpao();
                            galpao.setQuantidadeAtual(galpao.getQuantidadeAtual() + recebimentoPorcos.getQuantidadePorcos());
                            galpaoDAO.alterar(galpao);
                            connection.commit();
                            carregarListView();
                            limparCampos();
                            listViewRecebimentoPorcos.getSelectionModel().clearSelection();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Alteração registrada com sucesso");
                            alert.show();                            
                        }else{
                            connection.rollback();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Não é possível receber os porcos no dia " + 
                                    recebimentoPorcos.getData().toString() + " pois essa quantidade ultrapassa o limite diário de " + 
                                    recebimentoPorcos.getGalpao().getLimiteDiario() + " porcos");
                            alert.show();
                        }
                    }else{
                        connection.rollback();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Não é possível receber os porcos pois essa quantidade ultrapassa a capacidade máxima do galpão");
                        alert.show();
                    }
                }catch(SQLException ex){
                    try{
                        connection.rollback();
                    }catch (SQLException ex1) {
                        Logger.getLogger(FXMLAnchorPaneRecebimentoPorcosAlterarRemoverController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(FXMLAnchorPaneRecebimentoPorcosAlterarRemoverController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Os dados estão incorretos, vazios ou possuem valores negativos, favor revisar!");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha um Recebimento na tabela ao lado!");
            alert.show();
        }
    }
    
    @FXML
    public void handleButtonRemover(){
        if(listViewRecebimentoPorcos.getSelectionModel().getSelectedItem() != null){
            RecebimentoPorcos recebimentoPorcos = listViewRecebimentoPorcos.getSelectionModel().getSelectedItem();
            
            try{
                connection.setAutoCommit(false);
                Galpao galpao = recebimentoPorcos.getGalpao();
                if(galpao.getQuantidadeAtual() != 0){
                    galpao.setQuantidadeAtual(galpao.getQuantidadeAtual() - recebimentoPorcos.getQuantidadePorcos());
                }
                galpaoDAO.alterar(galpao);
                recebimentoPorcosDAO.remover(recebimentoPorcos);
                connection.commit();
                
                listViewRecebimentoPorcos.getSelectionModel().clearSelection();
                limparCampos();
                carregarListView();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Recebimento de Porcos deletado com sucesso!");
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
            
        }
    }
    
}
