/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import payrollsys.db.CargoOp;
import payrollsys.db.Conexao;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Cargo;
import payrollsys.utils.Actions;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class AddCargoController implements Initializable {
    //GUI components
    @FXML
    private JFXTextField textFieldNome;
    @FXML
    private JFXTextArea textDescricao;
    
    //Var to connect to database.
    Conexao conexao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize
    }    

    @FXML
    private void addCargo(ActionEvent event) { 
        Cargo cargoToInsert = new Cargo();
        cargoToInsert.setNome(textFieldNome.getText());
        cargoToInsert.setDescricao(textDescricao.getText());
        
        conexao = new Conexao();
        int result = CargoOp.InserirCargo(conexao.getConnection(), cargoToInsert);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Inserido";
            String content = "O Registro foi inserido com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }
    
}
