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
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLAnchorPaneGalpaoController implements Initializable {
    @FXML
    private JFXTabPane tabPaneGalpao;

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
            handleMenuCadastroGalpaoInserir();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnchorPaneGalpaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Criação de um listener no TabPane para troca de telas 
        tabPaneGalpao.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() { 
            @Override 
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab.equals(tabInserir)) {            
                    try {
                        handleMenuCadastroGalpaoInserir();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLHboxMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabAlterarRemover)){
                    try {
                        handleMenuCadastroGalpaoAlterarRemover();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAnchorPaneGalpaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(newTab.equals(tabListar)){
                    try {
                        showFXMLAnchorPaneCadastroGalapaoListarDialog();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLAnchorPaneGalpaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void handleMenuCadastroGalpaoInserir() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneGalpaoInserir.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuCadastroGalpaoAlterarRemover() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/sistema/view/FXMLAnchorPaneGalpaoAlterarRemover.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void showFXMLAnchorPaneCadastroGalapaoListarDialog() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneGalpaoListarDialogController.class.getResource("/sistema/view/FXMLAnchorPaneGalpaoListarDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Listagem de Galpões");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        dialogStage.showAndWait();
        tabPaneGalpao.getSelectionModel().select(tabInserir);
    }
}
