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
import sistema.Main;

public class FXMLHboxMainController implements Initializable {
    @FXML
    private JFXTabPane tabPaneOpcoes;
    
    @FXML
    private Tab tabGalpao;

    @FXML
    private Tab tabRecebimento;

    @FXML
    private Tab tabDashboard;

    @FXML
    private Tab tabRelatorio;
    
    @FXML
    private Tab tabSair;
    
    @FXML
    private AnchorPane anchorPane;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabPaneOpcoes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() { 
            @Override 
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab.equals(tabGalpao)) {            
                    try {
                        handleMenuCadastroGalpao();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLHboxMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabRecebimento)) {
                    try {
                        handleMenuCadastroRecebimento();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLHboxMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabDashboard)) {
                    try {
                        handleMenuDashboard();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLHboxMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabRelatorio)) {
                    try {
                        handleMenuRelatorio();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLHboxMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabSair)){
                    try {
                        handleMenuSair();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLHboxMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }   

    public void handleMenuCadastroGalpao() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneGalpao.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuCadastroRecebimento() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneRecebimentoPorcos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuDashboard() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneGrafico.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuRelatorio() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneRelatorio.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuSair() throws IOException{
        Main.setRoot(FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneLogin.fxml")));
    }
    
}
