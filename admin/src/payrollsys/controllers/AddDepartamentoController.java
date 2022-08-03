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
import payrollsys.db.Conexao;
import payrollsys.db.DepartamentoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Departamento;
import payrollsys.utils.Actions;


public class AddDepartamentoController implements Initializable {
    //GUI components
    @FXML
    private JFXTextField textFieldName;

    //Var to connect to database.
    Conexao conexao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize
    }    

    @FXML
    private void inserirDepartamento(ActionEvent event) {
        Departamento deptToInsert = new Departamento();
        deptToInsert.setNome(textFieldName.getText());
        
        conexao = new Conexao();
        int result = DepartamentoOp.InserirDados(conexao.getConnection(), deptToInsert);
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
    private void Cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }
    
}
