/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.PresencaOp;
import payrollsys.db.UsuarioOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Cargo;
import payrollsys.models.Departamento;
import payrollsys.models.Funcionario;
import payrollsys.models.Turno;
import payrollsys.models.Usuario;
import payrollsys.utils.Actions;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class UserConfigController implements Initializable {
    @FXML
    private Tab tabActualizarConta;
    @FXML
    private Tab tabGerenciarConta;
    /* ==================== */
    @FXML
    private JFXTextField textFieldNomeUsuarioEditar;
    @FXML
    private JFXPasswordField textFieldSenha1UsuarioEditar;
    @FXML
    private JFXPasswordField textFieldSenha2UsuarioEditar;
    @FXML
    private JFXPasswordField textFieldSenha3UsuarioEditar;
    @FXML
    private ImageView imgUsuarioLogado;
    
    /* =================== */
    @FXML
    private JFXTextField nomeFuncionario_2;
    @FXML
    private JFXTextField nomeUsuario_2;
    @FXML
    private JFXRadioButton rdUsuarioNormal;
    @FXML
    private ToggleGroup grupoUsuario;
    @FXML
    private JFXRadioButton rdAdministrador;
    @FXML
    private TableView<Usuario> tabelaUsuarios;
    @FXML
    private TableColumn<Usuario, String> colNomeUsuario;
    @FXML
    private TableColumn<Usuario, Funcionario> colNomeFuncionario;
    @FXML
    private JFXButton btnInserir;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    /* ======================== */
    
    /* Data collections */
    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    
    /* Selected */
    private Usuario selectedUsuario = null;
    private Usuario loggedUsuario = LoginController.loggedUsuario;
    
    
    /* Conexao Banco Dados */
    private Conexao conexao = null;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textFieldNomeUsuarioEditar.setText(loggedUsuario.getNomeUsuario());
        if(loggedUsuario.getAdmin() == 0){
            tabGerenciarConta.setDisable(true);
        }
        else{
            tabGerenciarConta.setDisable(false);
        }
        
        
        desableEditDeleteButtons();
      
        setDataInTable(listaUsuarios);
        setTableColumns();

        manageTableSelectionMode();
    }    

    @FXML
    private void actualizarDadosUsuario(ActionEvent event) {
        conexao = new Conexao();
        
        String nomeUsuario = textFieldNomeUsuarioEditar.getText();
        String senhaActual = textFieldSenha1UsuarioEditar.getText();
        String senhaNova = textFieldSenha2UsuarioEditar.getText();
        String resenha = textFieldSenha3UsuarioEditar.getText();
        
        loggedUsuario.setNomeUsuario(nomeUsuario);
        if(senhaActual.equals(loggedUsuario.getSenhaUsuario()) && senhaNova.equals(resenha)){
            loggedUsuario.setSenhaUsuario(senhaNova);
        }
        
        int result = UsuarioOp.ActualizarUsuario(conexao.getConnection(), loggedUsuario);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito. Reinicie o programa para actualizar!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }

    @FXML
    private void actualizarTabela(ActionEvent event) {
        setDataInTable(listaUsuarios);
        desableEditDeleteButtons();
        clearEdition();
    }

    @FXML
    private void loadInserirUsuario(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/addUsuario.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(path));

            Stage stage = new Stage();
            stage.setTitle("Novo Usuario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void loadEditarUsuario(ActionEvent event) {
        conexao = new Conexao();
        
        int adminStatus = 0;
        if(rdAdministrador.isSelected()){
            adminStatus = 1;
        }
        else if(rdUsuarioNormal.isSelected()){
            adminStatus = 0;
        }
        Usuario usuarioToEdit = new Usuario(
                selectedUsuario.getIdUsuario(),
                selectedUsuario.getNomeUsuario(),
                selectedUsuario.getSenhaUsuario(),
                adminStatus,
                selectedUsuario.getFuncionarioUsuario()
        );
        
        int result = UsuarioOp.ActualizarUsuario(conexao.getConnection(), usuarioToEdit);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar usuário!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = UsuarioOp.EliminarUsuario(conexao.getConnection(), selectedUsuario);
            conexao.fecharConexao();
            if (operationResult == 1) {
                String title = "Registro Eliminado";
                String content = "O Registro foi eliminado com exito!";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }  
        }
        
    }
    
    
    /* =============== Metodos auxiliares ====================== */
    private void setTableColumns(){
       colNomeUsuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nomeUsuario"));
       colNomeFuncionario.setCellValueFactory(new PropertyValueFactory<Usuario, Funcionario>("funcionarioUsuario"));
    }
    
    private void setDataInTable(ObservableList<Usuario> listaFuncionarios){
        conexao = new Conexao();
        listaUsuarios.clear();
        UsuarioOp.SelecionarUsuario(conexao.getConnection(), listaUsuarios);
        //System.out.println(listaUsuarios.get(0));
        if(listaUsuarios.size() > 0){
            tabelaUsuarios.setItems(listaUsuarios);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaUsuarios.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Usuario>() {
                @Override
                public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue);
                        selectedUsuario = selectedValue;
                        setEdition(selectedUsuario);
                        enableEditDeleteButtons();
                    }
                }
            }
        );
    }
    
    private void enableEditDeleteButtons(){
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    
    private void desableEditDeleteButtons(){
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    private void setEdition(Usuario usuario){
        nomeFuncionario_2.setText(usuario.getFuncionarioUsuario().getNome());
        nomeUsuario_2.setText(usuario.getNomeUsuario());
        if(usuario.getAdmin() == 1){
            rdAdministrador.setSelected(true);
            rdUsuarioNormal.setSelected(false);
        }
        else{
            rdAdministrador.setSelected(false);
            rdUsuarioNormal.setSelected(true);
        }        
        System.out.println(usuario.getIdUsuario());
    }
    
    private void clearEdition(){
        nomeFuncionario_2.setText("");
        nomeUsuario_2.setText("");
        rdAdministrador.setSelected(false); 
        rdUsuarioNormal.setSelected(false);     
    }
    
    /*public void usuarioLoggado(){
        textFieldNomeUsuarioEditar.setText("");
        textFieldSenha1UsuarioEditar.setText()
        textFieldSenha2UsuarioEditar;
        textFieldSenha3UsuarioEditar;
        imgUsuarioLogado;
    }*/
    
    public void setUI(Usuario usuario){
        loggedUsuario = usuario;
    }
}
