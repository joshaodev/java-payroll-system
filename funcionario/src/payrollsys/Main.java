/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author joshtag096
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage loginStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/payrollsys/ui/atendimento.fxml"));
        
        Scene scene = new Scene(root);
     
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
