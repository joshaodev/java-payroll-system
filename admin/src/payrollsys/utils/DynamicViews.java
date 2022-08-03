/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.utils;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
        stage.initModality (Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();
    }
    
    public static void informationAlert(String title, String content, String header){
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle(title);
        message.setContentText(content);
        message.setHeaderText(header);
        message.show();
    }
    
    public static boolean confirmationAlert(String title, String header, String context){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }
}
