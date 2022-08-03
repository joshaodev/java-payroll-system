/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
 import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.UsuarioOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.Usuario;
import payrollsys.utils.Actions;


public class AddUsuarioController implements Initializable {
    //GUI components
    @FXML
    private JFXComboBox<Funcionario> cmbFuncionario;
    @FXML
    private JFXTextField textFieldNomeUsuario;
    @FXML
    private JFXPasswordField textFieldSenhaUsuario;
    @FXML
    private JFXPasswordField textFieldResenhaUsuario;
    @FXML
    private JFXRadioButton rdUsuarioNormal;
    @FXML
    private JFXRadioButton rdAdministrador;

    
    /* Data collections */
    private ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();
    
    //Var to connect to database.
    private Conexao conexao = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregarFuncionarios(listaFuncionarios);
    }    

    @FXML
    private void addUsuario(ActionEvent event) {
        Usuario usuarioToInsert = new Usuario();
      
        usuarioToInsert.setNomeUsuario(textFieldNomeUsuario.getText()); // Campo1
        if( matchePassword()){
            usuarioToInsert.setSenhaUsuario(textFieldSenhaUsuario.getText());// Campo2
            if(isAdmin()){
                usuarioToInsert.setAdmin(1);
            }
            else{
                usuarioToInsert.setAdmin(0);
            }

            usuarioToInsert.setFuncionarioUsuario(cmbFuncionario.getValue());

            conexao = new Conexao();
            int result = UsuarioOp.InserirUsusario(conexao.getConnection(), usuarioToInsert);
            conexao.fecharConexao();

            if (result == 1) {
                String title = "Registro Inserido";
                String content = "O Registro foi inserido com exito!";
                String header = "Resultado:";
                DynamicViews.informationAlert(title, content, header);
            }

            Actions.closeWindow(event);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }
    
    // MÉTODOS AUXILIARES
    private void carregarFuncionarios(ObservableList<Funcionario> listaFuncionarios){
        conexao = new Conexao();
        listaFuncionarios.clear();
        FuncionarioOp.SelecionarFuncionarios(conexao.getConnection(), listaFuncionarios);
        System.out.println(listaFuncionarios.get(0));
        cmbFuncionario.setItems(listaFuncionarios);
        conexao.fecharConexao();
    }
    
    private boolean matchePassword(){
        if(textFieldSenhaUsuario.getText().equals(textFieldResenhaUsuario.getText())){
            return true;
        }
        else{
            String title = "Senhas Incompativeis";
            String content = "As Senhas inseridas não sao iguais, tenta denovo.";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
            return false;
        }
    }
    
    private boolean isAdmin(){
        return !rdUsuarioNormal.isSelected();
    }
    
}
