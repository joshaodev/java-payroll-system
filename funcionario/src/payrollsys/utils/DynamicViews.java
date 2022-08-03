/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author joshtag096
 */
public class DynamicViews {
    private DynamicViews(){
        
    }
    
    public static void getwindows(String path, String title){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(new DynamicViews().getClass().getResource(path));
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
        
        Scene scene = new Scene(root);
        
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public static void MessageAlert(String title, String content, String header){
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle(title);
        message.setContentText(content);
        message.setHeaderText(header);
        message.show();
    }
}
