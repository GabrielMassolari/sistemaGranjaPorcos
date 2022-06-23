package sistema.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import sistema.Main;
import sistema.model.dao.UsuarioDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;
import sistema.model.domain.Usuario;

public class FXMLAnchorPaneLoginController implements Initializable {
    @FXML
    private JFXTextField textFieldUsuario;

    @FXML
    private JFXPasswordField textFieldSenha;

    @FXML
    private JFXButton buttonLogin;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO.setConnection(connection);
        //Declaração dos objetos de validações de dados
        RequiredFieldValidator validadorCampoObrigatorio = new RequiredFieldValidator();
        IntegerValidator validadorInteiro = new IntegerValidator();
        
        //Configuração dos objetos de validações de dados
        validadorCampoObrigatorio.setMessage("Campo deve ser preenchido");
        validadorInteiro.setMessage("Insira um valor inteiro(Id)");
        
        //Adição dos objetos de validações de dados nos componentes visuais
        textFieldUsuario.getValidators().add(validadorInteiro);
        textFieldUsuario.getValidators().add(validadorCampoObrigatorio);
        
        textFieldSenha.getValidators().add(validadorCampoObrigatorio);
        
        //Criação de Listeners para validação imediata após inserção de algum valor
         textFieldUsuario.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldUsuario.validate();
        });
        textFieldSenha.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)textFieldSenha.validate();
        });
    }

    public String encriptarSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));
        
        //Transformação para Hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhaHex = hexString.toString();
        
       return senhaHex;
    }
    
    @FXML
    public void handleButtonLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{
        if(textFieldUsuario.validate() && textFieldSenha.validate()){
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(Integer.parseInt(textFieldUsuario.getText()));
            usuario.setSenha(textFieldSenha.getText());
            String senhaDigitada = usuario.getSenha();
            String senhaUser = usuarioDAO.buscarSenha(usuario);
            
            if(senhaUser != null){
                if(encriptarSenha(senhaDigitada).equals(senhaUser)){
                    Main.setRoot(FXMLLoader.load(getClass().getResource("/sistema/view/FXMLHboxMain.fxml")));
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Login Incorreto");
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ID não existe");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Favor preencher os campos de Login!");
            alert.show();
        }
    }
}
