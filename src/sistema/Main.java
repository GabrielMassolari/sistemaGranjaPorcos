package sistema;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLAnchorPaneLogin.fxml"));
        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema Granja de Porcos");
        stage.setResizable(false);
        stage.show();
    }
    
    public static void setRoot(Parent root){
        scene.setRoot(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
