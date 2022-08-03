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
import payrollsys.db.DepartamentoOp;
import payrollsys.models.Cargo;
import payrollsys.models.Departamento;
import payrollsys.utils.Actions;
import payrollsys.utils.DynamicViews;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class EditarCargoController implements Initializable {
    @FXML
    private JFXTextField textFieldNome;
    @FXML
    private JFXTextArea textDescricao;

    
    Conexao conexao;
    private Cargo cargoToEdit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }

    @FXML
    private void actualizar(ActionEvent event) {
        conexao = new Conexao();
        
        cargoToEdit.setNome(textFieldNome.getText());
        cargoToEdit.setDescricao(textDescricao.getText());
        int result = CargoOp.ActualizarCargo(conexao.getConnection(), cargoToEdit);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }
    
    //AUX METODOS
    public void setUI(Cargo cargo){
        this.cargoToEdit = cargo;
        textFieldNome.setText(cargo.getNome());
        textDescricao.setText(cargo.getDescricao());
    }
}
