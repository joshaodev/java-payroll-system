/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import payrollsys.db.Conexao;
import payrollsys.db.DepartamentoOp;
import payrollsys.models.Departamento;
import payrollsys.utils.Actions;
import payrollsys.utils.DynamicViews;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class EditarDepartamentoController implements Initializable {
    @FXML
    private JFXTextField textFieldName;
    
    Conexao conexao;
    private Departamento deptToEdit ;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Departamento dept = deptController.getSelectedDepartamento();
        //textFieldName.setText(dept.getNome());
    }    

    @FXML
    private void Cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }

    @FXML
    private void editarDepartamento(ActionEvent event) {
        conexao = new Conexao();
        
        deptToEdit.setNome(textFieldName.getText());
        int result = DepartamentoOp.ActualizarDados(conexao.getConnection(), deptToEdit);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }
    
    public void setUI(Departamento dept){
        this.deptToEdit = dept;
        textFieldName.setText(dept.getNome());
    }
}
