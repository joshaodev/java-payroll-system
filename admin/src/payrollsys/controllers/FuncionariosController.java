/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Cargo;
import payrollsys.models.Departamento;
import payrollsys.models.Funcionario;
import payrollsys.models.Turno;


public class FuncionariosController implements Initializable {
    /* GUI Components */
    @FXML
    private TableView<Funcionario> tabelaFuncionarios;
    @FXML
    private TableColumn<Funcionario, String> colCodigo;
    @FXML
    private TableColumn<Funcionario, String> colNome;
    @FXML
    private TableColumn<Funcionario, String> colEndereco;
    @FXML
    private TableColumn<Funcionario, String> colTelefone;
    @FXML
    private TableColumn<Funcionario, Date> colDataNasc;
    @FXML
    private TableColumn<Funcionario, Departamento> colDepartamento;
    @FXML
    private TableColumn<Funcionario, Cargo> colCargo;
    @FXML
    private TableColumn<Funcionario, Turno> colTurno;
    @FXML
    private TableColumn<Funcionario, Date> ColDataContrato;
    @FXML
    private TableColumn<Funcionario, Double> colSalarioBase;
    @FXML
    private JFXButton btnVisualizar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;

    
    /* Data collections */
    private ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();
    
    /* Selected */
    private Funcionario selectedFuncionario = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao = null;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desableEditDeleteButtons();
      
        setDataInTable(listaFuncionarios);
        setTableColumns();

        manageTableSelectionMode();
    }    

    @FXML
    private void loadAddFuncionario(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/addFuncionario.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(path));

            Stage stage = new Stage();
            stage.setTitle("Novo Funcionario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void gerarReport(ActionEvent event) {
        //GERA UM RELATORIO DE FUNCIONARIOS
        System.out.println("Gerando Reports ...");
        conexao = new Conexao();
        try{
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportFuncionarios.jasper"), null, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
    }
    
    @FXML
    private void actualizarTabela(ActionEvent event) {
        setDataInTable(listaFuncionarios);
        desableEditDeleteButtons();
    }
    
    @FXML
    private void visualizar(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/visualizarFuncionario.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            VisualizarFuncionarioController controller = (VisualizarFuncionarioController) loader.getController();
            controller.setUI(selectedFuncionario);
            
            Stage stage = new Stage();
            stage.setTitle("Visualizar Funcionqario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/editarFuncionario.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            EditarFuncionarioController controller = (EditarFuncionarioController) loader.getController();
            controller.setUI(selectedFuncionario);
            
            Stage stage = new Stage();
            stage.setTitle("Editar Funcionqario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar funcionário!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = FuncionarioOp.EliminarFuncionario(conexao.getConnection(), selectedFuncionario);
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
       colCodigo.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("codigo"));
       colNome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
       colEndereco.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("endereco"));
       colTelefone.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("telefone"));
       colDataNasc.setCellValueFactory(new PropertyValueFactory<Funcionario, Date>("dataNascimento"));
       colDepartamento.setCellValueFactory(new PropertyValueFactory<Funcionario, Departamento>("departamento"));
       colCargo.setCellValueFactory(new PropertyValueFactory<Funcionario, Cargo>("cargo"));
       colTurno.setCellValueFactory(new PropertyValueFactory<Funcionario, Turno>("turno"));
       ColDataContrato.setCellValueFactory(new PropertyValueFactory<Funcionario, Date>("dataContrato"));
       colSalarioBase.setCellValueFactory(new PropertyValueFactory<Funcionario, Double>("salarioBase"));
    }
    
    private void setDataInTable(ObservableList<Funcionario> listaFuncionarios){
        conexao = new Conexao();
        listaFuncionarios.clear();
        FuncionarioOp.SelecionarFuncionarios(conexao.getConnection(), listaFuncionarios);
        
        //System.out.println(listaFuncionarios.get(0));
        System.out.println(listaFuncionarios.size());
        if(listaFuncionarios.size() > 0){
            tabelaFuncionarios.setItems(listaFuncionarios);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaFuncionarios.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Funcionario>() {
                @Override
                public void changed(ObservableValue<? extends Funcionario> observable, Funcionario oldValue, Funcionario selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue.getCodigo());
                        selectedFuncionario = selectedValue;
                        enableEditDeleteButtons();
                    }
                }
            }
        );
    }
    
    private void enableEditDeleteButtons(){
        btnVisualizar.setDisable(false);
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    
    private void desableEditDeleteButtons(){
        btnVisualizar.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }
    
}
