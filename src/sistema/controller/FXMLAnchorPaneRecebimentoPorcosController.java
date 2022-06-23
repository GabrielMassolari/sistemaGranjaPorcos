package sistema.controller;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class FXMLAnchorPaneRecebimentoPorcosController implements Initializable {
    @FXML
    private JFXTabPane tabPaneRecebimento;

    @FXML
    private Tab tabInserir;

    @FXML
    private Tab tabAlterarRemover;

    @FXML
    private Tab tabListar;

    @FXML
    private AnchorPane anchorPane;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carregar AnchorPane de Inserção
        try {
            handleMenuCadastroRecebimentoPorcosInserir();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnchorPaneRecebimentoPorcosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Criação de um listener no TabPane para troca de telas 
        tabPaneRecebimento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() { 
            @Override 
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab.equals(tabInserir)) {            
                    try {
                        handleMenuCadastroRecebimentoPorcosInserir();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLHboxMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabAlterarRemover)){
                    try {
                        handleMenuCadastroRecebimentoPorcosAlterarRemover();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAnchorPaneGalpaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabListar)){
                    try {
                        handleMenuCadastroRecebimentoPorcosListar();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAnchorPaneGalpaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    public void handleMenuCadastroRecebimentoPorcosInserir() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneRecebimentoPorcosInserir.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuCadastroRecebimentoPorcosAlterarRemover() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneRecebimentoPorcosAlterarRemover.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuCadastroRecebimentoPorcosListar() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneRecebimentoPorcosListar.fxml"));
        anchorPane.getChildren().setAll(a);
    }
}
