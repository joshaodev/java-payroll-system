/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import payrollsys.models.Usuario;
import payrollsys.utils.Actions;
import payrollsys.utils.DynamicViews;


public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private ImageView userImage;
    @FXML
    private JFXButton btnUser;

    
    Usuario loggedUsuario = LoginController.loggedUsuario;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image(loggedUsuario.getFuncionarioUsuario().getFoto());
        userImage.setImage(image);
        btnUser.setText(loggedUsuario.getNomeUsuario());
        setWindow("/payrollsys/ui/dashboard.fxml");
    }    

    @FXML
    private void loadUserConfig(ActionEvent event) {
        DynamicViews.getwindows("/payrollsys/ui/userConfig.fxml", "Configurações do usuário");
    }
    
    @FXML
    private void loadHome(ActionEvent event) {
        setWindow("/payrollsys/ui/dashboard.fxml");
    }
    
    @FXML
    private void loadPresencas(ActionEvent event){
        setWindow("/payrollsys/ui/presencas.fxml");
    }
    
    @FXML
    private void loadFuncionarios(ActionEvent event){
        setWindow("/payrollsys/ui/funcionarios.fxml");
    }

    @FXML
    private void loadHorasExtras(ActionEvent event) {
       setWindow("/payrollsys/ui/horasExtras.fxml");
    }

    @FXML
    private void loadSaidasDoSistema(ActionEvent event) {
        setWindow("/payrollsys/ui/saidasDoSistema.fxml");
    }
    
    @FXML
    private void loadTurnos(ActionEvent event) {
        setWindow("/payrollsys/ui/turnos.fxml");
    }

    @FXML
    private void loadCargos(ActionEvent event) {
        setWindow("/payrollsys/ui/cargos.fxml");
    }

    @FXML
    private void loadDepartamentos(ActionEvent event) {
        setWindow("/payrollsys/ui/departamentos.fxml");
    }
    
    @FXML
    private void loadListaPagamento(ActionEvent event) {
        setWindow("/payrollsys/ui/listaPagamento.fxml");
    }
    
    @FXML
    private void loadGerarFolhaDeSalario(ActionEvent event) {
        getwindows("/payrollsys/ui/folhaSalario.fxml", "Folha de pagamento");
    }
    
    /* Metodo para carregar as telas */
    private void setWindow(String path){
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
            System.out.println(ex);
        }
        root.setCenter(parent);
    }

    private void getwindows(String path, String title){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(path));
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

    @FXML
    private void sair(ActionEvent event) {
        Actions.closeWindow(event);
        loadLogin();
    }

    public void setUI(Usuario usuario){
        loggedUsuario = usuario;
    }
    
    private void loadLogin(){
        try {
                String path = "/payrollsys/ui/login.fxml";
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource(path));

                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
                System.out.println(ex);
                System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }
 
}
