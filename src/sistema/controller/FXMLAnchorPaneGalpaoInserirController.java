package sistema.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.StringLengthValidator;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import sistema.model.dao.GalpaoDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;
import sistema.model.domain.Galpao;

public class FXMLAnchorPaneGalpaoInserirController implements Initializable {
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
    private JFXButton buttonInserir;
    
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
        
        //Criação de Listeners para validação imediata após inserção de algum valor
        textFieldNome.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldNome.validate();
        });
        textFieldMetrosQuadrados.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                textFieldMetrosQuadrados.validate();
            }
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
                          && Integer.parseInt(textFieldLimiteDiario.getText()) >= 0;

        return camposValidados;
    }
    
    public void limparCampos(){
        textFieldNome.clear();
        textFieldMetrosQuadrados.clear();
        textFieldCapacidadeMax.clear();
        textFieldNumeroBaias.clear();
        textFieldLimiteDiario.clear();
        checkBoxRefrigerado.setSelected(false);
        checkBoxMaternidade.setSelected(false);
    }
    
    @FXML
    public void handleButtonInserir(){
        if(validarCampos()){
            Galpao galpao = new Galpao();
            galpao.setCdGalpao(0);
            galpao.setNome(textFieldNome.getText());
            galpao.setMetrosQuadrados(Double.parseDouble(textFieldMetrosQuadrados.getText()));
            galpao.setCapacidadeMaxima(Integer.parseInt(textFieldCapacidadeMax.getText()));
            galpao.setNumeroBaias(Integer.parseInt(textFieldNumeroBaias.getText()));
            galpao.setLimiteDiario(Integer.parseInt(textFieldLimiteDiario.getText()));
            galpao.setQuantidadeAtual(0);
            galpao.setRefrigerado(checkBoxRefrigerado.isSelected());
            galpao.setMaternidade(checkBoxMaternidade.isSelected());         
            if(galpaoDAO.inserir(galpao)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Galpão inserido com sucesso");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Esse nome já existe, favor revisar!");
                alert.show();
            }
            limparCampos();
            
            
            
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Os dados estão incorretos, vazios ou possuem valores negativos, favor revisar!");
            alert.show();
        }
    }
}
