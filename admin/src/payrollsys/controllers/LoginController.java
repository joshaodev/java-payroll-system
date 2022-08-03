/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import payrollsys.db.Conexao;
import payrollsys.db.UsuarioOp;
import payrollsys.models.Usuario;
import payrollsys.utils.Actions;


public class LoginController implements Initializable {
    @FXML
    private Label msgError;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;

    
    /* Selected */
    public static Usuario loggedUsuario = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        msgError.setVisible(false);
    }    

    @FXML
    private void login(ActionEvent event) {
        String uName =  usernameField.getText();
        String uPassword =  passwordField.getText();
        
        conexao = new Conexao();
        loggedUsuario = UsuarioOp.SelecionarUsuario(conexao.getConnection(), uName);
        System.out.println(loggedUsuario);
        conexao.fecharConexao();
        
        if(loggedUsuario == null){
            System.out.println("Usuario não existe!"); 
            msgError.setText("O nome de usuário ou a senha, estão incorrectas, porfavor, verifique e tente novamente!");
            msgError.setVisible(true);
            resetLoginFields();
        }
        else{
            if(uName.equals(loggedUsuario.getNomeUsuario()) && uPassword.equals(loggedUsuario.getSenhaUsuario())){
                System.out.println("Login");
                Actions.closeWindow(event);   // Esconde a tela de Login
                loadMain();    // Chama a tela Main
            }
            else{
                System.out.println("Password invalida, tenta novamente."); 
                msgError.setVisible(true);
                passwordField.setText("");
            }
        }
        
    }
    
    
    //METODOS AUXILIARES
     
    public void resetLoginFields(){
        usernameField.setText("");
        passwordField.setText("");
    }
    
    private void loadMain(){
        try {
                String path = "/payrollsys/ui/main.fxml";
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource(path));
               
                Stage stage = new Stage();
                stage.setTitle("Main App");
                stage.setMaximized(true);
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
                System.out.println(ex);
                System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }
}
