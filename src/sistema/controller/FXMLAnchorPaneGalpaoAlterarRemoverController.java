package sistema.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import sistema.model.dao.GalpaoDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;
import sistema.model.domain.Galpao;

public class FXMLAnchorPaneGalpaoAlterarRemoverController implements Initializable {
    @FXML
    private JFXTextField textFieldNome;

    @FXML
    private JFXTextField textFieldMetrosQuadrados;

    @FXML
    private JFXTextField textFieldCapacidadeMax;

    @FXML
    private JFXTextField textFieldNumeroBaias;

    @FXML
    private JFXTextField textFieldLimiteDiario;

    @FXML
    private JFXTextField textFieldQuantidadeAtual;

    @FXML
    private JFXCheckBox checkBoxRefrigerado;

    @FXML
    private JFXCheckBox checkBoxMaternidade;

    @FXML
    private JFXListView<Galpao> listViewGalpao;

    @FXML
    private JFXButton buttonAlterar;

    @FXML
    private JFXButton buttonRemover;
    
    private List<Galpao> listGalpoes;
    private ObservableList<Galpao> observableListGalpoes;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final GalpaoDAO galpaoDAO = new GalpaoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        galpaoDAO.setConnection(connection);
        //Declaração dos objetos de validações de dados
        RequiredFieldValidator validadorCampoObrigatorio = new RequiredFieldValidator();
        NumberValidator validadorNumero = new NumberValidator();
        IntegerValidator validadorInteiro = new IntegerValidator();
        StringLengthValidator validadorTamanhoString = new StringLengthValidator(40);
        
        //Configuração dos objetos de validações de dados
        validadorCampoObrigatorio.setMessage("Campo deve ser preenchido");
        validadorNumero.setMessage("Insira um número");
        validadorInteiro.setMessage("Insira um inteiro");
        validadorTamanhoString.setMessage("Insira menos que 40 caracteres");
        
        //Adição dos objetos de validações de dados nos componentes visuais
        textFieldNome.getValidators().add(validadorCampoObrigatorio);
        textFieldNome.getValidators().add(validadorTamanhoString);
        
        textFieldMetrosQuadrados.getValidators().add(validadorNumero);
        textFieldMetrosQuadrados.getValidators().add(validadorCampoObrigatorio);
        
        textFieldCapacidadeMax.getValidators().add(validadorInteiro);
        textFieldCapacidadeMax.getValidators().add(validadorCampoObrigatorio);
        
        textFieldNumeroBaias.getValidators().add(validadorInteiro);
        textFieldNumeroBaias.getValidators().add(validadorCampoObrigatorio);
        
        textFieldLimiteDiario.getValidators().add(validadorInteiro);
        textFieldLimiteDiario.getValidators().add(validadorCampoObrigatorio);
        
        textFieldQuantidadeAtual.getValidators().add(validadorInteiro);
        textFieldQuantidadeAtual.getValidators().add(validadorCampoObrigatorio);
        
        //Criação de Listeners para validação imediata após inserção de algum valor
        textFieldNome.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldNome.validate();
        });
        textFieldMetrosQuadrados.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldMetrosQuadrados.validate();
        });
        textFieldCapacidadeMax.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldCapacidadeMax.validate();
        });
        textFieldNumeroBaias.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldNumeroBaias.validate();
        });
        textFieldLimiteDiario.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldLimiteDiario.validate();
        });
        textFieldQuantidadeAtual.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldQuantidadeAtual.validate();
        });
        listViewGalpao.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemListView(newValue));
        
        carregarListView();
    }

    public void carregarListView(){
        listGalpoes = galpaoDAO.listar();
        observableListGalpoes = FXCollections.observableArrayList(listGalpoes);
        listViewGalpao.setItems(observableListGalpoes);
    }
    
     public boolean validarCampos(){
        boolean camposValidados = true;
        
        camposValidados = camposValidados && textFieldNome.validate()
                          && textFieldMetrosQuadrados.validate()
                          && textFieldCapacidadeMax.validate()
                          && textFieldNumeroBaias.validate()
                          && textFieldLimiteDiario.validate()
                          && Double.parseDouble(textFieldMetrosQuadrados.getText()) >= 0
                          && Integer.parseInt(textFieldCapacidadeMax.getText()) >= 0
                          && Integer.parseInt(textFieldNumeroBaias.getText()) >= 0
                          && Integer.parseInt(textFieldLimiteDiario.getText()) >= 0
                          && Integer.parseInt(textFieldQuantidadeAtual.getText()) >= 0;

        return camposValidados;
    }
    
    public void selecionarItemListView(Galpao galpao){
        if(galpao != null){
            textFieldNome.setText(galpao.getNome());
            textFieldMetrosQuadrados.setText(String.valueOf(galpao.getMetrosQuadrados()));
            textFieldCapacidadeMax.setText(String.valueOf(galpao.getCapacidadeMaxima()));
            textFieldNumeroBaias.setText(String.valueOf(galpao.getNumeroBaias()));
            textFieldLimiteDiario.setText(String.valueOf(galpao.getLimiteDiario()));
            textFieldQuantidadeAtual.setText(String.valueOf(galpao.getQuantidadeAtual()));
            checkBoxMaternidade.setSelected(galpao.isMaternidade());
            checkBoxRefrigerado.setSelected(galpao.isRefrigerado());
            validarCampos();
        }else{
            limparCampos();
        }
    }
    
    public void limparCampos(){
        textFieldNome.clear();
        textFieldMetrosQuadrados.clear();
        textFieldCapacidadeMax.clear();
        textFieldNumeroBaias.clear();
        textFieldLimiteDiario.clear();
        textFieldQuantidadeAtual.clear();
        checkBoxRefrigerado.setSelected(false);
        checkBoxMaternidade.setSelected(false);
    }
    
    @FXML
    public void handleButtonAlterar(){
        if(listViewGalpao.getSelectionModel().getSelectedItem() != null) {
            if(validarCampos()){
                Galpao galpao = listViewGalpao.getSelectionModel().getSelectedItem();
            
                galpao.setNome(textFieldNome.getText());
                galpao.setMetrosQuadrados(Double.parseDouble(textFieldMetrosQuadrados.getText()));
                galpao.setCapacidadeMaxima(Integer.parseInt(textFieldCapacidadeMax.getText()));
                galpao.setNumeroBaias(Integer.parseInt(textFieldNumeroBaias.getText()));
                galpao.setLimiteDiario(Integer.parseInt(textFieldLimiteDiario.getText()));
                galpao.setQuantidadeAtual(Integer.parseInt(textFieldQuantidadeAtual.getText()));
                galpao.setRefrigerado(checkBoxRefrigerado.isSelected());
                galpao.setMaternidade(checkBoxMaternidade.isSelected());

                if(galpaoDAO.alterar(galpao)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Galpão alterado com sucesso!");
                    alert.show(); 
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Esse nome já existe, favor revisar!");
                    alert.show();
                }
                listViewGalpao.getSelectionModel().clearSelection();
                limparCampos();
                carregarListView();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Os dados estão incorretos, vazios ou possuem valores negativos, favor revisar!");
                alert.show(); 
            } 
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha um galpão na lista!");
            alert.show(); 
        }
    }
    
    @FXML
    public void handleButtonRemover(){
        if(listViewGalpao.getSelectionModel().getSelectedItem() != null){
            Galpao galpao = listViewGalpao.getSelectionModel().getSelectedItem();
            
            if(galpaoDAO.remover(galpao)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Galpão deletado com sucesso!");
                alert.show(); 
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Não foi possível excluir o Galpão " + galpao.getNome() +", pois há recebimentos dependentes deste galpão!");
                alert.show(); 
            }
            listViewGalpao.getSelectionModel().clearSelection();
            limparCampos();
            carregarListView();
            
            
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha um galpão na lista!");
            alert.show(); 
        }
    }
}
